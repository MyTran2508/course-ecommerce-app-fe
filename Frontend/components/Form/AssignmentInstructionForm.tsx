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

function AssignmentInstructionForm(props: AssignmentBasicInfoFormProps) {
  const { assignment, lectureId } = props;
  const [isOpenInputEditor, setIsOpenInputEditor] = useState<boolean>(false);
  const [textView, setTextView] = useState<EditorState>(
    assignment?.textInstructions
      ? EditorState.createWithContent(
          convertFromRaw(JSON.parse(assignment?.textInstructions || ""))
        )
      : EditorState.createEmpty()
  );
  const [textInstructions, setTextInstructions] = useState<string>(
    assignment?.textInstructions || ""
  );
  const [isChangeVideoInstructions, setIsChangeVideoInstructions] =
    useState(false);
  const [isChangeResourceInstructions, setIsChangeResourceInstructions] =
    useState(false);
  const { handleUploadFilesForSection } = useFileHooks();
  const [addAssignment] = useAddAssignmentMutation();
  const [updateAssignment] = useUpdateAssignmentMutation();

  useEffect(() => {
    if (assignment?.textInstructions) {
      setTextView(
        EditorState.createWithContent(
          convertFromRaw(JSON.parse(assignment.textInstructions))
        )
      );
    }
  }, [assignment]);

  useEffect(() => {
    if (textInstructions !== (assignment?.textInstructions as string)) {
      const newAssignment = { ...assignment };
      newAssignment.textInstructions = textInstructions;
      handleUpdateAssignment(newAssignment);
    }
  }, [textInstructions]);

  const handleUploadFile = async (file: File | null, field: Fields) => {
    const newAssignment = { ...assignment };
    if (!file) {
      if (field == Fields.ASSIGNMENT_RESOURCE_INSTRUCTIONS) {
        newAssignment.urlFileResource = "";
      } else if (field == Fields.ASSIGNMENT_VIDEO_INSTRUCTIONS) {
        newAssignment.urlVideoInstructions = "";
      }
    } else {
      const response = await handleUploadFilesForSection([file]);
      if (response) {
        const url = (response.data as string[])[0];
        if (field == Fields.ASSIGNMENT_RESOURCE_INSTRUCTIONS) {
          newAssignment.urlFileResource = url;
        } else if (field == Fields.ASSIGNMENT_VIDEO_INSTRUCTIONS) {
          newAssignment.urlVideoInstructions = url;
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
    setIsChangeResourceInstructions(false);
    setIsChangeVideoInstructions(false);
  };

  return (
    <div>
      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Video</label>
        {assignment?.urlVideoInstructions && !isChangeVideoInstructions && (
          <div className="flex gap-2">
            <video
              controls
              src={assignment?.urlVideoInstructions}
              className="w-96 h-[200px] rounded-md"
            />
            <button
              className="bg-gray-200 text-black rounded-md h-max px-4 py-2"
              onClick={() =>
                handleUploadFile(null, Fields.ASSIGNMENT_VIDEO_INSTRUCTIONS)
              }
            >
              Delete
            </button>
            <Button onClick={() => setIsChangeVideoInstructions(true)}>
              Change
            </Button>
          </div>
        )}
        {(isChangeVideoInstructions || !assignment?.urlVideoInstructions) && (
          <div className="space-y-1">
            <Input
              type="file"
              accept=".mp4, .mkv, .wmv"
              onChange={(e) =>
                handleUploadFile(
                  e.target.files?.[0] as File,
                  Fields.ASSIGNMENT_VIDEO_INSTRUCTIONS
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
            {assignment?.urlVideoInstructions && (
              <Button onClick={() => setIsChangeVideoInstructions(false)}>
                Cancel
              </Button>
            )}
          </div>
        )}
      </div>

      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Assignment Instructions</label>
        {(isOpenInputEditor || !assignment?.textInstructions) && (
          <InputEditor
            text={textInstructions}
            setTextInput={setTextInstructions}
            isAssignment={true}
            setIsOpenInputEditor={setIsOpenInputEditor}
          />
        )}
        {!isOpenInputEditor && assignment?.textInstructions && (
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
      <div className="mb-10 mt-3">
        <label className="text-black font-bold">Downloadable resource</label>
        {assignment?.urlFileResource && !isChangeResourceInstructions && (
          <div className="flex gap-2 items-center">
            <IoMdDownload />
            {assignment?.urlFileResource.split("_").pop()}
            <button
              className="bg-gray-200 text-black py-2 rounded-md h-max px-4"
              onClick={() =>
                handleUploadFile(null, Fields.ASSIGNMENT_RESOURCE_INSTRUCTIONS)
              }
            >
              Delete
            </button>
            <Button onClick={() => setIsChangeResourceInstructions(true)}>
              Change
            </Button>
          </div>
        )}
        {(isChangeResourceInstructions || !assignment?.urlFileResource) && (
          <div className="space-y-1">
            <Input
              type="file"
              accept=".pdf"
              onChange={(e) =>
                handleUploadFile(
                  e.target.files?.[0] as File,
                  Fields.ASSIGNMENT_RESOURCE_INSTRUCTIONS
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
            {assignment?.urlFileResource && (
              <Button onClick={() => setIsChangeResourceInstructions(false)}>
                Cancel
              </Button>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default AssignmentInstructionForm;
