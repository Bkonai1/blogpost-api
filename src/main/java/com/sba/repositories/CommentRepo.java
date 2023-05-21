package com.sba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sba.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
