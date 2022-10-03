package com.pmstudios.stronger.pojo;


import javax.validation.constraints.NotBlank;

public class Exercise {

    private int id;
    private String name;

    public Exercise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Exercise(Exercise exercise) {
        this.id = exercise.id;
        this.name = exercise.name;
    }

    public Exercise() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
