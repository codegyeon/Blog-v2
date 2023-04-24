package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// 테이블 설정
@Getter
@Entity
public class UserAccount {

    //기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 아이디
    @Column
    private String username;

    //유저 닉네임
    @Column
    private String nickname;

    //유저 패스워드
    @Column
    private String password;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //게시글

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    //댓글

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comments> comments = new ArrayList<>();

    //대댓글

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Replies> replies = new ArrayList<>();


    public UserAccount(String username, String nickname, String password, UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public UserAccount() {

    }


}
