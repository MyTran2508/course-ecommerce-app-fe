import React from "react";
import Sortable from "../DragAndDrop/Sorttable";
import { LectureType } from "@/utils/resources";
import { Question } from "@/types/section.type";
import { v4 as uuidv4 } from "uuid";
import { IoIosMenu } from "react-icons/io";
import { MdDelete, MdModeEditOutline } from "react-icons/md";
export const ques: Question[] = [
  {
    id: "1",
    ordinalNumber: 1,
    title: "cau1",
    options: "2\n4\n6\n8",
    rightAnswer: "0",
    answerExplanation: "1 + 1 = 2",
    quizType: 1,
  },
  {
    id: "2",
    ordinalNumber: 2,
    title: "cau2",
    options: "2\n4\n6\n8",
    rightAnswer: "0",
    answerExplanation: "1 + 1 = 2",
    quizType: 1,
  },
  {
    id: "3",
    ordinalNumber: 3,
    title: "cau3",
    options: "2\n4\n6\n8",
    rightAnswer: "0",
    answerExplanation: "1 + 1 = 2",
    quizType: 1,
  },
];
interface QuestionProps {
  question: Question;
}
function Question(props: QuestionProps) {
  const { question } = props;
  return (
    <div className="flex-between gap-2 mb-2 relative group">
      <div className="flex gap-2">
        <h1>{question.ordinalNumber}</h1>
        <p>{question.title}</p>
      </div>
      <div className="hidden group-hover:block ">
        <div className="flex gap-4">
          <MdModeEditOutline
            className={"text-xl hidden group-hover:block "}
            onClick={() => {
              console.log("ok");
            }}
          />
          <MdDelete className={"text-xl hidden group-hover:block"} />
          <IoIosMenu className={"text-xl hidden group-hover:block"} />
        </div>
      </div>
    </div>
  );
}

export default Question;
