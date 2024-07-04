"use client";
import { setAssignment } from "@/redux/features/contentSlice";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { useGetLectureByIdQuery } from "@/redux/services/contentApi";
import { Lecture } from "@/types/section.type";
import { Constant } from "@/utils/resources";
import Link from "next/link";
import { usePathname } from "next/navigation";
import React, { Fragment, useEffect, useState } from "react";
import { AiOutlineMenu } from "react-icons/ai";
import { BsFillFilePersonFill } from "react-icons/bs";
import { GrShieldSecurity } from "react-icons/gr";
import { IoIosCloseCircleOutline } from "react-icons/io";

function SideBarAssignment() {
  const path = usePathname();
  const route = path.substring(0, path.indexOf("/manage"));
  const [activeLink, setActiveLink] = useState<string>(path);
  const [isOpenMenu, setOpenMenu] = useState<boolean>(true);
  const userName = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  // const lectureId = useAppSelector(
  //   (state) => state.contentReducer.lectureIdForAssignment
  // );
  // const { data: lecture, isSuccess: getLectureSuccess } =
  //   useGetLectureByIdQuery(lectureId);

  // useEffect(() => {
  //   if (getLectureSuccess) {
  //     const assignment = (lecture.data as Lecture)?.assignment || null;
  //     console.log(assignment);
  //     dispatch(setAssignment(assignment));
  //   }
  // }, [lecture]);

  if (userName === "") return null;

  const handleLinkClick = (path: string) => {
    setActiveLink(path);
    handleOpenMenu();
  };

  const handleOpenMenu = () => {
    if (window.innerWidth <= 575) {
      setOpenMenu(!isOpenMenu);
    }
  };

  return (
    <Fragment>
      {isOpenMenu ? (
        <Fragment>
          <div className="xs:mx-0 xs:absolute xs:z-20 h-full  ">
            <div className="flex h-full xs:fixed xs:bg-white">
              <div className="flex-between items-center mb-4">
                <IoIosCloseCircleOutline
                  onClick={() => handleOpenMenu()}
                  className="lg:hidden"
                />
              </div>
              <nav className="flex flex-col w-max text-md min-w-[300px]">
                <Link
                  href={`${route + Constant.MANAGER_PRACTICE_BASIC_PATH}`}
                  className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 text-lg ${
                    activeLink.includes(Constant.MANAGER_PRACTICE_BASIC_PATH)
                      ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                      : "border border-l-2 border-y-0 border-r-0 border-white"
                  }`}
                  onClick={() =>
                    handleLinkClick(Constant.MANAGER_PRACTICE_BASIC_PATH)
                  }
                >
                  Basic Info
                </Link>

                <Link
                  href={`${route + Constant.MANAGER_PRACTICE_INSTRUCTION_PATH}`}
                  className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 text-lg ${
                    activeLink.includes(
                      Constant.MANAGER_PRACTICE_INSTRUCTION_PATH
                    )
                      ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                      : "border border-l-2 border-y-0 border-r-0 border-white"
                  }`}
                  onClick={() =>
                    handleLinkClick(Constant.MANAGER_PRACTICE_INSTRUCTION_PATH)
                  }
                >
                  Instructions
                </Link>
                <Link
                  href={`${route + Constant.MANAGER_PRACTICE_QUESTIONS_PATH}`}
                  className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 text-lg ${
                    activeLink.includes(
                      Constant.MANAGER_PRACTICE_QUESTIONS_PATH
                    )
                      ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                      : "border border-l-2 border-y-0 border-r-0 border-white"
                  }`}
                  onClick={() =>
                    handleLinkClick(Constant.MANAGER_PRACTICE_QUESTIONS_PATH)
                  }
                >
                  Questions
                </Link>
                <Link
                  href={`${route + Constant.MANAGER_PRACTICE_SOLUTION_PATH}`}
                  className={`p-2 hover:bg-gray-300 flex gap-2 mb-2 hover:rounded-md py-3 text-lg ${
                    activeLink.includes(Constant.MANAGER_PRACTICE_SOLUTION_PATH)
                      ? "border border-l-2 border-y-0 border-r-0 border-red-600 "
                      : "border border-l-2 border-y-0 border-r-0 border-white"
                  }`}
                  onClick={() =>
                    handleLinkClick(Constant.MANAGER_PRACTICE_SOLUTION_PATH)
                  }
                >
                  Solutions
                </Link>
              </nav>
            </div>
          </div>
        </Fragment>
      ) : (
        <div
          className="mx-2 lg:hidden absolute mt-5 z-20"
          onClick={() => handleOpenMenu()}
        >
          <AiOutlineMenu className="text-3xl" />
        </div>
      )}
    </Fragment>
  );
}

export default SideBarAssignment;
