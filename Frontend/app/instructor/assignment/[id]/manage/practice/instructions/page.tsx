"use client";
import Loading from "@/app/(root)/user/personal/loading";
import AssignmentInstructionForm from "@/components/Form/AssignmentInstructionForm";
import { useGetLectureByIdQuery } from "@/redux/services/contentApi";
import { Lecture } from "@/types/section.type";
import { usePathname } from "next/navigation";
import React, { useEffect, useState } from "react";

function InstructionsPage() {
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
        <AssignmentInstructionForm
          lectureId={lecture?.id}
          assignment={lecture?.assignment}
        />
      )}
    </div>
  );
}

export default InstructionsPage;
