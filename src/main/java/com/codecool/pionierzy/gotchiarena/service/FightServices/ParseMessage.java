package com.codecool.pionierzy.gotchiarena.service.FightServices;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ParseMessage {
    private String value;

    @JsonSetter("value")
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
