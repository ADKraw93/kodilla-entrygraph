package com.kodilla.entitygraph.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;

    @OneToMany(targetEntity = Person.class)
    private List<Person> persons = new ArrayList<>();

    public Subtask() {

    }

    public Subtask(Long id, String name, String status, List<Person> persons) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
