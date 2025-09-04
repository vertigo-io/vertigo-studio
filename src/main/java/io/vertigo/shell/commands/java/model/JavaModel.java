package io.vertigo.shell.commands.java.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record JavaModel(List<JavaPackage> packages) {
	public JavaModel() {
		this(new ArrayList<>());
	}

	// Nested records
	public record JavaPackage(String name, List<JavaClass> classes) {
		public JavaPackage(String name) {
			this(name, new ArrayList<>());
		}
	}

	public record JavaClass(String name, String type, Set<String> modifiers, String superclass, List<String> implementedInterfaces, List<JavaMethod> methods, List<JavaField> fields, List<JavaImport> imports) {
		public JavaClass(String name, String type) {
			this(name, type, new HashSet<>(), null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		}
	}

	public record JavaMethod(String name, String returnType, List<JavaVar> parameters, Set<String> modifiers, List<JavaVar> localVariables) {
		public JavaMethod(String name, String returnType) {
			this(name, returnType, new ArrayList<>(), new HashSet<>(), new ArrayList<>());
		}
	}

	public record JavaField(String name, String type, Set<String> modifiers) {
		public JavaField(String name, String type) {
			this(name, type, new HashSet<>());
		}
	}

	public record JavaImport(String name) {
	}

	public record JavaVar(String name, String type) {
	}
}
