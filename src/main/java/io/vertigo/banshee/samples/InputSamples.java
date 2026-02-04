package io.vertigo.banshee.samples;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.input.form.ShinyFormField;
import io.vertigo.shiny.models.input.form.ShinyFormFieldType;
import io.vertigo.shiny.models.input.form.ShinyFormFieldValidator;
import io.vertigo.shiny.models.input.form.ShinyFormOption;
import io.vertigo.shiny.models.input.toggle.ShinyToggleType;

final class InputSamples {
	static ShinyModel autocomplete() {
		return Shiny.autocomplete()
				.withLabel("Select a country")
				.addOption("France")
				.addOption("Germany")
				.addOption("Spain")
				.addOption("Italy")
				.withValue("France")
				.withPlaceholder("Start typing...")
				.build();
	}

	static ShinyModel codeeditor() {
		return Shiny.codeEditor()
				.withLanguage("javascript")
				.withContent("function hello() {\n  console.log('Hello, World!');\n}")
				.build();
	}

	static ShinyModel datepicker() {
		return Shiny.datePicker().withLabel("Select a date")
				.withValue("2025-11-13").isRequired().build();
	}

	static ShinyModel fileupload() {
		return Shiny.fileUpload().withLabel("Upload a file")
				.isMultiple().withAccept("image/*").build();
	}

	static ShinyModel formSample1() {
		return Shiny.form()
				.withTitle("Person Details")
				.addSection("Personal Info", List.of(
						new ShinyFormField("firstName", "First Name", ShinyFormFieldType.STRING, "John", true, "Enter first name", "", null, null, null, true),
						new ShinyFormField("lastName", "Last Name", ShinyFormFieldType.STRING, "Doe", true, "Enter last name", "", null, null, null, true),
						new ShinyFormField("age", "Age", ShinyFormFieldType.NUMBER, 30, false, "", "", null, null, new ShinyFormFieldValidator(null, null, null, 0, 120), true),
						new ShinyFormField("birthDate", "Birth Date", ShinyFormFieldType.DATE, "1990-01-01", false, "", "", null, null, null, true),
						new ShinyFormField("isActive", "Is Active", ShinyFormFieldType.BOOLEAN, true, false, "", "", null, null, null, false)), true, true)
				.addSection("Contact Info", List.of(
						new ShinyFormField("email", "Email", ShinyFormFieldType.STRING, "john.doe@example.com", true, "", "", null, null, new ShinyFormFieldValidator("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", null, null, null, null), true),
						new ShinyFormField("phone", "Phone", ShinyFormFieldType.STRING, "+1-555-123-4567", false, "", "", null, null, new ShinyFormFieldValidator("^\\+?[0-9]{1,3}?[ -]?[0-9]{3}?[ -]?[0-9]{3}?[ -]?[0-9]{4}$", null, null, null, null), true)), false, true)
				.build();
	}

	static ShinyModel formSample2() {
		return Shiny.form()
				.withTitle("Product Details")
				.addSection("Product Info", List.of(
						new ShinyFormField("productName", "Product Name", ShinyFormFieldType.STRING, "Smartphone X", true, "", "", null, null, null, false),
						new ShinyFormField("price", "Price", ShinyFormFieldType.NUMBER, 799.99, true, "", "", null, null, new ShinyFormFieldValidator(null, null, null, 0, 10000), false),
						new ShinyFormField("description", "Description", ShinyFormFieldType.TEXTAREA, "Latest model with advanced features.", false, "", "", null, null, null, false),
						new ShinyFormField("imageUrl", "Image", ShinyFormFieldType.IMAGE, "https://picsum.photos/id/237/200/300", false, "", "", null, null, null, false),
						new ShinyFormField("inStock", "In Stock", ShinyFormFieldType.BOOLEAN, true, false, "", "", null, null, null, false),
						new ShinyFormField("category", "Category", ShinyFormFieldType.SELECT, "Electronics", true, "", "", null, List.of(
								new ShinyFormOption("Electronics", "Electronics"),
								new ShinyFormOption("Books", "Books"),
								new ShinyFormOption("Home", "Home")), null, false)))
				.build();
	}

	static ShinyModel formSample3() {
		return Shiny.form()
				.withTitle("Photo Metadata")
				.addSection("File Info", List.of(
						new ShinyFormField("fileName", "File Name", ShinyFormFieldType.STRING, "IMG_001.jpg", true, "", "", null, null, null, true),
						new ShinyFormField("fileSize", "File Size (KB)", ShinyFormFieldType.NUMBER, 2048, false, "", "", null, null, null, true),
						new ShinyFormField("dateTaken", "Date Taken", ShinyFormFieldType.DATE, "2023-04-15", false, "", "", null, null, null, true),
						new ShinyFormField("isPublic", "Public", ShinyFormFieldType.BOOLEAN, false, false, "", "", null, null, null, false)))
				.addSection("Location", List.of(
						new ShinyFormField("latitude", "Latitude", ShinyFormFieldType.NUMBER, 34.0522, false, "", "", null, null, null, true),
						new ShinyFormField("longitude", "Longitude", ShinyFormFieldType.NUMBER, -118.2437, false, "", "", null, null, null, true)))
				.build();
	}

	static ShinyModel formSample4() {
		return Shiny.form()
				.withTitle("Critique de film")
				.addSection("Détails du film", List.of(
						new ShinyFormField("movie", "Film", ShinyFormFieldType.SELECT, "LOTR1", true, "", "", null, List.of(
								new ShinyFormOption("Star Wars", "Star Wars"),
								new ShinyFormOption("LOTR1", "Le Seigneur des Anneaux : La Communauté de l'anneau"),
								new ShinyFormOption("LOTR2", "Le Seigneur des Anneaux : Les Deux Tours"),
								new ShinyFormOption("LOTR3", "Le Seigneur des Anneaux : Le Retour du roi")), null, false),
						new ShinyFormField("rating", "Note", ShinyFormFieldType.NUMBER, 3, true, "", "Note sur 5", null, null, null, false),
						new ShinyFormField("review", "Critique", ShinyFormFieldType.TEXTAREA, "Un film incroyable !", false, "Écrivez votre critique ici", "", null, null, null, false)), true, false)
				.build();
	}

	static ShinyModel formSample5() {
		return Shiny.form()
				.withTitle("Commande de produit")
				.addSection("Détails du produit", List.of(
						new ShinyFormField("product", "Produit", ShinyFormFieldType.STRING, "T-shirt Vertigo", true, "", "", null, null, null, true),
						new ShinyFormField("size", "Taille", ShinyFormFieldType.RADIO, "M", true, "", "", null, List.of(
								new ShinyFormOption("S", "Small"),
								new ShinyFormOption("M", "Medium"),
								new ShinyFormOption("L", "Large")), null, false),
						new ShinyFormField("gift", "Emballage cadeau", ShinyFormFieldType.CHECKBOX_GROUP, true, false, "", "", null, null, null, false)), true, false)
				.build();
	}

	static ShinyModel formSample6() {
		return Shiny.form()
				.withTitle("Ajout de film")
				.addSection("Informations sur le film", List.of(
						new ShinyFormField("title", "Titre", ShinyFormFieldType.STRING, "Inception", true, "Titre du film", "", null, null, null, false),
						new ShinyFormField("genres", "Genres", ShinyFormFieldType.CHECKBOX_GROUP, List.of("SF", "Action"), true, "", "", null, List.of(
								new ShinyFormOption("SF", "Science-Fiction"),
								new ShinyFormOption("Action", "Action"),
								new ShinyFormOption("Drama", "Drame"),
								new ShinyFormOption("Comedy", "Comédie")), null, false)),
						true, false)
				.build();

	}

	static ShinyModel multiselection() {
		return Shiny.multiSelection()
				.withTitle("Select your favorite fruits")
				.addOption("Apple")
				.addOption("Banana")
				.addOption("Orange")
				.addOption("Grape")
				.build();
	}

	static ShinyModel rangeslider() {
		return Shiny.rangeSlider()
				.withLabel("Price Range")
				.withMin(0)
				.withMax(1000)
				.withStep(10)
				.withValue(200, 800)
				.withColor("green")
				.withThumbLabel(true)
				.build();
	}

	static ShinyModel slider() {
		return Shiny.slider()
				.withLabel("Volume")
				.withMin(0)
				.withMax(100)
				.withStep(1)
				.withValue(45)
				.withColor("orange")
				.withThumbLabel(true)
				.build();
	}

	static ShinyModel inputText() {
		return Shiny.inputText()
				.withLabel("Your Name")
				.withDefaultValue("John Doe")
				.build();
	}

	static ShinyModel toggle() {
		return Shiny.toggle()
				.withLabel("Enable Feature")
				.withValue(true)
				.withType(ShinyToggleType.SWITCH)
				.withOnText("Active")
				.withOffText("Inactive")
				.withShowText(true)
				.build();
	}
}
