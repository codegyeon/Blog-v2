package com.example.demo.Service;

import com.example.demo.Dto.SignUpRequestDto;
import com.example.demo.Repository.UserRepository;
import com.example.demo.domain.UserAccount;
import com.example.demo.domain.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }





    public void registerUser(SignUpRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<UserAccount> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        String nickname = requestDto.getNickname();

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        //권한 설정
        UserRoleEnum role = UserRoleEnum.USER;



        UserAccount user = new UserAccount(username,nickname,password,role );
        userRepository.save(user);
    }
}
