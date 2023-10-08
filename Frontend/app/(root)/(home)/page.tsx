"use client";
import CourseCard from "@/components/CourseCard";
import { useTypewriter, Cursor } from "react-simple-typewriter";

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

  const renderItem = () => {
    return (
      <div className="flex flex-wrap">
        <div className="w-1/4 gap-3">
          <CourseCard />
        </div>
        <div className="w-1/4 gap-3">
          <CourseCard />
        </div>
        <div className="w-1/4 gap-3">
          <CourseCard />
        </div>
        <div className="w-1/4 gap-3">
          <CourseCard />
        </div>
      </div>
    );
  };
  return (
    <main className=" max-w-screen-2xl w-full mx-auto flex-col">
      <section className="nav-padding w-full flex-center">
        <div className="flex-center relative bg-banner bg-center bg-cover rounded-xl min-h-[200px] w-11/12 flex-col mb-8">
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
