"use client";
import { useRouter } from "next/navigation";
import React from "react";
import { IoIosArrowBack } from "react-icons/io";
import SaveButton from "./SaveButton";

function CreateCourseNavBar() {
  const router = useRouter();

  const handleClickBack = () => {
    router.push("/instructor/courses");
  };

  return (
    <div className="sticky top-0 z-20">
      <div className="bg-gray-900 text-white py-4 px-2 ">
        <div className="text-sm flex-between gap-2">
          <div
            className="flex-start font-bold gap-1 hover:cursor-pointer"
            onClick={() => handleClickBack()}
          >
            <IoIosArrowBack className="text-xl " />
            <div>Quay Lại</div>
          </div>
          <div className="flex gap-10 mr-10 items-center">
            <SaveButton />
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreateCourseNavBar;
