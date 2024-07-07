"use client";
import InputEditor from "@/components/Input/InputEditor";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  useLazyGetAssignmentHistoryByIdQuery,
  useUpdateAssignmentHistoryMutation,
} from "@/redux/services/assignmentHistoryApi";
import { useLazyLoadFileFromCloudQuery } from "@/redux/services/courseApi";
import { AssignmentHistory } from "@/types/assignment.type";
import { EditorState, convertFromRaw } from "draft-js";
import { stateToHTML } from "draft-js-export-html";
import { useParams, useRouter } from "next/navigation";
import React, { Fragment, useEffect, useState } from "react";
import { IoMdDownload } from "react-icons/io";
import { IoCloseOutline } from "react-icons/io5";

function FeedbackPage() {
  const param = useParams();
  const route = useRouter();
  const [
    getAssignmentHistoryById,
    { data: assignmentHistoryData, isSuccess: getAssignmentHistorySuccess },
  ] = useLazyGetAssignmentHistoryByIdQuery();
  const [updateAssignmentHistory] = useUpdateAssignmentHistoryMutation();
  const [assignmentHistory, setAssignmentHistory] =
    useState<AssignmentHistory>();
  const [textViewAnswer, setTextViewAnswer] = useState(
    EditorState.createEmpty()
  );
  const [textViewQuestion, setTextViewQuestion] = useState(
    EditorState.createEmpty()
  );
  const [textViewEvaluation, setTextViewEvaluation] = useState(
    EditorState.createEmpty()
  );
  const [loadFile, { data: fileBase64, isSuccess: loadFileSuccess }] =
    useLazyLoadFileFromCloudQuery();

  const [isOpenInputEditor, setIsOpenInputEditor] = useState(false);
  const [feedBack, setFeedBack] = useState("");
  const [isUpdateFeedback, setIsUpdateFeedback] = useState(false);
  const [score, setScore] = useState(0);
  const [error, setError] = useState(false);

  useEffect(() => {
    getAssignmentHistoryById(param.id as string);
  }, [param.id]);

  useEffect(() => {
    if (getAssignmentHistorySuccess) {
      setAssignmentHistory(assignmentHistoryData?.data as AssignmentHistory);
      setTextViewQuestion(
        EditorState.createWithContent(
          convertFromRaw(
            JSON.parse(
              (assignmentHistoryData?.data as AssignmentHistory).assignment
                ?.questions as string
            )
          )
        )
      );
      setTextViewAnswer(
        EditorState.createWithContent(
          convertFromRaw(
            JSON.parse(
              (assignmentHistoryData?.data as AssignmentHistory)
                .textAnswer as string
            )
          )
        )
      );
      if ((assignmentHistoryData?.data as AssignmentHistory).evaluation) {
        setTextViewEvaluation(
          EditorState.createWithContent(
            convertFromRaw(
              JSON.parse(
                (assignmentHistoryData?.data as AssignmentHistory)
                  .evaluation as string
              )
            )
          )
        );
      }
      setFeedBack(
        (assignmentHistoryData?.data as AssignmentHistory).evaluation as string
      );
      if ((assignmentHistoryData?.data as AssignmentHistory).score) {
        setScore(
          (assignmentHistoryData?.data as AssignmentHistory).score as number
        );
      }
      if ((assignmentHistoryData?.data as AssignmentHistory).urlFileAnswer) {
        loadFile(
          (assignmentHistoryData?.data as AssignmentHistory)
            .urlFileAnswer as string
        );
      }
    }
  }, [getAssignmentHistorySuccess, assignmentHistoryData]);

  useEffect(() => {
    if (!score) {
      setError(true);
    } else {
      setError(false);
    }
    if (feedBack && score && isOpenInputEditor) {
      const assignmentHistoryUpdate = {
        ...assignmentHistory,
        evaluation: feedBack,
        score: score,
      };
      updateAssignmentHistory(assignmentHistoryUpdate);
      setIsOpenInputEditor(false);
    }

    if (feedBack && score && isUpdateFeedback) {
      const assignmentHistoryUpdate = {
        ...assignmentHistory,
        evaluation: feedBack,
        score: score,
      };
      updateAssignmentHistory(assignmentHistoryUpdate);
      setIsUpdateFeedback(false);
    }
  }, [isOpenInputEditor, feedBack, score]);

  return (
    <Fragment>
      <div className="flex justify-start items-center gap-2 font-normal fixed">
        <IoCloseOutline
          className=" mt-1 ml-1 cursor-pointer btn-back text-3xl"
          onClick={() => {
            route.push("/instructor/assignment-history");
          }}
        />
      </div>
      <div className="px-10">
        <strong className="text-2xl">Feedback Assignment</strong>
        <div className="flex gap-2">
          <label>Save or submit your work</label>
        </div>
        <div className="my-6">
          For Username: <strong>{assignmentHistory?.username}</strong>
        </div>
        {(isOpenInputEditor || isUpdateFeedback) && (
          <div className="space-y-4 ">
            <strong>Score: </strong>
            <Input
              min={1}
              max={10}
              type="number"
              className="w-1/3"
              id="inputScore"
              value={score}
              onChange={(e) => {
                const scoreValue = parseInt(e.target.value);
                if (scoreValue < 1 || scoreValue > 10) {
                  setError(true);
                  return;
                } else {
                  setScore(parseInt(e.target.value));
                  setError(false);
                }
              }}
            ></Input>
            {error && (
              <p style={{ color: "red" }}>{"Score must be between 1 and 10"}</p>
            )}
            <div>
              <strong>Evaluation: </strong>
              <InputEditor
                text={feedBack}
                setTextInput={setFeedBack}
                setIsOpenInputEditor={
                  assignmentHistory?.evaluation
                    ? setIsUpdateFeedback
                    : setIsOpenInputEditor
                }
              />
            </div>
          </div>
        )}
        {assignmentHistory?.evaluation ? (
          <Fragment>
            {!isOpenInputEditor && !isUpdateFeedback && (
              <div className="my-2 space-y-2 ">
                <strong className="text-2xl">Instructor Feedback</strong>

                <div className="border rounded-sm font-bold p-2 text-yellow-700">
                  <p>
                    <strong>Score: </strong>

                    {assignmentHistory?.score}
                  </p>
                  <p>
                    <strong>Evaluation:</strong>{" "}
                    <p
                      dangerouslySetInnerHTML={{
                        __html: stateToHTML(
                          textViewEvaluation.getCurrentContent()
                        ),
                      }}
                    />
                  </p>
                </div>

                {!isUpdateFeedback && (
                  <Button onClick={() => setIsUpdateFeedback(true)}>
                    Change
                  </Button>
                )}
              </div>
            )}
          </Fragment>
        ) : (
          <Fragment>
            {!isOpenInputEditor && (
              <Button onClick={() => setIsOpenInputEditor(true)}>
                Add Feedback
              </Button>
            )}
          </Fragment>
        )}
      </div>
      <div className="shadow-xl border mt-2 mx-6">
        <div className="m-10 space-y-10">
          <div>
            <strong>Questions:</strong>
            <p
              dangerouslySetInnerHTML={{
                __html: stateToHTML(textViewQuestion.getCurrentContent()),
              }}
            />
          </div>
          <div className="space-y-1">
            <strong>File Resource:</strong>
            {assignmentHistory?.urlFileAnswer ? (
              <div className="flex gap-2 items-center">
                <embed
                  // src="https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-file.pdf"
                  src={`data:application/pdf;base64,${fileBase64}`}
                  width="800px"
                  height="600px"
                />
              </div>
            ) : (
              <Fragment>
                <div>No file uploaded</div>
              </Fragment>
            )}
          </div>
          <div>
            <strong>User Answer:</strong>
            <p
              dangerouslySetInnerHTML={{
                __html: stateToHTML(textViewAnswer.getCurrentContent()),
              }}
            />
          </div>
        </div>
      </div>
    </Fragment>
  );
}

export default FeedbackPage;
