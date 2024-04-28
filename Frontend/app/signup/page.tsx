"use client";
import SignUpForm from "@/components/Form/SignUpForm";
import { useRouter } from "next/navigation";
import React from "react";
import { IoCloseOutline } from "react-icons/io5";
import "../../components/style/signup.scss";

function PageSignUp() {
  const route = useRouter();
  return (
    <div className="border-6 sign-up-page w-full">
      <div className="flex justify-start items-center gap-2 font-normal fixed">
        <IoCloseOutline
          className="text-3xl ml-3 mt-3 cursor-pointer btn-back text-6xl"
          onClick={() => {
            route.back();
          }}
        />
      </div>
      <div className="flex justify-center h-screen w-full sign-up-main ">
        <SignUpForm />
      </div>
    </div>
  );
}

export default PageSignUp;
