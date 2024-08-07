"use client";
import CourseCard from "@/components/Card/CourseCard";
import Paginate from "@/components/Paginate/Paginate";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import isUserExisted from "@/hoc/isUserExisted";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { useGetCourseByUserIdQuery } from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import { BiSolidMessageSquareAdd } from "react-icons/bi";

function MyCourses() {
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.id
  );
  const [page, setPage] = useState(1);
  const [totalPage, setTotalPage] = useState(1);
  const [courses, setCourses] = useState<Course[]>([]);
  const {
    data: coursesData,
    isSuccess,
    refetch,
  } = useGetCourseByUserIdQuery({
    id: userId,
    pageIndex: page - 1,
    pageSize: 4,
  });

  useEffect(() => {
    if (isSuccess) {
      setCourses(coursesData?.data as Course[]);
      setTotalPage(coursesData.totalPages);
    }
  }, [coursesData]);

  useEffect(() => {
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
    refetch();
  }, [page, refetch]);

  const renderCourse = () => {
    return (
      <div className="grid grid-cols-4 xs:grid-cols-2 xs:gap-2 xs:mr-2 lg:gap-3 mt-2">
        {courses.map((course) => (
          <div key={course.id}>
            <CourseCard myCourse={true} course={course} />
          </div>
        ))}
        {page === totalPage || totalPage === 0 ? (
          <div className="flex justify-center">
            <Card className="w-full max-w-fit !bg-transparent sm:max-w-[356px] m-8 border-none shadow-none">
              <CardHeader className="flex-center flex-col gap-2.5 !p-0 hover:cursor-pointer">
                <div className="h-fit w-full relative">
                  <div className="group">
                    <div className="border-none">
                      <BiSolidMessageSquareAdd className="text-3xl w-40 h-20 xs:text-[10px]" />
                    </div>

                    <div className="inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300 ease-in-out">
                      <div className="bg-orange-200 rounded-2xl py-2 px-4 transform translate-y-4 group-hover:translate-y-0 transition-transform duration-300 ease-in-out">
                        <Link href="/course/search">Create Courses</Link>
                      </div>
                    </div>
                  </div>
                </div>
              </CardHeader>
            </Card>
          </div>
        ) : null}
      </div>
    );
  };
  return (
    <div className="container mt-20">
      <div className="font-bold text-2xl xs:text-[15px]"> My Courses</div>
      {renderCourse()}
      <div>
        <div className="flex-center">
          {courses.length !== 0 ? (
            <Paginate
              totalPage={totalPage}
              currentPage={page}
              setCurrentPage={setPage}
            />
          ) : null}
        </div>
      </div>
    </div>
  );
}

export default isUserExisted(MyCourses);
