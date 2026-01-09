package com.gwaze2.user.controller;

import com.gwaze2.user.dto.*;
import com.gwaze2.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponse> signup(
            @RequestBody SignupUserRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(
            @Valid @RequestBody SigninUserRequest request,
            HttpSession session
    ){
        SigninUserResponse result = userService.signin(request);
        SessionUser sesseionUser = new SessionUser(
                result.getId(),
                request.getEmail()
        );
        session.setAttribute("loginUser", sesseionUser);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ){
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
