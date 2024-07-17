import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import Image from "next/image";
import { AiFillWechat } from "react-icons/ai";
import Comment from "../Comment";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { useEffect, useState } from "react";
import InputEditor from "../Input/InputEditor";
import {
  useAddForumLectureMutation,
  useGetForumLectureByIdMutation,
} from "@/redux/services/forumApi";
import { ForumLecture } from "@/types/forumLecture";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import { DataResponse } from "@/types/response.type";

interface DiscussionSheetProps {
  lectureId: string;
}
var stompClient: any = null;

function DiscussionSheet(props: DiscussionSheetProps) {
  const { lectureId } = props;
  const avatar = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.photos
  );
  const user = useAppSelector(
    (state) => state.persistedReducer.userReducer.user
  );
  const [getForumLectureById, { data: forumLectureData }] =
    useGetForumLectureByIdMutation();
  const [forumLecture, setForumLecture] = useState<ForumLecture[]>([]);
  const [pageIndex, setPageIndex] = useState(0);
  const [isLoadMore, setIsLoadMore] = useState(false);
  const [isOpenInputEditor, setIsOpenInputEditor] = useState(false);
  const [newComment, setNewComment] = useState<string>("");
  const [openForum, setOpenForum] = useState(false);

  useEffect(() => {
    setForumLecture([]);
    setPageIndex(0);
    getForumLectureById({
      keyword: [lectureId],
      pageIndex: pageIndex,
      pageSize: 10,
      isDecrease: true,
      sortBy: "created",
    });
    connect();
  }, [lectureId, pageIndex, getForumLectureById]);

  useEffect(() => {
    if (openForum) {
      setForumLecture([]);
      setPageIndex(0);
      getForumLectureById({
        keyword: [lectureId],
        pageIndex: pageIndex,
        pageSize: 10,
        isDecrease: true,
        sortBy: "created",
      });
      setOpenForum(false);
    }
  }, [openForum]);

  useEffect(() => {
    if (
      forumLectureData &&
      forumLectureData.totalRecords > forumLecture.length
    ) {
      setForumLecture((prevForumLecture) => [
        ...prevForumLecture,
        ...(forumLectureData.data as ForumLecture[]),
      ]);
      setIsLoadMore(false);
    }
  }, [forumLectureData]);

  useEffect(() => {
    if (newComment !== "") {
      sendValue();
    }
  }, [newComment]);

  const handleOpenComment = () => {
    setIsOpenInputEditor(true);
  };

  const fetchMoreData = () => {
    if ((forumLectureData?.totalRecords as number) > forumLecture?.length) {
      setIsLoadMore(true);
      setPageIndex((prevPageIndex) => prevPageIndex + 1);
    }
  };

  const connect = async () => {
    let Sock = new SockJS(process.env.NEXT_PUBLIC_SOCKET_END_POINT as string);
    // let Sock = new SockJS("http://localhost:8081/ws/courses");
    stompClient = await over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onMessageReceived = (payload: any) => {
    var payloadData = JSON.parse(payload.body);
    const newComment = (payloadData as DataResponse).data as ForumLecture;
    setForumLecture((prevForumLecture) => {
      const isIdExist = prevForumLecture.find(
        (comment) => comment.id === newComment.id
      );
      if (!isIdExist) {
        return [newComment, ...prevForumLecture];
      }
      const updateComment = prevForumLecture.map((comment) =>
        comment.id === newComment.id ? newComment : comment
      );
      return updateComment;
    });
  };

  const onConnected = () => {
    stompClient && stompClient.subscribe(
      `/rt/response/courses/forum-lecture/${lectureId}`,
      onMessageReceived
    );
  };

  const onError = (err: any) => {
    console.log(err);
  };

  const sendValue = () => {
    if (stompClient) {
      const createComment: ForumLecture = {
        comment: newComment,
        lectureId: lectureId,
        userId: user.id,
        userName: user.username,
        rawAvatar: avatar ? avatar : "",
      };
      stompClient.send(
        `/rt/request/courses/forum-lecture/add/${lectureId}`,
        {},
        JSON.stringify(createComment)
      );
    }
  };



  return (
    <Sheet>
      <SheetTrigger
        asChild
        className="fixed bottom-0 right-1/4 mr-5 mb-5 rounded-3xl gap-1 text-orange-500 xs:hidden z-20"
      >
        <Button variant="outline" onClick={()=>setOpenForum(true)} >
          <AiFillWechat />
          Hỏi đáp
        </Button>
      </SheetTrigger>
      <SheetContent
        className="overflow-y-scroll w-7/12"
        onScroll={(e) => {
          const target = e.target as Element;
          if (
            Math.ceil(target.scrollTop) + target.clientHeight >=
            target.scrollHeight - 10
          ) {
            fetchMoreData();
          }
        }}
      >
        <SheetHeader className="flex-start flex-col ml-2">
          <SheetTitle>
            {/* {forumLectureData?.totalRecords as number}  */}
            Bình Luận
          </SheetTitle>
          <SheetDescription className="font-sans">
            (Hãy bình luận nếu có thắc mắc)
          </SheetDescription>
        </SheetHeader>

        <div className="flex gap-2 items-center mt-10">
          <Image
            src={avatar ? `data:image/png;base64,${avatar}` : "/avatar.png"}
            alt="avatar"
            width={50}
            height={50}
            className="rounded-full min-w-[50px] h-[50px]"
          />
          {!isOpenInputEditor ? (
            <p
              className="border-b-4 border-y-none w-full text-gray-400 hover:cursor-pointer"
              onClick={() => handleOpenComment()}
            >
              Bạn có thắc mắc gì
            </p>
          ) : (
            <InputEditor
              setIsOpenInputEditor={setIsOpenInputEditor}
              parentId={lectureId}
              setTextInput={setNewComment}
            />
          )}
        </div>
        <div className="mt-10">
          {forumLecture?.map((item, index) => (
            <Comment
              key={item.id}
              data={item}
              stompClient={stompClient}
              lectureId={lectureId}
            />
          ))}
          {isLoadMore && <h1 className="item-center">Loading...</h1>}
          {/* <InfiniteScroll
            dataLength={forumLecture?.length as number}
            next={fetchMoreData}
            hasMore={true}
            height={"500"}
            className=""
            // style={{
            //   scrollbarWidth: "thin",
            //   scrollbarColor: "transparent transparent",
            // }}
            loader={<h1>Loading...</h1>}
          >
            {forumLecture?.map((item, index) => (
              <div
                key={index}
                // style={{
                //   height: 120,
                // }}
              >
                {forumLecture?.length}
                <Comment />
              </div>
            ))}
          </InfiniteScroll> */}
        </div>
      </SheetContent>
    </Sheet>
  );
}

export default DiscussionSheet;
