package com.prateek.instagram.repository;

import com.prateek.instagram.model.Comment;
import com.prateek.instagram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByPost(Post post);

    @Query("FROM Comment  c WHERE c.reply.id = :commentId")
    List<Comment> getAllByReplyId(Long commentId);
}
