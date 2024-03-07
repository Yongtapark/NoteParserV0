package com.example.demo.domain.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum NoteRegex {
    BARN(Pattern.compile("^(\\d+번축사)(,\\d+번축사)*$")),
    PEN(Pattern.compile("^(\\d+-\\d+)(,\\d+-\\d+)*$")),
    COW(Pattern.compile("^((\\d{4})(,\\d{4})*)$")),
    NOTE_FORM(Pattern.compile("\\[\\[([^\\]]+)\\]\\] (.*)"));

    private final Pattern pattern;

    NoteRegex(Pattern pattern) {
        this.pattern = pattern;
    }

    public Matcher extractString(String strPattern) {
        return getCompile().matcher(strPattern);
    }

    public Pattern getCompile() {
        return pattern;
    }

}
