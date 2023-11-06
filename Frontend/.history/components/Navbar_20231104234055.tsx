"use client";
import Link from "next/link";
import React, { Fragment, useEffect, useState } from "react";
import CustomButton from "./CustomButton";
import SearchBar from "./SearchBar";
import { AiOutlineMenu } from "react-icons/ai";
import { Menu, Transition } from "@headlessui/react";
import { useRouter } from "next/navigation";
import { iconMap } from "@/utils/map";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import { setCredential, logout } from "@/redux/features/authSlice";
import Image from "next/image";
import { ToastMessage, ToastStatus } from "@/utils/resources";
import showToast from "@/utils/showToast";
import { FiShoppingCart } from "react-icons/fi";
import { useGetByUserNameQuery } from "@/redux/services/userApi";
import { User } from "@/types/user.type";
import { setUser } from "@/redux/features/userSlice";
import { AiOutlineRight } from "react-icons/ai";
import "./style/category.scss";

const links = [
  { href: "/login", label: "Login", icon: "BiLogIn" },
  { href: "/signup", label: "Sign Up", icon: "BsFillPenFill" },
];

function Navbar() {
  const router = useRouter();
  const dispatch = useAppDispatch();
  const cart = useAppSelector((state) => state.cartReducer);
  const [isLogout, setLogout] = useState(false);
  const [userData, setUserData] = useState<User>();
  let user = JSON.parse(localStorage.getItem("user") || "{}");
  const { data, isLoading, isSuccess } = useGetByUserNameQuery(user.user);

  useEffect(() => {
    dispatch(setCredential(user));
    if (isSuccess) {
      dispatch(setUser(data.data as User));
      setUserData(data.data as User);
      // const avt = (data.data as User).photos;
      // setAvatar(avt);
    }
  }, [data]);

  const handleLogout = () => {
    dispatch(logout());
    setLogout(true);
    showToast(ToastStatus.SUCCESS, ToastMessage.LOGOUT_SUCCESS);
  };

  const handleChangeRouteCart = () => {
    router.push("/cart");
  };

  return (
    <div className="border-b bg-white w-full h-20 border-b-1 border-gray-200 text-black sticky top-0 z-20 shadow-md">
      <div className="max-w-screen-2xl h-full mx-auto flex items-center justify-between px-16">
        <Link href={"/"} className="text-2xl flex items-center">
          <h1 className="uppercase font-medium">E-LEANING</h1>
          <Category />
        </Link>

        <div className="hidden lg:inline-flex">
          <SearchBar />
        </div>
        <div className="">
          {user?.user ? (
            <div className="flex-center gap-10">
              <div className="xs:hidden">
                <Link href={"/my-courses"}>Khóa Học Của Tôi</Link>
              </div>
              <div
                className="flex relative hover:cursor-pointer"
                onClick={() => handleChangeRouteCart()}
              >
                <span className="absolute bg-orange-400 rounded-full text-xs text-white ml-4 px-1">
                  {cart.length}
                </span>
                <FiShoppingCart className="text-2xl" />
              </div>
              <div>
                <Menu>
                  <Menu.Button>
                    <Image
                      src={userData ? userData.photos : "/banner.jpg"}
                      width={400}
                      height={400}
                      className="w-12 h-12 rounded-full ml-2"
                      alt="avatar"
                    />
                  </Menu.Button>
                  <Menu.Items className="absolute right-2 mt-2 w-56 origin-top-right divide-y divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none p-2">
                    <div className="px-1 py-1">
                      <Transition
                        enter="transition ease-out duration-100"
                        enterFrom="transform opacity-0 scale-95"
                        enterTo="transform opacity-100 scale-100"
                        leave="transition ease-in duration-75"
                        leaveFrom="transform opacity-100 scale-100"
                        leaveTo="transform opacity-0 scale-95"
                      >
                        <div className="flex-center gap-4">
                          <Image
                            src={userData ? userData.photos : "/banner.jpg"}
                            width={400}
                            height={400}
                            alt="avt"
                            className="w-16 h-16 rounded-full"
                          />
                          <h4> {userData ? userData.firstName : ""}</h4>
                        </div>
                        <hr className="my-4" />

                        <div className="flex-col">
                          <div className="hidden xs:flex xs:flex-col">
                            <Link href={"/my-courses"}>Khóa Học Của Tôi</Link>
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
                containerStyles="bg-white-500 border-b-4 border-orange-500 hover:bg-blue-200 hover:scale-110 text-black font-bold py-2 px-4 rounded duration-1000"
                handleClick={() => {
                  router.push("/login");
                }}
              ></CustomButton>
              <CustomButton
                title="SignUp"
                containerStyles="bg-white-500  border-b-4 border-orange-500 hover:bg-blue-200 hover:scale-110 text-black font-bold py-2 px-4 rounded duration-1000"
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

const Category = () => {
  const [isOpenCategory, setOpenCategory] = useState(true);
  return (
    <div className="dropdown ml-6">
      <button
        className="dropdown-select text-lg"
        onMouseEnter={() => setOpenCategory(true)}
        onMouseLeave={() => setOpenCategory(false)}
      >
        Categories
      </button>
      <div className="dropdown-main">
        <div className="dropdown-option">
          <ul className="dropdown-list">
            <li>Backend</li>
            <li>FrontEnd</li>
            <li>Testing</li>
          </ul>
        </div>
        <div className="dropdown-option">
          <ul className="dropdown-list">
            <h3>Popular Topics</h3>
            <li>Backend</li>
            <li>FrontEnd</li>
            <li>Testing</li>
          </ul>
        </div>
      </div>
      {/* <div
        className="categories"
        onMouseEnter={() => setOpenCategory(true)}
        onMouseLeave={() => setOpenCategory(false)}
      >
        <ul className="categories-option">
          <OptionItem name="Backend" />
          <OptionItem name="Frontend" />
          <OptionItem name="Testing" />
          <li className="topics">
            <ul
              className="dropdown__option dropdown__option--topics"
              onMouseEnter={() => setOpenCategory(true)}
              onMouseLeave={() => setOpenCategory(false)}
            >
              <h1 className="topics-name">Popular Topics</h1>
              <OptionItem name="Backend" />
              <OptionItem name="Frontend" />
              <OptionItem name="Testing" />
            </ul>
          </li>
        </ul>
      </div> */}
    </div>
  );
  // <span className="ml-6 text-lg nav__category">Category</span>;
};

export default Navbar;
