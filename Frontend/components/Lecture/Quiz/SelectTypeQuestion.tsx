import React, { Fragment, useEffect, useState } from "react";
import { CiCircleQuestion } from "react-icons/ci";
import { v4 as uuidv4 } from "uuid";
import { QuizType } from "@/utils/resources";
import { useAppDispatch } from "@/redux/hooks";
import { setTypeQuizCreate } from "@/redux/features/quizSlice";

function SelectTypeQuiz() {
  const dispatch = useAppDispatch();
  const [isChoiceTypeQuestion, setIsChoiceTypeQuestion] = useState(false);
  const [selectedOptions, setSelectedOptions] = useState<{
    [key: number]: string[];
  }>({});
  const [typeQuestion, setTypeQuestion] = useState("");

  const handleChoiceTypeQuestion = (type: QuizType) => {
    setIsChoiceTypeQuestion(true);
    setTypeQuestion(type);
    dispatch(setTypeQuizCreate(type));
  };

  function handleOptionChange(event: any) {
    const selectedOption = event.target.value;
    setSelectedOptions((prevSelectedOptions) => ({
      ...prevSelectedOptions,
      [selectedOption]: [selectedOption],
    }));
    console.log(selectedOption);
  }

  const handleAnswerOption = (
    answer: string,
    questionId: number,
    type: string = QuizType.SINGLE_CHOICE
  ) => {
    setSelectedOptions((prevSelectedOptions: { [key: number]: string[] }) => {
      const prevSelectedOption = selectedOptions[questionId];
      const updatedOptions = prevSelectedOption
        ? type === QuizType.MULTIPLE_CHOICE
          ? prevSelectedOption.includes(answer)
            ? prevSelectedOption.filter(
                (selectedAnswer) => selectedAnswer !== answer
              )
            : [...prevSelectedOption, answer]
          : [answer]
        : [answer];
      return { ...prevSelectedOptions, [questionId]: updatedOptions };
    });
  };

  useEffect(() => {
    console.log(selectedOptions);
  }, [selectedOptions]);

  return (
    <div className="">
      {isChoiceTypeQuestion ? (
        <Fragment>
          <div>
            <strong className="mb-2">Câu hỏi:</strong>
            <input
              className="border border-black w-full h-10 pl-2"
              placeholder="Hãy ghi câu hỏi ở đây"
            />
          </div>
          <div className="mt-4">
            <strong className="mb-2">Câu trả lời:</strong>
            {Array.from({ length: 4 }).map((_, index) => {
              const questionId = 0;
              const isSelected = Array.isArray(selectedOptions[questionId])
                ? selectedOptions[questionId].includes(index.toString())
                : selectedOptions[questionId] === index.toString();

              return (
                <div
                  className="flex gap-5 items-center justify-center py-4 pl-5 m-2 ml-0 space-x-2"
                  key={uuidv4()}
                >
                  <input
                    type={
                      typeQuestion === QuizType.MULTIPLE_CHOICE
                        ? "checkbox"
                        : "radio"
                    }
                    className="w-6 h-6 bg-black"
                    name="options"
                    id={`${index + 1}`}
                    value={`${index + 1}`}
                    checked={isSelected}
                    onChange={() =>
                      handleAnswerOption(`${index}`, 0, typeQuestion)
                    }
                  />
                  <input
                    className="border border-black w-full h-10 pl-2"
                    placeholder="Hãy ghi câu trả lời ở đây"
                  />
                </div>
              );
            })}
          </div>
        </Fragment>
      ) : (
        <Fragment>
          <div className="flex items-center justify-center gap-2">
            <div
              className="border border-black p-5 pb-2 mt-2 flex flex-col items-center hover:bg-slate-500 hover:cursor-pointer w-[150px]"
              onClick={() => handleChoiceTypeQuestion(QuizType.MULTIPLE_CHOICE)}
            >
              <CiCircleQuestion className={"text-3xl ju"} />
              <p>Nhiều lựa chọn</p>
            </div>
            <div
              className="border border-black p-5 pb-2 mt-2 flex flex-col items-center hover:bg-slate-500 hover:cursor-pointer w-[150px]"
              onClick={() => handleChoiceTypeQuestion(QuizType.SINGLE_CHOICE)}
            >
              <CiCircleQuestion className={"text-3xl"} />
              <p>Một lựa chọn</p>
            </div>
          </div>
        </Fragment>
      )}
    </div>
  );
}

export default SelectTypeQuiz;
