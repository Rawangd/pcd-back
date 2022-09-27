package com.pcd.jwt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CenterCourses {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String courseName;
    private String centerName;
    @Email
    private String centerEmail;
    @Email
    private  String formerEmail;
    private int formerPhoneNumber;
    private String description;
    private double price;
    private String category;
    private String formerName;
    private String city;
    private  int phoneNumber;
    private  String address;
    private String date;

    @Lob
    private byte[] picture;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "CENTERCOURSE_USER",
            joinColumns = {
                    @JoinColumn(name = "CENTERCOURSE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "USER_ID")
            }
    )
    private Set<User> usersC =new HashSet<>();

       public CenterCourses(Long id,String formerEmail,String address,int formerPhoneNumber, String courseName, String centerName,String date, String centerEmail, String description, double price, String category, String formerName, String city, int phoneNumber, byte[] picture) {
        this.id = id;
        this.courseName = courseName;
        this.centerName = centerName;
        this.centerEmail = centerEmail;
        this.description = description;
        this.price = price;
        this.category = category;
        this.formerName = formerName;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.formerEmail=formerEmail;
        this.formerPhoneNumber=formerPhoneNumber;
        this.address=address;
        this.date=date;
    }

    public CenterCourses(){};
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterEmail() {
        return centerEmail;
    }

    public void setCenterEmail(String centerEmail) {
        this.centerEmail = centerEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Set<User> getUsersC() {
        return usersC;
    }

    public void setUsersC(Set<User> usersC) {
        this.usersC = usersC;
    }

    public String getFormerEmail() {
        return formerEmail;
    }

    public void setFormerEmail(String formerEmail) {
        this.formerEmail = formerEmail;
    }

    public int getFormerPhoneNumber() {
        return formerPhoneNumber;
    }

    public void setFormerPhoneNumber(int formerPhoneNumber) {
        this.formerPhoneNumber = formerPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
