import React from "react";
import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { Constant, LectureType } from "@/utils/resources";
import Question from "../Quiz/Question";
import SectionComponent from "../../Section/Section";
import Quiz from "../Quiz/Quiz";
import VideoLecture from "../Video/VideoLecture";

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

  const style = {
    // transform: CSS.Transform.toString(transform),
    transform: CSS.Translate.toString(transform),
    transition,
  };

  return (
    <div>
      <div ref={setNodeRef} style={style}>
        {type === Constant.SECTION && (
          <SectionComponent section={data as object} index={index} attributes={attributes} listeners={listeners}/>
        )}
        {type === LectureType.QUIZ_TEST && (
          <Quiz index={index} lecture={data as object} attributes={attributes} listeners={listeners}/>
        )}
        {type === LectureType.VIDEO && (
          <VideoLecture lecture={data as object} index={index} attributes={attributes} listeners={listeners}/>
        )}
        {type === Constant.QUESTION && <Question question={data as object} attributes={attributes} listeners={listeners} />}
      </div>
    </div>
  );
}
