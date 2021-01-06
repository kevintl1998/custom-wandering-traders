package me.t0c.cwt_v3.utils;

import java.util.regex.Pattern;

public enum StringParser {
    
    INTEGER("^-?\\d+$"),
    POS_INTEGER("^\\d+$"),
    POS_NONZERO_INTEGER("^[1-9](\\d+)?$"),
    DOUBLE("^-?(\\d+)?(\\.\\d+)?$"),
    NAMESPACE("^\\w{3,16}$"),
    YAML_FILE("^\\w{3,16}(\\.yml)$");

    StringParser(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    private Pattern pattern;

    public boolean isStringType(String value) {
        return pattern.matcher(value).find();
    }
}