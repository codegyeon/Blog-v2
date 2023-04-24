package com.example.demo.controller;


import com.example.demo.Dto.PostRequestDto;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.Service.PostService;
import com.example.demo.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
public class PostRestController {
    private final PostService postService;

    @Autowired
    public PostRestController( PostService postService){
        this.postService = postService;
    }


    //게시글 목록
    @GetMapping("/api/postlist")
    public ResponseEntity<Page<Post>> getPost(@RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Post> items = postService.getAllItems(pageNo, pageSize);
        return ResponseEntity.ok(items);
    }

    //게시글 상세페이지
    @GetMapping("/api/blog/detail/{id}")
    public Post getDetailPost(@PathVariable Long id){
        return postService.getDetailPost(id);
    }

    //게시글 작성
    @PostMapping("/api/blog/write")
    public Post createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(postRequestDto,userDetails);
    }

    //게시글 수정
    @PatchMapping("/api/blog/modify/{id}")
    public Post modifyPost (@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return  postService.modifyPost(id,postRequestDto,userDetails);
    }
    //게시글 삭제
    @DeleteMapping("/api/blog/delete/{id}")
    public boolean deletePost (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id,userDetails);
    }

    //게시글 검색
    @GetMapping("/api/postlist/search")
    public ResponseEntity<Page<Post>> getPostsSearch(@RequestParam("p") String query,@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(name = "category", required = false) String category,
                                                     @RequestParam(name = "keyword", required = false) String keyword){

            Page<Post> items = postService.getPostsSearch(pageNo,pageSize,query, category);



        return ResponseEntity.ok(items);
    }
}
