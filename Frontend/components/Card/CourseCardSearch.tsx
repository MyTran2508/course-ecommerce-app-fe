import React, { Fragment } from "react";
import { Course } from "@/types/course.type";
import { useLoadFileFromCloudQuery } from "@/redux/services/courseApi";
import Image from "next/image";
import { BsDot, BsFillTagFill } from "react-icons/bs";
import { useRouter } from "next/navigation";
import { FaRegStar, FaStar, FaStarHalfAlt } from "react-icons/fa";

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
            className="w-40 h-28 pb-2 xs:min-w-[160px]"
            width={40}
            height={24}
          />
          <div>
            <div className="text-[20px] font-bold mb-1 xs:text-[13px] xs:w-full">
              {course.name}
            </div>
            <div className="flex-start xl:gap-2 xs:gap-0.5 opacity-50 xs:hidden">
              <div>{course.language.name}</div>
              <BsDot className="text-[15px]" />
              <div>{course.topic.name}</div>
              <BsDot className="text-[15px]" />
              <div>{course.level.name}</div>
            </div>
            <div className="flex items-center justify-start text-center">
              <Fragment>
                {course.averageRating ? (
                  <Fragment>
                    <div className="mr-1 font-bold">{course.averageRating}</div>
                    {[...Array(5)].map((_, index) => {
                      const star = Math.floor(course.averageRating as number);
                      const halfStar = (course.averageRating as number) - star;
                      if (index < star) {
                        return <FaStar key={index} fill={"#ffd03f"} />;
                      } else if (index === star && halfStar > 0) {
                        return <FaStarHalfAlt key={index} fill={"#ffd03f"} />;
                      } else {
                        return <FaRegStar key={index} fill={"#ffd03f"} />;
                      }
                    })}
                  </Fragment>
                ) : (
                  <Fragment>
                    <div className="mr-1 font-bold">0</div>
                    {[...Array(5)].map((_, index) => {
                      return <FaRegStar key={index} fill={"#ffd03f"} />;
                    })}
                  </Fragment>
                )}
              </Fragment>
              <p className="ml-1 text-gray-500">({course.totalRatings ?? 0})</p>
            </div>
            <div>
              <p className="text-[13px] text-gray-700 italic">
                {course.authorName}
              </p>
            </div>
          </div>
        </div>
        <div className="flex gap-16 items-center">
          <div className="flex gap-4 items-center">
            <div className="text-violet-800 font-bold flex gap-2 w-[200px] xs:w-[100px] ml-auto flex-row-reverse text-xl">
              <BsFillTagFill className="pt-1 text-xl xs:text-[13px]" />
              {course.price ? (
                <div className="xs:text-[14px]">
                  {course.price?.toLocaleString()} đ
                </div>
              ) : (
                <span className="xs:text-[14px]">Miễn Phí</span>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CourseCardSearch;
