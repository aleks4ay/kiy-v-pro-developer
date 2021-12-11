package org.aleks4ay.developer.model;

public enum  TypeName {
    NEW ("Новый"),
    KB ("КБ"),
    CEH ("ЦЕХ"),
    TECHNO ("Техн."),
    OTHER ("Прочее");

    private String name;

    TypeName(String name) {
        this.name = name;
    }
}
