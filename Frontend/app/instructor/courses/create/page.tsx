"use client";
import NotificationPopUp, {
  sendNotification,
} from "@/components/Notification/Notification";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import withAuth from "@/hoc/withAuth";
import withAuthManager from "@/hoc/withAuthManager";
import {
  setManageCourse,
  setParamCourseId,
} from "@/redux/features/courseSlice";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { useCreateCourseMutation } from "@/redux/services/courseApi";
import {
  useGetAllUsernameAdminQuery,
  useLazyGetAllUsernameAdminQuery,
} from "@/redux/services/userApi";
import { Course } from "@/types/course.type";
import { DataResponse } from "@/types/response.type";
import { extractId } from "@/utils/function";
import {
  ModuleName,
  NotificationMessage,
  StatusCode,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import showToast from "@/utils/showToast";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useState } from "react";

function CreateCoursePage() {
  const username = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const [nameCourse, setNameCourse] = useState<string>();
  const [createCourse] = useCreateCourseMutation();
  const dispatch = useAppDispatch();
  const router = useRouter();
  const { data: allUsernameAdmin, isSuccess: getAllUsernameAdminSuccess } =
    useGetAllUsernameAdminQuery(null);

  const handleChangeRouteManageCourse = (courseId: string | undefined) => {
    dispatch(setParamCourseId(courseId as string));
    const manageCourseUrl = `/instructor/courses/${courseId}/manage/content`;
    router.push(manageCourseUrl);
  };

  const handleCreateCourse = async () => {
    const newCourse: Course = {
      name: nameCourse ? nameCourse : " ",
      language: {
        id: "0",
      },
      level: {
        id: "0",
      },
      topic: {
        id: "0",
      },
      authorName: username,
    };

    await createCourse(newCourse)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        handleToast(fulfilled);
        sendNotification(
          username,
          allUsernameAdmin?.data as string[],
          NotificationMessage.CREATE_COURSE
        );
      })
      .catch((error) => {
        console.log(error);
        showToast(ToastStatus.ERROR, ToastMessage.CREATE_COURSE_FAIL);
      });
  };

  const handleToast = (dataResult: DataResponse) => {
    if (dataResult?.statusCode === StatusCode.REQUEST_SUCCESS) {
      showToast(ToastStatus.SUCCESS, ToastMessage.CREATE_COURSE_SUCCESS);
      handleChangeRouteManageCourse(extractId(dataResult.data as string));
    } else {
      showToast(ToastStatus.ERROR, ToastMessage.DATA_COURSE_EXISTED);
    }
  };
  return (
    <div>
      <div className="overflow-hidden">
        <div className="flex flex-col min-h-screen">
          <div className="border-b bg-white w-full h-20 border-b-1 border-gray-200 text-black sticky top-0 z-10 shadow-md">
            <div className="max-w-screen-2xl h-full mx-auto flex items-center justify-between px-4">
              <Link href={"/"} className="text-2xl uppercase">
                E-LEANING
              </Link>
              <div className="flex gpa-2">
                <NotificationPopUp hidden={true}/>
                <Link
                  href={"/instructor/courses"}
                  className="hover:text-orange-400 font-bold"
                >
                  Cancel
                </Link>
              </div>
            </div>
          </div>
          <div className="flex justify-center flex-col items-center w-1/2 mx-auto my-auto ">
            <div className="text-2xl pb-10">Name Your Course</div>
            <Input
              className="rounded-none border-black h-16 text-lg"
              placeholder="Ví dụ: Khóa Học JavaScript"
              onChange={(e) => setNameCourse(e.target.value)}
            ></Input>
          </div>
          <footer className="bg-gray-200 py-8 sticky bottom-0 shadow-md text-right">
            <Button
              className="mr-10 "
              disabled={nameCourse ? false : true}
              onClick={() => handleCreateCourse()}
            >
              Submit
            </Button>
          </footer>
        </div>
      </div>
    </div>
  );
}

export default withAuthManager(CreateCoursePage, ModuleName.COURSE_MANAGER);
