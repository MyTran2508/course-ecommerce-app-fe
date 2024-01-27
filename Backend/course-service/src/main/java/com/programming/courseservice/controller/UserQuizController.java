package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.UserQuizDto;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import com.programming.courseservice.service.UserQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses/user-quiz")
@RequiredArgsConstructor
public class UserQuizController extends BaseApiImpl<UserQuiz, UserQuizDto> {
    private final UserQuizService userQuizService;

    @Override
    protected BaseService<UserQuiz, UserQuizDto> getBaseService() {
        return userQuizService;
    }
}
