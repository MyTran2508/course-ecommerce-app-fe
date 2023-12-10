import { Button } from "@/components/ui/button";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import React, { Fragment, useEffect, useState } from "react";
import Message from "./Message";
import { Course } from "@/types/course.type";
import { useUpdateApprovedMutation } from "@/redux/services/courseApi";
import showToast from "@/utils/showToast";
import { ToastMessage, ToastStatus } from "@/utils/resources";
import { setManageCourse, updateCourse } from "@/redux/features/courseSlice";

function ApprovedButton() {
  const course = useAppSelector((state) => state.courseReducer.manageCourse);
  const [isOpen, setOpen] = useState(false);
  const [courseData, setCourseData] = useState<Course>();
  const dispatch = useAppDispatch();
  const [updateApproveCourse] = useUpdateApprovedMutation();

  const handleClickOpen = () => {
    setOpen(!isOpen);
  };
  useEffect(() => {
    setCourseData(course);
    console.log(course);
  }, [course]);

  const handleApproveCourse = async () => {
    const request = {
      courseId: courseData?.id as string,
      isApproved: true,
      courseIssueReport: {},
    };
    await updateApproveCourse(request)
      .unwrap()
      .then((fulfilled) => {
        showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_COURSE_SUCCESS);
        dispatch(
          setManageCourse({ ...(courseData as Course), isApproved: true })
        );
      });
    handleClickOpen();
  };
  return (
    <div>
      {courseData && courseData.isAwaitingApproval && !courseData.isApproved ? (
        <Fragment>
          {isOpen ? (
            <Fragment>
              <div className="flex gap-3">
                <Button
                  className="bg-orange-400 text-white hover:bg-orange-300"
                  onClick={handleApproveCourse}
                >
                  Chấp nhận
                </Button>
                <Message />
                <Button
                  className="bg-white text-black hover:bg-red-400"
                  onClick={handleClickOpen}
                >
                  Hủy
                </Button>
              </div>
            </Fragment>
          ) : (
            <Fragment>
              <div className="flex gap-5">
                <Button
                  className="bg-white text-black hover:bg-white"
                  onClick={() => handleClickOpen()}
                >
                  Xét Duyệt
                </Button>
              </div>
            </Fragment>
          )}
        </Fragment>
      ) : (
        <Fragment>
          {courseData && courseData.isApproved ? (
            <div className="italic text-orange-400"> Đã Xét Duyệt</div>
          ) : null}
        </Fragment>
      )}
    </div>
  );
}

export default ApprovedButton;
