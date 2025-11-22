package org.ateam.ateam.domain.linker.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechStack {
	SWIFT("Swift"),
	KOTLIN("Kotlin"),
	JAVA("Java"),
	FLUTTER("Flutter"),
	REACT_NATIVE("React Native"),
	NODE_JS("Node.js"),
	PYTHON_DJANGO_FASTAPI("Python(Django/FastAPI)"),
	SPRING_JAVA("Spring(Java)");

	private final String title;
}
