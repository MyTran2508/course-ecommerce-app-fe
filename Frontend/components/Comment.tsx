import Image from "next/image";
import React from "react";
import CustomButton from "./CustomButton";
import { Input } from "./ui/input";

function Comment() {
  const replyComment = () => {
    console.log("đã zô");
    return 123;
  };
  return (
    <div className="flex flex-col mb-4">
      <div className="flex">
        <Image
          src="/banner.jpg"
          alt="avatar"
          width={70}
          height={65}
          className="rounded-full w-[40px] h-[40px]"
        />
        <div className="bg-gray-200 p-2 px-4 ml-4 rounded-2xl">
          <div className="font-bold">Đạt</div>
          <div className="w-full mt-2 text-sm">
            khóa học rất hay quá là hay luôn á
          </div>
        </div>
      </div>
      <div>
        <CustomButton
          title="Trả lời"
          containerStyles="ml-16 text-sm mt-2 hover:text-orange-200"
          handleClick={() => replyComment()}
        />
      </div>
    </div>
  );
}

export default Comment;
