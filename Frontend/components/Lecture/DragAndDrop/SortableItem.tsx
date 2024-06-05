import React from "react";
import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import {
  Constant,
  LectureType,
  ModuleName,
  PermissionName,
} from "@/utils/resources";
import AddQuestion from "../Quiz/Instructor/AddQuestion";
import SectionComponent from "../../Section/Section";
import AddQuiz from "../Quiz/Instructor/AddQuiz";
import FileLecture from "../File/FileLecture";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { isPermissionGranted } from "@/utils/function";
import { RoleDetail } from "@/types/roles.type";

interface SortableItemProp {
  id: string;
  type?: LectureType | Constant;
  data?: object;
  index: number;
}

export function SortableItem(props: SortableItemProp) {
  const { id, type, data, index } = props;
  const { attributes, listeners, setNodeRef, transform, transition } =
    useSortable({ id: id });
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const roleDetail = role?.roleDetails;
  const canCreate = isPermissionGranted(
    roleDetail as RoleDetail[],
    PermissionName.CAN_CREATE,
    ModuleName.CONTENT
  );
  const canUpdate = isPermissionGranted(
    roleDetail as RoleDetail[],
    PermissionName.CAN_UPDATE,
    ModuleName.CONTENT
  );

  const style = {
    // transform: CSS.Transform.toString(transform),
    transform: CSS.Translate.toString(transform),
    transition,
  };

  return (
    <div>
      <div ref={setNodeRef} style={style}>
        {type === Constant.SECTION && (
          <SectionComponent
            section={data as object}
            index={index}
            attributes={attributes}
            listeners={listeners}
            canCreate={canCreate}
            canUpdate={canUpdate}
          />
        )}
        {type === LectureType.QUIZ_TEST && (
          <AddQuiz
            index={index}
            lecture={data as object}
            attributes={attributes}
            listeners={listeners}
            canCreate={canCreate}
            canUpdate={canUpdate}
          />
        )}
        {(type === LectureType.VIDEO || type === LectureType.DOCUMENT) && (
          <FileLecture
            data={data as object}
            index={index}
            attributes={attributes}
            listeners={listeners}
            type={type}
            canCreate={canCreate}
            canUpdate={canUpdate}
          />
        )}
        {type === Constant.QUESTION && (
          <AddQuestion
            question={data as object}
            attributes={attributes}
            listeners={listeners}
            canCreate={canCreate}
            canUpdate={canUpdate}
          />
        )}
        {/* {type === LectureType.DOCUMENT && (
          <DocumentComponent
            doc={data as object}
            attributes={attributes}
            listeners={listeners}
          />
        )} */}
      </div>
    </div>
  );
}
