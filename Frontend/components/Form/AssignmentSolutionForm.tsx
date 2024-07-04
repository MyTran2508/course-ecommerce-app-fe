"use client";
import { Input } from "../ui/input";
import React, { use, useEffect, useState } from "react";
import { Button } from "../ui/button";
import { Assignment } from "@/types/assignment.type";
import InputEditor from "../Input/InputEditor";
import { EditorState, convertFromRaw } from "draft-js";
import { stateToHTML } from "draft-js-export-html";
import { useFileHooks } from "@/redux/hooks/courseHooks/fileHooks";
import { Fields } from "@/utils/resources";
import {
  useAddAssignmentMutation,
  useUpdateAssignmentMutation,
} from "@/redux/services/contentApi";
import { IoMdDownload } from "react-icons/io";

interface AssignmentBasicInfoFormProps {
  assignment?: Assignment;
  lectureId?: string;
}

function AssignmentSolutionForm(props: AssignmentBasicInfoFormProps) {
  const { assignment, lectureId } = props;
  const [isOpenInputEditor, setIsOpenInputEditor] = useState<boolean>(false);
  const [textViewQuestion, setTextViewQuestion] = useState<EditorState>(
    assignment?.questions
      ? EditorState.createWithContent(
          convertFromRaw(JSON.parse(assignment?.questions || ""))
        )
      : EditorState.createEmpty()
  );
  const [textViewSolution, setTextViewSolution] = useState<EditorState>(
    EditorState.createEmpty()
  );
  const [solutions, setSolutions] = useState<string>(
    assignment?.textSolution || ""
  );
  const [isChangeVideoSolution, setIsChangeVideoSolution] = useState(false);
  const [isChangeFileSolution, setIsChangeFileSolution] = useState(false);
  const { handleUploadFilesForSection } = useFileHooks();
  const [addAssignment] = useAddAssignmentMutation();
  const [updateAssignment] = useUpdateAssignmentMutation();

  useEffect(() => {
    if (assignment?.questions) {
      setTextViewQuestion(
        EditorState.createWithContent(
          convertFromRaw(JSON.parse(assignment.questions))
        )
      );
    }
    if (assignment?.textSolution) {
      setTextViewSolution(
        EditorState.createWithContent(
          convertFromRaw(JSON.parse(assignment.textSolution))
        )
      );
    }
  }, [assignment]);

  useEffect(() => {
    if (solutions !== (assignment?.questions as string)) {
      const newAssignment = { ...assignment };
      newAssignment.textSolution = solutions;
      handleUpdateAssignment(newAssignment);
    }
  }, [solutions]);

  const handleUploadFile = async (file: File | null, field: Fields) => {
    const newAssignment = { ...assignment };
    if (!file) {
      if (field == Fields.ASSIGNMENT_FILE_SOLUTION) {
        newAssignment.urlFileSolution = "";
      } else if (field == Fields.ASSIGNMENT_VIDEO_SOLUTION) {
        newAssignment.urlVideoSolution = "";
      }
    } else {
      const response = await handleUploadFilesForSection([file]);
      if (response) {
        const url = (response.data as string[])[0];
        if (field == Fields.ASSIGNMENT_FILE_SOLUTION) {
          newAssignment.urlFileSolution = url;
        } else if (field == Fields.ASSIGNMENT_VIDEO_SOLUTION) {
          newAssignment.urlVideoSolution = url;
        }
      }
    }
    handleUpdateAssignment(newAssignment);
  };

  const handleUpdateAssignment = (newAssignment: Assignment) => {
    if (assignment) {
      updateAssignment(newAssignment);
    } else {
      newAssignment.lecture = {
        id: lectureId,
      };
      addAssignment(newAssignment);
    }
    setIsChangeFileSolution(false);
    setIsChangeVideoSolution(false);
    setIsOpenInputEditor(false);
  };

  return (
    <div>
      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Video</label>
        {assignment?.urlVideoSolution && !isChangeVideoSolution && (
          <div className="flex gap-2">
            <video
              controls
              src={assignment?.urlVideoSolution}
              className="w-96 h-[200px] rounded-md"
            />
            <button
              className="bg-gray-200 text-black rounded-md h-max px-4 py-2"
              onClick={() =>
                handleUploadFile(null, Fields.ASSIGNMENT_VIDEO_SOLUTION)
              }
            >
              Delete
            </button>
            <Button onClick={() => setIsChangeVideoSolution(true)}>
              Change
            </Button>
          </div>
        )}
        {(isChangeVideoSolution || !assignment?.urlVideoSolution) && (
          <div className="space-y-1">
            <Input
              type="file"
              accept=".mp4, .mkv, .wmv"
              onChange={(e) =>
                handleUploadFile(
                  e.target.files?.[0] as File,
                  Fields.ASSIGNMENT_VIDEO_SOLUTION
                )
              }
              className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black w-1/4"
              // disabled={
              //   !isPermissionGranted(
              //     roleDetail as RoleDetail[],
              //     PermissionName.CAN_CREATE,
              //     ModuleName.COURSE
              //   )
              // }
            ></Input>
            {assignment?.urlVideoSolution && (
              <Button onClick={() => setIsChangeVideoSolution(false)}>
                Cancel
              </Button>
            )}
          </div>
        )}
      </div>

      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Questions:</label>
        <div
          dangerouslySetInnerHTML={{
            __html: stateToHTML(textViewQuestion.getCurrentContent()),
          }}
        />
      </div>

      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Assignment Solution</label>
        {(isOpenInputEditor || !assignment?.textSolution) && (
          <InputEditor
            text={solutions}
            setTextInput={setSolutions}
            isAssignment={true}
            setIsOpenInputEditor={setIsOpenInputEditor}
          />
        )}
        {!isOpenInputEditor && assignment?.textSolution && (
          <div>
            <div
              dangerouslySetInnerHTML={{
                __html: stateToHTML(textViewSolution.getCurrentContent()),
              }}
            />
            <Button onClick={() => setIsOpenInputEditor(true)}>Edit</Button>
          </div>
        )}
      </div>
      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Downloadable resource</label>
        {assignment?.urlFileSolution && !isChangeFileSolution && (
          <div className="flex gap-2 items-center">
            <IoMdDownload />
            {assignment?.urlFileSolution.split("_").pop()}
            <button
              className="bg-gray-200 text-black py-2 rounded-md h-max px-4"
              onClick={() =>
                handleUploadFile(null, Fields.ASSIGNMENT_FILE_SOLUTION)
              }
            >
              Delete
            </button>
            <Button onClick={() => setIsChangeFileSolution(true)}>
              Change
            </Button>
          </div>
        )}
        {(isChangeFileSolution || !assignment?.urlFileSolution) && (
          <div className="space-y-1">
            <Input
              type="file"
              accept=".pdf"
              onChange={(e) =>
                handleUploadFile(
                  e.target.files?.[0] as File,
                  Fields.ASSIGNMENT_FILE_SOLUTION
                )
              }
              className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black w-1/4"
              // disabled={
              //   !isPermissionGranted(
              //     roleDetail as RoleDetail[],
              //     PermissionName.CAN_CREATE,
              //     ModuleName.COURSE
              //   )
              // }
            ></Input>
            {assignment?.urlFileSolution && (
              <Button onClick={() => setIsChangeFileSolution(false)}>
                Cancel
              </Button>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default AssignmentSolutionForm;
