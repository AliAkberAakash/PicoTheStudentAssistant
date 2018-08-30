package com.example.cedwa.studentassistant.DatabaseFiles.ContactsOperations;

public class ContactStructure {

    private long id;
    private String imagePath;
    private String name;
    private String phone;
    private String email;
    private String contactType;

    public ContactStructure(long id, String imagePath, String name, String phone, String email, String contactType) {
        this.id = id;
        this.imagePath = imagePath;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.contactType = contactType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
}
