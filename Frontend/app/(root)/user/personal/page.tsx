"use client";
import PersonalForm from "@/components/form/PersonalForm";
import { User } from "@/types/user.type";
import React from "react";

const test: Omit<User, "password" | "re_password"> = {
  id: "123",
  username: "tranchimy123123",
  firstName: "mỹ",
  lastName: "trần",
  telephone: "0797934535",
  photos: "123",
  address: {
    addressLine: "123123",
    postalCode: null,
    defaultAddress: true,
  },
  email: "tranchimy@gmail.com",
};

function PagePersonal() {
  return (
    <div className="mt-10 mr-28 w-full">
      <div className="sticky top-[80px] bg-white h-10">
        <div className="font-bold"> Thông Tin Cá Nhân </div>
        <hr />
      </div>
      <PersonalForm userInfor={test} />
    </div>
  );
}

export default PagePersonal;
