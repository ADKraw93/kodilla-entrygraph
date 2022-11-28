package com.kodilla.entitygraph.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;

    @OneToMany(targetEntity = Person.class)
    private List<Person> persons = new ArrayList<>();

    @OneToMany(targetEntity = Subtask.class)
    private List<Subtask> subtasks = new ArrayList<>();

    public Task() {

    }

    public Task(Long id, String name, String status, List<Person> persons, List<Subtask> subtasks) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.persons = persons;
        this.subtasks = subtasks;
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

    public List<Subtask> getSubtasks() {
        return subtasks;
    }
}
