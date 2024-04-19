import React, { useState } from "react";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { toast } from "react-toastify";
import {
  Constant,
  LectureType,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import showToast from "@/utils/showToast";
import {
  useAddLectureMutation,
  useAddSectionMutation,
} from "@/redux/services/contentApi";
import { Lecture, Question, Section } from "@/types/section.type";
import { useSectionHooks } from "@/redux/hooks/courseHooks/sectionHooks";
import { useLectureHooks } from "@/redux/hooks/courseHooks/lectureHooks";
import { Action } from "@/utils/resources";
import { access } from "fs";

interface CreateTitleProps {
  handleIsOpen?: (isOpen: boolean) => void;
  type?: string;
  parentId?: string;
  ordinalNumber?: number;
  data?: Section | Lecture;
  action: Action;
}

function CreateTitle(props: CreateTitleProps) {
  const { handleIsOpen, type, parentId, ordinalNumber, data, action } = props;
  const [name, setName] = useState<string>(data ? (data.name as string) : "");
  const [limitTimeCompleteQuiz, setLimitTimeCompleteQuiz] = useState<number>(0);
  const { handleAddSection, handleUpdateSection } = useSectionHooks();
  const { handleAddLecture, handleUpdateLecture } = useLectureHooks();

  const handleSubmit = () => {
    console.log(type, action);
    if (name.length > 0) {
      if (action === Action.CREATE) {
        if (type === Constant.SECTION) {
          const newSection: Section = {
            name: name,
            ordinalNumber: ordinalNumber,
            lectures: [],
            totalDurationVideoLectures: 0,
            content: {
              id: parentId,
            },
          };
          console.log(newSection);
          handleAddSection(newSection);
        } else if (
          type === LectureType.VIDEO ||
          type === LectureType.DOCUMENT
        ) {
          const newLecture: Lecture = {
            name: name,
            lectureType: type,
            ordinalNumber: ordinalNumber,
            videoDuration: 0,
          };
          handleAddLecture(parentId as string, newLecture);
        } else if (type === LectureType.QUIZ_TEST) {
          const newLecture: Lecture = {
            name: name,
            lectureType: type,
            ordinalNumber: ordinalNumber,
            videoDuration: 0,
            exQuiz: {
              category: "Java",
              difficulty: "0",
              limitTime: limitTimeCompleteQuiz,
              questions: [],
            },
          };
          console.log(newLecture);
          handleAddLecture(parentId as string, newLecture);
        }
      } else if (action === Action.UPDATE) {
        if (type === Constant.SECTION) {
          const updateSection: Section = {
            ...data,
            name: name,
          };
          handleUpdateSection(updateSection);
        } else if (
          type === LectureType.VIDEO ||
          type === LectureType.DOCUMENT
        ) {
          // const updateLecture: Lecture = {
          //   ...data,
          //   name: name,
          // };
          (data as Lecture).name = name;
          handleUpdateLecture(data as Lecture);
        }
      }
      handleIsOpen?.(false);
    } else {
      showToast(ToastStatus.WARNING, "Vui lòng điền đầy đủ thông tin");
    }
  };

  return (
    <div className=" bg-slate-200 p-2 border-2 mr-20 mb-10">
      <div className="flex items-center gap-1 ">
        {action === Action.UPDATE ? (
          <div className="font-bold">{type + ` ${data?.ordinalNumber}`}</div>
        ) : (
          <div className="font-bold">{type + " Mới: "}</div>
        )}
        <Input
          onChange={(e) => setName(e.target.value)}
          className="w-[90%]"
          placeholder="Nhập tên"
          value={name}
        />
      </div>
      {type === LectureType.QUIZ_TEST && (
        <div className="flex items-center gap-1 mt-5 ">
          <div className="font-bold">Thời gian (giây): </div>
          <Input
            onChange={(e) => setLimitTimeCompleteQuiz(Number(e.target.value))}
            type="number"
            placeholder="Nhập giới hạn thời gian(giây)"
            className="w-[20%]"
          />
        </div>
      )}
      <div className="flex-end mt-2 gap-2 mr-[5%]">
        <Button
          className="bg-slate-200 hover:bg-slate-200 text-black"
          onClick={(e) => {
            e.preventDefault();
            handleIsOpen?.(false);
          }}
        >
          Hủy
        </Button>
        <Button
          onClick={(e) => {
            e.preventDefault();
            handleSubmit();
          }}
        >
          {action === Action.UPDATE ? "Cập nhật " : "Thêm "}
          {type}
        </Button>
      </div>
    </div>
  );
}

export default CreateTitle;
