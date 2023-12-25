"use client";
import CourseCard from "@/components/CourseCard";
import {
  useFilterCourseMutation,
  useGetAllCourseQuery,
  useGetNewestCourseQuery,
  useGetPopularCourseQuery,
} from "@/redux/services/courseApi";
import { useTypewriter, Cursor } from "react-simple-typewriter";
import Loading from "../user/personal/loading";
import { Course } from "@/types/course.type";
import { course } from "@/redux/features/courseSlice";
import Checkout from "@/components/PayPal";
import Paginate from "@/components/Paginate";
import { Fragment, useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";

export default function Home() {
  const router = useRouter();
  const [text] = useTypewriter({
    words: [
      "We accompany you on your learning journey and personal development",
      "Become an expert in your field with high-quality courses.",
    ],
    loop: 50,
    typeSpeed: 50,
    delaySpeed: 3000,
    deleteSpeed: 20,
  });
  const [courseNewest, setCourseNewest] = useState<Course[]>([]);
  const [coursePopular, setCoursePopular] = useState<Course[]>([]);
  const {
    data: courseNewestData,
    isLoading: isCourseNewestLoading,
    isSuccess: isCourseNewestSuccess,
  } = useGetNewestCourseQuery({
    topicId: -1,
    size: 4,
  });
  const {
    data: coursePopularData,
    isLoading: isCoursePopularLoading,
    isSuccess: isCoursePopularSuccess,
  } = useGetPopularCourseQuery({
    topicId: -1,
    size: 4,
  });
  useEffect(() => {
    if (isCourseNewestSuccess) {
      setCourseNewest(courseNewestData?.data as Course[]);
    }
    if (isCoursePopularSuccess) {
      setCoursePopular(coursePopularData?.data as Course[]);
    }
  }, [courseNewestData, coursePopularData]);
  if (isCourseNewestLoading || isCoursePopularLoading)
    return (
      <Fragment>
        {" "}
        <Loading />
      </Fragment>
    );

  const renderCourse = (courseList: Course[], title: string) => {
    return (
      <Fragment>
        {courseList ? (
          <Fragment>
            <div className="flex-between">
              <h3 className="text-2xl font-bold ml-6">{title}</h3>
              <div
                onClick={() => router.push("/course/search")}
                className="hover:cursor-pointer hover:text-orange-400"
              >
                Xem thêm
              </div>
            </div>
            <div className="flex justify-center items-center">
              <div className="grid xl:grid-cols-4 gap-5 md:grid-cols-2">
                <Fragment>
                  {courseList.map((course) => (
                    <div key={course.id}>
                      <CourseCard course={course} />
                    </div>
                  ))}
                </Fragment>
              </div>
            </div>
          </Fragment>
        ) : null}
      </Fragment>
    );
  };

  return (
    <main className=" max-w-screen-2xl w-full mx-auto flex-col">
      <section className="nav-padding w-full flex-center">
        <div className="flex-center relative bg-banner bg-center bg-cover rounded-xl min-h-[300px] w-11/12 flex-col mb-8">
          <h1 className="sm:heading1 heading2 text-center text-white">
            EXPERT LEARNING
          </h1>
          <p className="text-white font-semibold">
            {text} <Cursor cursorColor="rgb(43,43,43)" cursorStyle="|" />
          </p>
        </div>
      </section>
      <div className="mx-10">{renderCourse(courseNewest, "Khóa Học Mới")}</div>
      <div className="mx-10">
        {renderCourse(coursePopular, "Khóa Học Phổ Biến")}
      </div>
      <div className="flex-center">
        {/* <Paginate totalPage={4} key={"123123"} setPage={setPage} /> */}
        {/* {newestCourseList.length !== 0 ? (
          <Paginate totalPage={newestCourseList.length} />
        ) : null} */}
      </div>
    </main>
  );
}
