import React, { Fragment, useState } from "react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";
import { MoreHorizontal } from "lucide-react";
import UserDialog from "./UserDialog";
import { User } from "@/types/user.type";
import {
  useGetAllUsernameAdminQuery,
  useUpdateUserAdminMutation,
} from "@/redux/services/userApi";
import showToast from "@/utils/showToast";
import {
  ModuleName,
  NotificationMessage,
  PermissionName,
  Role,
  RoleUser,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import { Course } from "@/types/course.type";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { updateUser } from "@/redux/features/userSlice";
import { useRouter } from "next/navigation";
import { setManageCourse, updateCourse } from "@/redux/features/courseSlice";
import { Order, OrderItem } from "@/types/order.type";
import OrderDialog from "./OrderDialog";
import { isPermissionGranted } from "@/utils/function";
import { AssignmentHistory } from "@/types/assignment.type";
import NotificationPopUp, {
  sendNotification,
} from "../Notification/Notification";
import { useRejectApprovedCourseMutation, useUpdateApprovedMutation } from "@/redux/services/courseApi";

interface ActionCellProps {
  user?: User;
  course?: Course;
  bill?: OrderItem[];
  assignmentHistory?: AssignmentHistory;
}

const ActionsCell = (props: ActionCellProps) => {
  const router = useRouter();
  const { user, course, bill, assignmentHistory } = props;
  const username = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const [isOpenUserDialog, setIsOpenUserDialog] = useState(false);
  const [isOpenOrderDialog, setIsOpenOrderDialog] = useState(false);
  const [updateUserAdmin] = useUpdateUserAdminMutation();
  const dispatch = useAppDispatch();
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const { data: allUsernameAdmin, isSuccess: getAllUsernameAdminSuccess } =
    useGetAllUsernameAdminQuery(null);

  const roleDetail = role?.roleDetails;
  const [updateApproveCourse] = useRejectApprovedCourseMutation();

  const handleUpdateUserAdmin = async (user: User) => {
    await updateUserAdmin(user)
      .unwrap()
      .then((fulfilled) => {
        dispatch(updateUser());
        showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_USER_SUCCESS);
      });

    sendNotification(
      username,
      (allUsernameAdmin?.data as string[]).filter(
        (item) => item !== user.username
      ),
      NotificationMessage.UPDATE_USER
    );
  };

  const handleEditUser = () => {
    setIsOpenUserDialog(true);
  };
  const handleViewOrder = () => {
    setIsOpenOrderDialog(true);
  };

  const handleViewCourse = () => {
    dispatch(setManageCourse(course as Course));
    router.push(`/instructor/courses/${course?.id}/manage/content`);
  };

  const handleViewAssignment = () => {
    router.push(`/instructor/assignment-history/${assignmentHistory?.id}`);
  };

  const updateUserActivationStatus = (item: User) => {
    const user = { ...item, removed: !item.removed };
    handleUpdateUserAdmin(user);
    sendNotification(
      username,
      (allUsernameAdmin?.data as string[]).filter(
        (item) => item !== user.username
      ),
      user?.removed
        ? NotificationMessage.DELETE_USER
        : NotificationMessage.RESTORE_USER
    );
  };
  const handleUnApproveCourse = async() => {
    await updateApproveCourse(course?.id as string)
    .unwrap()
    .then((fulfilled) => {
      showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_COURSE_SUCCESS);
      dispatch(
        setManageCourse({ ...(course as Course), isAwaitingApproval: false })
      );
    });
    sendNotification(
      "SYSTEM",
      [course?.authorName as string],
      NotificationMessage.REJECT_COURSE
    );
    dispatch(updateCourse()); 
  }

  return (
    <div>
      <NotificationPopUp hidden={true} />
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant="ghost" className="h-8 w-8 p-0">
            <span className="sr-only">Open menu</span>
            <MoreHorizontal className="h-4 w-4" />
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end">
          <DropdownMenuLabel>Actions</DropdownMenuLabel>
          {user ? (
            <Fragment>
              <DropdownMenuItem onClick={() => handleEditUser()}>
                Xem Chi Tiết
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              {(isPermissionGranted(
                roleDetail || [],
                PermissionName.CAN_REMOVE,
                ModuleName.USER
              ) ||
                role?.name == Role.ADMIN) && (
                <DropdownMenuItem
                  onClick={() => updateUserActivationStatus(user)}
                >
                  {user.removed ? "Khôi Phục Tài Khoản" : "Xóa Tài Khoản"}
                </DropdownMenuItem>
              )}
            </Fragment>
          ) : null}
          {course ? (
            <Fragment>
              {(isPermissionGranted(
                roleDetail || [],
                PermissionName.CAN_VIEW,
                ModuleName.COURSE_ADMIN
              ) ||
                isPermissionGranted(
                  roleDetail || [],
                  PermissionName.CAN_APPROVE_COURSE,
                  ModuleName.COURSE_ADMIN
                ) ||
                role?.name == Role.ADMIN ||
                role?.name == Role.MANAGER) && (
                <DropdownMenuItem onClick={() => handleViewCourse()}>
                  Xem Chi Tiết
                </DropdownMenuItem>
              )}
              <DropdownMenuSeparator />
              {/* <DropdownMenuItem
                onClick={() => updateUserActivationStatus(user)}
              >
                {user.removed ? "Khôi Phục Tài Khoản" : "Xóa Tài Khoản"}
              </DropdownMenuItem> */}
              {role?.name == Role.ADMIN &&
                course?.isApproved &&(
                <DropdownMenuItem onClick={() => handleUnApproveCourse()}>
                  Hoàn Tác Xét Duyệt
                </DropdownMenuItem>
                )
              }
            </Fragment>
          ) : null}
          {bill ? (
            <Fragment>
              <DropdownMenuItem onClick={() => handleViewOrder()}>
                Xem Chi Tiết
              </DropdownMenuItem>
              <DropdownMenuSeparator />
            </Fragment>
          ) : null}
          {assignmentHistory ? (
            <Fragment>
              <DropdownMenuItem onClick={() => handleViewAssignment()}>
                Xem Chi Tiết
              </DropdownMenuItem>
              <DropdownMenuSeparator />
            </Fragment>
          ) : null}
        </DropdownMenuContent>
      </DropdownMenu>
      <UserDialog
        isOpen={isOpenUserDialog}
        setIsOpen={setIsOpenUserDialog}
        user={user as User}
      />
      <OrderDialog
        isOpen={isOpenOrderDialog}
        setIsOpen={setIsOpenOrderDialog}
        bill={bill as OrderItem[]}
      />
    </div>
  );
};

export default ActionsCell;
