"use client";
import React, { Fragment, useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Link from "next/link";
import Image from "next/image";
import { Course } from "@/types/course.type";
import { useLoadFileFromCloudQuery } from "@/redux/services/courseApi";
import { FaRegStar, FaStar, FaStarHalfAlt } from "react-icons/fa";
import ReviewDialog from "../Dialog/ReviewDialog";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import {
  useGetReviewByUserNameAndCourseIdQuery,
  useLazyGetReviewByUserNameAndCourseIdQuery,
} from "@/redux/services/reviewApi";
import { Review } from "@/types/review.type";
import { v4 as uuidv4 } from "uuid";
import { useLazyGetCourseAccessQuery } from "@/redux/services/courseProcessApi";
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
  const router = useRouter();
  const username = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.id
  );
  const [isRating, setIsRating] = useState(false);
  const [hovered, setHovered] = useState(false);
  const [reviewByUser, setReviewByUser] = useState<Review>();
  const { data: imageBase64 } = useLoadFileFromCloudQuery(
    course ? (course.urlCourseImages as string) : ""
  );
  const [getReviewByUserName, { data: reviewByUserData }] =
    useLazyGetReviewByUserNameAndCourseIdQuery();

  useEffect(() => {
    if (isMyCourse) {
      setTimeout(() => {
        getReviewByUserName({
          username: username,
          courseId: course.id as string,
        })
          .unwrap()
          .then((res: any) => {
            if (res.data) {
              setReviewByUser(res.data);
            }
          });
      }, 500);
    }
  }, [isMyCourse, isRating]);

  const handleRating = () => {
    setIsRating(true);
  };
  const [
    getCourseAccess,
    { data: courseAccess, isSuccess: getCourseAccessSuccess },
  ] = useLazyGetCourseAccessQuery();

  const handleRedirectCourse = () => {
    if (userId.length === 0) {
      router.push(`/course/${course.id as string}`);
    } else {
      getCourseAccess({
        userId: userId as string,
        courseId: course.id as string,
      });
    }
  };
  useEffect(() => {
    if (getCourseAccessSuccess) {
      if ((courseAccess?.data as string) === "True") {
        router.push(`/learning/${course.id as string}`);
      } else {
        router.push(`/course/${course.id as string}`);
      }
    }
  }, [courseAccess]);
  return (
    <Fragment>
      <Card
        className="w-full hover:scale-105 duration-300 mb-8 mx-auto p-4 max-w-[350px]"
        key={course.id}
        // style={{
        //   background:
        //     "linear-gradient(349deg, rgba(250,217,118,0.5188200280112045) 19%, rgba(228,245,237,1) 70%)",
        // }}
        data-aos="flip-down"
      >
        <CardHeader className="flex flex-col gap-2 !p-0 hover:cursor-pointer">
          <div className="h-fit w-full relative">
            <div className="group flex justify-center text-center ">
              <Image
                src={
                  imageBase64
                    ? `data:image/png;base64,${imageBase64}`
                    : "/banner.jpg"
                }
                className="w-[20rem] h-40"
                width={300}
                height={128}
                alt={course.name}
              />
              <div className="absolute inset-0 bg-black opacity-0 group-hover:opacity-50" />
              <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-500">
                <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300 ease-in-out">
                  <div className="bg-white rounded-2xl py-2 px-4 transform translate-y-4 group-hover:translate-y-0 transition-transform duration-300 ease-in-out">
                    {isMyCourse ? (
                      <div onClick={() => handleRedirectCourse()}>
                        Tiếp tục học
                      </div>
                    ) : isInstructorCourse ? (
                      <Link
                        href={`/instructor/courses/${course.id}/manage/content`}
                      >
                        Quản lý khóa học
                      </Link>
                    ) : (
                      <div onClick={() => handleRedirectCourse()}>
                        Xem khóa học
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>

          <CardTitle className="text-black paragraph-semibold line-clamp-2 w-full text-left xs:text-[12px] font-serif min-h-[52px]">
            {course.name}
          </CardTitle>

          <div className="flex gap-1 justify-end items-end pr-2 flex-col hover:cursor-pointer">
            {isMyCourse && (
              <Fragment>
                <div
                  onClick={() => handleRating()}
                  onMouseEnter={() => setHovered(true)}
                  onMouseLeave={() => setHovered(false)}
                >
                  <div className="flex justify-center">
                    {[...Array(5)].map((_, index) => {
                      if (index < (reviewByUser?.rating as number)) {
                        return <FaStar key={index} fill={"#ffd03f"} />;
                      } else {
                        return <FaRegStar key={index} fill={"#ffd03f"} />;
                      }
                    })}
                  </div>
                  {reviewByUser?.rating ? (
                    <p className="text-[10px]">
                      {hovered
                        ? "Edit rated this course"
                        : "You rated this course"}
                    </p>
                  ) : (
                    <p className="text-[10px] mt-1">Leaving a rating</p>
                  )}
                </div>
              </Fragment>
            )}
          </div>
        </CardHeader>
        <div>
          <p className="text-[13px] text-gray-700">{course.authorName}</p>
        </div>
        {isMyCourse || isInstructorCourse ? null : (
          <CardContent className="p-0 mt-1">
            <div className="flex items-center justify-start text-center">
              {!isMyCourse && (
                <Fragment>
                  {course.averageRating ? (
                    <Fragment>
                      <div className="mr-1 font-bold">
                        {Math.round(course.averageRating * 10) / 10}
                      </div>
                      {[...Array(5)].map((_, index) => {
                        const star = Math.floor(course.averageRating as number);
                        const halfStar =
                          (course.averageRating as number) - star;
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
              )}
              <p className="ml-1 text-gray-500">({course.totalRatings ?? 0})</p>
            </div>
            <div className="mr-2">
              <div className="text-xl font-normal xs:text-sm">
                {course.price === 0 ? (
                  <span className="text-red-500 italic xs:min-w-[75px]">
                    <span className="xs:mr-2">F</span>
                    <span className="xs:mr-2">r</span>
                    <span className="xs:mr-2">e</span>
                    <span>e</span>
                  </span>
                ) : (
                  <p className="flex xs:min-w-[75px] text-lg font-bold">
                    <span className="underline">đ</span>
                    {(course.price as number).toLocaleString()}
                  </p>
                )}
              </div>
            </div>
          </CardContent>
        )}
      </Card>
      {isRating && (
        <ReviewDialog
          isRating={isRating}
          setIsRating={setIsRating}
          course={course}
          review={reviewByUser}
        />
      )}
    </Fragment>
  );
}

export default CourseCard;
