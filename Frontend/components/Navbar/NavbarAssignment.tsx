"use client";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { useRouter } from "next/navigation";
import React from "react";
import { IoChevronBack } from "react-icons/io5";

function NavbarAssignment() {
  const route = useRouter();
  const courseId = useAppSelector((state) => state.courseReducer.courseId);
  return (
    <div>
      <div className="mx-10">
        <div
          className="hover:cursor-pointer text-orange-600 flex gap-2 items-center my-2 text-xl"
          onClick={() =>
            route.push(`/instructor/courses/${courseId}/manage/curriculum`)
          }
        >
          <IoChevronBack />
          <span>Back to curriculum</span>
        </div>
        <div className="flex-between">
          <h1 className="font-bold text-4xl">Create Assignment</h1>
          {/* <button className="bg-blue-500 text-white px-4 py-2 rounded-lg mt-4">
            Save Assignment
          </button> */}
        </div>
      </div>
      <hr className="my-10" />
    </div>
  );
}

export default NavbarAssignment;
