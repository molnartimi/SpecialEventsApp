package hu.molti.specialevents.entities;


import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class PersonEntity {
    @Getter
    private String id;
    @Getter
    @Setter
    private String name;

    public PersonEntity(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }
}
