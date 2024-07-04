"use client";
import React, { useEffect, useState } from "react";
import AssignmentBasicInfoForm from "@/components/Form/AssignmentBasicInfoForm";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { useGetLectureByIdQuery } from "@/redux/services/contentApi";
import { setAssignment } from "@/redux/features/contentSlice";
import { Lecture } from "@/types/section.type";
import { usePathname } from "next/navigation";
import Loading from "@/app/(root)/user/personal/loading";

function BasicInfoPage() {
  const path = usePathname();
  const lectureId = path.split("/")[3];
  const { data: lectureData, isSuccess: getLectureSuccess } =
    useGetLectureByIdQuery(lectureId);
  const [lecture, setLecture] = useState<Lecture | null>(null);
  useEffect(() => {
    if (getLectureSuccess) {
      setLecture(lectureData.data as Lecture);
    }
  }, [lectureData]);
  if (!getLectureSuccess) {
    return <Loading />;
  }
  return (
    <div>
      {getLectureSuccess && lecture && (
        <AssignmentBasicInfoForm lecture={lecture as Lecture} />
      )}
    </div>
  );
}

export default BasicInfoPage;
