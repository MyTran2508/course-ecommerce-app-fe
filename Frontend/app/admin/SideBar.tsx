"use client";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import {
  useLazyGetAvatarQuery,
  useLazyGetByUserNameQuery,
} from "@/redux/services/userApi";
import { RoleDetail, Roles } from "@/types/roles.type";
import { isPermissionGranted } from "@/utils/function";
import { Constant, ModuleName, PermissionName, Role } from "@/utils/resources";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import React, { useEffect } from "react";
import { CiMoneyBill } from "react-icons/ci";
import { SiOpenaccess, SiScikitlearn } from "react-icons/si";
import { useLazyGetRolesByUserNameQuery } from "@/redux/services/roleApi";
import { User } from "@/types/user.type";
import { loadUser, setUser } from "@/redux/features/userSlice";
import { CiLogout } from "react-icons/ci";
import { MdDarkMode } from "react-icons/md";
import NotificationPopUp from "@/components/Notification/Notification";

function SideBar() {
  const path = usePathname();
  const dispatch = useAppDispatch();
  const userName = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const [getByUserName, { data: userNameData, isSuccess: userNameSuccess }] =
    useLazyGetByUserNameQuery();
  const [getAvatar, { data: avatarData, isSuccess: avatarSuccess }] =
    useLazyGetAvatarQuery();
  const [role, setRole] = React.useState<Roles | null>(null);

  useEffect(() => {
    getByUserName(userName);
    getAvatar(userName);
  }, []);

  useEffect(() => {
    if (userNameSuccess) {
      const userState: Pick<
        User,
        "username" | "photos" | "email" | "id" | "roles"
      > = {
        id: (userNameData?.data as User).id,
        username: (userNameData?.data as User).username,
        photos: avatarData?.rawAvatar as string,
        email: (userNameData?.data as User).email,
        roles: (userNameData?.data as User)?.roles,
      };
      setRole((userNameData?.data as User)?.roles?.[0] || null);

      dispatch(setUser(userState));
      dispatch(loadUser());
    }
  }, [userNameData, avatarData]);
  if (
    !role ||
    (role.name !== Role.ADMIN &&
      !role.roleDetails?.some((role) => role.canView === true))
  ) {
    return null;
  }

  return (
    <div className="sticky top-0 h-screen flex flex-col bg-clip-border bg-[#263238] text-gray-700 h-[calc(100vh-2rem)] w-full max-w-[16rem]">
      <div className="my-4 p-2 flex gap-2">
        <h5 className="flex-center gap-2 block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-white">
          <SiScikitlearn className="text-3xl" />
          E-LEARNING
        </h5>
      </div>
      <nav className="flex flex-col h-full w-full min-w-240px font-sans text-base font-normal text-[#CFD8DC]">
        {(role?.name === Role.ADMIN ||
          isPermissionGranted(
            role?.roleDetails || [],
            PermissionName.CAN_VIEW,
            ModuleName.STATISTIC
          )) &&  (
          <Link href={Constant.ADMIN_STATISTIC_PATH}>
            <div
              role="button"
              tabIndex={0}
              className={`${
                path === Constant.ADMIN_STATISTIC_PATH
                  ? "bg-[#37474f] flex items-center p-4 w-full text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
                  : "flex items-center w-full p-4 border-b-2 border-[#455A64] text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none"
              }`}
            >
              <div className="grid place-items-center mr-4">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="currentColor"
                  aria-hidden="true"
                  className="h-5 w-5"
                >
                  <path
                    fillRule="evenodd"
                    d="M2.25 2.25a.75.75 0 000 1.5H3v10.5a3 3 0 003 3h1.21l-1.172 3.513a.75.75 0 001.424.474l.329-.987h8.418l.33.987a.75.75 0 001.422-.474l-1.17-3.513H18a3 3 0 003-3V3.75h.75a.75.75 0 000-1.5H2.25zm6.04 16.5l.5-1.5h6.42l.5 1.5H8.29zm7.46-12a.75.75 0 00-1.5 0v6a.75.75 0 001.5 0v-6zm-3 2.25a.75.75 0 00-1.5 0v3.75a.75.75 0 001.5 0V9zm-3 2.25a.75.75 0 00-1.5 0v1.5a.75.75 0 001.5 0v-1.5z"
                    clipRule="evenodd"
                  />
                </svg>
              </div>
              Statistics
            </div>
          </Link>
        )}
        {(role?.name === Role.ADMIN ||
          isPermissionGranted(
            role?.roleDetails || [],
            PermissionName.CAN_VIEW,
            ModuleName.COURSE_ADMIN
          )) && (
          <Link href={Constant.ADMIN_COURSE_PATH}>
            <div
              role="button"
              tabIndex={0}
              className={`${
                path === Constant.ADMIN_COURSE_PATH
                  ? "bg-[#37474f] flex items-center p-4 w-full text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
                  : "flex items-center w-full p-4 border-b-2 border-[#455A64] text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none"
              }`}
            >
              <div className="grid place-items-center mr-4">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="currentColor"
                  aria-hidden="true"
                  className="h-5 w-5"
                >
                  <path
                    fillRule="evenodd"
                    d="M6.912 3a3 3 0 00-2.868 2.118l-2.411 7.838a3 3 0 00-.133.882V18a3 3 0 003 3h15a3 3 0 003-3v-4.162c0-.299-.045-.596-.133-.882l-2.412-7.838A3 3 0 0017.088 3H6.912zm13.823 9.75l-2.213-7.191A1.5 1.5 0 0017.088 4.5H6.912a1.5 1.5 0 00-1.434 1.059L3.265 12.75H6.11a3 3 0 012.684 1.658l.256.513a1.5 1.5 0 001.342.829h3.218a1.5 1.5 0 001.342-.83l.256-.512a3 3 0 012.684-1.658h2.844z"
                    clipRule="evenodd"
                  />
                </svg>
              </div>
              Course management
            </div>
          </Link>
        )}
        {(role?.name === Role.ADMIN ||
          isPermissionGranted(
            role?.roleDetails || [],
            PermissionName.CAN_VIEW,
            ModuleName.USER
          )) && (
          <Link href={Constant.ADMIN_USER_PATH}>
            <div
              role="button"
              tabIndex={0}
              className={`${
                path === Constant.ADMIN_USER_PATH
                  ? "bg-[#37474f] flex items-center p-4 w-full text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
                  : "flex items-center w-full p-4 border-b-2 border-[#455A64] text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none"
              }`}
            >
              <div className="grid place-items-center mr-4">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="currentColor"
                  aria-hidden="true"
                  className="h-5 w-5"
                >
                  <path
                    fillRule="evenodd"
                    d="M18.685 19.097A9.723 9.723 0 0021.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 003.065 7.097A9.716 9.716 0 0012 21.75a9.716 9.716 0 006.685-2.653zm-12.54-1.285A7.486 7.486 0 0112 15a7.486 7.486 0 015.855 2.812A8.224 8.224 0 0112 20.25a8.224 8.224 0 01-5.855-2.438zM15.75 9a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0z"
                    clipRule="evenodd"
                  />
                </svg>
              </div>
              Account management
            </div>
          </Link>
        )}
        {(role?.name === Role.ADMIN ||
          isPermissionGranted(
            role?.roleDetails || [],
            PermissionName.CAN_VIEW,
            ModuleName.USER_LOG
          )) && (
          <Link href={Constant.ADMIN_USER_HISTORY_PATH}>
            <div
              role="button"
              tabIndex={0}
              className={`${
                path === Constant.ADMIN_USER_HISTORY_PATH
                  ? "bg-[#37474f] flex items-center p-4 w-full text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
                  : "flex items-center w-full p-4 border-b-2 border-[#455A64] text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none"
              }`}
            >
              <div className="grid place-items-center mr-4">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="currentColor"
                  aria-hidden="true"
                  className="h-5 w-5"
                >
                  <path
                    fillRule="evenodd"
                    d="M18.685 19.097A9.723 9.723 0 0021.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 003.065 7.097A9.716 9.716 0 0012 21.75a9.716 9.716 0 006.685-2.653zm-12.54-1.285A7.486 7.486 0 0112 15a7.486 7.486 0 015.855 2.812A8.224 8.224 0 0112 20.25a8.224 8.224 0 01-5.855-2.438zM15.75 9a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0z"
                    clipRule="evenodd"
                  />
                </svg>
              </div>
              User history
            </div>
          </Link>
        )}
        {(role?.name === Role.ADMIN ||
          isPermissionGranted(
            role?.roleDetails || [],
            PermissionName.CAN_VIEW,
            ModuleName.ROLE
          )) && (
          <Link href={Constant.ADMIN_ROLE_PATH}>
            <div
              role="button"
              tabIndex={0}
              className={`${
                path === Constant.ADMIN_ROLE_PATH
                  ? "bg-[#37474f] flex items-center p-4 w-full text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
                  : "flex items-center w-full p-4 border-b-2 border-[#455A64] text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none"
              }`}
            >
              <div className="grid place-items-center mr-4">
                <SiOpenaccess className="text-lg" />
              </div>
              Permission management
            </div>
          </Link>
        )}
        {(role?.name === Role.ADMIN ||
          isPermissionGranted(
            role?.roleDetails || [],
            PermissionName.CAN_VIEW,
            ModuleName.ORDER
          )) && (
          <Link href={Constant.ADMIN_BILL_PATH}>
            <div
              role="button"
              tabIndex={0}
              className={`${
                path === Constant.ADMIN_BILL_PATH
                  ? "bg-[#37474f] flex items-center p-4 w-full text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
                  : "flex items-center w-full p-4 text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
              }`}
            >
              <div className="grid place-items-center mr-4">
                <CiMoneyBill className="text-lg" />
              </div>
              Order management
            </div>
          </Link>
        )}

        <Link href={"/"}>
          <div
            role="button"
            tabIndex={0}
            className="flex items-center w-full p-4 text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-[#455A64]"
          >
            <div className="grid place-items-center mr-4">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
                aria-hidden="true"
                className="h-5 w-5"
              >
                <path
                  fillRule="evenodd"
                  d="M12 2.25a.75.75 0 01.75.75v9a.75.75 0 01-1.5 0V3a.75.75 0 01.75-.75zM6.166 5.106a.75.75 0 010 1.06 8.25 8.25 0 1011.668 0 .75.75 0 111.06-1.06c3.808 3.807 3.808 9.98 0 13.788-3.807 3.808-9.98 3.808-13.788 0-3.808-3.807-3.808-9.98 0-13.788a.75.75 0 011.06 0z"
                  clipRule="evenodd"
                />
              </svg>
            </div>
            Home
          </div>
        </Link>

        <div className="flex-1 flex flex-col mb-8">
          {/* <Link href={"/"} className="mt-auto">
            <div
              role="button"
              tabIndex={0}
              className="flex items-center w-full p-4 text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none border-b-2 border-t-2 border-[#455A64]"
            >
              <div className="grid place-items-center mr-4">
                <MdDarkMode className="text-lg" />
              </div>
              Chế Độ Tối
            </div>
          </Link> */}
          <Link href={"/"} className="mt-auto">
            <div
              role="button"
              tabIndex={0}
              className="flex items-center w-full p-4 text-start leading-tight transition-all hover:bg-opacity-80 hover:text-[#ECEFF1] focus:[#ECEFF1] active:[#ECEFF1] outline-none"
            >
              <div className="grid place-items-center mr-4">
                <CiLogout className="text-lg" />
              </div>
              Logout
            </div>
          </Link>
        </div>
      </nav>
    </div>
  );
}

export default SideBar;
