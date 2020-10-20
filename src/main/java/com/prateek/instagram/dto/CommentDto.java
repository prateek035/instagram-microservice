package com.prateek.instagram.dto;


import com.prateek.instagram.model.Comment;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentDto {

    private String description;
    private Long postId;
    private Long userId;
    private Long replyId;

}
