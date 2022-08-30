package com.jungeeks.apple;

public enum AppleUserScope {
    EMAIL("email"),
    NAME("name");

    private final String literal;

    AppleUserScope(String literal)
    {
        this.literal = literal;
    }

    String getLiteral() {
        return literal;
    }
}
