import CourseCard from "@/components/CourseCard";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import React, { useEffect } from "react";
import { BiSolidMessageSquareAdd } from "react-icons/bi";

function MyCourses() {
  return (
    <div className="container mt-20">
      <div className="font-bold text-2xl xs:text-[10px]"> Khóa Học Của Tôi</div>
      <div className="grid grid-cols-4 xs:grid-cols-1">
        <CourseCard myCourse={true} />
        <CourseCard myCourse={true} />
        <div>
          <Card className="w-full max-w-fit border-0 !bg-transparent sm:max-w-[356px] m-8 border-spacing-3">
            <CardHeader className="flex-center flex-col gap-2.5 !p-0 hover:cursor-pointer">
              <div className="h-fit w-full relative">
                <div className="group">
                  <div className=" bg-white border-none">
                    <BiSolidMessageSquareAdd className="text-3xl w-40 h-20 xs:text-[10px]" />
                  </div>

                  <div className="inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300 ease-in-out">
                    <div className="bg-orange-200 rounded-2xl py-2 px-4 transform translate-y-4 group-hover:translate-y-0 transition-transform duration-300 ease-in-out">
                      Thêm Khóa Học
                    </div>
                  </div>
                  <div className="min-h-[400px]"></div>
                </div>
              </div>
            </CardHeader>
          </Card>
        </div>
      </div>
    </div>
  );
}

export default MyCourses;
