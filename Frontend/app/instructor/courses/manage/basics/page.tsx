"use client";
import CourseInforForm from "@/components/form/CourseInforForm";
import React from "react";

function BasicsPage() {
  return (
    <div className="mt-10 shadow-xl w-full mx-5 ">
      <div className="my-5 mx-5 flex items-center font-bold text-xl">
        Thông Tin Cơ Bản
      </div>
      <hr />
      <div className="mt-10 ml-10">
        <CourseInforForm />
      </div>
    </div>
  );
}

export default BasicsPage;
