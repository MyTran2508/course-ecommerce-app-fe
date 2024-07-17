import {
  ContentState,
  EditorState,
  convertToRaw,
  convertFromRaw,
} from "draft-js";
import React, { useEffect, useState } from "react";
import { Editor } from "react-draft-wysiwyg";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import { Button } from "../ui/button";
import { useAddForumLectureMutation } from "@/redux/services/forumApi";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import showToast from "@/utils/showToast";
import { Action, ToastMessage, ToastStatus } from "@/utils/resources";
import Swal from "sweetalert2";

interface InputEditorProps {
  setIsOpenInputEditor: (value: boolean) => void;
  parentId?: string;
  text?: string;
  setTextInput?: (value: string) => void;
  setIsCreateComment?: (value: boolean) => void;
  isAssignment?: boolean;
  isAssignmentHistory?: boolean;
}

function InputEditor(props: InputEditorProps) {
  const {
    setIsOpenInputEditor,
    text: comment,
    parentId,
    setTextInput,
    setIsCreateComment,
    isAssignment,
    isAssignmentHistory,
  } = props;
  const user = useAppSelector(
    (state) => state.persistedReducer.userReducer.user
  );
  const [addForumLecture] = useAddForumLectureMutation();
  // const data = `{"blocks":[{"key":"dc7dv","text":"Thảo Cute","type":"unstyled","depth":0,"inlineStyleRanges":[{"offset":0,"length":11,"style":"BOLD"},{"offset":0,"length":11,"style":"ITALIC"}],"entityRanges":[],"data":{}}],"entityMap":{}}`;
  const [text, setText] = useState<EditorState>(
    comment
      ? EditorState.createWithContent(convertFromRaw(JSON.parse(comment || "")))
      : EditorState.createEmpty()
  );

  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn bg-blue-700 text-white px-4 py-2 rounded-md mx-2",
      cancelButton: "btn bg-red-700 text-white px-4 py-2 rounded-md",
    },
    buttonsStyling: false,
  });

  const handleClickSubmit = (data: string) => {
    swalWithBootstrapButtons
      .fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, Submit it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true,
      })
      .then((result) => {
        if (result.isConfirmed) {
          swalWithBootstrapButtons.fire({
            title: "Submit!",
            text: "Your answer has been submit.",
            icon: "success",
          });
          setTextInput && setTextInput(data as string);
          setIsOpenInputEditor(false);
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          swalWithBootstrapButtons.fire({
            title: "Cancelled",
            icon: "error",
          });
          setIsOpenInputEditor(true);
        }
      });
  };

  const handleEditor = (text: any) => {
    setText(text);
  };

  const handleComment = () => {
    if (text) {
      const data = convertToRaw(text.getCurrentContent() as ContentState);
      if (data.blocks[0].text === "") {
        showToast(ToastStatus.WARNING, ToastMessage.EMPTY_COMMENT);
        return;
      }
      if (isAssignmentHistory) {
        handleClickSubmit(JSON.stringify(data));
      } else if (setTextInput) {
        setTextInput(JSON.stringify(data));
      } else {
        addForumLecture({
          comment: JSON.stringify(data),
          lectureId: parentId,
          userId: user.id,
          userName: user.username,
        });
        setIsCreateComment && setIsCreateComment(true);
        // setIsOpenInputEditor(false);
      }
      setIsOpenInputEditor(false);
    }
  };

  return (
    <div className="">
      <div className="border-2 w-max-[75%] h-[300px] p-2">
        <Editor
          editorState={text}
          toolbarClassName="toolbarClassName"
          wrapperClassName="wrapperClassName "
          editorClassName="editorClassName "
          editorStyle={{
            wordBreak: "break-word",
            overflowY: "scroll",
            maxHeight: "150px",
          }}
          onEditorStateChange={handleEditor}
        />
      </div>
      <div className="flex gap-2 mt-4 justify-end">
        {!(isAssignment && comment === "") && (
          <button
            className="bg-gray-200 text-black px-2 py-1 rounded-md"
            onClick={() => setIsOpenInputEditor(false)}
          >
            Close
          </button>
        )}
        <Button onClick={() => handleComment()}>
          {isAssignment ? "Save" : "Send"}
        </Button>
      </div>
    </div>
  );
}

export default InputEditor;
