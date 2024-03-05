package com.example.demo.enums;

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

    public String getIds(Matcher matcher){
        return matcher.group(1);
    }

    public String getValue(Matcher matcher){
        return matcher.group(2);
    }
}
