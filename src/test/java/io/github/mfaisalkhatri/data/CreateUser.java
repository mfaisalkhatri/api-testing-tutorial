package io.github.mfaisalkhatri.data;

public class CreateUser {

    private String name;
    private String job;

    public String getJob() {
        return job;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public CreateUser(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
