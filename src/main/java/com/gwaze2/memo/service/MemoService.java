package com.gwaze2.memo.service;

import com.gwaze2.memo.dto.CreateMemoResponse;
import com.gwaze2.memo.dto.CreateMemoResquest;
import com.gwaze2.memo.entity.Memo;
import com.gwaze2.memo.repository.MemoRepository;
import com.gwaze2.user.dto.SessionUser;
import com.gwaze2.user.entity.User;
import com.gwaze2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateMemoResponse save(SessionUser sessionUser, CreateMemoResquest resquest) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        Memo memo = new Memo(resquest.getText(), user);
        Memo savedMemo = memoRepository.save(memo);
        return new CreateMemoResponse(
                savedMemo.getId(),
                savedMemo.getText()
        );
    }
}
