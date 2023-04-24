package com.example.demo.Repository;

import com.example.demo.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post , Long> {

    //닉네임을 기준으로 게시글 검색
    @Query("SELECT i FROM Post i WHERE i.nickname LIKE %:Nickname%")
    Page<Post> findByNicknameContaining(@Param("Nickname") String name, Pageable pageable);

    //제목을 기준으로 게시글 검색
    @Query("SELECT i FROM Post i WHERE i.title LIKE %:Title%")
    Page<Post> findByTitleContaining(@Param("Title") String name, Pageable pageable);

}
