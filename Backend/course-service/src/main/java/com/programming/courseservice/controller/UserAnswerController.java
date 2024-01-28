package com.programming.courseservice.controller;

import com.main.progamming.common.response.DataResponse;
import com.programming.courseservice.domain.dto.UserAnswerDto;
import com.programming.courseservice.service.UserAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/user-answer")
public class UserAnswerController {
    private final UserAnswerService userAnswerService;

    @PostMapping("/upsert")
    public DataResponse<String> upsertUserAnswer(@RequestParam("user-quiz-id") String userQuizId,
                                                @RequestBody List<UserAnswerDto> userAnswerDtoList) {
        return userAnswerService.upsertUserAnswer(userAnswerDtoList, userQuizId);
    }
}
