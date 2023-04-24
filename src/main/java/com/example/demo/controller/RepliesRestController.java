package com.example.demo.controller;

import com.example.demo.Dto.RepliesRequestDto;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.Service.RepliesService;
import com.example.demo.domain.Replies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RepliesRestController {

    private final RepliesService repliesService;

    @Autowired
    public RepliesRestController(RepliesService repliesService) {
        this.repliesService = repliesService;
    }


    //대댓글 불러오기
    @GetMapping("/api/blog/detail/{id}/replies")
    public List<Replies> getReplies(@PathVariable Long id){
        return repliesService.getReplies(id);
    }

    //대댓글 작성하기
    @PostMapping("/api/blog/detail/{id}/replies")
    public Replies writeReplies(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails ,@RequestBody RepliesRequestDto repliesRequestDto){
        return repliesService.writeReplies(id,userDetails,repliesRequestDto);
    }

    //대댓글 수정하기
    @PatchMapping("/api/blog/detail/{id}/replies")
    public Replies modifyReplies(@PathVariable Long id, @RequestBody RepliesRequestDto repliesRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return repliesService.modifyReplies(id,repliesRequestDto,userDetails);
    }

    //대댓글 삭제하기
    @DeleteMapping("/api/blog/detail/{id}/replies")
    public boolean deleteReplies(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return repliesService.deleteReplies(id,userDetails);
    }

}
