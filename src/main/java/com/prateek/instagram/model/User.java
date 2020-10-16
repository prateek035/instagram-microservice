package com.prateek.instagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40, unique = true)
    private String userName;

    private String password;

    @Column(length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @Column(columnDefinition = "boolean default false")
    private boolean isVerified;

    @Column(length = 6)
    private String gender;

    @CreationTimestamp
    private Timestamp createdAt;

    @Column(length = 10)
    @Pattern(regexp="(^$|[0-9]{10})")
    private String contactNo;

    @OneToMany( mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> posts;


}
