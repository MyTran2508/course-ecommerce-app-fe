package com.programming.courseservice.utilities.schedule;

import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import com.programming.courseservice.repository.UserQuizRepository;
import com.programming.courseservice.service.UserAnswerService;

public class ScheduleSubmitQuiz implements Runnable {

    private final UserAnswerService userAnswerService;

    private final UserQuizRepository userQuizRepository;

    private final String userQuizId;

    public ScheduleSubmitQuiz(UserAnswerService userAnswerService, UserQuizRepository userQuizRepository, String userQuizId) {
        this.userAnswerService = userAnswerService;
        this.userQuizRepository = userQuizRepository;
        this.userQuizId = userQuizId;
    }

    @Override
    public void run() {
        System.out.println(userQuizId);
        System.out.println(userQuizRepository);
        UserQuiz userQuiz = userQuizRepository.findById(userQuizId).get();
        System.out.println(userQuiz);

        if (userQuiz.getIsCompleted()) {
            System.out.println("Quiz has been completed");
        } else {
            userAnswerService.evaluateQuiz(userQuizId);
        }
    }
}
