import { Lecture, Section } from "@/types/section.type";
import React, { Fragment, useEffect, useState } from "react";
import LectureTypeBox from "../Lecture/LectureTypeBox";
import { BiMessageSquareAdd } from "react-icons/bi";
import { IoAddOutline } from "react-icons/io5";
import Sortable from "../Lecture/DragAndDrop/Sorttable";
import { v4 as uuidv4 } from "uuid";
import { Constant } from "@/utils/resources";

interface SectionProps {
  section: Section;
}

function SectionComponent(props: SectionProps) {
  const { section } = props;
  const [lectures, setLectures] = useState<Lecture[]>(
    section.lectures as Lecture[]
  );
  const [isCreatedNewLecture, setNewCreatedLecture] = useState(false);
  const handleOpenOptionsAddLecture = (isOpen: boolean) => {
    setNewCreatedLecture(isOpen);
  };
  useEffect(() => {
    console.log(lectures);
  }, [lectures]);
  return (
    <div
      key={section.id}
      className="bg-gray-100 flex flex-col py-4 px-3 my-4 mr-20"
    >
      <div>
        <strong>Chương {section.ordinalNumber}: </strong> {section.name}
      </div>
      <div className="flex flex-col mx-10">
        <Sortable
          data={lectures}
          key={uuidv4()}
          type={Constant.LECTURE}
          onDataChange={setLectures as any}
        />
      </div>
      {isCreatedNewLecture ? (
        <Fragment>
          <div className="rounded-full border p-1 w-max border-blue-200">
            <IoAddOutline
              className="transform rotate-45 text-xl text-black hover:cursor-pointer"
              onClick={() => handleOpenOptionsAddLecture(false)}
            />
          </div>
          <div className="ml-10">
            <LectureTypeBox parentId={section.id as string} />
          </div>
        </Fragment>
      ) : (
        <Fragment>
          <div
            className="hover:cursor-pointer flex items-center text-orange-500 gap-1 mb-5 mx-10"
            onClick={() => handleOpenOptionsAddLecture(true)}
          >
            <BiMessageSquareAdd /> Bài Học
          </div>
        </Fragment>
      )}
    </div>
  );
}

export default SectionComponent;
