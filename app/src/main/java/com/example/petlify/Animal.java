package com.example.petlify;

public class Animal {

    String name,age,color,breed,type,gender,image,email;

    public Animal(String name, String age, String color, String breed, String type, String gender,String image,String email) {
        this.name = name;
        this.age = age;
        this.color = color;
        this.breed = breed;
        this.type = type;
        this.gender = gender;
        this.email=email;
        this.image=image;
    }
    public Animal(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gen) {
        this.gender = gen;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}