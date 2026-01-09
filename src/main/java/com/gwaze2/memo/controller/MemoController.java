package com.gwaze2.memo.controller;

import com.gwaze2.memo.dto.CreateMemoResponse;
import com.gwaze2.memo.dto.CreateMemoResquest;
import com.gwaze2.memo.service.MemoService;
import com.gwaze2.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/memos")
    public ResponseEntity<CreateMemoResponse> create(
            @SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser,
            @RequestBody CreateMemoResquest resquest
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(memoService.save(sessionUser, resquest));
    }
}
