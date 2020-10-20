package com.prateek.instagram.repository;

import com.prateek.instagram.model.FollowMap;
import com.prateek.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowMapRepository extends JpaRepository<FollowMap, Long> {
    List<FollowMap> findAllByUser(User user);
    List<FollowMap> findAllByFollower(User user);
    FollowMap findByUserAndFollower(User user, User follower);
    boolean existsByUserAndFollower(User user, User follower);

}
