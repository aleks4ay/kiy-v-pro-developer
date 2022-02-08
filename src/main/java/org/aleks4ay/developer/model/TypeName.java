package org.aleks4ay.developer.model;

public enum  TypeName {
    NEW ("Новый"),
    KB ("КБ"),
    FACTORY("ЦЕХ"),
    TECHNO ("Техн."),
    ABC ("ABC"),
    OTHER ("Прочее");

    private final String name;

    TypeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
