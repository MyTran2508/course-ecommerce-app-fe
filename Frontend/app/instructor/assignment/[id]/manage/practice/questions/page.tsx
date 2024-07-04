"use client";
import { EditorState, convertFromRaw } from "draft-js";
import { stateToHTML } from "draft-js-export-html";
import InputEditor from "@/components/Input/InputEditor";
import { Button } from "@/components/ui/button";
import {
  useAddAssignmentMutation,
  useGetLectureByIdQuery,
  useUpdateAssignmentMutation,
} from "@/redux/services/contentApi";
import { Lecture } from "@/types/section.type";
import { usePathname } from "next/navigation";
import React, { use, useEffect, useState } from "react";
import Loading from "@/app/(root)/user/personal/loading";
import { Assignment } from "@/types/assignment.type";

function QuestionPage() {
  const path = usePathname();
  const lectureId = path.split("/")[3];
  const { data: lectureData, isSuccess: getLectureSuccess } =
    useGetLectureByIdQuery(lectureId);
  const [lecture, setLecture] = useState<Lecture | null>(null);
  const [isOpenInputEditor, setIsOpenInputEditor] = useState<boolean>(false);
  const [questions, setQuestions] = useState<string>(
    lecture?.assignment?.questions as string | ""
  );
  const [textView, setTextView] = useState<EditorState>(
    lecture?.assignment?.questions
      ? EditorState.createWithContent(
          convertFromRaw(JSON.parse(lecture?.assignment?.questions || ""))
        )
      : EditorState.createEmpty()
  );
  const [addAssignment] = useAddAssignmentMutation();
  const [updateAssignment] = useUpdateAssignmentMutation();

  useEffect(() => {
    if (getLectureSuccess) {
      setLecture(lectureData.data as Lecture);
    }
  }, [lectureData]);

  useEffect(() => {
    if (lecture?.assignment?.questions) {
      setTextView(
        EditorState.createWithContent(
          convertFromRaw(JSON.parse(lecture?.assignment?.questions || ""))
        )
      );
    }
  }, [lecture]);

  useEffect(() => {
    if (questions !== (lecture?.assignment?.questions as string)) {
      const newAssignment = { ...lecture?.assignment };
      newAssignment.questions = questions;
      handleUpdateAssignment(newAssignment);
    }
  }, [questions]);

  const handleUpdateAssignment = (newAssignment: Assignment) => {
    if (lecture?.assignment) {
      updateAssignment(newAssignment);
    } else {
      newAssignment.lecture = {
        id: lecture?.id as string,
      };
      addAssignment(newAssignment);
    }
  };

  if (!getLectureSuccess) {
    return <Loading />;
  }

  return (
    <div>
      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Add Questions</label>
        {(isOpenInputEditor || !lecture?.assignment?.questions) && (
          <InputEditor
            text={questions}
            setTextInput={setQuestions}
            isAssignment={true}
            setIsOpenInputEditor={setIsOpenInputEditor}
          />
        )}
        {!isOpenInputEditor && lecture?.assignment?.questions && (
          <div>
            <div
              dangerouslySetInnerHTML={{
                __html: stateToHTML(textView.getCurrentContent()),
              }}
            />
            <Button onClick={() => setIsOpenInputEditor(true)}>Edit</Button>
          </div>
        )}
      </div>
    </div>
  );
}

export default QuestionPage;
