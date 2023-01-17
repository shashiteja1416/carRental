package com.carRental.User.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tbl_users")
@Getter
@Setter
public class UserDetails {
    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", block='" + block + '\''+
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "exception_seq_generator")
    @SequenceGenerator(name = "exception_seq_generator", sequenceName = "exception_seq", allocationSize=1)
    @Column(name = "userId",nullable = false, length = 20)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Boolean block;
}
