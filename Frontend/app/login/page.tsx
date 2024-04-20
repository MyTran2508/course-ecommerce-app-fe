"use client";
import LoginForm from "@/components/Form/LoginForm";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { useLoginUserMutation } from "@/redux/services/authApi";
import { useRouter } from "next/navigation";
import React from "react";
import { IoChevronBackCircleSharp, IoCloseOutline } from "react-icons/io5";
import "../../components/style/LoginForm.scss";

function PageLogin() {
  const route = useRouter();
  const token = useAppSelector(
    (state) => state.persistedReducer.authReducer.token
  );

  return (
    <div className=" bg-blue-300 border-6 login-page w-full">
      <div className="flex justify-start items-center gap-2 font-normal fixed">
        <IoCloseOutline
          className="text-3xl ml-3 mt-3 cursor-pointer btn-back text-6xl"
          onClick={() => {
            route.push("/");
          }}
        />
      </div>
      <div className="flex justify-center h-screen w-full login-main">
        <LoginForm />
      </div>
    </div>
  );
}

export default PageLogin;
