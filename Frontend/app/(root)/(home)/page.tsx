"use client";
import CourseCard from "@/components/Card/CourseCard";
import {
  useFilterCourseMutation,
  useGetAllCourseQuery,
  useGetNewestCourseQuery,
  useGetPopularCourseQuery,
} from "@/redux/services/courseApi";
import Loading from "../user/personal/loading";
import { Course } from "@/types/course.type";
import { Fragment, useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";
import { ReactTyped } from "react-typed";
import { Topic } from "@/utils/data";
import AOS from "aos";
import "aos/dist/aos.css";
import CampSite from "@/components/Home/CampSite";
import PartnerBanner from "@/components/Home/PartnerBanner";

export default function Home() {
  const router = useRouter();
  const [courseNewest, setCourseNewest] = useState<Course[]>([]);
  const [coursePopular, setCoursePopular] = useState<Course[]>([]);
  const {
    data: courseNewestData,
    isLoading: isCourseNewestLoading,
    isSuccess: isCourseNewestSuccess,
  } = useGetNewestCourseQuery({
    topicId: -1,
    size: 8,
  });
  const {
    data: coursePopularData,
    isLoading: isCoursePopularLoading,
    isSuccess: isCoursePopularSuccess,
  } = useGetPopularCourseQuery({
    topicId: -1,
    size: 8,
  });

  useEffect(() => {
    if (isCourseNewestSuccess) {
      setCourseNewest(courseNewestData?.data as Course[]);
    }
    if (isCoursePopularSuccess) {
      setCoursePopular(coursePopularData?.data as Course[]);
    }
  }, [courseNewestData, coursePopularData]);

  useEffect(() => {
    AOS.init({
      duration: 1200,
      easing: "ease-out-back",
      delay: 100,
      once: true,
    });
  });

  if (isCourseNewestLoading || isCoursePopularLoading)
    return (
      <Fragment>
        <Loading />
      </Fragment>
    );

  const handleSearch = (topic: any) => {
    router.push("/course/search?topicId=" + topic.id);
  };

  const renderCourse = (courseList: Course[], title: string) => {
    return (
      <Fragment>
        {courseList ? (
          <Fragment>
            <div className="flex-between">
              <h2 className="text-3xl font-bold ml-6 text-[#00df9a] ">
                {title}
              </h2>
            </div>

            <div className="xs:justify-normal xs:items-start w-full xs:overflow-x-scroll xs:gap-5 mb-5 grid grid-cols-4 xs:flex xs:custom-scrollbar pl-3 mt-2">
              <Fragment>
                {courseList.map((course) => (
                  <CourseCard course={course} key={course.id} />
                ))}
              </Fragment>
            </div>
          </Fragment>
        ) : null}
      </Fragment>
    );
  };

  return (
    <main className="max-w-screen-2xl w-full mx-auto flex-col">
      {/* <section className="nav-padding w-full flex-center">
        <div className="flex-center relative bg-banner bg-center bg-cover rounded-xl min-h-[300px] w-11/12 flex-col mb-8">
          <h1 className="sm:heading1 heading2 text-center text-white">
            EXPERT LEARNING
          </h1>
          <p className="text-white font-semibold xs:hidden">
            {text} <Cursor cursorColor="rgb(43,43,43)" cursorStyle="|" />
          </p>
        </div>
      </section> */}
      <div
        className="text-white relative flex lg:h-[500px] bg-white"
        // style={{
        //   background:
        //     " radial-gradient(circle, rgba(160,242,131,1) 0%, rgba(193,235,185,1) 44%, rgba(81,241,234,0.3031337535014006) 95%)",
        // }}
      >
        <div className="bg-[#f7f9fa] h-full w-full xs:py-[20px] xs:px-0 px-10">
          <div className="hide-scrollbar flex w-full items-start justify-start gap-8 overflow-x-auto h-full px-12">
            <CampSite 
              backgroundImage="bg-bg-hero1"
              title="Expert Learning"
              subTitle="Invest in your future today, and embark on a journey of lifelong learning, opening doors to 
              new opportunities and a brighter future"
            />
          </div>
        </div>
      </div>
      <div className="w-full bg-[#f7f9fa] mx-auto xs:ml-[10px]">
        <PartnerBanner />
      </div>
      <div className="mx-10 xs:mx-0 mt-10 border-2 border-gray-300">
        <div className="mx-12 my-4">
          <h1 className="text-2xl text-black font-bold font-mono my-2">Many popular course options</h1>
          <p>Choose from over 220,000 online video courses with new additions published every month</p>
        </div>
        
        {renderCourse(coursePopular)}
      </div>
      <div className="max-w-[1240px] mx-auto grid lg:grid-cols-2 xs:ml-[10px]">
        <Image
          className="w-[500px] mx-auto my-4"
          src={"/laptop-removebg.png"}
          alt="/"
          width={500}
          height={200}
          data-aos="fade-up"
        />
        <div className="flex flex-col justify-center">
          <h1
            className="md:text-4xl sm:text-3xl text-2xl font-bold py-2 font-mono"
            data-aos="fade-left"
          >
            Empower Your Future: Centralized Data Analytics and Comprehensive
            Online Learning
          </h1>
          <p data-aos="fade-right" className="text- text-gray-500">
            Explore the vast expanse of knowledge and skills with our
            comprehensive online courses, tailored to suit your learning pace
            and style. Invest in your future today, and embark on a journey of
            lifelong learning, opening doors to new opportunities and a brighter
            future
          </p>
          <button className='text-white bg-black p-2 font-bold w-[200px] mt-4'>Get Started</button>
        </div>
      </div>

      <div className="mx-10 xs:mx-0 mt-10 border-2 border-gray-300">
        <div className="mx-12 my-4">
          <h1 className="text-2xl text-black font-bold font-mono my-2">Many new course options</h1>
          <p>Choose from over 220,000 online video courses with new additions published every month</p>
        </div>
        
        {renderCourse(courseNewest)}
      </div>

      <div className="w-full py-[2rem] xs:py-[20px] xs:px-0 px-10 mx-6 my-4">
        <h1 className="text-2xl text-black font-bold font-mono my-2">Top categories</h1>
        <div className="grid grid-cols-4 xs:grid-cols-2 gap-4">
          {Topic.map((topic, index) => (
            <div
              key={index}
              className="shadow-xl flex flex-col my-4 hover:scale-105 duration-300 w-[300px] group" // Thêm class group vào đây
              data-aos="zoom-in-up"
            >
              <div className="w-full bg-transparent cursor-pointer" onClick={() => handleSearch(topic)}>
                <img
                  src={topic.img}
                  alt=""
                  className="transform transition duration-300 ease-in-out group-hover:scale-110" // Áp dụng hiệu ứng zoom khi hover
                />
              </div>
              <span className="mt-3 text-black font-bold text-lg font-mono">{topic.name}</span>
            </div>
          ))}
        </div>
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
