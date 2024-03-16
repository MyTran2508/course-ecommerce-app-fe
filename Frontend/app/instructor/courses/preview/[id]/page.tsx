"use client";
import React, { Fragment, useEffect, useRef, useState } from "react";
import { IoIosArrowBack } from "react-icons/io";
import { BiNotepad } from "react-icons/bi";
import { BsQuestionCircle } from "react-icons/bs";
import CourseContentLearning from "@/components/CourseContentLearning";
import DiscussionSheet from "@/components/Discussion/DiscussionSheet";
import { useParams, useRouter } from "next/navigation";
import {
  useLazyLoadFileFromCloudQuery,
  useLoadFileFromCloudQuery,
} from "@/redux/services/courseApi";
import { useAppSelector } from "@/redux/hooks";
import { Lecture, Section } from "@/types/section.type";
import { useGetContentByCourseIdQuery } from "@/redux/services/contentApi";
import Content from "@/types/content.type";
import { Course } from "@/types/course.type";
import {
  useGetByUserIdAndCourseIdQuery,
  useGetCourseAccessQuery,
  useUpdateCurrentProgressMutation,
} from "@/redux/services/courseProcessApi";
import { CourseProcess } from "@/types/courseProcess.type";
import PDFViewer from "@/components/PDF/PDFviewer";
import { debounce } from "lodash";
import { handleCountFieldsInSection } from "@/utils/function";

function PreviewPage() {
  const param = useParams();
  const router = useRouter();
  const videoRef = useRef<HTMLVideoElement>(null);
  const [courseId, setCourseId] = useState(param.id as string);
  const [nameCourse, setNameCourse] = useState("");
  const [sections, setSections] = useState<Section[]>([]);
  const [lecture, setLecture] = useState<Lecture>();
  const [readdoc, setReadDocComplete] = useState(false);
  const [loadFile, { data: fileBase64, isSuccess: loadFileSuccess }] =
    useLazyLoadFileFromCloudQuery();

  const { data: contentData, isSuccess: getContentSuccess } =
    useGetContentByCourseIdQuery(courseId);

  useEffect(() => {
    if (getContentSuccess) {
      setSections(
        ((contentData?.data as Content).sections as Section[])?.filter(
          (section) => section.ordinalNumber !== -1
        )
      );
      setNameCourse(((contentData?.data as Content).course as Course)?.name);
    }
  }, [contentData]);

  useEffect(() => {
    if (lecture?.videoDuration === 0) {
      loadFile(lecture.url);
    }
  }, [lecture]);

  const renderCourseContent = () => {
    const { totalLectureCount } = handleCountFieldsInSection(sections);
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
                  currentProgress={totalLectureCount}
                  lectureActive={lecture?.ordinalNumber as number}
                />
              </div>
            );
          })}
      </div>
    );
  };

  return (
    <div>
      <Fragment>
        <div className="bg-gray-900 text-white py-4 px-2 sticky top-0 z-20">
          <div className="text-sm flex-between gap-2">
            <div
              className="flex-start font-bold gap-1 hover:cursor-pointer"
              onClick={() =>
                router.push(`/instructor/courses/${courseId}/manage/content`)
              }
            >
              <IoIosArrowBack className="text-xl" />
              {nameCourse}
            </div>
            <div className="flex gap-10 mr-10 items-center">
              {/* <div className="flex gap-2">
                <BiNotepad />
                Ghi Chú
              </div>
              <div className="flex gap-2">
                <BsQuestionCircle />
                Hướng Dẫn
              </div> */}
            </div>
          </div>
        </div>
        <div className="flex">
          <div className="w-9/12 custom-scrollbar overflow-y-scroll h-2/3">
            {/* <DiscussionSheet /> */}
            <div>
              <div>
                {lecture?.videoDuration !== 0 ? (
                  <video
                    ref={videoRef}
                    controls
                    src={
                      lecture?.url
                      // loadFileSuccess
                      //   ? `data:video/mp4;base64,${fileBase64}`
                      //   : ""
                    }
                    className="w-full h-[500px]"
                    autoPlay
                  />
                ) : (
                  // <div>
                  //   <object
                  //     data={`data:application/pdf;base64,${fileBase64}#page=1&zoom=50`}
                  //     type="application/pdf"
                  //     className="w-full h-[500px]"
                  //   />
                  // </div>

                  <PDFViewer
                    fileBase64={fileBase64 as string}
                    setReadDocComplete={setReadDocComplete}
                    lectureUrl={lecture?.url}
                  />
                )}
              </div>

              <div className="text-xl font-bold mt-2 ml-4">{lecture?.name}</div>
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
    </div>
  );
}

export default PreviewPage;
