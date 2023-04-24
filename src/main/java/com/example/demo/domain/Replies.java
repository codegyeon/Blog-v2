package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Replies {

    //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 댓글 쓴이
    @Column
    private String username;

    // 댓글 내용
    @Column
    private String content;

    //댓글
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comments comments;

    //유저
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;

    //생성일자
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt; // 생성일시


    //수정일자
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    public Replies() {
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Replies(String username, String content) {
        this.username = username;
        this.content = content;
    }
    public Replies(String username, String content, Comments comments) {
        this.username = username;
        this.content = content;
        this.comments = comments;
    }
}
