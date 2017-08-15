package ua.org.ubts.applicationssystem.dto;

import ua.org.ubts.applicationssystem.entity.Program;

public class StudentListItem {

    private Integer id;

    private String name;

    private Program program;

    public StudentListItem(Integer id, String name, Program program) {
        this.id = id;
        this.name = name;
        this.program = program;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
