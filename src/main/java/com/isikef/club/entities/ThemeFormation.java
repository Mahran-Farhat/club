package com.isikef.club.entities;

public enum ThemeFormation {
    JAVA("Java"),
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    CSHARP("C#"),
    RUBY("Ruby"),
    PHP("PHP"),
    SWIFT("Swift"),
    KOTLIN("Kotlin"),
    GO("Go"),
    RUST("Rust"),
    HTML_CSS("HTML/CSS"),
    SQL("SQL"),
    TYPESCRIPT("TypeScript"),
    CPLUSPLUS("C++"),
    C("C");

    private final String displayName;

    ThemeFormation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
