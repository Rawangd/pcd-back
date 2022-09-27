package com.pcd.jwt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "Courses")
public class Courses {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String CourseName;
    private String description;
    private double price;
    private String category;
    private String formerName;
    private String city;
    private  int phoneNumber;
    private boolean isFavorite;
    //@ElementCollection
    //private List<String> persons;
    private String user;
    @Email

    private  String formerEmail;
    @Lob
    private byte[] picture;
    private boolean isPresent;
    private boolean isConfirmed;
    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "COURSE_USER",
            joinColumns = {
                    @JoinColumn(name = "COURSES_ID")

            },
            inverseJoinColumns = {
                    @JoinColumn(name = "USER_ID"),

            }

    )
    private Set<User> users =new HashSet<>();


    @OneToMany(mappedBy = "courses")
    Set<CourseConfirmation> confirmations;
    public Courses(String courseName, String description,String user, double price, String category, byte[] picture,String formerName,String formerEmail) {
        CourseName = courseName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.picture = picture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Courses() {
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public String getFormerEmail() {
        return formerEmail;
    }

    public void setFormerEmail(String formerEmail) {
        this.formerEmail = formerEmail;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Set<CourseConfirmation> getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Set<CourseConfirmation> confirmations) {
        this.confirmations = confirmations;
    }
}
