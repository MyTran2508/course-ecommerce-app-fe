"use client";
import Loading from "@/app/(root)/user/personal/loading";
import CourseContentForm from "@/components/form/CourseContentForm";
import { setParamCourseId } from "@/redux/features/courseSlice";
import { useAppDispatch } from "@/redux/hooks";
import { useGetContentByCourseIdQuery } from "@/redux/services/contentApi";
import Content from "@/types/content.type";
import { useParams } from "next/navigation";
import React from "react";

function CourseContentPage() {
  const param = useParams();
  const courseId = param.id as string;
  const dispatch = useAppDispatch();
  dispatch(setParamCourseId(courseId));
  const { data, isLoading } = useGetContentByCourseIdQuery(courseId);

  if (isLoading) return <Loading />;

  return (
    <div className="mt-10 shadow-xl w-full mx-5 ">
      <div className="my-5 mx-5 flex items-center font-bold text-xl">
        Mô tả chi tiết khóa học
      </div>
      <hr />
      <div className="mt-10 ml-10"></div>
      <CourseContentForm content={data?.data as Content} courseId={courseId} />
    </div>
  );
}

export default CourseContentPage;
