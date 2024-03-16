import React, {
  ChangeEvent,
  Fragment,
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
  Constant,
  CourseLectureField,
  LectureType,
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
import { IoAddOutline } from "react-icons/io5";
import Loading from "@/app/(root)/user/personal/loading";
import LectureTypeBox from "../Lecture/LectureTypeBox";
import Quiz from "../Lecture/Quiz/Quiz";
import Sorttable from "../Lecture/DragAndDrop/Sorttable";
import SectionComponent from "../Section/Section";
import CreateTitle from "../Lecture/CreateTitle";
import Sortable from "../Lecture/DragAndDrop/Sorttable";

interface CourseSectionProps {
  contentId: string;
  sections: Section[];
}

type LectureField = "name" | "url" | "fileName";

function CourseSectionForm(props: CourseSectionProps) {
  const { contentId, sections } = props;
  const [countLecture, setCountLecture] = useState(0);
  const [sectionFields, setSectionFields] = useState<Section[]>(sections);
  const [isLoading, setIsLoading] = useState(false);
  const [lectureFiles, setLectureFiles] = useState<{
    [sessionIndex: number]: {
      [lectureIndex: number]: { file: File; url: string };
    };
  }>({});
  const [isCreated, setCreated] = useState<number[]>([]);
  const [isCreatedNewLecture, setNewCreatedLecture] = useState([false, 0]);
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

  const handleOpenOptionsAddLecture = (
    isOpen: boolean,
    sectionIndex: number
  ) => {
    setNewCreatedLecture([isOpen, sectionIndex]);
  };

  const handleAddSection = async (newSection: Section) => {
    await addSection(newSection)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        // handleToast(fulfilled);
        setCreated([]);
      });
  };

  const handleUpdateSection = async (newSection: Section) => {
    await updateSection(newSection)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        // handleToast(fulfilled);
        setCreated([]);
      });
  };

  const handleUploadFiles = async () => {
    try {
      if (Object.keys(lectureFiles).length !== 0) {
        setIsLoading(true);
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
        setIsLoading(false);
        showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_CONTENT_SUCCESS);
        return urlList;
      }
      return null;
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
          let count = 0;
          const updatedUrlLecture = Object.values(lectureFiles).flatMap(
            (lectureIndexFiles) =>
              Object.values(lectureIndexFiles).map((lectureFile) => {
                lectureFile.url = urlList[count];
                count++;
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
    if (fieldType === LectureType.LECTURE) {
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
    if (fieldType === LectureType.SESSION) {
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
      fieldType === LectureType.LECTURE &&
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

    if (fieldType === LectureType.SESSION) {
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

  // if (isLoading) {
  //   return (
  //     <div>
  //       <Loading />
  //       <div className="flex-center text-sm italic text-orange-400">
  //         Đang lưu
  //       </div>
  //     </div>
  //   );
  // }

  const [isCreateNewSection, setCreateNewSection] = useState(false);
  const handleAddNewSection = () => {
    setCreateNewSection(!isCreateNewSection);
  };
  const [listTest, setListTest] = useState<Section[]>([
    {
      id: "1s",
      name: "1s",
      ordinalNumber: 1,
      lectures: [
        {
          ordinalNumber: 1,
          name: "Bai Tap 1",
          url: null,
          fileName: null,
          videoDuration: 0,
          lectureType: LectureType.VIDEO,
        },
        {
          ordinalNumber: 2,
          name: "Bai Tap 1",
          url: null,
          fileName: null,
          videoDuration: 0,
          lectureType: LectureType.VIDEO,
        },
      ],
      content: {
        id: "1",
      },
    },
    {
      id: "2s",
      name: "2s",
      ordinalNumber: 2,
      lectures: [
        {
          ordinalNumber: 1,
          name: "Bai Tap 1",
          url: null,
          fileName: null,
          videoDuration: 0,
          lectureType: LectureType.VIDEO,
        },
      ],
      content: {
        id: "2",
      },
    },
    {
      id: "3s",
      name: "3s",
      ordinalNumber: -1,
      lectures: [
        {
          ordinalNumber: 1,
          name: "Bai Tap 1",
          url: null,
          fileName: null,
          videoDuration: 0,
          lectureType: LectureType.VIDEO,
        },
        {
          ordinalNumber: 2,
          name: "Bai Tap 1",
          url: null,
          fileName: null,
          videoDuration: 0,
          lectureType: LectureType.VIDEO,
        },
        {
          ordinalNumber: 3,
          name: "Bai Tap 2",
          url: null,
          fileName: null,
          videoDuration: 0,
          lectureType: LectureType.QUIZ_TEST,
        },
      ],
      content: {
        id: "3",
      },
    },
    {
      id: "4s",
      name: "4s",
      ordinalNumber: 4,
      lectures: [
        {
          ordinalNumber: 3,
          name: "Bai Tap 2",
          url: null,
          fileName: null,
          videoDuration: 0,
          lectureType: LectureType.QUIZ_TEST,
        },
      ],
      content: {
        id: "3",
      },
    },
  ]);

  useEffect(() => {
    console.log(listTest);
  }, [listTest]);

  return (
    <div>
      <form onSubmit={(e) => onSubmit(e)} ref={formRef}>
        <div>
          <Sortable
            data={listTest}
            key={uuidv4()}
            type={Constant.SECTION}
            onDataChange={setListTest}
          />
          {/* {listTest.map((section, sectionIndex) =>
            !section.ordinalNumber || section.ordinalNumber !== -1 ? (
              <SectionComponent key={sectionIndex} section={section} />
            ) : null
          )} */}
          {isCreateNewSection ? (
            <CreateTitle
              handleIsOpen={setCreateNewSection}
              type={Constant.SECTION}
              key={uuidv4()}
              parentId={contentId}
            />
          ) : (
            <div
              className="hover: cursor-pointer flex items-center text-orange-500 gap-1 my-5"
              onClick={() => handleAddNewSection()}
            >
              <BiMessageSquareAdd /> Chương
            </div>
          )}
        </div>
        <Quiz />
      </form>
    </div>
  );
}

export default CourseSectionForm;
