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
public class PostDto {
    private long id;
    private String caption;
    private UserDto user;
    private Timestamp createdAt;
    private Timestamp editedAt;
}
