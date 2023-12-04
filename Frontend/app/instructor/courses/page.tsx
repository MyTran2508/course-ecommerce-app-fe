"use client";
import CourseCard from "@/components/CourseCard";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import { BiSolidMessageSquareAdd } from "react-icons/bi";
import InstructorNavbar from "../Navbar";
import {
  useFilterCourseMutation,
  useGetAllCourseQuery,
} from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import { SearchCourseRequest } from "@/types/request.type";
import Paginate from "@/components/Paginate";

function InstructorCourses() {
  const [courses, setCourses] = useState<Course[]>([]);
  const [searchCourse] = useFilterCourseMutation();
  const [page, setPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);

  const handleSearch = async (page: number) => {
    await searchCourse({
      keyword: null,
      pageIndex: page - 1,
      pageSize: 8,
    })
      .unwrap()
      .then((fulfilled) => {
        setCourses(fulfilled.data as Course[]);
        setTotalPage(fulfilled.totalPages);
      });
  };

  useEffect(() => {
    handleSearch(page);
  }, []);

  useEffect(() => {
    handleSearch(page);
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  }, [page]);
  // useEffect(() => {
  //   if (isSuccess) {
  //     setCourses(courseData?.data as Course[]);
  //   }
  // }, [courseData]);
  return (
    <div>
      <InstructorNavbar />
      <div className="container mt-20">
        <div className="font-bold text-2xl xs:text-[10px]">
          Quản Lý Khóa Học
        </div>
        <div className="grid grid-cols-4 xs:grid-cols-1">
          {courses.map((course) => (
            <div key={course.id}>
              <CourseCard instructorCourse={true} course={course} />
            </div>
          ))}
        </div>
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

export default InstructorCourses;
