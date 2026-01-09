package com.gwaze2.user.service;

import com.gwaze2.user.dto.SigninUserRequest;
import com.gwaze2.user.dto.SigninUserResponse;
import com.gwaze2.user.dto.SignupUserRequest;
import com.gwaze2.user.dto.SignupUserResponse;
import com.gwaze2.user.entity.User;
import com.gwaze2.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public SignupUserResponse save(SignupUserRequest request) {
        User user = new User(
                request.getEmail(),
                request.getPassword()
        );
        User savedUser = userRepository.save(user);
        return new SignupUserResponse(
                savedUser.getId(),
                savedUser.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public SigninUserResponse signin(@Valid SigninUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("유효하지 않은 이메일입니다.")
        );
        if(!ObjectUtils.nullSafeEquals(user.getPassword(), request.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        return new SigninUserResponse(
                user.getId(),
                user.getEmail()
        );
    }
}
