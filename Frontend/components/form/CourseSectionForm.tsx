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
import { course, setStatusSaveCourse } from "@/redux/features/courseSlice";
import { BiMessageSquareAdd } from "react-icons/bi";
import {
  CourseLectureField,
  CourseSessionField,
  StatusCode,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import {
  useAddSectionMutation,
  useUpdateSectionByIdMutation,
} from "@/redux/services/contentApi";
import showToast from "@/utils/showToast";
import { DataResponse } from "@/types/response.type";
import { Label } from "../ui/label";
import { AiTwotoneDelete } from "react-icons/ai";
import { Lecture, Section } from "@/types/section.type";
import { useUploadSectionFilesMutation } from "@/redux/services/sectionApi";
import { handleGetDurationFormVideo } from "@/utils/function";
import { v4 as uuidv4 } from "uuid";

interface CourseSectionProps {
  contentId: string;
  sections: Section[];
}

type LectureField = "name" | "url" | "fileName";

function CourseSectionForm(props: CourseSectionProps) {
  const { contentId, sections } = props;
  const [countLecture, setCountLecture] = useState(0);
  const [sectionFields, setSectionFields] = useState<Section[]>(sections);

  const [lectureFiles, setLectureFiles] = useState<{
    [sessionIndex: number]: {
      [lectureIndex: number]: { file: File; url: string };
    };
  }>({});
  const [isCreated, setCreated] = useState<number[]>([]);

  const formRef = useRef<HTMLFormElement>(null);
  const inputFileRefs = useRef<Record<string, HTMLInputElement | null>>({});

  const saveStatus = useAppSelector(
    (state) => state.courseReducer.saveCourseStatus
  );
  const dispatch = useAppDispatch();
  const [addSection] = useAddSectionMutation();
  const [updateSection] = useUpdateSectionByIdMutation();
  const [uploadFiles] = useUploadSectionFilesMutation();

  useEffect(() => {
    setSectionFields(sections);
    if (inputFileRefs.current) {
      for (const inputRef of Object.values(inputFileRefs.current)) {
        if (inputRef) {
          inputRef.value = "";
        }
      }
    }
  }, [sections]);
  const handleAddSection = async (newSection: Section) => {
    await addSection(newSection)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        handleToast(fulfilled);
        setCreated([]);
      });
  };

  const handleUpdateSection = async (newSection: Section) => {
    await updateSection(newSection)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        handleToast(fulfilled);
        setCreated([]);
      });
  };

  const handleUploadFiles = async () => {
    try {
      const lectureFilesArray: File[] = Object.values(lectureFiles).flatMap(
        (lectureIndexFiles) =>
          Object.values(lectureIndexFiles).map(
            (lectureFile) => lectureFile.file
          )
      );

      const [uploadFilesResponse] = await Promise.all([
        lectureFilesArray ? uploadFiles(lectureFilesArray) : null,
      ]);

      let urlList: string[] = [];
      if (uploadFilesResponse && "data" in uploadFilesResponse) {
        urlList = uploadFilesResponse.data.data as string[];
      }
      setLectureFiles({});

      return urlList;
    } catch (error) {
      console.error(error);
      return null;
    }
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

  const checkEmptyNames = () => {
    let isChecked = true;
    sectionFields.forEach((section, sessionIndex) => {
      if (section.name === "" && section.ordinalNumber !== -1) {
        showToast(
          ToastStatus.WARNING,
          `Vui lòng điền đầy đủ thông tin vào Chương Mới`
        );
        isChecked = false;
      }
      section.lectures.forEach((lecture, lectureIndex) => {
        if (lecture.name === "" || lecture.fileName === "") {
          showToast(
            ToastStatus.WARNING,
            `Vui lòng điền đầy đủ thông tin vào Bài Học Mới tại Chương ${section.ordinalNumber}`
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
      if (contentId) {
        const urlList = await handleUploadFiles();
        if (urlList) {
          const updatedUrlLecture = Object.values(lectureFiles).flatMap(
            (lectureIndexFiles) =>
              Object.values(lectureIndexFiles).map((lectureFile, index) => {
                lectureFile.url = urlList[index];
                return lectureFile;
              })
          );
          setLectureFiles(updatedUrlLecture);
          const sectionList = handleUpdateURLSection();
          handleSectionList(sectionList);
        }
      }
    }
  };

  const handleSectionList = (sections: Section[]) => {
    let countLecture = 1;
    let countSection = 1;
    sections.forEach((section) => {
      if (section.ordinalNumber !== -1) {
        section.ordinalNumber = countSection;
        countSection++;

        section.lectures.forEach((lecture) => {
          if (lecture.ordinalNumber !== -1) {
            lecture.ordinalNumber = countLecture;
            countLecture++;
          }
        });
      }

      if (section.id) {
        handleUpdateSection(section);
      } else {
        //set id
        section.content ??= {};
        section.content.id = contentId;

        handleAddSection(section);
      }
    });
    console.log(sections);
    console.log(lectureFiles);
  };

  const handleUpdateURLSection = () => {
    let updatedSessionFields = [...sectionFields];
    updatedSessionFields = sectionFields.map((section, sectionIndex) => {
      const updatedLectures: Lecture[] = section.lectures.map(
        (lecture, lectureIndex) => {
          const url = lectureFiles[sectionIndex]?.[lectureIndex]?.url;

          return {
            ...lecture,
            url: url || lecture.url,
          };
        }
      );
      return {
        ...section,
        lectures: updatedLectures,
      };
    });
    return updatedSessionFields;
  };

  const handleAddField = (fieldType: string, sectionIndex: number) => {
    if (fieldType === CourseSessionField.LECTURE) {
      const fieldName = `Tên Bài Học`;
      setCountLecture(countLecture + 1);
      const updatedSessionFields = [...sectionFields];
      updatedSessionFields[sectionIndex].lectures.push({
        name: fieldName,
        fileName: "",
        url: "",
      });
      setSectionFields(updatedSessionFields);
    }
    if (fieldType === CourseSessionField.SESSION) {
      const newSessionField = {
        name: `Tên Chương`,
        lectures: [],
      };
      setSectionFields([...sectionFields, newSessionField]);
    }
    if (!isCreated.includes(sectionIndex)) {
      setCreated((prevState) => [...prevState, sectionIndex]);
    }
  };

  const handleRemoveField = (
    fieldType: string,
    sectionIndex: number,
    lectureIndex?: number
  ) => {
    if (
      fieldType === CourseSessionField.LECTURE &&
      typeof lectureIndex !== "undefined"
    ) {
      const updatedSessionFields = [...sectionFields];
      if (updatedSessionFields[sectionIndex].lectures[lectureIndex].id) {
        updatedSessionFields[sectionIndex].lectures[
          lectureIndex
        ].ordinalNumber = -1;
      } else {
        updatedSessionFields[sectionIndex].lectures.splice(lectureIndex, 1);
        const updatedLectureFiles = { ...lectureFiles };
        if (updatedLectureFiles[sectionIndex]) {
          delete updatedLectureFiles[sectionIndex][lectureIndex];
        }
        setLectureFiles(updatedLectureFiles);
      }

      setSectionFields(updatedSessionFields);
      setCountLecture(countLecture - 1);
    }

    if (fieldType === CourseSessionField.SESSION) {
      const updatedSessionFields = [...sectionFields];
      if (updatedSessionFields[sectionIndex].id) {
        updatedSessionFields[sectionIndex].ordinalNumber = -1;
      } else {
        updatedSessionFields.splice(sectionIndex, 1);

        const updatedLectureFiles = { ...lectureFiles };
        delete updatedLectureFiles[sectionIndex];
        setLectureFiles(updatedLectureFiles);
      }
      setSectionFields(updatedSessionFields);
    }
    if (!isCreated.includes(sectionIndex)) {
      setCreated((prevState) => [...prevState, sectionIndex]);
    }
  };

  const handleInputChange = async (
    sessionIndex: number,
    lectureIndex: number,
    event: ChangeEvent<HTMLInputElement>,
    field: LectureField
  ) => {
    const updatedSessionFields = [...sectionFields];
    if (field === CourseLectureField.FILENAME) {
      const fileInput = event.target;
      const file = fileInput?.files?.[0];

      if (file) {
        updatedSessionFields[sessionIndex].lectures[lectureIndex].fileName =
          file.name;
        const duration = await handleGetDurationFormVideo(file);
        updatedSessionFields[sessionIndex].lectures[
          lectureIndex
        ].videoDuration = duration;

        const updatedLectureFiles = { ...lectureFiles };
        if (!updatedLectureFiles[sessionIndex]) {
          updatedLectureFiles[sessionIndex] = {};
        }
        updatedLectureFiles[sessionIndex][lectureIndex] = {
          file: file,
          url: "",
        };
        setLectureFiles(updatedLectureFiles);
        inputFileRefs.current[lectureIndex] = event.target;
      }
    } else {
      updatedSessionFields[sessionIndex].lectures[lectureIndex][field] =
        event.target.value;
    }
    setSectionFields(updatedSessionFields);
  };

  return (
    <div>
      <form onSubmit={(e) => onSubmit(e)} ref={formRef}>
        <div>
          {sectionFields.map((sectionField, sectionIndex) =>
            !sectionField.ordinalNumber || sectionField.ordinalNumber !== -1 ? (
              <div
                key={sectionIndex}
                className="bg-gray-100 flex flex-col py-4 px-3 my-4 mr-20"
              >
                <div className="flex items-center gap-3 ">
                  <Label className="">
                    Chương {sectionField.ordinalNumber}:{" "}
                  </Label>
                  <Input
                    value={sectionField.name}
                    onChange={(e) => {
                      const updatedSessionFields = [...sectionFields];
                      updatedSessionFields[sectionIndex].name = e.target.value;
                      setSectionFields(updatedSessionFields);
                    }}
                    className="w-30 focus-visible:ring-1 focus-visible:ring-orange-200 disabled:opacity-1 disabled:cursor-default border-none rounded-md"
                    autoComplete="off"
                  />
                  <div
                    className="hover:cursor-pointer  text-3xl pb-3"
                    onClick={() =>
                      handleRemoveField(
                        CourseSessionField.SESSION,
                        sectionIndex
                      )
                    }
                  >
                    <AiTwotoneDelete className="pt-1" />
                  </div>
                  <div>
                    {isCreated.map((index) => {
                      return index === sectionIndex ? (
                        <div key={index} className="text-orange-300">
                          Bấm <span className="italic font-bold">lưu</span> để
                          cập nhật
                        </div>
                      ) : null;
                    })}
                  </div>
                </div>
                <div className="flex flex-col mx-10">
                  {sectionField.lectures.map((lecture, lectureIndex) =>
                    !lecture.ordinalNumber || lecture.ordinalNumber !== -1 ? (
                      <div
                        key={lectureIndex}
                        className="bg-white my-2 px-3 py-3 rounded-sm"
                      >
                        <div className="flex items-center gap-2 ">
                          <Label className="">
                            {lecture.ordinalNumber ? (
                              `Bài Học ${lecture.ordinalNumber}`
                            ) : (
                              <span className="text-orange-400">
                                Bài Học Mới
                              </span>
                            )}
                          </Label>
                          <Input
                            className="w-30 rounded-md focus-visible:ring-orange-200 focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                            value={lecture.name}
                            onChange={(event) =>
                              handleInputChange(
                                sectionIndex,
                                lectureIndex,
                                event,
                                CourseLectureField.NAME
                              )
                            }
                            autoComplete="off"
                          />
                          <div
                            className="hover:cursor-pointer text-3xl pb-3"
                            onClick={() =>
                              handleRemoveField(
                                CourseSessionField.LECTURE,
                                sectionIndex,
                                lectureIndex
                              )
                            }
                          >
                            <AiTwotoneDelete className="pt-1" />
                          </div>
                        </div>
                        <div className="px-20 mt-3">
                          <div className="flex gap-4 items-center">
                            <Label>
                              File Bài Học
                              <span className="italic">
                                (có thể là file video hoặc document):
                              </span>
                            </Label>
                          </div>
                          <Label className="pl-8 italic pt-4 font-bold">
                            {lecture.fileName
                              ? lecture.fileName
                              : "Chưa có file tải lên"}
                          </Label>

                          <Input
                            className="w-70 my-5 mx-5"
                            type="File"
                            accept=".mp4, .pdf"
                            placeholder="Video URL"
                            onChange={(event) =>
                              handleInputChange(
                                sectionIndex,
                                lectureIndex,
                                event,
                                CourseLectureField.FILENAME
                              )
                            }
                            ref={(ref) =>
                              (inputFileRefs.current[uuidv4()] = ref)
                            }
                          />
                        </div>
                      </div>
                    ) : null
                  )}
                  <div
                    className="hover: cursor-pointer flex items-center text-orange-500 gap-1 mb-5"
                    onClick={() =>
                      handleAddField(CourseSessionField.LECTURE, sectionIndex)
                    }
                  >
                    <BiMessageSquareAdd /> Bài Học
                  </div>
                </div>
              </div>
            ) : null
          )}

          <div
            className="hover: cursor-pointer flex items-center text-orange-500 gap-1 mb-5"
            onClick={() => handleAddField(CourseSessionField.SESSION, 1)}
          >
            <BiMessageSquareAdd /> Chương
          </div>
        </div>
      </form>
    </div>
  );
}

export default CourseSectionForm;
