"use client";
import { useParams, useRouter } from "next/navigation";
import React, { Fragment, useEffect } from "react";
import { IoIosArrowBack } from "react-icons/io";
import SaveButton from "./SaveButton";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import { RoleType } from "@/types/user.type";
import { Role } from "@/utils/resources";
import ApprovedButton from "./ApprovedButton";
import RequestApprovalButton from "./RequestApprovalButton";
import { handleCountFieldsInSection } from "@/utils/function";

function CreateCourseNavBar() {
  const router = useRouter();
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles as RoleType[]
  )[0].id;
  const sections = useAppSelector((state) => state.sectionReducer.section);
  const { totalLectureCount } = handleCountFieldsInSection(sections);

  const handleClickBack = () => {
    router.back();
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
            {role === Role.ADMIN ? (
              <ApprovedButton />
            ) : (
              <Fragment>
                {totalLectureCount > 0 ? <RequestApprovalButton /> : null}

                <SaveButton />
              </Fragment>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreateCourseNavBar;
