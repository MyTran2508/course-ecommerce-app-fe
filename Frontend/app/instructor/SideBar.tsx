"use client";
import Link from "next/link";
import React, { Fragment, useState } from "react";
import { AiFillProfile, AiOutlineInfoCircle } from "react-icons/ai";

function SideBar() {
  const [activeLink, setActiveLink] = useState<number>(0);

  const handleLinkClick = (index: number) => {
    setActiveLink(index);
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
                href="/instructor/courses/manage/basics"
                className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 ${
                  activeLink === 0
                    ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                    : "border border-l-2 border-y-0 border-r-0 border-white"
                }`}
                onClick={() => handleLinkClick(0)}
              >
                <div className="flex items-center gap-2">
                  <AiOutlineInfoCircle />
                  Thông Tin Khóa Học
                </div>
              </Link>

              <Link
                href="/instructor/courses/manage/curriculum"
                className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 ${
                  activeLink === 1
                    ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                    : "border border-l-2 border-y-0 border-r-0 border-white"
                }`}
                onClick={() => handleLinkClick(1)}
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

export default SideBar;
