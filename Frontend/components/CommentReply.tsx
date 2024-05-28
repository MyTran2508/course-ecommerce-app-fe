import Image from "next/image";
import React, { Fragment, useEffect, useState } from "react";
import CustomButton from "./CustomButton";
import { Input } from "./ui/input";
import InputEditor from "./Input/InputEditor";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { EditorState, convertFromRaw } from "draft-js";
import { stateToHTML } from "draft-js-export-html";
import { CommentReply, ForumLecture } from "@/types/forumLecture";
import { Action, Constant } from "@/utils/resources";
import {
  useAddCommentReplyMutation,
  useGetCommentReplyByCommentIdMutation,
  useUpdateCommentReplyMutation,
  useUpdateForumLectureMutation,
} from "@/redux/services/forumApi";
import { is } from "immutable";
import { convertMillisToDateTime, formatTime } from "@/utils/function";
import { HiChevronUp } from "react-icons/hi";

interface CommentReplyProps {
  data: CommentReply;
  setIsEditComment: (data: boolean) => void;
  commentId: string;
  setTextCommentReply?: (data: string) => void;
  setIsCreateCommentReply?: (data: boolean) => void;
}

function CommentRep(props: CommentReplyProps) {
  const {
    data,
    setIsEditComment,
    setIsCreateCommentReply,
    setTextCommentReply,
  } = props;
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.id
  );
  const userName = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const [text, setText] = useState<EditorState>(
    data
      ? EditorState.createWithContent(
          convertFromRaw(JSON.parse(data.comment || ""))
        )
      : EditorState.createEmpty()
  );
  const [updateText, setUpdateText] = useState<string>(data.comment || "");
  const [isEdit, setIsEdit] = useState(false);
  const avatar = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.photos
  );
  const [isOpenReply, setIsOpenReply] = useState(false);
  const [updateCommentReply] = useUpdateCommentReplyMutation();

  useEffect(() => {
    if (updateText !== data.comment) {
      updateCommentReply({
        ...data,
        comment: updateText,
      });
      setIsEditComment(true);
    }
  }, [isEdit]);

  useEffect(() => {
    setText(
      data
        ? EditorState.createWithContent(
            convertFromRaw(JSON.parse(data.comment || ""))
          )
        : EditorState.createEmpty()
    );
    setUpdateText(data.comment || "");
  }, [data]);

  const handleOpenInputEditor = (action: Action) => {
    if (action === Action.UPDATE) {
      setIsEdit(true);
    } else {
      setIsOpenReply(true);
    }
  };

  return (
    <div className="flex flex-col mb-2">
      <div className="flex">
        <Image
          src="/banner.jpg"
          alt="avatar"
          width={70}
          height={65}
          className="rounded-full w-[40px] h-[40px]"
        />

        {isEdit ? (
          <InputEditor
            setIsOpenInputEditor={setIsEdit}
            parentId={data.id as string}
            text={updateText}
            setTextInput={setUpdateText}
          />
        ) : (
          <Fragment>
            <div className="bg-gray-200 p-2 px-4 ml-4 rounded-2xl min-w-[60%]">
              <div className="font-bold">{data.userName}</div>
              <div className="w-full mt-2 text-sm">
                <div
                  dangerouslySetInnerHTML={{
                    __html: stateToHTML(text.getCurrentContent()),
                  }}
                />
              </div>
            </div>
          </Fragment>
        )}
      </div>
      <div className="flex items-center">
        <CustomButton
          title="Trả lời"
          containerStyles="ml-16 text-sm mt-2 hover:text-orange-200"
          handleClick={() => handleOpenInputEditor(Action.CREATE)}
        />
        {data.userId === userId && (
          <CustomButton
            title="Chỉnh sửa"
            containerStyles="ml-5 text-sm mt-2 hover:text-orange-200"
            handleClick={() => handleOpenInputEditor(Action.UPDATE)}
          />
        )}
        {data.updated ? (
          <p className="italic text-xs ml-2 mt-2 opacity-80">
            {convertMillisToDateTime(data.updated as number)} (Đã chỉnh sửa)
          </p>
        ) : (
          <p className="italic text-xs ml-2 mt-2 opacity-80">
            {convertMillisToDateTime(data.created as number)}
          </p>
        )}
      </div>

      {isOpenReply && (
        <div className="flex gap-2 items-center my-10">
          <Image
            src={avatar ? `data:image/png;base64,${avatar}` : "/banner.jpg"}
            alt="avatar"
            width={50}
            height={50}
            className="rounded-full min-w-[50px] h-[50px]"
          />

          <InputEditor
            setIsOpenInputEditor={setIsOpenReply}
            setTextInput={setTextCommentReply}
            setIsCreateComment={setIsCreateCommentReply}
          />
        </div>
      )}
    </div>
  );
}

export default CommentRep;
