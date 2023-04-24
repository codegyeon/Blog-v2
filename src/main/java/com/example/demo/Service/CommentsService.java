package com.example.demo.Service;

import com.example.demo.Dto.CommentsRequestDto;
import com.example.demo.Repository.CommentsRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.domain.Comments;
import com.example.demo.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentsService {
    private final PostRepository postRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(PostRepository postRepository, CommentsRepository commentsRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
    }

    @Transactional
    public List<Comments> getComments(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new RuntimeException("post not found"));
        return post.getComments();
    }

    @Transactional
    public Comments writeComments(Long id, CommentsRequestDto commentsRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new RuntimeException("post not found"));
        Comments comments =
                new Comments(commentsRequestDto,userDetails.getUsername(),post);


        return commentsRepository.save(comments);
    }

    @Transactional
    public Comments modifyComments(Long id, CommentsRequestDto commentsRequestDto, UserDetailsImpl userDetails) {
        Comments comments = commentsRepository.findById(id).orElseThrow(
                ()->new RuntimeException());
        if (userDetails.getUsername() != comments.getUsername()){
            throw new IllegalArgumentException("작성자만이 수정할 수 있습니다.");
        }
        comments.setContent(commentsRequestDto.getContent());

        return commentsRepository.save(comments);
    }

    @Transactional
    public boolean deleteComments(Long id, UserDetailsImpl userDetails) {
        Comments comments = commentsRepository.findById(id).orElseThrow(
                ()->new RuntimeException());
        if (userDetails.getUsername() != comments.getUsername()){
            throw new IllegalArgumentException("작성자만이 삭제할 수 있습니다.");
        }
        commentsRepository.deleteById(id);
        return true;
    }



}
