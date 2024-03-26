import { LectureType } from "@/utils/resources";
import React, { useState } from "react";
import { IoAddOutline } from "react-icons/io5";
import { v4 as uuidv4 } from "uuid";
import CreateTitle from "./CreateTitle";
import Quiz from "./Quiz/Quiz";

interface LectureTypeProps {
  // key: number;
  parentId: string;
}

function LectureTypeBox(props: LectureTypeProps) {
  const { parentId } = props;
  const [isCreated, setIsCreated] = useState(false);
  const [type, setType] = useState<LectureType>();

  const renderLectureTypes = () => {
    const lectureTypes = Object.keys(LectureType);
    return lectureTypes.map((lecture, index) => {
      const lectureType = LectureType[lecture as keyof typeof LectureType];
      return (
        <div
          key={index}
          className="flex hover:cursor-pointer text-blue-500"
          onClick={() => handleCreateBox(lectureType)}
        >
          <IoAddOutline className="mt-1" />
          {lecture}
        </div>
      );
    });
  };

  const handleCreateBox = (lecture: LectureType) => {
    console.log("đã zô");
    setIsCreated(true);
    setType(lecture);
  };
  return (
    <div>
      {isCreated ? (
        <CreateTitle
          handleIsOpen={setIsCreated}
          type={type as LectureType}
          parentId={parentId}
        />
      ) : (
        // <Quiz />
        <div className="flex gap-2 border-dashed border-2 border-gray-400 p-2 w-max">
          {renderLectureTypes()}
        </div>
      )}
    </div>
  );
}

export default LectureTypeBox;
