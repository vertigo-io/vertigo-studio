# Gemini Coding Rules for Vertigo Studio Project

This document outlines the coding rules and guidelines that Gemini, the AI assistant, should adhere to when working on the Vertigo Studio project. These rules are designed to ensure consistency, maintainability, and adherence to existing project conventions.

## Core Mandates for Gemini

-   **Conventions:** Rigorously adhere to existing project conventions when reading or modifying code. Analyze surrounding code, tests, and configuration first.
-   **Libraries/Frameworks:** NEVER assume a library/framework is available or appropriate. Verify its established usage within the project (check imports, configuration files like 'package.json', 'Cargo.toml', 'requirements.txt', 'build.gradle', etc., or observe neighboring files) before employing it.
-   **Style & Structure:** Mimic the style (formatting, naming), structure, framework choices, typing, and architectural patterns of existing code in the project.
-   **Idiomatic Changes:** When editing, understand the local context (imports, functions/classes) to ensure your changes integrate naturally and idiomatically.
-   **Comments:** Add code comments sparingly. Focus on *why* something is done, especially for complex logic, rather than *what* is done. Only add high-value comments if necessary for clarity or if requested by the user. Do not edit comments that are separate from the code you are changing. *NEVER* talk to the user or describe your changes through comments.
-   **Proactiveness:** Fulfill the user's request thoroughly, including reasonable, directly implied follow-up actions.
-   **Confirm Ambiguity/Expansion:** Do not take significant actions beyond the clear scope of the request without confirming with the user. If asked *how* to do something, explain first, don't just do it.
-   **Path Construction:** Before using any file system tool (e.g., 'read_file' or 'write_file'), you must construct the full absolute path for the file_path argument. Always combine the absolute path of the project's root directory with the file's path relative to the root. For example, if the project root is `/path/to/project/` and the file is `foo/bar/baz.txt`, the final path you must use is `/path/to/project/foo/bar/baz.txt`. If the user provides a relative path, you must resolve it against the root directory to create an absolute path.
-   **Do Not revert changes:** Do not revert changes to the codebase unless asked to do so by the user. Only revert changes made by you if they have resulted in an error or if the user has explicitly asked you to revert the changes.

## How to Relaunch Gemini with these Rules

To ensure Gemini adheres to these coding rules for the Vertigo Studio project, you can provide this `CODING_RULES.md` file as part of the context when initiating a new session or when Gemini needs a reminder of the project's specific guidelines.

You can typically do this by:
1.  **Loading the file as context:** When starting a new Gemini session, ensure this `CODING_RULES.md` file is included in the initial context or prompt.
2.  **Referencing the file:** If Gemini seems to deviate from these rules during a session, you can explicitly refer to this file (e.g., "Please refer to `CODING_RULES.md` for project conventions.") to guide its behavior.

By following these guidelines, Gemini will be able to contribute more effectively and consistently to the Vertigo Studio project.
