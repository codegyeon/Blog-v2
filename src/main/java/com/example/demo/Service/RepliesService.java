package com.example.demo.Service;

import com.example.demo.Dto.RepliesRequestDto;
import com.example.demo.Repository.CommentsRepository;
import com.example.demo.Repository.RepliesRepository;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.domain.Comments;
import com.example.demo.domain.Replies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RepliesService {
    private final CommentsRepository commentsRepository;
    private final RepliesRepository repliesRepository;

    public RepliesService(CommentsRepository commentsRepository, RepliesRepository repliesRepository) {
        this.commentsRepository = commentsRepository;
        this.repliesRepository = repliesRepository;
    }

    //대댓글 리스트 가져오기
    @Transactional
    public List<Replies> getReplies(Long id){
        Comments comments = commentsRepository.findById(id).orElseThrow(()->new RuntimeException());
        return comments.getReplies();
    }

    //대댓글 작성하기
    @Transactional
    public Replies writeReplies(Long id , UserDetailsImpl userDetails, RepliesRequestDto repliesRequestDto){
        Comments comments = commentsRepository.findById(id).orElseThrow(()->new RuntimeException());
        Replies replies = new Replies(userDetails.getUsername(),repliesRequestDto.getContent(),comments);

        return repliesRepository.save(replies);
    }

    //대댓글 수정하기
    @Transactional
    public Replies modifyReplies(Long id, RepliesRequestDto repliesRequestDto, UserDetailsImpl userDetails){
        Replies replies = repliesRepository.findById(id).orElseThrow(()->new RuntimeException());
        if (replies.getUsername() != userDetails.getUsername()){
            throw new RuntimeException("작성자만이 수정 가능합니다.");
        }
        replies.setContent(repliesRequestDto.getContent());
        return repliesRepository.save(replies);
    }

    @Transactional
    public boolean deleteReplies(Long id , UserDetailsImpl userDetails) {
        Replies replies = repliesRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (replies.getUsername() != userDetails.getUsername()) {
            throw new RuntimeException("작성자만이 삭제 가능합니다.");
        }
        repliesRepository.deleteById(id);
        return true;
    }
}
