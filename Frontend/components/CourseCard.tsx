"use client";
import React, { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import Link from "next/link";
import Image from "next/image";
import { CourseCardProps } from "@/types";
import { BiMoneyWithdraw } from "react-icons/bi";
import { BsPeopleFill } from "react-icons/bs";

const test = [
  {
    id: 1,
    title: "Java Script",
    image: "/banner.jpg",
    price: 200000,
    members: 2001,
  },
];

function CourseCard(props: any) {
  const isMyCourse = props.myCourse;

  return (
    <Card
      className="w-full max-w-fit border-0 !bg-transparent sm:max-w-[356px] m-8 "
      key={test[0].id}
    >
      <CardHeader className="flex-center flex-col gap-2.5 !p-0 hover:cursor-pointer">
        <div className="h-fit w-full relative">
          <div className="group">
            <Image
              src={test[0].image}
              className="h-full w-full rounded-md"
              width={380}
              height={440}
              alt={test[0].title}
            />
            <div className="absolute inset-0 bg-black opacity-0 group-hover:opacity-50 rounded-md" />
            <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-500">
              <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300 ease-in-out">
                <div className="bg-white rounded-2xl py-2 px-4 transform translate-y-4 group-hover:translate-y-0 transition-transform duration-300 ease-in-out">
                  Xem Khóa Học
                </div>
              </div>
            </div>
          </div>
        </div>

        <CardTitle className="text-black paragraph-semibold line-clamp-1 w-full text-left">
          {test[0].title}
        </CardTitle>
      </CardHeader>
      {isMyCourse ? null : (
        <CardContent className=" mt-4 p-0 flex-between">
          <div className="flex-start">
            <BsPeopleFill className="text-2xl mr-2" />
            <div className="text-xl font-normal">
              {test[0].members.toLocaleString()}
            </div>
          </div>
          <div className="flex-end mr-2">
            <div className="text-xl font-normal">
              {test[0].price.toLocaleString()} đ{" "}
            </div>
          </div>
          {/* <div className="flex-center body-medium gap-1.5 text-white">
          <Image src="/downloads.svg" width={20} height={20} alt="download" />
          {downloadNumber}
        </div>
        <Link
          href={downloadLink}
          target="_blank"
          className="flex-center text-gradient_purple-blue body-semibold gap-1.5"
        >
          Download Now
          <Image src="/arrow-blue.svg" width={13} height={10} alt="arrow" />
        </Link> */}
        </CardContent>
      )}
    </Card>
  );
}

export default CourseCard;
