package com.example.demo.domain;

import com.example.demo.Dto.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
public class Post {


    //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //글 제목
    @Setter
    @Column
    private String title;

    //글쓴이
    @Setter
    @Column
    private String nickname;

    //글내용
    @Setter
    @Column
    private String contents;

    //생성일자
    @DateTimeFormat(pattern = "yyyy년MM월dd일HH시mm분ss초")
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt; // 생성일시


    //수정일자
    @DateTimeFormat(pattern = "yyyy년MM월dd일HH시mm분ss초")
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시


    //유저
    @JsonIgnore
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccount_id")
    private UserAccount user;

    //댓글

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    public Post() {
    }



    public Post(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.nickname = postRequestDto.getNickname();
        this.contents = postRequestDto.getContents();
    }
}
