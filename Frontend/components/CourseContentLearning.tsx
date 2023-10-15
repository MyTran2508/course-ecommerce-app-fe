"use client";
import { Disclosure } from "@headlessui/react";
import React from "react";
import { AiFillCheckCircle } from "react-icons/ai";
import { HiChevronUp } from "react-icons/hi";

function CourseContentLearning(props: any) {
  return (
    <div>
      <Disclosure>
        {({ open }) => (
          <>
            <Disclosure.Button className="flex w-full justify-between border-b-2 bg-gray-100 px-2 pt-1 text-left text-sm font-medium focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75 items-center sticky top-0">
              <div className="flex flex-col pl-2">
                <span className="text-sm">1. Giới Thiệu</span>
                <div className="flex gap-1 text-[10px]">
                  <div>0/2 |</div>
                  <div>30:00</div>
                </div>
              </div>
              <HiChevronUp
                className={`${
                  open ? "rotate-180 transform" : ""
                } h-5 w-5 text-3xl`}
              />
            </Disclosure.Button>
            <div className="text-sm text-gray-500 bg-gray-50">
              {open ? (
                <>
                  <div className="pt-1 pb-1 pl-10 ">
                    <Disclosure.Panel>1. Giới thiệu khóa học</Disclosure.Panel>
                    <div className="text-[11px] flex-between">
                      4:20
                      <AiFillCheckCircle className="text-xl text-green-700 mr-6 mb-1 pl-1" />
                    </div>
                    <hr />
                    <Disclosure.Panel>2. Dev C++</Disclosure.Panel>
                    <div className="text-[11px] flex-between">
                      6:20
                      <AiFillCheckCircle className="text-xl text-green-700 mr-6 mb-1 pl-1" />
                    </div>
                  </div>
                </>
              ) : null}
            </div>
          </>
        )}
      </Disclosure>
    </div>
  );
}

export default CourseContentLearning;
