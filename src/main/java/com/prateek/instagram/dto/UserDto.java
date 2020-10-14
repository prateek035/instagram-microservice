package com.prateek.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDto {

    private long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String gender;
    private String contactNo;
    private Boolean isVerified;

}
