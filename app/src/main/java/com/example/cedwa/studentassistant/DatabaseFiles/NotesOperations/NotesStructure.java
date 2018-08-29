package com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations;

/*
 *Class for the basic structure of a Note
 */

public class NotesStructure {

    private long id;
    private String noteTitle;
    private String noteDescription;


    public NotesStructure(long id, String noteTitle, String noteDescription) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
