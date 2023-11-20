"use client";
import React, { Fragment, useEffect, useRef, useState } from "react";
import { IoIosArrowBack } from "react-icons/io";
import { BiNotepad } from "react-icons/bi";
import { BsQuestionCircle } from "react-icons/bs";
import CourseContentLearning from "@/components/CourseContentLearning";
import DiscussionSheet from "@/components/DiscussionSheet";
import { useParams, useRouter } from "next/navigation";
import {
  useGetCourseAccessQuery,
  useLoadFileFromCloudQuery,
} from "@/redux/services/courseApi";
import { useAppSelector } from "@/redux/hooks";
import { Lecture, Section } from "@/types/section.type";
import { useGetContentByCourseIdQuery } from "@/redux/services/contentApi";
import Content from "@/types/content.type";
import { Course } from "@/types/course.type";
import { handleCountFieldsInSection } from "@/utils/function";

function PageLearning() {
  const param = useParams();
  const router = useRouter();
  const [courseId, setCourseId] = useState("");
  const [nameCourse, setNameCourse] = useState("");
  const [isAccess, setAccess] = useState<boolean>();
  const [sections, setSections] = useState<Section[]>([]);
  const [lecture, setLecture] = useState<Lecture>();
  const { totalLectureCount } = handleCountFieldsInSection(sections);
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.id
  );

  const { data: fileBase64, isSuccess: loadFileSuccess } =
    useLoadFileFromCloudQuery(lecture ? lecture.url : "");
  const { data: courseAccess, isSuccess: getCourseAccessSuccess } =
    useGetCourseAccessQuery({
      userId: userId,
      courseId: param.name as string,
    });
  const { data: contentData, isSuccess: getContentSuccess } =
    useGetContentByCourseIdQuery(courseId);

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
      setNameCourse(((contentData?.data as Content).course as Course)?.name);
    }
  }, [courseAccess, contentData]);

  useEffect(() => {
    if (isAccess === false) {
      router.push("/");
    }
  }, [isAccess]);

  const renderCourseContent = () => {
    return (
      <div className="sticky top-[100px]  custom-scrollbar overflow-y-scroll h-2/3">
        {sections
          ?.filter((section) => section.ordinalNumber !== -1)
          .map((section) => {
            return (
              <div key={section.id} className="">
                <CourseContentLearning
                  section={section}
                  setLecture={setLecture}
                />
              </div>
            );
          })}
      </div>
    );
  };

  const videoRef = useRef<HTMLVideoElement>(null);

  const handleTimeUpdate = () => {
    if (videoRef.current) {
      const currentTime = videoRef.current.currentTime;
      if (currentTime > (lecture?.videoDuration as number) - 60) {
        setSections((prevSections) =>
          prevSections.map((section) => {
            const updatedLectures = section.lectures.map((lec) =>
              lec.id === lecture?.id ? { ...lec, isSuccess: true } : lec
            );
            return { ...section, lectures: updatedLectures };
          })
        );
      }
    }
  };

  return (
    <div>
      {isAccess ? (
        <Fragment>
          <div className="bg-gray-900 text-white py-2 px-2 sticky top-0 z-20">
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
                        50%
                      </span>
                    </div>
                  </div>
                  <div>0/{totalLectureCount} bài học</div>
                </div>
                <div className="flex gap-2">
                  <BiNotepad />
                  Ghi Chú
                </div>
                <div className="flex gap-2">
                  <BsQuestionCircle />
                  Hướng Dẫn
                </div>
              </div>
            </div>
          </div>
          <div className="flex">
            <div className="w-9/12 custom-scrollbar overflow-y-scroll h-2/3">
              <DiscussionSheet />
              <div>
                <div>
                  {lecture?.videoDuration !== 0 ? (
                    <video
                      ref={videoRef}
                      controls
                      src={
                        loadFileSuccess
                          ? `data:video/mp4;base64,${fileBase64}`
                          : ""
                      }
                      className="w-full h-[500px]"
                      onTimeUpdate={handleTimeUpdate}
                      autoPlay
                    />
                  ) : (
                    <div>
                      <object
                        data={`data:application/pdf;base64,${fileBase64}#page=1&zoom=50`}
                        type="application/pdf"
                        className="w-full h-[500px]"
                      />
                    </div>
                  )}
                </div>

                <div className="text-xl font-bold mt-2 ml-4">
                  {lecture?.name}
                </div>
                <div className="min-h-[200px]"></div>
              </div>
            </div>
            <div className="w-3/12 sticky z-30">
              <div className=" py-2 ml-4 sticky top-[56px] z-30">
                Nội dung khóa học
              </div>
              {renderCourseContent()}
            </div>
          </div>
        </Fragment>
      ) : null}
    </div>
  );
}

export default PageLearning;
