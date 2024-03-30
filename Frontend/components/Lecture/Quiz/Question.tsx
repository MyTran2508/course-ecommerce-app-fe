import React, { Fragment, useState } from "react";
import { Question } from "@/types/section.type";
import SelectTypeQuestion from "../Quiz/SelectTypeQuestion";
import ActionButtons from "../ActionButtons";
import { Action } from "@/utils/resources";

interface QuestionProps {
  question: Question;
  attributes?: any;
  listeners?: any;
}
function Question(props: QuestionProps) {
  const { question, attributes, listeners } = props;
  const [isEdit, setEdit] = useState(false);
  return (
    <Fragment>
      {isEdit ? (
        <SelectTypeQuestion
          action={Action.UPDATE}
          exQuizId="123"
          ordinalNumber={1}
          questionData={question}
          handleOpen={setEdit}
        />
      ) : (
        <div className="flex-between gap-4 mb-2 relative group">
          <div className="flex gap-2">
            <h1>
              <strong>{question.ordinalNumber}. </strong>
            </h1>
            <p>{question.title}</p>
          </div>
          <ActionButtons
            attributes={attributes}
            listeners={listeners}
            handleEdit={setEdit}
          />
        </div>
      )}
    </Fragment>
  );
}

export default Question;
