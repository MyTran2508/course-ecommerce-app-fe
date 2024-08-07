"use client";
import { useParams, useRouter } from "next/navigation";
import React, { Fragment, useEffect, useState } from "react";
import { IoIosArrowBack } from "react-icons/io";
import SaveButton from "./SaveButton";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { RoleType } from "@/types/user.type";
import { ModuleName, PermissionName, Role, RoleUser } from "@/utils/resources";
import ApprovedButton from "./ApprovedButton";
import RequestApprovalButton from "./RequestApprovalButton";
import {
  handleCountFieldsInSection,
  isPermissionGranted,
} from "@/utils/function";
import { Course } from "@/types/course.type";
import { Button } from "@/components/ui/button";
import withAuth from "@/hoc/withAuth";
import { RoleDetail } from "@/types/roles.type";
import NotificationPopUp from "@/components/Notification/Notification";
import withAuthManager from "@/hoc/withAuthManager";

function CreateCourseNavBar() {
  const router = useRouter();
  // const role = useAppSelector(
  //   (state) => state.persistedReducer.userReducer.user.roles as RoleType[]
  // )[0].id;

  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const roleDetail = role?.roleDetails;

  const sections = useAppSelector((state) => state.sectionReducer.section);
  const { totalLectureCount } = handleCountFieldsInSection(sections);
  const course = useAppSelector((state) => state.courseReducer.manageCourse);
  const courseId = useAppSelector((state) => state.courseReducer.courseId);
  const [courseData, setCourseData] = useState<Course>();
  useEffect(() => {
    setCourseData(course);
  }, [course]);
  useEffect(() => {}, [sections]);

  const handleClickBack = () => {
    if (role?.name === Role.ADMIN) {
      router.push("/admin/courses");
    } else {
      router.push("/instructor/courses");
    }
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
          <div className="flex gap-10 xs:gap-1 xs:mr-1 mr-10 items-center">
            {role?.name === Role.ADMIN ||
            isPermissionGranted(
              roleDetail as RoleDetail[],
              PermissionName.CAN_APPROVE_COURSE,
              ModuleName.COURSE_ADMIN
            ) ? (
              <ApprovedButton course={courseData as Course} />
            ) : (
              <Fragment>
                {totalLectureCount > 0 && !courseData?.isApproved ? (
                  <RequestApprovalButton course={courseData as Course} />
                ) : (
                  <Fragment>
                    {courseData?.isApproved ? (
                      <div className="italic text-orange-400">
                        Xét duyệt thành công
                      </div>
                    ) : null}
                  </Fragment>
                )}
                <SaveButton />
              </Fragment>
            )}
            {totalLectureCount !== 0 ? (
              <Button
                onClick={() =>
                  router.push(`/instructor/courses/preview/${courseId}`)
                }
                className="bg-blue-600 hover:bg-blue-200"
                disabled={
                  !(
                    isPermissionGranted(
                      roleDetail as RoleDetail[],
                      PermissionName.CAN_VIEW,
                      ModuleName.COURSE_MANAGER
                    ) ||
                    role?.name == Role.ADMIN ||
                    role?.name == Role.MANAGER
                  )
                }
              >
                Xem thử
              </Button>
            ) : null}
          </div>
        </div>
      </div>
    </div>
  );
}
export default withAuthManager(CreateCourseNavBar, ModuleName.COURSE_MANAGER);
