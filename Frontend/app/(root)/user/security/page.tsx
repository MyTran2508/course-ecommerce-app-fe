"use client";
import PasswordForm from "@/components/form/ChangePasswordForm";
import isUserExisted from "@/hoc/isUserExisted";
import React from "react";

function PageSecurity() {
  return (
    <div className="mt-10 mr-28 w-full">
      <div className="sticky top-[80px]  h-10">
        <div className="font-bold flex-center"> Bảo Mật </div>
        <hr />
      </div>
      <PasswordForm />
    </div>
  );
}

export default isUserExisted(PageSecurity);
