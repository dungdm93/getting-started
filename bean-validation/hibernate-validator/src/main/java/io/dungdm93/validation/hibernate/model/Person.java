package io.dungdm93.validation.hibernate.model;

import javax.validation.constraints.NotNull;

public class Person {
    @NotNull
    public String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }
}
