package com.kuldeep;

public class UserModel {
    int id;
    String name;
    int age;
    String course;

    public UserModel() {
        this.id = 1;
        this.name = "Kuldeep";
        this.age = 21;
        this.course = "B.Tech.";
    }

    public UserModel(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCourse() {
        return this.course;
    }

    public int getAge() {
        return this.age;
    }
}
