import Image from "next/image";
import React, { Fragment, use, useEffect, useState } from "react";
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
  useUpdateForumLectureMutation,
} from "@/redux/services/forumApi";
import { is } from "immutable";
import { convertMillisToDateTime, formatTime } from "@/utils/function";
import { HiChevronUp } from "react-icons/hi";
import CommentRep from "./CommentReply";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import { DataResponse } from "@/types/response.type";

interface CommentProps {
  data: ForumLecture;
  stompClient: any;
  lectureId: string;
}
var stompClientForCommentReply: any = null;
function Comment(props: CommentProps) {
  const { data, stompClient: stompClientForForumLecture, lectureId } = props;
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.id
  );
  const userName = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const avatar = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.photos
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
  const [isOpenReply, setIsOpenReply] = useState(false);
  const [replyText, setReplyText] = useState<string>("");
  const [pageIndex, setPageIndex] = useState(0);
  const [
    getCommentReplyByCommentId,
    { data: commentReplyData, isSuccess: getCommentReplySuccess },
  ] = useGetCommentReplyByCommentIdMutation();

  const [commentReply, setCommentReply] = useState<CommentReply[]>([]);
  const [isOpenCommentReply, setIsOpenCommentReply] = useState(false);

  useEffect(() => {
    if (updateText !== data.comment) {
      if (stompClientForForumLecture) {
        const updateComment: ForumLecture = {
          ...data,
          comment: updateText,
        };
        stompClientForForumLecture.send(
          `/rt/request/courses/forum-lecture/update/${lectureId}`,
          {},
          JSON.stringify(updateComment)
        );
      }
    }
  }, [isEdit]);

  useEffect(() => {
    if (isOpenCommentReply) {
      connect();
    }
  }, [isOpenCommentReply]);

  useEffect(() => {
    if (!stompClientForCommentReply && isOpenReply) {
      connect();
    }
  }, [isOpenReply]);

  useEffect(() => {
    setText(
      data
        ? EditorState.createWithContent(
            convertFromRaw(JSON.parse(data.comment || ""))
          )
        : EditorState.createEmpty()
    );
    setUpdateText(data.comment || "");
    handleGetCommentReply();
  }, [data]);

  useEffect(() => {
    if (getCommentReplySuccess) {
      setCommentReply((prevCommentReply) => [
        ...prevCommentReply,
        ...(commentReplyData?.data as CommentReply[]),
      ]);
    }
  }, [commentReplyData]);

  useEffect(() => {
    if (replyText !== "") {
      setIsOpenReply(false);
      // createReplyComment({
      //   commentReply: {
      //     comment: replyText,
      //     userId: userId,
      //     userName: userName,
      //     avatarUrl: "",
      //   },
      //   commentId: data.id as string,
      // });
      sendValue();
    }
  }, [replyText]);

  // useEffect(() => {
  //   if (isCreateReplyComment || isUpdateCommentReply) {
  //     // setCommentReply([]);
  //     setPageIndex(0);
  //     // handleGetCommentReply();
  //     setIsCreateReplyComment(false);
  //     setIsUpdateCommentReply(false);
  //   }
  // }, [isUpdateCommentReply, isCreateReplyComment]);

  useEffect(() => {
    handleGetCommentReply();
  }, [pageIndex]);

  const handleOpenInputEditor = (action: Action) => {
    if (action === Action.UPDATE) {
      setIsEdit(true);
    } else {
      setIsOpenReply(true);
    }
  };

  const handleGetCommentReply = () => {
    setTimeout(() => {
      getCommentReplyByCommentId({
        keyword: [data.id as string],
        pageIndex: pageIndex,
        pageSize: 5,
        isDecrease: false,
        sortBy: "created",
      });
    }, 500);
  };

  const handleOpenCommentReply = () => {
    setIsOpenCommentReply(!isOpenCommentReply);
  };

  //socket
  const connect = async () => {
    // let Sock = new SockJS(process.env.NEXT_PUBLIC_END_POINT + "/ws/courses");
    let Sock = new SockJS("http://localhost:8081/ws/courses");
    stompClientForCommentReply = await over(Sock);
    await stompClientForCommentReply.connect({}, onConnected, onError);
  };

  const onMessageReceived = (payload: any) => {
    var payloadData = JSON.parse(payload.body);
    const newComment = (payloadData as DataResponse).data as CommentReply;
    setCommentReply((prevCommentReply) => {
      const isIdExist = prevCommentReply.find(
        (comment) => comment.id === newComment.id
      );
      if (!isIdExist) {
        return [...prevCommentReply, newComment];
      }
      const updateComment = prevCommentReply.map((comment) =>
        comment.id === newComment.id ? newComment : comment
      );
      return updateComment;
    });
  };

  const onConnected = () => {
    if (stompClientForCommentReply) {
      stompClientForCommentReply.subscribe(
        `/rt/response/courses/comment-reply/${lectureId}/${data.id}`,
        onMessageReceived
      );
    }
  };

  const onError = (err: any) => {
    console.log(err);
  };

  const sendValue = () => {
    if (stompClientForCommentReply) {
      const newCommentReply: CommentReply = {
        comment: replyText,
        userId: userId,
        forumLectureId: lectureId,
        userName: userName,
        rawAvatar: avatar ? avatar : "",
      };

      stompClientForCommentReply.send(
        `/rt/request/courses/comment-reply/add/${lectureId}/${data.id}`,
        {},
        JSON.stringify(newCommentReply)
      );
    }
  };

  return (
    <div className="flex flex-col mb-4">
      <div className="flex">
        <Image
          src={
            data.rawAvatar
              ? `data:image/png;base64,${data.rawAvatar}`
              : "/banner.jpg"
          }
          alt="avatar"
          width={70}
          height={65}
          className="rounded-full w-[40px] h-[40px]"
        />

        {isEdit ? (
          <InputEditor
            setIsOpenInputEditor={setIsEdit}
            parentId={data.lectureId as string}
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
      {!isOpenReply && commentReply.length > 0 && (
        <Fragment>
          <div
            className="ml-[64px] text-sm font-bold hover:cursor-pointer flex gap-1"
            onClick={() => handleOpenCommentReply()}
          >
            {isOpenCommentReply ? "Ẩn" : `Xem các phản hồi`}
            <HiChevronUp
              className={`${
                isOpenCommentReply ? "rotate-180 transform" : ""
              } h-5 w-5 text-orange-500`}
            />
          </div>
          {isOpenCommentReply && (
            <Fragment>
              <div className="ml-16 border-orange-500 border-l-[1px] pl-2">
                {commentReply?.map((item, index) => (
                  <CommentRep
                    data={item}
                    commentId={data.id as string}
                    stompClient={stompClientForCommentReply}
                    lectureId={lectureId}
                    setTextCommentReply={setReplyText}
                    key={item.id}
                  />
                ))}
                {commentReply.length <
                  (commentReplyData?.totalRecords as number) && (
                  <div
                    className="ml-4 text-medium text-orange-400 hover:cursor-pointer"
                    onClick={() => setPageIndex(pageIndex + 1)}
                  >
                    {" "}
                    Xem Thêm
                  </div>
                )}
              </div>
            </Fragment>
          )}
        </Fragment>
      )}

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
            parentId={data.id as string}
            setTextInput={setReplyText}
          />
        </div>
      )}
    </div>
  );
}

export default Comment;
