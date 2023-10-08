"use client";
import LoginForm from "@/components/form/LoginForm";
import { useRouter } from "next/navigation";
import React from "react";
import { IoChevronBackCircleSharp } from "react-icons/io5";

function PageLogin() {
  const route = useRouter();
  return (
    <div className=" bg-blue-100 border-6 p-2">
      <div className="flex justify-start items-center gap-2 font-normal">
        <IoChevronBackCircleSharp
          className="text-3xl ml-2 cursor-pointer"
          onClick={() => {
            route.back();
          }}
        />
        <p className="">Back to Home</p>
      </div>
      <div className="flex-center h-screen">
        <LoginForm />
      </div>
    </div>
  );
}

export default PageLogin;
