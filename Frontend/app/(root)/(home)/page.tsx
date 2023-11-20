"use client";
import CourseCard from "@/components/CourseCard";
import { useGetNewestCourseQuery } from "@/redux/services/courseApi";
import { useTypewriter, Cursor } from "react-simple-typewriter";
import Loading from "../user/personal/loading";
import { Course } from "@/types/course.type";
import { course } from "@/redux/features/courseSlice";
import Checkout from "@/components/PayPal";

export default function Home() {
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
  let newestCourseList: Course[] = [];
  const { data, isLoading } = useGetNewestCourseQuery({ topicId: 0, size: 4 });
  if (isLoading) return <Loading />;

  if (data) {
    newestCourseList = data?.data as Course[];
  }

  const renderItem = () => {
    return (
      <div className="flex justify-center items-center">
        <div className="grid xl:grid-cols-4 gap-5 md:grid-cols-2">
          {newestCourseList.map((course) => (
            <div key={course.id}>
              <CourseCard course={course} />
            </div>
          ))}
        </div>
      </div>
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
      <div>
        <h3 className="text-2xl font-bold ml-6">Khóa Học Mới</h3>
        {renderItem()}
      </div>
    </main>
  );
}
