package edu.grade.model;

public class CreateUserRequest {

    private String email;
    private String password;
    private String name;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CreateUserRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateUserRequest withPassword(String password) {
        this.password = password;
        return this;
    }

    public CreateUserRequest withName(String name) {
        this.name = name;
        return this;
    }
}
