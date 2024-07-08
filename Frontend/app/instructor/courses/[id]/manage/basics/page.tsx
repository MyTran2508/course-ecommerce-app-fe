"use client";
import Loading from "@/app/(root)/user/personal/loading";
import CourseInforForm from "@/components/Form/CourseInforForm";
import withAuth from "@/hoc/withAuth";
import {
  setManageCourse,
  setParamCourseId,
} from "@/redux/features/courseSlice";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { useGetCourseByIdQuery } from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import { ModuleName } from "@/utils/resources";
import { useParams } from "next/navigation";
import React, { useEffect, useState } from "react";
import LayoutManage from "../LayoutManage";
import withAuthManager from "@/hoc/withAuthManager";
import { useGetContentByCourseIdQuery } from "@/redux/services/contentApi";
import { Section } from "@/types/section.type";
import { setContentId } from "@/redux/features/contentSlice";
import { setSections } from "@/redux/features/sectionSlice";
import Content from "@/types/content.type";

function BasicsPage() {
  const params = useParams();
  const dispatch = useAppDispatch();
  const { data, isLoading } = useGetCourseByIdQuery(params.id as string);
  const courseId = params.id as string;
  const { data: contentData, isLoading: isContentLoading } =
    useGetContentByCourseIdQuery(courseId);

  if (isLoading || isContentLoading) return <Loading />;
  dispatch(setManageCourse(data?.data as Course));
  dispatch(setParamCourseId(params.id as string));
  dispatch(setContentId((data?.data as Content).id as string));
  dispatch(setSections((contentData?.data as Content)?.sections as Section[]));
  return (
    <LayoutManage>
      <div className="mt-10 shadow-xl w-full mx-5 ">
        <div className="my-5 mx-5 flex items-center font-bold text-xl">
          Thông Tin Cơ Bản
        </div>
        <hr />
        <div className="mt-10 ml-10">
          <CourseInforForm course={data?.data as Course} />
        </div>
      </div>
    </LayoutManage>
  );
}

export default withAuthManager(BasicsPage, ModuleName.CONTENT);
