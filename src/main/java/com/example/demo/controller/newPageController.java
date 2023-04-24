package com.example.demo.controller;

import com.example.demo.Repository.PostRepository;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.domain.Post;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/new")
@Controller
public class newPageController {
    private final PostRepository postRepository;

    public newPageController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    //메인페이지
    @GetMapping
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        //현재 인증된 사용자의 정보 추출후 전달.(로그인을 한 사용자의 유저네임 전달.)
        if(userDetails != null){
            model.addAttribute("username",userDetails.getUsername());
        }

        return "new/newindex";
    }

    //게시글 상세페이지
    @GetMapping("/postdetail/{id}")
    public String detail(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        if(userDetails != null){
            model.addAttribute("username",userDetails.getUsername());
        }
        return "new/newpostdetail";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    public String signup(){
        return "new/newsignup";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login(){
        return "new/newlogin";
    }


    //----------------------------------------- 로그인 이후 이용 가능

    //게시글 쓰기페이지
    @GetMapping("/postwrite")
    public String write(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        model.addAttribute("username",userDetails.getUsername());
        return "new/newWrite";
    }

    //게시글 수정페이지
    @GetMapping("/postmodify/{id}")
    public String modify(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, Model model){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if (userDetails.getUsername() != post.getNickname()){
            model.addAttribute("keys","자신의 게시글만 지울수 있습니다.");
            return "redirect:/postdetail/"+id ;
        }
        model.addAttribute("username",userDetails.getUsername());
        return "new/newpostmodify";
    }


}
