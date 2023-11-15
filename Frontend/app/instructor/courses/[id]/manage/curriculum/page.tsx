"use client";
import CourseSessionForm from "@/components/form/CourseSessionForm";
import React from "react";

function CurriculumPage() {
  return (
    <div className="mt-10 shadow-xl w-full mx-5 ">
      <div className="my-5 mx-5 flex items-center font-bold text-xl">
        Chương Trình Giảng Dạy
      </div>
      <hr />
      <div className="ml-10 my-10 mr-5">
        <div>
          Bắt đầu tập hợp khóa học của bạn bằng cách tạo các phần, bài giảng và
          thực hành (câu đố, bài tập viết mã và bài tập).
        </div>
        <div>
          Bắt đầu tập hợp khóa học của bạn bằng cách tạo các phần, bài giảng và
          hoạt động thực hành (câu đố, bài tập viết mã và bài tập). Sử dụng dàn
          ý khóa học để cấu trúc nội dung và gắn nhãn các phần cũng như bài
          giảng một cách rõ ràng.
        </div>
      </div>
      <div className="mt-10 ml-10">
        <CourseSessionForm />
      </div>
    </div>
  );
}

export default CurriculumPage;
