package org.aleks4ay.developer.model;

public enum  TypeName {
    NEW ("Новый"),
    KB ("КБ"),
    FACTORY("ЦЕХ"),
    TECHNO ("Техн."),
    ABC ("ABC"),
    OTHER ("Прочее");

    private String name;

    TypeName(String name) {
        this.name = name;
    }
}
