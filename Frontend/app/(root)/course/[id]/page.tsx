"use client";
import React, { Fragment, useEffect, useState } from "react";
import DisclosureCourseContent from "@/components/DisclosureCourseContent";
import { BsDot } from "react-icons/bs";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import CustomButton from "@/components/CustomButton";
import { GoVideo, GoInfinity } from "react-icons/go";
import { HiOutlineFilm } from "react-icons/hi";
import { GrCertificate } from "react-icons/gr";
import { v4 as uuidv4 } from "uuid";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import { Cart } from "@/types/cart.type";
import { addToCart } from "@/redux/features/cartSlice";
import showToast from "@/utils/showToast";
import { ToastMessage, ToastStatus } from "@/utils/resources";
import { useParams } from "next/navigation";
import {
  useGetCourseAccessQuery,
  useGetCourseByIdQuery,
  useLoadFileFromCloudQuery,
} from "@/redux/services/courseApi";
import Loading from "../../user/personal/loading";
import { Course } from "@/types/course.type";
import { useGetContentByCourseIdQuery } from "@/redux/services/contentApi";
import Content from "@/types/content.type";
import { Section } from "@/types/section.type";
import { handleCountFieldsInSection } from "@/utils/function";
import { useRouter } from "next/navigation";

const initCourse: Course = {
  id: "0",
  name: "",
  language: {
    id: "0",
  },
  level: {
    id: "0",
  },
  topic: {
    id: "0",
  },
};

function CoursePage() {
  const param = useParams();
  const router = useRouter();
  const [isOpenAllContent, setOpenAllContent] = useState<boolean>(false);
  const [course, setCourse] = useState(initCourse);
  const [video, setVideo] = useState("");
  const [isAccess, setAccess] = useState<boolean>();
  const [sections, setSections] = useState<Section[]>([]);
  const { data: videoBase64 } = useLoadFileFromCloudQuery(video);
  const carts = useAppSelector((state) => state.persistedReducer.cartReducer);
  const dispatch = useAppDispatch();

  const { data: contentData, isSuccess } = useGetContentByCourseIdQuery(
    param.id as string
  );
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.id
  );
  const { data: courseAccess, isSuccess: getCourseAccessSuccess } =
    useGetCourseAccessQuery({
      userId: userId,
      courseId: course?.id as string,
    });
  const { totalDurationCourse, totalLectureCount } =
    handleCountFieldsInSection(sections);

  useEffect(() => {
    if (isSuccess) {
      setCourse((contentData?.data as Content).course as Course);
      setVideo(
        ((contentData?.data as Content).course as Course)
          ?.urlPromotionVideos as string
      );
      setSections(
        ((contentData?.data as Content).sections as Section[])?.filter(
          (section) => section.ordinalNumber !== -1
        )
      );
    }
  }, [contentData]);

  useEffect(() => {
    if (getCourseAccessSuccess) {
      setAccess(courseAccess?.data as boolean);
      if ((courseAccess.data as boolean) === true) {
        router.push(`/learning/${course.id}`);
      }
    }
  }, [courseAccess]);

  const handleClickOpenAllContent = () => {
    setOpenAllContent(!isOpenAllContent);
  };

  const renderCourseContent = () => {
    return (
      <div>
        {sections?.map((section, index) => {
          return (
            <div key={index}>
              <DisclosureCourseContent
                openAll={isOpenAllContent}
                key={uuidv4()}
                section={section}
              />
            </div>
          );
        })}
      </div>
    );
  };

  const handleAddToCart = () => {
    const newCart: Cart = {
      id: uuidv4().toString(),
      courseId: course.id as string,
      image: course.urlCourseImages as string,
      nameCourse: course.name,
      totalSection: sections.length,
      totalDurationCourse: totalDurationCourse,
      totalLecture: totalLectureCount,
      price: course.price as number,
      checked: false,
    };

    if (
      carts.length === 0 ||
      carts.every((cart: Cart) => cart.nameCourse !== newCart.nameCourse)
    ) {
      dispatch(addToCart(newCart));
      showToast(ToastStatus.SUCCESS, ToastMessage.ADD_CART_SUCCESS);
    } else {
      showToast(ToastStatus.WARNING, ToastMessage.ADD_CART_DUPLICATE);
    }
  };
  return (
    <div className="xl:flex m-2 mt-10 gap-2 font-roboto">
      {!isAccess ? (
        <Fragment>
          <div className="xl:w-2/3 ml-8 xs:m-6">
            <div className="text-3xl font-bold mb-6"> {course?.name}</div>
            <div className="font-light mb-2">{course?.subTitle}</div>
            <div className="sticky top-[80px] z-1 bg-white">
              <div className="text-2xl font-bold md-6 ">Nội Dung Khóa Học</div>
              <div className="flex-between my-2 xs:text-[10px]">
                <div className="flex-start xl:gap-2 xs:gap-0.5">
                  <div>{sections?.length} chương</div>
                  <BsDot className="text-2xl xs:text-[10px]" />
                  <div>{totalLectureCount} bài học</div>
                  <BsDot className="text-2xl xs:text-[10px]" />
                  <div>Thời lượng {totalDurationCourse}</div>
                </div>
                <div
                  onClick={() => {
                    handleClickOpenAllContent();
                  }}
                  className="hover:cursor-pointer text-orange-600 xs:ml-1"
                >
                  {isOpenAllContent ? "Thu gọn tất cả" : "Mở rộng tất cả"}
                </div>
              </div>
            </div>
            <div className="w-full">{renderCourseContent()}</div>
          </div>
          <div className="xl:w-1/3 flex mr-2">
            <Card className="p-4 max-w-md mx-auto shadow-md sticky top-[65px] z-10 max-h-[550px]">
              <CardTitle className="text-xl font-semibold mb-2 ml-2">
                {course?.name}
              </CardTitle>
              <CardHeader>
                <video
                  controls
                  src={`data:video/mp4;base64,${videoBase64}`}
                  className="w-96 h-[200px] rounded-md"
                  autoPlay
                />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold mb-1">
                  {course?.price?.toLocaleString()} ₫
                </div>
                <div className="flex justify-center mb-2 text-white xs:bg-red-950 xs:h-full">
                  <CustomButton
                    title="Add To Card"
                    containerStyles="bg-orange-600 rounded-3xl py-2 px-4 xs:fixed xs:bottom-0 xs:w-full xs:mb-0 xs:rounded-sm xs:ml-[-5px]"
                    handleClick={() => handleAddToCart()}
                  />
                </div>
                <div className="flex-start text-sm flex-col">
                  <div className="font-semibold mb-2">
                    Khóa Học Này Bao Gồm:
                  </div>

                  <div className="flex-start gap-4 pl-4 mb-1">
                    <GoVideo /> Thời lượng {totalDurationCourse}
                  </div>
                  <div className="flex-start gap-4 pl-4 mb-1">
                    <HiOutlineFilm /> Tổng số {totalLectureCount} bài học
                  </div>
                  <div className="flex-start gap-4 pl-4 mb-1">
                    <GrCertificate /> Chứng chỉ hoàn thành khóa học
                  </div>
                  <div className="flex-start gap-4 pl-4 mb-1">
                    <GoInfinity /> Truy Cập Trọn Đời
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </Fragment>
      ) : null}
    </div>
  );
}

export default CoursePage;
