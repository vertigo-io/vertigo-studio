package io.vertigo.shiny.models.data.board;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyBoardCard(
		UUID id,
		String title,
		String description
//		Instant createdAt,
//		Instant dueDate,
//		List<Label> labels,
//		List<Checklist> checklists,
//		List<Comment> comments,
//		List<Attachment> attachments
) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyBoardCard";
	}
}
