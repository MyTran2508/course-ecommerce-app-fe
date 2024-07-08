"use client";
import React, { Fragment, useEffect, useRef, useState } from "react";
import { IoIosArrowBack, IoIosCloseCircleOutline } from "react-icons/io";
import { BiNotepad } from "react-icons/bi";
import { BsQuestionCircle } from "react-icons/bs";
import CourseContentLearning from "@/components/CourseContentLearning";
import DiscussionSheet from "@/components/Discussion/DiscussionSheet";
import { useParams, useRouter } from "next/navigation";
import {
  useLazyLoadFileFromCloudQuery,
  useLoadFileFromCloudQuery,
} from "@/redux/services/courseApi";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { ExQuiz, Lecture, Section } from "@/types/section.type";
import { useGetContentByCourseIdQuery } from "@/redux/services/contentApi";
import Content, { Description } from "@/types/content.type";
import { Course } from "@/types/course.type";
import {
  useGetByUserIdAndCourseIdQuery,
  useGetCourseAccessQuery,
  useUpdateCurrentProgressMutation,
} from "@/redux/services/courseProcessApi";
import { CourseProcess } from "@/types/courseProcess.type";
import PDFViewer from "@/components/PDF/PDFviewer";
import { debounce } from "lodash";
import { AiOutlineMenu } from "react-icons/ai";
import { LectureType } from "@/utils/resources";
import Quiz from "@/components/Lecture/Quiz/Client/Quiz";
import {
  renderDetails,
  renderRequirement,
  renderTargetConsumers,
} from "@/app/(root)/course/[id]/page";
import { current } from "@reduxjs/toolkit";
import AssignmentPractice from "@/components/Assignment/AssignmentPractice";

function PageLearning() {
  const param = useParams();
  const router = useRouter();
  const videoRef = useRef<HTMLVideoElement>(null);
  const [courseId, setCourseId] = useState(param.name as string);
  const [nameCourse, setNameCourse] = useState("");
  const [isAccess, setAccess] = useState<boolean>();
  const [sections, setSections] = useState<Section[]>([]);
  const [lecture, setLecture] = useState<Lecture>();
  const [course, setCourse] = useState<Course>();
  const [description, setDescription] = useState<Description>();
  const [readDocComplete, setReadDocComplete] = useState(false);
  const [quizIsComplete, setQuizIsComplete] = useState(false);
  const [courseProcess, setCourseProcess] = useState<CourseProcess>();
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.id
  );
  const [isOpenMenu, setOpenMenu] = useState<boolean>(true);

  const [loadFile, { data: fileBase64, isSuccess: loadFileSuccess }] =
    useLazyLoadFileFromCloudQuery();
  const { data: courseAccess, isSuccess: getCourseAccessSuccess } =
    useGetCourseAccessQuery({
      userId: userId,
      courseId: courseId,
    });
  const { data: courseProcessData, isSuccess: getCourseProcessSuccess } =
    useGetByUserIdAndCourseIdQuery({
      userId: userId,
      courseId: courseId,
    });
  const { data: contentData, isSuccess: getContentSuccess } =
    useGetContentByCourseIdQuery(courseId);
  const [updateCourseProcess] = useUpdateCurrentProgressMutation();

  const handleUpdateCourseProcess = async () => {
    await updateCourseProcess({
      userId: userId,
      courseId: courseId,
      exQuizId: lecture?.exQuiz?.id ?? "",
    });
  };

  const handleOpenMenu = () => {
    if (window.innerWidth <= 575) {
      setOpenMenu(!isOpenMenu);
    }
  };

  useEffect(() => {
    if (getCourseProcessSuccess) {
      console.log(courseProcessData);
      setCourseProcess(courseProcessData.data as CourseProcess);
    }
  }, [courseProcessData]);

  useEffect(() => {
    if (lecture?.videoDuration === 0) {
      loadFile(lecture?.url as string);
    }
    handleOpenMenu();
  }, [lecture]);

  useEffect(() => {
    if (getCourseAccessSuccess) {
      setAccess(courseAccess?.data as boolean);
      setCourseId(param.name as string);
    }
    if (getContentSuccess) {
      setSections(
        ((contentData?.data as Content).sections as Section[])?.filter(
          (section) => section.ordinalNumber !== -1
        )
      );
      setCourse((contentData?.data as Content).course as Course);
      setDescription((contentData?.data as Content).description);
      setNameCourse(((contentData?.data as Content).course as Course)?.name);
    }
  }, [courseAccess, contentData]);

  useEffect(() => {
    if (isAccess === false) {
      router.push("/");
    }
  }, [isAccess]);

  useEffect(() => {
    if (readDocComplete) {
      const ordinalNumber = calculateProgress(lecture?.id as string, sections);
      if (ordinalNumber > (courseProcess as CourseProcess).currentProgress) {
        handleUpdateCourseProcess();
        setReadDocComplete(false);
      }
      // if (
      //   !lecture?.isSuccess &&
      //   lecture?.videoDuration === 0 &&
      //   (lecture?.ordinalNumber as number) >
      //     (courseProcess as CourseProcess).currentProgress
      // ) {
      //   handleUpdateCourseProcess();
      //   setReadDocComplete(false);
      // }
    }
  }, [readDocComplete]);

  useEffect(() => {
    if (quizIsComplete) {
      const progress = calculateProgress(lecture?.id as string, sections);
      console.log(progress, (courseProcess as CourseProcess).currentProgress);
      if (progress > (courseProcess as CourseProcess).currentProgress) {
        handleUpdateCourseProcess();
        setQuizIsComplete(false);
      }
      // if (
      //   !lecture?.isSuccess &&
      //   lecture?.videoDuration === 0 &&
      //   (lecture?.ordinalNumber as number) >
      //     (courseProcess as CourseProcess).currentProgress
      // ) {
      //   handleUpdateCourseProcess();
      //   setQuizIsComplete(false);
      // }
    }
  }, [quizIsComplete]);

  const renderCourseContent = () => {
    return (
      <div className="sticky top-[100px]  custom-scrollbar overflow-y-scroll h-2/3">
        {sections
          ?.filter((section) => section.ordinalNumber !== -1)
          .map((section) => {
            return (
              <div key={section.id}>
                <CourseContentLearning
                  section={section}
                  setLecture={setLecture}
                  currentProgress={courseProcess?.currentProgress as number}
                  lectureActive={lecture?.id as string}
                  dataGetOrdinalNumber={sections}
                />
              </div>
            );
          })}
      </div>
    );
  };

  const handleTimeUpdate = debounce(() => {
    const progress = calculateProgress(lecture?.id as string, sections);
    if (videoRef.current) {
      const currentTime = videoRef.current.currentTime;

      if (
        currentTime / (lecture?.videoDuration as number) > 0.98 &&
        progress > (courseProcess as CourseProcess).currentProgress
      ) {
        handleUpdateCourseProcess();
      }
    }
  }, 1000);

  return (
    <div>
      {isAccess ? (
        <Fragment>
          <div className="bg-gray-900 text-white py-2 px-2 sticky top-0 z-40 xs:relative ">
            <div className="text-sm flex-between gap-2">
              <div
                className="flex-start font-bold gap-1 hover:cursor-pointer"
                onClick={() => router.push(`/my-courses`)}
              >
                <IoIosArrowBack className="text-xl" />
                {nameCourse}
              </div>
              <div className="flex gap-10 mr-10 items-center">
                <div className="flex gap-2 items-center">
                  <div className="relative w-10 h-10">
                    <div className="relative h-10 w-10">
                      <div className="absolute inset-0 border-2 border-blue-500 rounded-full" />
                      <div className="absolute inset-0 border-2 border-transparent rounded-full clip-[50%]" />
                    </div>
                    <div className="absolute inset-0 flex items-center justify-center">
                      <span className="text-sm font-semibold text-blue-500">
                        {Math.round(
                          (courseProcess?.rateProgress as number) * 100
                        )}
                        %
                      </span>
                    </div>
                  </div>
                  <div>
                    {courseProcess?.currentProgress}/
                    {courseProcess?.totalAmountOfLecture} bài học
                  </div>
                </div>
                {/* <div className="flex gap-2 xs:hidden">
                  <BiNotepad />
                  Ghi Chú
                </div>
                <div className="flex gap-2 xs:hidden">
                  <BsQuestionCircle />
                  Hướng Dẫn
                </div> */}
              </div>
            </div>
          </div>
          <div className="flex xs:flex-col">
            <div className="w-9/12 custom-scrollbar overflow-y-scroll h-2/3 xs:w-full xs:overflow-hidden">
              {lecture && <DiscussionSheet lectureId={lecture?.id as string} />}
              <div>
                <div>
                  {lecture?.videoDuration !== 0 ? (
                    <Fragment>
                      {(lecture?.url?.length as number) > 0 ? (
                        <video
                          ref={videoRef}
                          controls
                          src={
                            lecture?.url as string
                            // loadFileSuccess
                            //   ? `data:video/mp4;base64,${fileBase64}`
                            //   : ""
                          }
                          className="w-full h-[500px] xs:h-[200px]"
                          onTimeUpdate={handleTimeUpdate}
                          autoPlay
                        />
                      ) : null}
                    </Fragment>
                  ) : (
                    // <div>
                    //   <object
                    //     data={`data:application/pdf;base64,${fileBase64}#page=1&zoom=50`}
                    //     type="application/pdf"
                    //     className="w-full h-[500px]"
                    //   />
                    // </div>

                    <Fragment>
                      {lecture.lectureType === LectureType.DOCUMENT ? (
                        <PDFViewer
                          fileBase64={fileBase64 as string}
                          setReadDocComplete={setReadDocComplete}
                          lectureUrl={lecture?.url as string}
                        />
                      ) : (
                        <Fragment>
                          {lecture.lectureType === LectureType.ASSIGNMENT ? (
                            <AssignmentPractice lecture={lecture} />
                          ) : (
                            <Fragment>
                              <div className="text-xl font-bold my-2 ml-10 xs:ml-[20px]">
                                {"Bài Kiểm Tra: " + lecture?.name}
                              </div>
                              <Quiz
                                exQuiz={lecture.exQuiz as ExQuiz}
                                setQuizIsCompleted={setQuizIsComplete}
                              />
                            </Fragment>
                          )}
                        </Fragment>
                      )}
                    </Fragment>
                  )}
                </div>

                <div className="text-xl font-bold mt-2 ml-4">
                  {lecture?.lectureType !== LectureType.QUIZ_TEST
                    ? lecture?.name
                    : null}
                </div>
                <hr className="border-gray-300 my-10" />
                <div className="xl:w-2/3 ml-10 xs:m-6">
                  <h2 className="text-2xl font-bold">Mô tả khóa học</h2>
                  <div className="text-3xl font-bold mb-6">{course?.name}</div>
                  <div className="font-light mb-2">{course?.subTitle}</div>
                  {renderTargetConsumers(description as Description)}
                  {renderRequirement(description as Description)}
                  {renderDetails(description as Description)}
                </div>
              </div>
            </div>
            {isOpenMenu ? (
              <Fragment>
                <div className="w-3/12 sticky z-30 xs:w-full xs:absolute xs:z-30 bg-white h-full">
                  <div className="flex-between py-2 ml-4 sticky top-[56px] z-30 bg-white">
                    <div>Nội dung khóa học</div>
                    <IoIosCloseCircleOutline
                      onClick={() => handleOpenMenu()}
                      className="lg:hidden text-xl mr-2"
                    />
                  </div>

                  {renderCourseContent()}
                </div>
              </Fragment>
            ) : (
              <div
                className="mx-2 lg:hidden fixed bottom-0 z-30"
                onClick={() => handleOpenMenu()}
              >
                <AiOutlineMenu className="text-3xl" />
              </div>
            )}
          </div>
        </Fragment>
      ) : null}
    </div>
  );
}
export const calculateProgress = (
  lectureId: string,
  sections: Section[]
): number => {
  let progress = 0;
  const targetLectureNumber = lectureId;

  if (targetLectureNumber === undefined) return 0;
  sections?.some((section) => {
    return (section.lectures as Lecture[])
      .filter((lecture) => lecture.ordinalNumber !== -1)
      .some((lecture, index) => {
        progress++;
        if (lecture.id === targetLectureNumber) {
          return true;
        }
        return false;
      });
  });
  return progress;
};

export default PageLearning;
