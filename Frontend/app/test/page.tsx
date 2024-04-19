"use client";
import React, { useEffect, useState } from "react";
import InputEditor from "../instructor/courses/[id]/manage/InputEditor";
import { v4 as uuidv4 } from "uuid";
import { QuizType } from "@/utils/resources";

const questions = [
  {
    question: "What type of framework is Next.js?",
    quizType: QuizType.SINGLE_CHOICE,
    answerOptions: [
      { answer: "Frontend" },
      { answer: "Backend" },
      { answer: "FullStack", isCorrect: true },
      { answer: "None of the above" },
    ],
  },
  {
    question: "When was Next.js released?",
    quizType: QuizType.MULTIPLE_CHOICE,
    answerOptions: [
      { answer: "20 September 2019" },
      { answer: "14 January 2017" },
      { answer: "25 October 2016", isCorrect: true },
      { answer: "28 March 2018" },
    ],
  },
  {
    question: "Which CSS Framework are we using?",
    quizType: QuizType.MULTIPLE_CHOICE,
    answerOptions: [
      { answer: "Bootstrap" },
      { answer: "TailwindCSS", isCorrect: true },
      { answer: "Chakra UI" },
      { answer: "Bulma CSS" },
    ],
  },
  {
    question:
      "Which class in Tailwind is used to set flex direction of column?",
    answerOptions: [
      { answer: "col" },
      { answer: "col-flex" },
      { answer: "flex-col", isCorrect: true },
      { answer: "None of the above" },
    ],
  },
];

function Page() {
  const [selectedOptions, setSelectedOptions] = useState<{
    [key: number]: string[];
  }>({});

  const handleAnswerOption = (
    answer: string,
    questionId: number,
    type: string = QuizType.SINGLE_CHOICE
  ) => {
    setSelectedOptions((prevSelectedOptions: { [key: number]: string[] }) => {
      const prevSelectedOption = selectedOptions[questionId];
      console.log(prevSelectedOption);
      const updatedOptions = prevSelectedOption
        ? type === QuizType.MULTIPLE_CHOICE
          ? prevSelectedOption.includes(answer)
            ? prevSelectedOption.filter(
                (selectedAnswer) => selectedAnswer !== answer
              )
            : [...prevSelectedOption, answer]
          : [answer]
        : [answer];
      console.log(updatedOptions);

      return { ...prevSelectedOptions, [questionId]: updatedOptions };
    });
  };
  const renderQuiz = (quiz: any, idQuiz: number) => {
    return (
      <>
        <div className="flex flex-col w-full">
          <div className="flex flex-col items-start w-full">
            <h4 className="mt-10 text-xl text-black/60">
              Question {idQuiz + 1} of 5
            </h4>
            <div className="mt-4 text-2xl">{quiz.question}</div>
          </div>

          {quiz.answerOptions.map((answer: any, answerIndex: number) => {
            const questionId = idQuiz;
            const isSelected = Array.isArray(selectedOptions[questionId])
              ? selectedOptions[questionId].includes(answer.answer)
              : selectedOptions[questionId] === answer.answer;

            return (
              <div
                key={uuidv4()}
                className="flex items-center w-full py-4 pl-5 m-2 ml-0 space-x-2 border-2 cursor-pointer border-black/10 rounded-xl bg-black/5"
                onClick={() => handleAnswerOption(answer.answer, questionId)}
              >
                <input
                  type={
                    quiz.quizType === QuizType.MULTIPLE_CHOICE
                      ? "checkbox"
                      : "radio"
                  }
                  name={`answer-${questionId}`}
                  value={answer.answer}
                  onChange={() =>
                    handleAnswerOption(answer.answer, questionId, quiz.quizType)
                  }
                  checked={isSelected}
                  className="w-6 h-6 bg-black"
                />
                <p className="ml-6 ">{answer.answer}</p>
              </div>
            );
          })}
        </div>
      </>
    );
  };

  useEffect(() => {
    console.log(selectedOptions);
  }, [selectedOptions]);
  const handlePrevious = () => {
    // const prevQues = currentQuestion - 1;
    // prevQues >= 0 && setCurrentQuestion(prevQues);
  };

  const handleNext = () => {
    // const nextQues = currentQuestion + 1;
    // nextQues < questions.length && setCurrentQuestion(nextQues);
  };

  return (
    <div className="w-2/3 flex justify-center flex-col items-center px-5 ">
      {questions.map((quiz, index) => renderQuiz(quiz, index))}
      <div className="w-full flex  gap-2">
        <button
          // onClick={handlePrevious}
          className="w-[49%] py-3 bg-indigo-600 rounded-lg"
        >
          Previous
        </button>
        <button
          // onClick={handleNext}
          className="w-[49%] py-3 bg-indigo-600 rounded-lg"
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default Page;
