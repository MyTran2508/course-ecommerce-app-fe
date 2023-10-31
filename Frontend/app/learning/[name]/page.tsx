"use client";
import React, { useEffect, useState } from "react";
import { IoIosArrowBack } from "react-icons/io";
import { BiNotepad } from "react-icons/bi";
import { BsQuestionCircle } from "react-icons/bs";
import CourseContentLearning from "@/components/CourseContentLearning";
import DiscussionSheet from "@/components/DiscussionSheet";

function PageLearning() {
  const [videoUrl, setVideoUrl] = useState("");

  const renderCourseContent = () => {
    return (
      <div className="overflow-y-scroll h-[500px] custom-scrollbar">
        <CourseContentLearning videoUrl={videoUrl} setVideoUrl={setVideoUrl} />
        <CourseContentLearning />
        <CourseContentLearning />
        <CourseContentLearning />
        <CourseContentLearning />
      </div>
    );
  };

  return (
    <div>
      <div className="bg-gray-900 text-white py-2 px-2">
        <div className="text-sm flex-between gap-2">
          <div className="flex-start font-bold gap-1">
            <IoIosArrowBack className="text-xl hover:cursor-pointer" />
            Lập Trình JavaScript Căn Bản
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
              <div>97/204 bài học</div>
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
        <div className="w-9/12 custom-scrollbar overflow-y-scroll h-[550px]">
          <DiscussionSheet />
          <div>
            <div>
              <video
                controls
                src={
                  "http://localhost:8081/api/courses/download/1698332371869_002%20What%20are%20Microservices%20Really%20All%20About.mp4"
                }
                className="w-full h-full min-h-[500px] "
                autoPlay
              />
            </div>

            <div className="text-xl font-bold mt-2 ml-4">
              Giới Thiệu Khóa Học
            </div>
            <div className="min-h-[200px]"></div>
          </div>
        </div>
        <div className="w-3/12">
          <div className=" py-2 ml-4">Nội dung khóa học</div>
          {renderCourseContent()}
        </div>
      </div>
    </div>
  );
}

export default PageLearning;
