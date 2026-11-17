package com.tourist.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tourists")
public class Tourist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String destination;
    @Column(nullable = false) private String email;
    private String phone;

    protected Tourist() {}
    public Tourist(String name, String destination, String email, String phone) {
        this.name = name; this.destination = destination; this.email = email; this.phone = phone;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDestination() { return destination; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public void update(String name, String destination, String email, String phone) {
        this.name = name; this.destination = destination; this.email = email; this.phone = phone;
    }
}
