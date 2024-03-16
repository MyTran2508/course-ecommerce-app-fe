"use client";
import React, { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Link from "next/link";
import Image from "next/image";

import { BsPeopleFill } from "react-icons/bs";
import { Course } from "@/types/course.type";
import { useLoadFileFromCloudQuery } from "@/redux/services/courseApi";
import { useRouter } from "next/navigation";

interface CourseProps {
  course: Course;
  myCourse?: boolean;
  instructorCourse?: boolean;
}

function CourseCard(props: CourseProps) {
  const {
    course,
    myCourse: isMyCourse,
    instructorCourse: isInstructorCourse,
  } = props;
  const { data: imageBase64 } = useLoadFileFromCloudQuery(
    course ? (course.urlCourseImages as string) : ""
  );

  const MAX_TITLE_LENGTH = 22;
  const truncatedTitle =
    course.name.length > MAX_TITLE_LENGTH
      ? course.name.substring(0, MAX_TITLE_LENGTH) + "..."
      : course.name;
  return (
    <Card
      className="w-full max-w-fit border-0 !bg-transparent xs:max-w-[400px] m-8 "
      key={course.id}
    >
      <CardHeader className="flex-center flex-col gap-2.5 !p-0 hover:cursor-pointer">
        <div className="h-fit w-full relative">
          <div className="group">
            <Image
              src={
                imageBase64
                  ? `data:image/png;base64,${imageBase64}`
                  : "/banner.jpg"
              }
              className="w-60 h-32 rounded-md"
              width={240}
              height={128}
              alt={course.name}
            />
            <div className="absolute inset-0 bg-black opacity-0 group-hover:opacity-50 rounded-md" />
            <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-500">
              <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300 ease-in-out">
                <div className="bg-white rounded-2xl py-2 px-4 transform translate-y-4 group-hover:translate-y-0 transition-transform duration-300 ease-in-out">
                  {isMyCourse ? (
                    <Link href={`/course/${course.id}`}>Tiếp tục học</Link>
                  ) : isInstructorCourse ? (
                    <Link
                      href={`/instructor/courses/${course.id}/manage/content`}
                    >
                      Quản lý khóa học
                    </Link>
                  ) : (
                    <Link href={`/course/${course.id}`}>Xem khóa học</Link>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>

        <CardTitle className="text-black paragraph-semibold line-clamp-1 w-full text-left xs:text-[14px]">
          {truncatedTitle}
        </CardTitle>
      </CardHeader>
      {isMyCourse || isInstructorCourse ? null : (
        <CardContent className=" mt-4 p-0 flex-between">
          <div className="flex-start">
            <BsPeopleFill className="text-2xl mr-2" />
            <div className="text-xl font-normal"></div>
          </div>
          <div className="flex-end mr-2">
            <div className="text-xl font-normal xs:text-sm">
              {course.price === 0 ? (
                <span className="text-red-500 italic">
                  <span className="xs:mr-2">F</span>
                  <span className="xs:mr-2">r</span>
                  <span className="xs:mr-2">e</span>
                  <span>e</span>
                </span>
              ) : (
                (course.price as number).toLocaleString() + " đ"
              )}
            </div>
          </div>
          {/* <div className="flex-center body-medium gap-1.5 text-white">
          <Image src="/downloads.svg" width={20} height={20} alt="download" />
          {downloadNumber}
        </div>
        <Link
          href={downloadLink}
          target="_blank"
          className="flex-center text-gradient_purple-blue body-semibold gap-1.5"
        >
          Download Now
          <Image src="/arrow-blue.svg" width={13} height={10} alt="arrow" />
        </Link> */}
        </CardContent>
      )}
    </Card>
  );
}

export default CourseCard;
