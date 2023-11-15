import React, {
  ChangeEvent,
  MouseEvent,
  useEffect,
  useRef,
  useState,
} from "react";
import { Input } from "../ui/input";
import * as _ from "lodash";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import { setStatusSaveCourse } from "@/redux/features/courseSlice";
import { BiMessageSquareAdd } from "react-icons/bi";
import { StatusCode, ToastMessage, ToastStatus } from "@/utils/resources";
import Content from "@/types/content.type";
import {
  useAddContentMutation,
  useUpdateContentMutation,
} from "@/redux/services/contentApi";
import showToast from "@/utils/showToast";
import { DataResponse } from "@/types/response.type";
import { Label } from "../ui/label";
import { AiTwotoneDelete } from "react-icons/ai";

const handleSetDefaultValueForm = (content: Content | null) => {};

interface Session {
  session: string;
  lectures: Lecture[];
}
interface Lecture {
  name: string;
  video: string;
}
type LectureField = "name" | "video";
function CourseSessionForm(props: any) {
  const { content, courseId } = props;
  const [countLecture, setCountLecture] = useState(0);
  const [lectureFields, setLectureFields] = useState<Lecture[]>([]);
  const [sessionFields, setSessionFields] = useState<Session[]>([]);
  const formRef = useRef<HTMLFormElement>(null);
  const saveStatus = useAppSelector(
    (state) => state.courseReducer.saveCourseStatus
  );
  const dispatch = useAppDispatch();
  const [addContent] = useAddContentMutation();
  const [updateContent] = useUpdateContentMutation();

  const handleAddContent = async (newContent: Content) => {
    await addContent(newContent)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        handleToast(fulfilled);
      });
  };
  const handleUpdateContent = async (newContent: Content) => {
    await updateContent(newContent)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        handleToast(fulfilled);
      });
  };
  const handleToast = (dataResult: DataResponse) => {
    if (dataResult?.statusCode === StatusCode.REQUEST_SUCCESS) {
      showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_CONTENT_SUCCESS);
    } else {
      showToast(ToastStatus.ERROR, ToastMessage.UPDATE_CONTENT_FAIL);
    }
  };

  useEffect(() => {
    if (saveStatus && formRef.current) {
      formRef.current.requestSubmit();
      dispatch(setStatusSaveCourse(false));
    }
  }, [saveStatus]);

  // useEffect(() => {
  //   setDefaultValueForm(handleSetDefaultValueForm(content));
  // }, [content]);

  const checkEmptyNames = () => {
    let isChecked = true;
    sessionFields.forEach((session, sessionIndex) => {
      if (session.session === "") {
        console.log(`Empty session name at session index ${sessionIndex}`);
        isChecked = false;
      }
      session.lectures.forEach((lecture, lectureIndex) => {
        if (lecture.name === "") {
          console.log(
            `Empty lecture name at session index ${sessionIndex}, lecture index ${lectureIndex}`
          );
          isChecked = false;
        }
      });
    });
    return isChecked;
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (checkEmptyNames()) {
      console.log("Session Fields:", sessionFields);
      console.log("Lecture Fields:", lectureFields);
    }
    // if (content?.id) {
    //   newContent.id = content.id;
    //   handleUpdateContent(newContent);
    // } else {
    //   handleAddContent(newContent);
    // }
  };

  const handleAddField = (fieldType: string, sessionIndex: number) => {
    if (fieldType === "lecture") {
      const fieldName = `Tên Bài Học`;
      setLectureFields([...lectureFields, { name: fieldName, video: "" }]);
      setCountLecture(countLecture + 1);

      const updatedSessionFields = [...sessionFields];
      updatedSessionFields[sessionIndex].lectures.push({
        name: fieldName,
        video: "",
      });
      setSessionFields(updatedSessionFields);
    }
    if (fieldType === "session") {
      const newSessionField = {
        session: `Tên Chương`,
        lectures: [],
      };
      setSessionFields([...sessionFields, newSessionField]);
    }
  };

  const handleRemoveField = (
    fieldType: string,
    sessionIndex: number,
    lectureIndex?: number
  ) => {
    if (fieldType === "lecture" && typeof lectureIndex !== "undefined") {
      const updatedLectureFields = [...lectureFields];
      updatedLectureFields.splice(lectureIndex, 1);
      setLectureFields(updatedLectureFields);

      const updatedSessionFields = [...sessionFields];
      updatedSessionFields[sessionIndex].lectures.splice(lectureIndex, 1);
      setSessionFields(updatedSessionFields);

      setCountLecture(countLecture - 1);
    }

    if (fieldType === "session") {
      const updatedSessionFields = [...sessionFields];
      updatedSessionFields.splice(sessionIndex, 1);
      setSessionFields(updatedSessionFields);
    }
  };

  const handleInputChange = (
    sessionIndex: number,
    lectureIndex: number,
    value: ChangeEvent<HTMLInputElement>,
    field: LectureField
  ) => {
    const updatedSessionFields = [...sessionFields];
    const updatedLectureFields = [...lectureFields];
    if (field === "video") {
      const fileInput = value.target;
      const file = fileInput?.files?.[0];
      console.log(file);

      if (file) {
        updatedSessionFields[sessionIndex].lectures[lectureIndex].video =
          file.name;
        updatedLectureFields[lectureIndex][field] = file.name;
      }
    } else {
      updatedSessionFields[sessionIndex].lectures[lectureIndex][field] =
        value.target.value;
      updatedLectureFields[lectureIndex][field] = value.target.value;
    }
    setSessionFields(updatedSessionFields);
    setLectureFields(updatedLectureFields);
  };

  return (
    <div>
      <form onSubmit={(e) => onSubmit(e)} ref={formRef}>
        <div>
          {sessionFields.map((sessionField, sessionIndex) => (
            <div
              key={sessionIndex}
              className="bg-gray-100 flex flex-col py-4 px-3 my-4 mr-20"
            >
              <div className="flex items-center gap-3 ">
                <Label className="">Chương {sessionIndex + 1}: </Label>
                <Input
                  value={sessionField.session}
                  onChange={(e) => {
                    const updatedSessionFields = [...sessionFields];
                    updatedSessionFields[sessionIndex].session = e.target.value;
                    setSessionFields(updatedSessionFields);
                  }}
                  className="w-30 focus-visible:ring-1 focus-visible:ring-orange-200 disabled:opacity-1 disabled:cursor-default border-none rounded-md"
                  autoComplete="off"
                />

                <div
                  className="hover:cursor-pointer  text-3xl pb-3"
                  onClick={() => handleRemoveField("session", sessionIndex)}
                >
                  <AiTwotoneDelete />
                </div>
              </div>
              <div className="flex flex-col mx-10">
                {sessionField.lectures.map((lecture, lectureIndex) => (
                  <div
                    key={lectureIndex}
                    className="bg-white my-2 px-3 py-3 rounded-sm"
                  >
                    <div className="flex items-center gap-2 ">
                      <Label className="">Bài Học {lectureIndex + 1}: </Label>
                      <Input
                        className="w-30 rounded-md focus-visible:ring-orange-200 focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                        value={lecture.name}
                        onChange={(event) =>
                          handleInputChange(
                            sessionIndex,
                            lectureIndex,
                            event,
                            "name"
                          )
                        }
                        autoComplete="off"
                      />
                    </div>
                    <div className="px-20 mt-3">
                      <div className="flex gap-4">
                        <Label className="">Video Bài Học: </Label>
                        <Label>
                          {lecture.video ? lecture.video : "Chưa có file video"}
                        </Label>
                      </div>
                      <Input
                        className="w-70 my-5 mx-5"
                        type="File"
                        placeholder="Video URL"
                        onChange={(event) =>
                          handleInputChange(
                            sessionIndex,
                            lectureIndex,
                            event,
                            "video"
                          )
                        }
                      />

                      <div
                        className="hover:cursor-pointer text-3xl pb-3"
                        onClick={() =>
                          handleRemoveField(
                            "lecture",
                            sessionIndex,
                            lectureIndex
                          )
                        }
                      >
                        <AiTwotoneDelete />
                      </div>
                    </div>
                  </div>
                ))}
                <div
                  className="hover: cursor-pointer flex items-center text-orange-500 gap-1 mb-5"
                  onClick={() => handleAddField("lecture", sessionIndex)}
                >
                  <BiMessageSquareAdd /> Bài Học
                </div>
              </div>
            </div>
          ))}

          <div
            className="hover: cursor-pointer flex items-center text-orange-500 gap-1 mb-5"
            onClick={() => handleAddField("session", 1)}
          >
            <BiMessageSquareAdd /> Chương
          </div>
        </div>
      </form>
      {/* {sessionFields.map((sessionField, sessionIndex) => (
        <div key={sessionIndex}>
          <input
            type="text"
            value={sessionField.session}
            onChange={(e) => {
              const updatedSessionFields = [...sessionFields];
              updatedSessionFields[sessionIndex].session = e.target.value;
              setSessionFields(updatedSessionFields);
            }}
          />

          {sessionField.lectures.map((lecture, lectureIndex) => (
            <div key={lectureIndex}>
              <input
                type="text"
                value={lecture.name}
                onChange={(e) =>
                  handleInputChange(
                    sessionIndex,
                    lectureIndex,
                    e.target.value,
                    "name"
                  )
                }
              />
              <input
                type="text"
                value={lecture.video}
                placeholder="Video URL"
                onChange={(e) =>
                  handleInputChange(
                    sessionIndex,
                    lectureIndex,
                    e.target.value,
                    "video"
                  )
                }
              />
            </div>
          ))}

          <button onClick={() => handleAddField1("lecture", sessionIndex)}>
            Add Lecture
          </button>
        </div>
      ))}

      <button onClick={() => handleAddField1("session", 1)}>Add Session</button>
      <button onClick={handlePrintValues}>Print Values</button> */}
    </div>
  );
}

export default CourseSessionForm;
