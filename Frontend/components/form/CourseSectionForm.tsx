import React, {
  ChangeEvent,
  Fragment,
  MouseEvent,
  useEffect,
  useRef,
  useState,
} from "react";
import { Input } from "../ui/input";
import * as _ from "lodash";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import { course, setStatusSaveCourse } from "@/redux/features/courseSlice";
import { BiMessageSquareAdd } from "react-icons/bi";
import {
  Action,
  Constant,
  CourseLectureField,
  LectureType,
  ModuleName,
  PermissionName,
  StatusCode,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import showToast from "@/utils/showToast";
import { Lecture, Section } from "@/types/section.type";
import { isPermissionGranted } from "@/utils/function";
import { v4 as uuidv4 } from "uuid";
import CreateTitle from "../Lecture/CreateTitle";
import Sortable from "../Lecture/DragAndDrop/Sorttable";
import { useSectionHooks } from "@/redux/hooks/courseHooks/sectionHooks";
import { RoleDetail } from "@/types/roles.type";

interface CourseSectionProps {
  contentId: string;
  sections: Section[];
}

function CourseSectionForm(props: CourseSectionProps) {
  const { contentId, sections } = props;
  const [sectionData, setSectionData] = useState<Section[]>(sections);
  const [isUpdateSection, setUpdateSection] = useState(false);
  const [isCreateNewSection, setCreateNewSection] = useState(false);
  const { handleListUpdateSection } = useSectionHooks();
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const roleDetail = role?.roleDetails;

  useEffect(() => {
    if (isUpdateSection) {
      handleListUpdateSection(contentId, sectionData);
      setUpdateSection(false);
    }
  }, [isUpdateSection]);

  const handleAddNewSection = () => {
    if (
      !(
        isPermissionGranted(
          roleDetail as RoleDetail[],
          PermissionName.CAN_CREATE,
          ModuleName.CONTENT
        ) ||
        isPermissionGranted(
          roleDetail as RoleDetail[],
          PermissionName.CAN_UPDATE,
          ModuleName.CONTENT
        )
      )
    ) {
      showToast(ToastStatus.WARNING, ToastMessage.NO_PERMISSION);
      return;
    }
    setCreateNewSection(!isCreateNewSection);
  };

  useEffect(() => {
    setSectionData(sections);
  }, [sections]);

  return (
    <div>
      <div>
        <Sortable
          data={sectionData}
          key={uuidv4()}
          type={Constant.SECTION}
          onDataChange={setSectionData}
          onDataUpdate={setUpdateSection}
        />
        {isCreateNewSection ? (
          <CreateTitle
            handleIsOpen={setCreateNewSection}
            type={Constant.SECTION}
            key={uuidv4()}
            parentId={contentId}
            ordinalNumber={
              sections.filter(
                (section) => (section.ordinalNumber as number) > 0
              ).length + 1
            }
            action={Action.CREATE}
          />
        ) : (
          <div
            className="hover: cursor-pointer flex items-center text-orange-500 gap-1 my-5"
            onClick={() => handleAddNewSection()}
          >
            <BiMessageSquareAdd /> Chương
          </div>
        )}
      </div>
    </div>
  );
}

export default CourseSectionForm;
