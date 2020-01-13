package com.badcompany.auction.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 32)
    private String username;
    @NotBlank
    @Size(max = 32)
    private String name;
    @NotBlank
    @Size(max = 32)
    private String surname;
    @NotBlank
    @Size(max = 128)
    private String password;
    @NotBlank
    @Email
    @Size(max = 256)
    private String email;

    private String address1;
    private String address2;
    private String country;
    private String city;
    private String state;
    private Long zipcode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(){
        super();
    }

    public User(Long id, String name, String surname, String password, String email){
        super();
        this.id=id;
        this.name=name;
        this.password = password;
        this.surname = surname;
        this.email = email;
    }

    public User(String name, String surname, String username, String password, String email){
        super();
        this.name=name;
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"username\":\"" + username + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"surname\":\"" + surname + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"address1\":\"" + address1 + '\"' +
                ", \"address2\":\"" + address2 + '\"' +
                ", \"country\":\"" + country + '\"' +
                ", \"city\":\"" + city + '\"' +
                ", \"state\":\"" + state + '\"' +
                ", \"zipcode\":" + zipcode +
                '}';
    }
}
