import { Button } from "@/components/ui/button";
import { setManageCourse } from "@/redux/features/courseSlice";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { useUpdateAwaitingApprovalMutation } from "@/redux/services/courseApi";
import { Course, CourseIssueReport } from "@/types/course.type";
import {
  NotificationMessage,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import showToast from "@/utils/showToast";
import React, { Fragment, useState } from "react";
import { FaRegBell } from "react-icons/fa";
import { Menu, Transition } from "@headlessui/react";
import { MdFiberNew } from "react-icons/md";
import { usePathname } from "next/navigation";
import NotificationPopUp, {
  sendNotification,
} from "@/components/Notification/Notification";
import { useGetAllUsernameAdminQuery } from "@/redux/services/userApi";
interface RequestApprovalProps {
  course: Course;
}

function RequestApprovalButton(props: RequestApprovalProps) {
  const { course } = props;
  const path = usePathname();
  const dispatch = useAppDispatch();
  const username = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const courseId = useAppSelector((state) => state.courseReducer.courseId);
  const [isOpen, setOpen] = useState(false);
  const [updateAwaitingApproval] = useUpdateAwaitingApprovalMutation();
  const { data: allUsernameAdmin, isSuccess: getAllUsernameAdminSuccess } =
    useGetAllUsernameAdminQuery(null);

  const handleClickOpen = () => {
    setOpen(!isOpen);
  };
  const handleClickUpdateAwaitingApproval = async () => {
    const request = {
      courseId: courseId as string,
      isAwaitingApproval: true,
    };
    console.log(request);
    await updateAwaitingApproval(request)
      .unwrap()
      .then((fulfilled) => {
        showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_COURSE_SUCCESS);
        dispatch(
          setManageCourse({ ...(course as Course), isAwaitingApproval: true })
        );
      });

    sendNotification(
      username,
      allUsernameAdmin?.data as string[],
      NotificationMessage.REQUEST_APPROVAL,
      path
    );
  };
  const renderIssue = () => {
    const issueList = [
      ...(course.courseIssueReports as CourseIssueReport[]),
    ] as CourseIssueReport[];
    const renderedIssues = issueList.reverse().map((issue, index) => {
      return (
        <div
          className={` px-4 py-2 rounded-sm font-bold mt-2
          ${
            index === 0
              ? "text-black bg-orange-200"
              : "text-slate-600  bg-slate-100"
          }`}
          key={issue.id}
        >
          <div className={`${index === 0 ? "flex-between" : null}`}>
            <div>
              Lỗi: <em>{issue.message}</em>
            </div>
            {index === 0 ? (
              <div className="text-orange-800 italic text-2xl">
                <MdFiberNew />
              </div>
            ) : null}
          </div>
          <div className="flex-between">
            <div>
              Loại: <span>{issue.issueType}</span>
            </div>
            <div>
              Mức độ:
              <span className={issue.severityLevel.toString()}>
                {issue.severityLevel}
              </span>
            </div>
          </div>
        </div>
      );
    });
    if (issueList.length === 0)
      return <div className="text-center">Không có lỗi nào</div>;
    return renderedIssues;
  };

  return (
    <div>
      <NotificationPopUp hidden={true} />
      {course && !course.isAwaitingApproval ? (
        <Fragment>
          <div className="flex gap-6 flex-center">
            <div>
              {(course.courseIssueReports as CourseIssueReport[]) ? (
                <Fragment>
                  <Menu>
                    <Menu.Button>
                      <div className="relative hover:cursor-pointer">
                        <div className="absolute top-0 bg-red-500 rounded-full w-2 h-2">
                          &nbsp;
                        </div>
                        <FaRegBell className="text-2xl" />
                      </div>
                    </Menu.Button>
                    <Menu.Items className="absolute mt-1 right-80 w-[400px] origin-top-right divide-x divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none p-2">
                      <div className="px-1 py-1 overflow-y-scroll h-[200px] custom-scrollbar">
                        <Transition
                          enter="transition ease-out duration-100"
                          enterFrom="transform opacity-0 scale-95"
                          enterTo="transform opacity-100 scale-100"
                          leave="transition ease-in duration-75"
                          leaveFrom="transform opacity-100 scale-100"
                          leaveTo="transform opacity-0 scale-95"
                        >
                          {renderIssue()}
                        </Transition>
                      </div>
                    </Menu.Items>
                  </Menu>
                </Fragment>
              ) : null}
            </div>
            {isOpen ? (
              <Fragment>
                <div className="flex gap-3">
                  <Button
                    className="bg-orange-400 text-white hover:bg-orange-300"
                    onClick={handleClickUpdateAwaitingApproval}
                  >
                    Gửi Yêu Cầu
                  </Button>
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
                    className="bg-orange-400 text-white hover:bg-orange-300"
                    onClick={() => handleClickOpen()}
                  >
                    Yêu Cầu Xét Duyệt
                  </Button>
                </div>
              </Fragment>
            )}
          </div>
        </Fragment>
      ) : (
        <div className="italic text-orange-400">Đang chờ xét duyệt</div>
      )}
    </div>
  );
}

export default RequestApprovalButton;
