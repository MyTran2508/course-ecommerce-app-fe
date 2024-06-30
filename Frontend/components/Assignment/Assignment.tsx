import React, { Fragment, use, useEffect, useState } from "react";
import ActionButtons from "../Lecture/ActionButtons";
import { MdQuiz } from "react-icons/md";
import { Lecture } from "@/types/section.type";
import { useRouter } from "next/navigation";
import { useAppDispatch } from "@/redux/hooks/reduxHooks";
import { setLectureIdForAssignment } from "@/redux/features/contentSlice";

interface AssignmentProps {
  lecture: Lecture;
  index: number;
  attributes?: any;
  listeners?: any;
  canCreate?: boolean;
  canUpdate?: boolean;
}

function Assignment(props: AssignmentProps) {
  const { lecture, index, attributes, listeners, canCreate, canUpdate } = props;
  const route = useRouter();
  const dispatch = useAppDispatch();
  const [isDelete, setDelete] = useState(false);
  const [isEdit, setEdit] = useState(false);

  useEffect(() => {
    if (isDelete) {
    }
    if (isEdit) {
      dispatch(setLectureIdForAssignment(lecture?.id as string));
      route.push(
        `/instructor/assignment/${lecture?.id}/manage/practice/basic-info`
      );
    }
  }, [isDelete, isEdit]);

  return (
    <div>
      <Fragment>
        <div
          className={
            "flex w-full border border-black bg-white px-2 py-4 text-sm font-medium mb-[24px]"
          }
        >
          <div className="flex gap-4 group w-full">
            <div className="flex w-max gap-2">
              <p>
                <strong>{index}.</strong> Assignment{" "}
              </p>
              <MdQuiz className={"text-xl"} />
              <label>{lecture.name}</label>
            </div>
            {(canUpdate || canCreate) && (
              <ActionButtons
                attributes={attributes}
                listeners={listeners}
                handleDelete={setDelete}
                handleEdit={setEdit}
              />
            )}
          </div>
        </div>
      </Fragment>
    </div>
  );
}

export default Assignment;
