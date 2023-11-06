"use client";
import React, { useEffect, useState } from "react";
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

function CoursePage() {
  const [isOpenAllContent, setOpenAllContent] = useState<boolean>(false);
  const dispatch = useAppDispatch();
  const carts = useAppSelector((state) => state.cartReducer);

  const handleClickOpenAllContent = () => {
    setOpenAllContent(!isOpenAllContent);
  };

  const renderCourseContent = () => {
    return (
      <div className="">
        <DisclosureCourseContent openAll={isOpenAllContent} key={uuidv4()} />
        <DisclosureCourseContent openAll={isOpenAllContent} key={uuidv4()} />
      </div>
    );
  };

  const handleAddToCart = () => {
    const cartExample: Cart = {
      id: uuidv4().toString(),
      image: "https://www.udemy.com/staticx/udemy/images/v9/hpp-paypal.svg",
      nameCourse: " Khóa Học JavaScript",
      sections: 11,
      totalLength: 1111,
      lectures: 11,
      price: 300000,
      checked: false,
    };

    dispatch(addToCart(cartExample));
    showToast(ToastStatus.SUCCESS, ToastMessage.ADD_CART_SUCCESS);
  };
  return (
    <div className="xl:flex m-2 mt-10 gap-2 font-roboto">
      <div className="xl:w-2/3 ml-8 xs:m-6">
        <div className="text-3xl font-bold mb-6"> Khóa Học Cơ Bản Về C++</div>
        <div className="font-light mb-2">
          Khóa học lập trình C++ từ cơ bản tới nâng cao dành cho người mới bắt
          đầu. Mục tiêu của khóa học này nhằm giúp các bạn nắm được các khái
          niệm căn cơ của lập trình, giúp các bạn có nền tảng vững chắc để chinh
          phục con đường trở thành một lập trình viên.
        </div>
        <div className="sticky top-[80px] z-1 bg-white">
          <div className="text-2xl font-bold md-6 ">Nội Dung Khóa Học</div>
          <div className="flex-between my-2 xs:text-[10px]">
            <div className="flex-start xl:gap-2 xs:gap-0.5">
              <div>11 chương</div>
              <BsDot className="text-2xl xs:text-[10px]" />
              <div>11 bài học</div>
              <BsDot className="text-2xl xs:text-[10px]" />
              <div>Thời lượng 10 giờ 29 phút</div>
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
            Khóa Học Cơ Bản Về C++
          </CardTitle>
          <CardHeader className="w-2/3">
            <iframe
              className="w-[300px] h-[170px] rounded-md"
              src="https://www.youtube.com/embed/ejoEUpUSIiU?si=9j6TVfcop5pRY1b6"
              title="YouTube video player"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share, rel"
            ></iframe>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold mb-1">200,000 ₫</div>
            <div className="flex justify-center mb-2 text-white xs:bg-red-950 xs:h-full">
              <CustomButton
                title="Add To Card"
                containerStyles="bg-orange-600 rounded-3xl py-2 px-4 xs:fixed xs:bottom-0 xs:w-full xs:mb-0 xs:rounded-sm xs:ml-[-5px]"
                handleClick={() => handleAddToCart()}
              />
            </div>
            <div className="flex-start text-sm flex-col">
              <div className="font-semibold mb-2">Khóa Học Này Bao Gồm:</div>

              <div className="flex-start gap-4 pl-4 mb-1">
                <GoVideo /> Thời lượng 10 giờ 29 phút
              </div>
              <div className="flex-start gap-4 pl-4 mb-1">
                <HiOutlineFilm /> Tổng số 138 bài học
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
    </div>
  );
}

export default CoursePage;
