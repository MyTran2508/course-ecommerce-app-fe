import React from "react";
import { Course } from "@/types/course.type";
import { useLoadFileFromCloudQuery } from "@/redux/services/courseApi";
import Image from "next/image";
import { BsDot, BsFillTagFill } from "react-icons/bs";
import { useRouter } from "next/navigation";

interface CourseCardProps {
  course: Course;
}

function CourseCardSearch(props: CourseCardProps) {
  const router = useRouter();
  const { course } = props;
  const { data: imageBase64 } = useLoadFileFromCloudQuery(
    course ? (course.urlCourseImages as string) : ""
  );

  const handleChangRoute = () => {
    const url = `/course/${course.id}`;
    router.push(url);
  };

  return (
    <div
      className="my-2 hover:cursor-pointer"
      onClick={() => handleChangRoute()}
    >
      <div className="flex justify-between">
        <div className="flex gap-4">
          <Image
            src={
              imageBase64
                ? `data:image/png;base64,${imageBase64}`
                : "/banner.jpg"
            }
            alt="course"
            className="w-64 h-36 pb-2"
            width={64}
            height={36}
          />
          <div>
            <div className="text-[25px] font-bold mb-1">{course.name}</div>
            <div className="flex-start xl:gap-2 xs:gap-0.5  opacity-50">
              <div>{course.language.name}</div>
              <BsDot className="text-[15px]" />
              <div>{course.topic.name}</div>
              <BsDot className="text-[15px]" />
              <div>{course.level.name}</div>
            </div>
          </div>
        </div>
        <div className="flex gap-16 items-center">
          <div className="flex gap-4 items-center">
            <div className="text-violet-800 font-bold flex gap-2 w-[200px] xs:w-[100px] ml-auto flex-row-reverse text-2xl">
              <BsFillTagFill className="pt-1 text-2xl" />
              {course.price?.toLocaleString()}đ
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CourseCardSearch;
