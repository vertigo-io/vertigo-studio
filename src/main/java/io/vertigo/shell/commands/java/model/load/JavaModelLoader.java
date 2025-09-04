package io.vertigo.shell.commands.java.model.load;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import io.vertigo.shell.commands.java.model.JavaModel;
import io.vertigo.shell.commands.java.model.JavaModel.JavaPackage;

public final class JavaModelLoader {
	private final JavaParser javaParser;

	JavaModelLoader() {
		ParserConfiguration configuration = new ParserConfiguration()
				.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);

		javaParser = new JavaParser(configuration);
	}

	JavaModel loadModel(Path projectRoot) throws IOException {
		JavaModel javaModel = new JavaModel();
		Map<String, JavaPackage> packageMap = new HashMap<>();

		try (Stream<Path> paths = Files.walk(projectRoot)) {
			paths.filter(Files::isRegularFile)
					.filter(path -> path.toString().endsWith(".java"))
					.forEach(javaFile -> {
						try {
							final Optional<CompilationUnit> cuOpt = javaParser.parse(javaFile).getResult();
							if (cuOpt.isPresent()) {
								final String packageName = cuOpt.get().getPackageDeclaration()
										.map(pd -> pd.getName().asString())
										.orElse("");
								JavaPackage currentPackage = packageMap.computeIfAbsent(packageName, JavaModel.JavaPackage::new);
								// Add class to the package's list of classes
								cuOpt.get().accept(new ClassVisitor(currentPackage), null);
							}

						} catch (IOException e) {
							System.err.println("Error parsing file " + javaFile + ": " + e.getMessage());
						}
					});
		}
		// After processing all files, add all collected packages to the JavaModel
		javaModel.packages().addAll(packageMap.values());
		return javaModel;
	}

	private static class ClassVisitor extends VoidVisitorAdapter<Void> {
		private final JavaModel.JavaPackage currentPackage;

		public ClassVisitor(JavaModel.JavaPackage currentPackage) {
			this.currentPackage = currentPackage;
		}

		@Override
		public void visit(ClassOrInterfaceDeclaration n, Void arg) {
			String type = n.isInterface() ? "interface" : "class";
			Set<String> modifiers = n.getModifiers().stream().map(Modifier::getKeyword).map(Modifier.Keyword::asString).collect(Collectors.toSet());
			String superclass = n.getExtendedTypes().stream().findFirst().map(t -> t.getNameAsString()).orElse(null);
			List<String> implementedInterfaces = n.getImplementedTypes().stream().map(t -> t.getNameAsString()).collect(Collectors.toList());
			List<JavaModel.JavaMethod> methods = new ArrayList<>();
			List<JavaModel.JavaField> fields = new ArrayList<>();
			List<JavaModel.JavaImport> imports = new ArrayList<>();

			// Visit imports
			n.findCompilationUnit().ifPresent(cu -> {
				cu.getImports().forEach(imp -> imports.add(new JavaModel.JavaImport(imp.getNameAsString())));
			});

			// Visit fields
			n.getFields().forEach(field -> {
				field.getVariables().forEach(var -> {
					Set<String> fieldModifiers = field.getModifiers().stream().map(Modifier::getKeyword).map(Modifier.Keyword::asString).collect(Collectors.toSet());
					fields.add(new JavaModel.JavaField(var.getNameAsString(), var.getTypeAsString(), fieldModifiers));
				});
			});

			// Visit methods
			n.getMethods().forEach(method -> {
				Set<String> methodModifiers = method.getModifiers().stream().map(Modifier::getKeyword).map(Modifier.Keyword::asString).collect(Collectors.toSet());
				List<JavaModel.JavaVar> parameters = method.getParameters().stream().map(param -> new JavaModel.JavaVar(param.getNameAsString(), param.getTypeAsString())).collect(Collectors.toList());
				List<JavaModel.JavaVar> localVariables = new ArrayList<>();

				// Visit local variables within method body
				method.getBody().ifPresent(body -> {
					body.findAll(VariableDeclarationExpr.class).forEach(varDecl -> {
						varDecl.getVariables().forEach(var -> {
							localVariables.add(new JavaModel.JavaVar(var.getNameAsString(), var.getTypeAsString()));
						});
					});
				});
				methods.add(new JavaModel.JavaMethod(method.getNameAsString(), method.getTypeAsString(), parameters, methodModifiers, localVariables));
			});

			JavaModel.JavaClass javaClass = new JavaModel.JavaClass(n.getNameAsString(), type, modifiers, superclass, implementedInterfaces, methods, fields, imports);
			currentPackage.classes().add(javaClass); // Add to the package's list of classes

			super.visit(n, arg);
		}

		@Override
		public void visit(EnumDeclaration n, Void arg) {
			Set<String> modifiers = n.getModifiers().stream().map(Modifier::getKeyword).map(Modifier.Keyword::asString).collect(Collectors.toSet());
			List<JavaModel.JavaMethod> methods = new ArrayList<>();
			List<JavaModel.JavaField> fields = new ArrayList<>();
			List<JavaModel.JavaImport> imports = new ArrayList<>();

			// Visit imports (enums can have imports)
			n.findCompilationUnit().ifPresent(cu -> {
				cu.getImports().forEach(imp -> imports.add(new JavaModel.JavaImport(imp.getNameAsString())));
			});

			// Visit fields (enum constants are fields)
			n.getEntries().forEach(entry -> {
				fields.add(new JavaModel.JavaField(entry.getNameAsString(), n.getNameAsString(), new HashSet<>())); // Type is the enum itself, no specific modifiers for enum constants
			});

			// Visit methods (enums can have methods)
			n.getMethods().forEach(method -> {
				Set<String> methodModifiers = method.getModifiers().stream().map(Modifier::getKeyword).map(Modifier.Keyword::asString).collect(Collectors.toSet());
				List<JavaModel.JavaVar> parameters = method.getParameters().stream().map(param -> new JavaModel.JavaVar(param.getNameAsString(), param.getTypeAsString())).collect(Collectors.toList());
				List<JavaModel.JavaVar> localVariables = new ArrayList<>(); // Enums typically don't have local vars in their methods in the same way as classes

				method.getBody().ifPresent(body -> {
					body.findAll(VariableDeclarationExpr.class).forEach(varDecl -> {
						varDecl.getVariables().forEach(var -> {
							localVariables.add(new JavaModel.JavaVar(var.getNameAsString(), var.getTypeAsString()));
						});
					});
				});
				methods.add(new JavaModel.JavaMethod(method.getNameAsString(), method.getTypeAsString(), parameters, methodModifiers, localVariables));
			});

			JavaModel.JavaClass javaClass = new JavaModel.JavaClass(n.getNameAsString(), "enum", modifiers, null, new ArrayList<>(), methods, fields, imports);
			currentPackage.classes().add(javaClass); // Add to the package's list of classes

			super.visit(n, arg);
		}
	}
}
