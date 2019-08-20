package com.mallfe.item.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityMessage implements Serializable {
    private String id;
    private String name;
    private String spellName;
    private String fullName;
    private String sortLetters;
}
