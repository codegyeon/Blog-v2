package com.example.demo.Service;

import com.example.demo.Dto.PostRequestDto;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.UserDetailsImpl;
import com.example.demo.domain.Post;
import com.example.demo.domain.UserAccount;
import com.example.demo.domain.type.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Page<Post> getAllItems(Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return postRepository.findAll(pageable);
    }

    @Transactional
    public Post getDetailPost(Long id) {
         Post post = postRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
         return post;
    }

    @Transactional
    public Post createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();

        //로그인 한 유저의 이름을 넣을 postRequestDto.setName 이 필요.
        postRequestDto.setNickname(username);
        UserAccount user = userRepository.findByNickname(username).orElseThrow(
                ()->new IllegalArgumentException("UserName 이 존재하지 않습니다."));
        Post post = new Post(postRequestDto);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Transactional
    public Post modifyPost(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if (userDetails.getUsername() != post.getNickname()){
            throw new IllegalArgumentException("작성자만이 수정할 수 있습니다.");
        }
        post.setTitle(postRequestDto.getTitle());
        post.setContents(postRequestDto.getContents());
        return postRepository.save(post);
    }

    @Transactional
    public boolean deletePost(Long id, UserDetailsImpl userDetails) {
        String name = postRepository.findById(id).get().getNickname();
        if (userDetails.getUsername() != name){
            throw new IllegalArgumentException("작성자만이 삭제할 수 있습니다.");
        }
        postRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Page<Post> getPostsSearch(Integer pageNo, Integer pageSize, String name, String Type){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        SearchType searchType;
        if (Type.equals("title")){
          searchType = SearchType.TITLE;
        }  else {
            searchType = SearchType.NICKNAME;
        }

        return  switch (searchType) {
            case TITLE-> postRepository.findByTitleContaining(name, pageable);
            case NICKNAME -> postRepository.findByNicknameContaining(name,pageable);
        };
    }
}
