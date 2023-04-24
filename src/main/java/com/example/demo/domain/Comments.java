package com.example.demo.domain;

import com.example.demo.Dto.CommentsRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comments {

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

    //게시글
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //유저
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;

    //대댓글
    @OneToMany(mappedBy = "comments",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Replies> replies = new ArrayList<>();

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

    public List<Replies> getReplies() {
        return replies;
    }

    public Comments() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    public Comments(String username, String content) {
        this.username = username;
        this.content = content;
    }


    public Comments(CommentsRequestDto commentsRequestDto,String username,Post post){
        this.content = commentsRequestDto.getContent();
        this.username=username;
        this.post = post;
    }
}
