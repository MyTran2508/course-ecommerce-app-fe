"use client";
import withAuth from "@/hoc/withAuth";
import { setParamCourseId } from "@/redux/features/courseSlice";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { Constant, ModuleName } from "@/utils/resources";
import Link from "next/link";
import { usePathname } from "next/navigation";
import React, { Fragment, useState } from "react";
import { AiFillProfile, AiOutlineInfoCircle } from "react-icons/ai";
import { MdOutlineDescription } from "react-icons/md";

function SideBar() {
  const id = useAppSelector((state) => state.courseReducer.courseId);
  const path = usePathname();
  const dispatch = useAppDispatch();
  const [activeLink, setActiveLink] = useState<string>(path);
  const courseId = path.split("/")[3];
  dispatch(setParamCourseId(courseId));

  const handleLinkClick = (path: string) => {
    setActiveLink(path);
  };

  return (
    <div className="mx-12">
      <div className="flex h-full">
        <div className="p-4">
          <div className="sticky top-[96px]">
            <div className="flex items-center mb-4">
              <h2 className="text-lg font-bold">Nội Dung Khóa Học</h2>
            </div>
            <nav className="flex flex-col w-max text-md">
              <Link
                href={`/instructor/courses/${id}/manage/content`}
                className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 ${
                  activeLink.includes(Constant.MANAGER_CONTENT_PATH)
                    ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                    : "border border-l-2 border-y-0 border-r-0 border-white"
                }`}
                onClick={() => handleLinkClick(Constant.MANAGER_CONTENT_PATH)}
              >
                <div className="flex items-center gap-2">
                  <MdOutlineDescription />
                  Chi Tiết Khóa Học
                </div>
              </Link>
              <Link
                href={`/instructor/courses/${id}/manage/basics`}
                className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 ${
                  activeLink.includes(Constant.MANAGER_BASICS_PATH)
                    ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                    : "border border-l-2 border-y-0 border-r-0 border-white"
                }`}
                onClick={() => handleLinkClick(Constant.MANAGER_BASICS_PATH)}
              >
                <div className="flex items-center gap-2">
                  <AiOutlineInfoCircle />
                  Thông Tin Khóa Học
                </div>
              </Link>

              <Link
                href={`/instructor/courses/${id}/manage/curriculum`}
                className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 ${
                  activeLink.includes(Constant.MANAGER_CURRICULUM_PATH)
                    ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                    : "border border-l-2 border-y-0 border-r-0 border-white"
                }`}
                onClick={() =>
                  handleLinkClick(Constant.MANAGER_CURRICULUM_PATH)
                }
              >
                <div className="flex items-center gap-2">
                  <AiFillProfile />
                  Chương Trình Giảng Dạy
                </div>
              </Link>
            </nav>
          </div>
        </div>
      </div>
    </div>
  );
}

export default withAuth(SideBar, ModuleName.CONTENT);
