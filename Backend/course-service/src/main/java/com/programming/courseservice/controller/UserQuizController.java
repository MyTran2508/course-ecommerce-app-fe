package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.UserQuizDto;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import com.programming.courseservice.service.UserQuizService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Override
    @ShowOpenAPI
    public DataResponse<String> add(UserQuizDto objectDTO) {
        return super.add(objectDTO);
    }

    @ShowOpenAPI
    @GetMapping("/get")
    public DataResponse<UserQuizDto> getByUserIdAndExQuizIdAndAttemptNumber(
            @RequestParam String userId,
            @RequestParam String exQuizId,
            @RequestParam Integer attemptNumber
    ) {
        return userQuizService.getByUserIdAndExQuizIdAndAttemptNumber(userId, exQuizId, attemptNumber);
    }

    @ShowOpenAPI
    @GetMapping("/get-by-user-id-and-ex-quiz-id")
    public ListResponse<UserQuizDto> getByUserIdAndExQuizId(
            @RequestParam String userId,
            @RequestParam String exQuizId
    ) {
        return userQuizService.getByUserIdAndExQuizId(userId, exQuizId);
    }

//    @ShowOpenAPI
//    @GetMapping("/is-complete")
//    public DataResponse<Boolean> isCompleteQuiz(@RequestParam String userId, @RequestParam String exQuizId) {
//        return userQuizService.isCompleteQuiz(userId, exQuizId);
//    }
}
