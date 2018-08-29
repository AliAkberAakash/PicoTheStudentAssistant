package com.example.cedwa.studentassistant.DatabaseFiles.RoutineOperations;

public class RoutineStructure {

    private int id;
    private String start;
    private String subject;
    private String teacher;
    private int alarm;


    public RoutineStructure(int id, String start, String subject, String teacher, int alarm) {
        this.id = id;
        this.start = start;
        this.subject = subject;
        this.teacher=teacher;
        this.alarm = alarm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }
}
