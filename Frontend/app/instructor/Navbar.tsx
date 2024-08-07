"use client";
import Link from "next/link";
import React, { Fragment, use, useEffect, useState } from "react";
import CustomButton from "../../components/CustomButton";
import { AiOutlineMenu } from "react-icons/ai";
import { Menu, Transition } from "@headlessui/react";
import { usePathname, useRouter } from "next/navigation";
import { iconMap } from "@/utils/map";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { setCredential, logout } from "@/redux/features/authSlice";
import Image from "next/image";
import {
  ModuleName,
  PermissionName,
  Role,
  RoleUser,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import showToast from "@/utils/showToast";
import {
  useGetAvatarQuery,
  useGetByUserNameQuery,
} from "@/redux/services/userApi";
import { User } from "@/types/user.type";
import { loadUser, removeUser, setUser } from "@/redux/features/userSlice";
import { RoleDetail, Roles } from "@/types/roles.type";
import { isPermissionGranted } from "@/utils/function";
import NotificationPopUp from "@/components/Notification/Notification";

const links = [
  { href: "/login", label: "Login", icon: "BiLogIn" },
  { href: "/signup", label: "Sign Up", icon: "BsFillPenFill" },
];

function InstructorNavbar() {
  const router = useRouter();
  const dispatch = useAppDispatch();
  const path = usePathname();
  const [userData, setUserData] = useState<User>();
  const [isLogout, setLogout] = useState(false);
  const [currentAvatar, setCurrentAvatar] = useState<string>();
  const user = useAppSelector((state) => state.persistedReducer.authReducer);
  const email = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.email
  );

  const { data: userNameData, isSuccess: userNameSuccess } =
    useGetByUserNameQuery(user.username as string);

  const { data: avatarData, isSuccess: avatarSuccess } = useGetAvatarQuery(
    user.username as string
  );
  const [role, setRole] = useState<Roles | null>(null);

  const MAX_TITLE_LENGTH = 25;
  const truncatedEmail =
    email.length > MAX_TITLE_LENGTH
      ? email.substring(0, MAX_TITLE_LENGTH) + "..."
      : email;

  useEffect(() => {
    if (userNameSuccess) {
      const userState: Pick<
        User,
        "username" | "photos" | "email" | "id" | "roles"
      > = {
        id: (userNameData.data as User).id,
        username: (userNameData.data as User).username,
        photos: (userNameData.data as User).photos,
        email: (userNameData.data as User).email,
        roles: (userNameData.data as User).roles,
      };
      dispatch(setUser(userState));
      setRole((userNameData.data as User).roles?.[0] || null);
      // dispatch(loadUser());
      setUserData(userNameData.data as User);
    }
    if (avatarSuccess) {
      setCurrentAvatar(avatarData.rawAvatar as string);
    }
  }, [userNameData, avatarData]);

  const handleLogout = () => {
    dispatch(removeUser());
    dispatch(logout());
    setLogout(true);
    router.push("/");
    showToast(ToastStatus.SUCCESS, ToastMessage.LOGOUT_SUCCESS);
  };

  return (
    <div className="border-b w-full h-20 border-b-1 border-gray-200 text-black sticky top-0 z-20 shadow-md">
      <div className="max-w-screen-2xl h-full mx-auto flex items-center justify-between px-16 xs:px-5">
        <Link href={"/"} className="text-2xl flex items-center">
          <h1 className="uppercase font-medium">E-LEANING</h1>
        </Link>
        <div className="">
          {user?.username ? (
            <div className="flex-center gap-10">
              {(isPermissionGranted(
                role?.roleDetails as RoleDetail[],
                PermissionName.CAN_CREATE,
                ModuleName.COURSE_MANAGER
              ) ||
                role?.name == Role.MANAGER) && (
                <Link href={"/instructor/courses/create"} className="xs:hidden">
                  Create New Course
                </Link>
              )}
              {(isPermissionGranted(
                role?.roleDetails as RoleDetail[],
                PermissionName.CAN_ASSIGNMENT,
                ModuleName.COURSE_MANAGER
              ) ||
                role?.name == Role.MANAGER) && (
                <Link
                  href={"/instructor/assignment-history"}
                  className={`xs:hidden ${path === "/instructor/assignment-history" ? "text-orange-500" : ""}`}
                >
                  Feedback Assignment
                </Link>
              )}

              <NotificationPopUp />

              <div>
                <Menu>
                  <Menu.Button>
                    <Image
                      src={
                        currentAvatar !== "Error"
                          ? `data:image/png;base64,${currentAvatar}`
                          : "/avatar.png"
                      }
                      width={400}
                      height={400}
                      className="w-12 h-12 rounded-full ml-2"
                      alt="avatar"
                    />
                  </Menu.Button>
                  <Menu.Items className="absolute right-2 mt-2 w-80 origin-top-right divide-y divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none p-2">
                    <div className="px-1 py-1">
                      <Transition
                        enter="transition ease-out duration-100"
                        enterFrom="transform opacity-0 scale-95"
                        enterTo="transform opacity-100 scale-100"
                        leave="transition ease-in duration-75"
                        leaveFrom="transform opacity-100 scale-100"
                        leaveTo="transform opacity-0 scale-95"
                      >
                        <div className="flex items-center gap-3">
                          <Image
                            src={
                              currentAvatar !== "Error"
                                ? `data:image/png;base64,${currentAvatar}`
                                : "/avatar.png"
                            }
                            width={400}
                            height={400}
                            alt="avt"
                            className="w-16 h-16 rounded-full"
                          />
                          <div>
                            <h4 className="font-bold text-orange-400">
                              {" "}
                              {userData ? userData.firstName : ""}
                            </h4>
                            <h4> {truncatedEmail}</h4>
                          </div>
                        </div>
                        <hr className="my-4" />

                        <div className="flex-col">
                          <div className="hidden xs:flex xs:flex-col">
                            <Link href={"/instructor/courses/create"}>
                              Quản Lý Khóa Học
                            </Link>
                            <hr className="my-4 w-full" />
                          </div>
                          <div>
                            <Link href={"/user/personal"}>Trang Cá Nhân </Link>
                            <hr className="my-4" />
                          </div>
                          <div
                            className="hover:cursor-pointer"
                            onClick={() => {
                              handleLogout();
                            }}
                          >
                            Đăng Xuất
                          </div>
                        </div>
                      </Transition>
                    </div>
                  </Menu.Items>
                </Menu>
              </div>
            </div>
          ) : (
            <div className="hidden lg:inline-flex gap-3">
              <CustomButton
                title="Login"
                containerStyles="bg-white-500 border-b-4 border-main-colors hover:bg-blue-200 hover:scale-110 text-black font-bold py-2 px-4 rounded duration-1000"
                handleClick={() => {
                  router.push("/login");
                }}
              ></CustomButton>
              <CustomButton
                title="SignUp"
                containerStyles="bg-white-500 border-b-4 border-main-colors hover:bg-blue-200 hover:scale-110 text-black font-bold py-2 px-4 rounded duration-1000"
                handleClick={() => {
                  router.push("/signup");
                }}
              ></CustomButton>
              <div className="text-3xl inline-flex lg:hidden">
                <Menu>
                  <Menu.Button>
                    <AiOutlineMenu />
                  </Menu.Button>
                  <Menu.Items className="absolute right-0 mt-10 w-56 origin-top-right divide-y divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none z-30">
                    <div className="px-1 py-1">
                      <Transition
                        enter="transition ease-out duration-100"
                        enterFrom="transform opacity-0 scale-95"
                        enterTo="transform opacity-100 scale-100"
                        leave="transition ease-in duration-75"
                        leaveFrom="transform opacity-100 scale-100"
                        leaveTo="transform opacity-0 scale-95"
                      >
                        {links.map((link) => (
                          <Menu.Item
                            as={Link}
                            key={link.href}
                            href={link.href}
                            className="ui-active:bg-violet-500 ui-active:text-white ui-not-active:bg-white ui-not-active:text-black group flex w-full items-center rounded-md px-2 py-2 text-sm"
                          >
                            {link.icon &&
                              iconMap[link.icon] &&
                              React.createElement(iconMap[link.icon], {
                                className: "mr-2",
                              })}
                            {link.label}
                          </Menu.Item>
                        ))}
                      </Transition>
                    </div>
                  </Menu.Items>
                </Menu>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
export default InstructorNavbar;
