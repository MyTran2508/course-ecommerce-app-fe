import { useLazyGetExQuizByIdQuery } from "@/redux/services/quizApi";
import { ExQuiz, Question, UserAnswer, UserQuiz } from "@/types/section.type";
import {
  Action,
  DEFAULT_PAGE_SIZE,
  QuizType,
  StatusCode,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import React, { Fragment, useEffect, useState } from "react";
import { CiClock2 } from "react-icons/ci";
import QuestionComponent from "./Question";
import { UserAnswerRequest } from "@/types/request.type";
import { v4 as uuidv4 } from "uuid";
import { get, set } from "lodash";
import { useUserQuizHooks } from "@/redux/hooks/courseHooks/userQuizHooks";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import {
  convertLongToTime,
  convertMillisToDateTime,
  extractId,
  formatTime,
  getCurrentTimeInMillisecondsFromAPI,
} from "@/utils/function";
import Swal from "sweetalert2";
import {
  useLazyGetAllUserQuizByUserIdAndQuizIdQuery,
  useLazyGetUserQuizByUserIdAndQuizIdQuery,
} from "@/redux/services/userQuizApi";
import { deleteUserQuiz, setUserQuiz } from "@/redux/features/quizSlice";
import showToast from "@/utils/showToast";
import CompleteQuiz from "./CompleteQuiz";
import { describe } from "node:test";

interface AnswerProps {
  exQuiz: ExQuiz;
  setQuizIsCompleted?: (isCompleted: boolean) => void;
}

const Answer = (props: AnswerProps) => {
  const { exQuiz, setQuizIsCompleted } = props;
  const dispatch = useAppDispatch();
  const quizState = useAppSelector(
    (state) => state.persistedReducer.quizReducer.quiz
  )?.find((q) => q.exQuizId === exQuiz?.id);
  const [getUserAnswerByUserIdAndExQuizId, { data: userAnswersData }] =
    useLazyGetUserQuizByUserIdAndQuizIdQuery();

  const [userAnswers, setUserAnswers] = useState<UserAnswer[]>([]);
  const [questionIndex, setQuestionIndex] = useState<number>(0);
  const [isStartQuiz, setStartQuiz] = useState<boolean>(false);
  const [userAnswer, setUserAnswer] = useState<UserAnswerRequest>();
  const [userQuizId, setUserQuizId] = useState<string>(
    (quizState as any)?.userQuizId || ""
  );
  const [listUserQuizHistory, setListUserQuizHistory] = useState<UserQuiz[]>(
    []
  );
  const [isShowResult, setIsShowResult] = useState<boolean>(false);
  const [timeLeft, setTimeLeft] = useState((exQuiz?.limitTime as number) / 1000);
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.id
  );
  const { handleAddUserQuiz, handleUpsertUserQuiz } = useUserQuizHooks();
  const [getAllUserQuizByUserIdAndQuizId, { data: userQuizData }] =
    useLazyGetAllUserQuizByUserIdAndQuizIdQuery();

  const percentageCompleted =
    (userAnswers.filter(
      (userAnswer) => (userAnswer.currentAnswer?.length as number) > 0
    ).length /
      (exQuiz?.totalQuestion as number)) *
    100;

  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn bg-blue-700 text-white px-4 py-2 rounded-md mx-2",
      cancelButton: "btn bg-red-700 text-white px-4 py-2 rounded-md",
    },
    buttonsStyling: false,
  });

  const handleChangeQuestion = (action: Action) => {
    if (action === Action.NEXT) {
      setQuestionIndex(questionIndex + 1);
    } else if (action === Action.PREVIOUS) {
      setQuestionIndex(questionIndex - 1);
    }
  };

  const handleStartQuiz = async () => {
    if (
      (listUserQuizHistory.length as number) <
      (exQuiz?.maxAttemptNumber as number)
    ) {
      const userQuiz = await handleAddUserQuiz({
        userId: userId as string,
        startTime: await getCurrentTimeInMillisecondsFromAPI(),
        limitTime: exQuiz?.limitTime,
        exQuizId: exQuiz?.id as string,
      });
      setUserQuizId((userQuiz as UserQuiz).id as string);
      setUserAnswers((userQuiz as UserQuiz).userAnswers as UserAnswer[]);
      dispatch(
        setUserQuiz({
          exQuizId: exQuiz?.id as string,
          userQuizId: (userQuiz as UserQuiz).id as string,
          expiryTime: exQuiz?.limitTime as number,
          attemptNumber: (userQuiz as UserQuiz).attemptNumber as number,
        })
      );
      setStartQuiz(true);
    }
  };

  const handleSubmit = () => {
    swalWithBootstrapButtons
      .fire({
        title: "Are you sure?",
        // text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, submit it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true,
      })
      .then((result) => {
        if (result.isConfirmed) {
          swalWithBootstrapButtons.fire({
            title: "Submit!",
            icon: "success",
          });
          dispatch(deleteUserQuiz(exQuiz?.id as string));
          handleUpsertUserQuiz(userQuizId, true, userAnswers);
          setIsShowResult(true);
          setStartQuiz(false);
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          swalWithBootstrapButtons.fire({
            title: "Cancelled",
            icon: "info",
          });
        }
      });
  };

  useEffect(() => {
    if (quizState) {
      setUserQuizId(quizState.userQuizId);
      setStartQuiz(true);
      getUserAnswerByUserIdAndExQuizId({
        userId: userId as string,
        exQuizId: exQuiz?.id as string,
        attemptNumber: quizState?.attemptNumber as number,
      });
    } else {
      setUserQuizId("");
      setStartQuiz(false);
    }
    getAllUserQuizByUserIdAndQuizId({
      userId: userId as string,
      exQuizId: exQuiz?.id as string,
    });
    setUserAnswers([]);
    setListUserQuizHistory([]);
    setQuestionIndex(0);
  }, [quizState, exQuiz?.id]);

  useEffect(() => {
    if (
      !timeLeft ||
      (userQuizId?.length === 0 && !isStartQuiz) ||
      Math.round(timeLeft) === 0
    ) {
      return;
    }
    if (timeLeft < 2) {
      dispatch(deleteUserQuiz(exQuiz?.id as string));
      handleUpsertUserQuiz(userQuizId, true, userAnswers);
      showToast(ToastStatus.SUCCESS, ToastMessage.SUBMIT_SUCCESS);
    }
    const intervalId = setInterval(() => {
      setTimeLeft(timeLeft - 1);
    }, 1000);
    return () => clearInterval(intervalId);
  }, [timeLeft]);

  useEffect(() => {
    if (userAnswersData?.statusCode === StatusCode.REQUEST_SUCCESS) {
      const fetchData = async () => {
        const listUserAnswer = (userAnswersData.data as UserQuiz)
          .userAnswers as UserAnswer[];
        if (listUserAnswer.length !== 0) {
          setUserAnswers(
            (userAnswersData.data as UserQuiz).userAnswers as UserAnswer[]
          );
        }
        const startTime = (userAnswersData.data as UserQuiz).startTime;
        const limitTime = exQuiz?.limitTime;
        if (startTime && limitTime) {
          const timeQuiz =
            startTime +
            limitTime -
            (await getCurrentTimeInMillisecondsFromAPI());
          if (timeQuiz > 0) {
            setTimeLeft(timeQuiz / 1000);
          } else {
            setTimeLeft(0);
            setUserAnswers([]);
            dispatch(deleteUserQuiz(exQuiz?.id as string));
          }
        }
      };

      fetchData();
    }
  }, [userAnswersData]);

  useEffect(() => {
    if (userAnswer) {
      const updateUserAnswers = set(
        [...userAnswers],
        questionIndex,
        userAnswer
      );
      handleUpsertUserQuiz(userQuizId, false, updateUserAnswers);
      setUserAnswers(updateUserAnswers);
    }
  }, [userAnswer]);

  useEffect(() => {
    if (userQuizData?.statusCode === StatusCode.REQUEST_SUCCESS) {
      const listUserQuizHistory = userQuizData.data as UserQuiz[];
      listUserQuizHistory.forEach((userQuiz) => {
        if (userQuiz.isCompleted && setQuizIsCompleted) {
          setQuizIsCompleted(true);
          return;
        }
      });
      setListUserQuizHistory(listUserQuizHistory);
    }
  }, [userQuizData]);

  return (
    <div className="mx-20 xs:mx-[20px]">
      {!isStartQuiz && !isShowResult && (
        <Fragment>
          <div className="flex items-center justify-end gap-3 xs:flex-col">
            <p>
              {" "}
              <strong>Sô điểm cần đạt:</strong> {exQuiz?.requiredScore}
            </p>
            <p>
              <strong>Sô lần làm tối đa:</strong> {exQuiz?.maxAttemptNumber}
            </p>
            <p>
              <strong> Thời gian: </strong>

              {convertLongToTime(
                (exQuiz?.limitTime as number) / 1000,
                true,
                true
              )}
            </p>
            <p>
              {" "}
              <strong>Số lượng câu hỏi:</strong> {exQuiz?.totalQuestion}
            </p>
            {(listUserQuizHistory.length as number) <
              (exQuiz?.maxAttemptNumber as number) && (
              <button
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                onClick={() => handleStartQuiz()}
              >
                Start
              </button>
            )}
          </div>
          <div>
            {listUserQuizHistory.length > 0 && (
              <div className="p-5 ">
                <h2 className="text-2xl font-bold mb-5">Lịch sử làm bài</h2>
                <div className="flex gap-3 flex-wrap">
                  {listUserQuizHistory?.map((userQuiz) => (
                    <div
                      key={uuidv4()}
                      className="bg-gray-300 p-5 rounded-md m-2 flex flex-col hover:scale-110 duration-300"
                    >
                      <p className="mb-2 font-semibold">
                        Lần: {userQuiz.attemptNumber}
                      </p>
                      <p className="mb-2">
                        Thời gian:{" "}
                        {convertMillisToDateTime(userQuiz.startTime as number)}
                      </p>
                      <p className="mb-2">Điểm số: {userQuiz.score}</p>

                      <button
                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-2 self-start"
                        onClick={() => {
                          getUserAnswerByUserIdAndExQuizId({
                            userId: userId as string,
                            exQuizId: exQuiz?.id as string,
                            attemptNumber: userQuiz.attemptNumber as number,
                          });
                          setIsShowResult(true);
                        }}
                      >
                        Xem lại
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>
        </Fragment>
      )}

      {(isShowResult &&
        (userAnswersData?.data as UserQuiz)?.correctAnswerCount) ||
      (isShowResult &&
        (userAnswersData?.data as UserQuiz)?.correctAnswerCount === 0) ? (
        <Fragment>
          <CompleteQuiz
            userQuiz={userAnswersData?.data as UserQuiz}
            close={setIsShowResult}
            key={(userAnswersData?.data as UserQuiz).id as string}
          />
        </Fragment>
      ) : (
        <Fragment>
          {userAnswers.length > 0 && isStartQuiz && (
            <Fragment>
              <div className="flex bg-gray-300 p-2 items-center justify-center gap-2 rounded-md">
                <div>
                  {
                    userAnswers.filter(
                      (answer) => (answer.currentAnswer?.length as number) > 0
                    ).length
                  }
                  /{exQuiz?.totalQuestion}
                </div>
                <div className="w-full flex">
                  <div
                    className="h-4 bg-red-500 transition-all duration-500"
                    style={{ width: `${percentageCompleted}%` }}
                  />
                  <div
                    className="h-4 bg-gray-200 transition-all duration-500"
                    style={{ width: `${100 - percentageCompleted}%` }}
                  />
                </div>
                <CiClock2 className={"text-2xl"} />
                <div className="w-max">{convertLongToTime(timeLeft)}</div>
                <button
                  className="bg-green-700 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
                  onClick={() => handleSubmit()}
                >
                  Submit
                </button>
              </div>
              <QuestionComponent
                userAnswer={userAnswers[questionIndex]}
                key={uuidv4()}
                updateUserAnswer={setUserAnswer}
              />
              <div className="flex-between gap-3">
                <button
                  disabled={questionIndex === 0 ? true : false}
                  onClick={() => handleChangeQuestion(Action.PREVIOUS)}
                  className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                  Previous
                </button>
                <button
                  disabled={
                    (exQuiz?.totalQuestion as number) === questionIndex + 1
                      ? true
                      : false
                  }
                  onClick={() => handleChangeQuestion(Action.NEXT)}
                  className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                  Next
                </button>
              </div>
            </Fragment>
          )}
        </Fragment>
      )}
    </div>
  );
};

export default Answer;
