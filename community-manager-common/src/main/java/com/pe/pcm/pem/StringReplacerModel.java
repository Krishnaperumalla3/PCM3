package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StringReplacerModel implements Serializable {

    private String content;
    private String regex;
    private String replacement;

    public String getContent() {
        return content;
    }

    public StringReplacerModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getRegex() {
        return regex;
    }

    public StringReplacerModel setRegex(String regex) {
        this.regex = regex;
        return this;
    }

    public String getReplacement() {
        return replacement;
    }

    public StringReplacerModel setReplacement(String replacement) {
        this.replacement = replacement;
        return this;
    }
}
