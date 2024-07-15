"use client";
import React, { Fragment, useEffect, useState } from "react";
import { AiOutlineFullscreen, AiOutlineFullscreenExit } from "react-icons/ai";
import { Button } from "../ui/button";
import { Lecture } from "@/types/section.type";
import { CiTimer } from "react-icons/ci";
import { FaFolderOpen } from "react-icons/fa";
import InputEditor from "../Input/InputEditor";
import { EditorState, convertFromRaw } from "draft-js";
import { stateToHTML } from "draft-js-export-html";
import {
  useAddAssignmentHistoryMutation,
  useGetAssignmentHistoryByUserNameAndLectureIdQuery,
  useUpdateAssignmentHistoryMutation,
} from "@/redux/services/assignmentHistoryApi";
import { useFileHooks } from "@/redux/hooks/courseHooks/fileHooks";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { AssignmentHistory } from "@/types/assignment.type";
import { Input } from "../ui/input";
import { IoMdDownload } from "react-icons/io";
import showToast from "@/utils/showToast";
import { StatusCode, ToastMessage, ToastStatus } from "@/utils/resources";
import { useUpdateCurrentProgressMutation } from "@/redux/services/courseProcessApi";
import { useParams } from "next/navigation";
import { convertMillisToDateTime } from "@/utils/function";
import Link from "next/link";
import { set } from "lodash";
import { useLazyLoadFileDocumentFromCloudQuery } from "@/redux/services/sectionApi";

interface AssignmentPracticeProps {
  lecture: Lecture;
}

function AssignmentPractice(props: AssignmentPracticeProps) {
  const { lecture } = props;
  const param = useParams();
  const userId = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.id
  );
  const username = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const [isFullscreen, setIsFullscreen] = useState(false);
  const [isStart, setIsStart] = useState(false);
  const [progress, setProgress] = useState(1);
  const [answer, setAnswer] = useState("");
  const [urlFile, setUrlFile] = useState("");
  const [count, setCount] = useState(0);
  const [isOpenInputEditor, setIsOpenInputEditor] = useState(true);
  const [isNotCompleted, setIsNotCompleted] = useState(false);
  const [isReview, setIsReview] = useState(false);
  const [textView, setTextView] = useState<EditorState>(
    lecture?.assignment?.questions
      ? EditorState.createWithContent(
          convertFromRaw(JSON.parse(lecture?.assignment?.questions || ""))
        )
      : EditorState.createEmpty()
  );
  const [textViewSolution, setTextViewSolution] = useState<EditorState>(
    lecture?.assignment?.textSolution
      ? EditorState.createWithContent(
          convertFromRaw(JSON.parse(lecture?.assignment?.textSolution || ""))
        )
      : EditorState.createEmpty()
  );
  const [textViewEvaluation, setTextViewEvaluation] = useState<EditorState>(EditorState.createEmpty()
  );

  const [assignmentHistory, setAssignmentHistory] =
    useState<AssignmentHistory>();
  const [answerView, setAnswerView] = useState<EditorState>(
    EditorState.createEmpty()
  );
  const [addAssignmentHistory] = useAddAssignmentHistoryMutation();
  const [isChangeAssignment, setIsChangeAssignment] = useState(false);
  const { handleUploadFilesForSection } = useFileHooks();
  const [updateCourseProcess] = useUpdateCurrentProgressMutation();
  const {
    data: assignmentHistoryData,
    isSuccess: getAssignmentHistorySuccess,
  } = useGetAssignmentHistoryByUserNameAndLectureIdQuery({
    username: username,
    lectureId: lecture?.id as string,
  });
  const [fileURL, setFileUrl] = useState("");
  const [fileURLAnswer, setFileUrlAnswer] = useState("");
  const [loadFile, { data: fileBase64, isSuccess: loadFileSuccess }] =
    useLazyLoadFileDocumentFromCloudQuery();
    const [loadFileAnswer, { data: fileBase64Answer, isSuccess: loadFileAnswerSuccess }] =
    useLazyLoadFileDocumentFromCloudQuery();

  useEffect(() => {
    if (getAssignmentHistorySuccess) {
      if (assignmentHistoryData?.statusCode === StatusCode.DATA_NOT_FOUND) {
        setIsNotCompleted(true);
      } else {
        setIsNotCompleted(false);
      }
    }
  }, [assignmentHistoryData]);

  useEffect(() => {
    if (isStart && answer === "") {
      const timer = setInterval(() => {
        setCount((prevCount) => prevCount + 1);
      }, 1000);

      return () => {
        clearInterval(timer);
      };
    }
    if ((answer && !isReview) || (assignmentHistory && isChangeAssignment)) {
      const newAssignmentHistory: AssignmentHistory = {
        textAnswer: answer,
        timeSubmit: count > 60 ? Math.floor(count / 60) : count,
        urlFileAnswer: urlFile,
        assignment: {
          id: lecture?.assignment?.id,
          lecture: {
            id: lecture?.id,
          },
        },
        username: username,
      };
      handleUpdateProcess();
      addAssignmentHistory(newAssignmentHistory);
      handleRefresh();
    }
  }, [isStart, answer]);

  useEffect(() => {
    const handleLoadFileSuccess = (fileBase64: string, setFileUrl: React.Dispatch<React.SetStateAction<string>>) => {
      const byteCharacters = atob(fileBase64);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const file = new Blob([byteArray], { type: "application/pdf" });
      const fileURL = URL.createObjectURL(file);
      setFileUrl(fileURL);
    };

    if (loadFileSuccess) {
      handleLoadFileSuccess(fileBase64, setFileUrl);
    }
  }, [loadFileSuccess]);

  useEffect(() => {
    const handleLoadFileAnswerSuccess = (fileBase64Answer: string, setFileUrlAnswer: React.Dispatch<React.SetStateAction<string>>) => {
      const byteCharacters = atob(fileBase64Answer);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const file = new Blob([byteArray], { type: "application/pdf" });
      const fileURL = URL.createObjectURL(file);
      setFileUrlAnswer(fileURL);
    };

    if (loadFileAnswerSuccess) {
      handleLoadFileAnswerSuccess(fileBase64Answer, setFileUrlAnswer);
    }
  }, [loadFileAnswerSuccess]);

  const handleReviewAssignment = (assignment: AssignmentHistory) => {
    setAssignmentHistory(assignment);
    setAnswer(assignment?.textAnswer as string);
    setAnswerView(
      EditorState.createWithContent(
        convertFromRaw(JSON.parse(assignment?.textAnswer as string))
      )
    );
    if (lecture?.assignment?.urlFileResource) {
      loadFile(lecture?.assignment?.urlFileResource as string);
    }
    if (assignment?.urlFileAnswer) {
      loadFileAnswer(assignment?.urlFileAnswer as string);
    }
    setTextViewEvaluation(
      assignment?.evaluation
      ? EditorState.createWithContent(
          convertFromRaw(JSON.parse(assignment?.evaluation || ""))
        )
      : EditorState.createEmpty()
    )
    setIsStart(true);
    setIsReview(true);
    setIsChangeAssignment(false);
  };

  const handleUpdateProcess = () => {
    if (isNotCompleted) {
      updateCourseProcess({
        courseId: param.name as string,
        userId: userId,
        exQuizId: "",
      });
    }
  };

  const toggleFullscreen = () => {
    if (!isFullscreen) {
      enterFullscreen();
    } else {
      exitFullscreen();
    }
    setIsFullscreen(!isFullscreen);
  };

  const enterFullscreen = () => {
    if (document.documentElement.requestFullscreen) {
      document.documentElement.requestFullscreen();
    }
  };

  const exitFullscreen = () => {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    }
  };

  const handleStart = () => {
    setIsStart(true);
    loadFile(lecture?.assignment?.urlFileResource as string);
  };

  const handleUploadFile = async (file: File) => {
    const response = await handleUploadFilesForSection([file]);
    if (response) {
      const url = (response.data as string[])[0];
      setUrlFile(url);
    }
  };
  const handleRefresh = () => {
    setAssignmentHistory(undefined);
    setIsStart(false);
    setProgress(1);
    setAnswer("");
    setAnswerView(EditorState.createEmpty());
  };

  return (
    <div className="container ">
      {!isStart ? (
        <Fragment>
          <div className="h-[500px] flex justify-center flex-col">
            <strong className="text-2xl">Assignment: {lecture?.name}</strong>
            <div className="flex gap-2 mb-8">
              <CiTimer />
              <label>{lecture?.assignment?.estimatedDuration} minutes</label>
            </div>
            <p>
              {lecture?.description ||
                "This is an assignment that you need to complete"}
            </p>
            {getAssignmentHistorySuccess && !isNotCompleted && (
              <div>
                <strong className="text-xl mt-3">Assignment History:</strong>
                {(assignmentHistoryData?.data as AssignmentHistory[])?.map(
                  (assignmentHistory: AssignmentHistory) => {
                    return (
                      <div key={assignmentHistory.id}>
                        <div
                          className="bg-gray-300 rounded-md m-2 p-2 flex gap-10 hover:scale-110 duration-300 items-center"
                          onClick={() =>
                            handleReviewAssignment(assignmentHistory)
                          }
                        >
                          <div className="font-semibold">
                            Lần:{" "}
                            {(assignmentHistory.originalNumber as number) + 1}
                          </div>
                          <div>
                            Thời gian:{" "}
                            {convertMillisToDateTime(
                              assignmentHistory.created as number
                            )}
                          </div>
                          <div>
                            Điểm số: {assignmentHistory.score ?? "No value"}
                          </div>
                        </div>
                      </div>
                    );
                  }
                )}
              </div>
            )}
          </div>
        </Fragment>
      ) : (
        <Fragment>
          <div className="sticky top-0 bg-white z-10 ">
            <div className="flex-end pt-1 ">
              {assignmentHistory && (
                <Button onClick={() => handleRefresh()}>Back</Button>
              )}
            </div>
            <div className="flex items-center justify-around">
              <p>Instructions</p>
              <p>Submission</p>
              <p>Instructor Example</p>
            </div>
            <div className="flex items-center justify-around py-2 px-[215px]">
              <div
                className={`w-4 h-4  rounded-full ${
                  progress === 1 ? "bg-purple-600" : "bg-gray-300"
                }`}
                onClick={() => setProgress(1)}
              ></div>
              <div className="flex-1 h-1 bg-gray-300 mx-2"></div>
              <div
                className={`w-4 h-4  rounded-full ${
                  progress === 2 ? "bg-purple-600" : "bg-gray-300"
                }`}
                onClick={() => setProgress(2)}
              ></div>
              <div className="flex-1 h-1 bg-gray-300 mx-2"></div>
              <div
                className={`w-4 h-4  rounded-full ${
                  progress === 3 ? "bg-purple-600" : "bg-gray-300"
                }`}
                onClick={() => setProgress(3)}
              ></div>
            </div>
          </div>

          <div className="my-10 mx-5">
            {progress === 1 && (
              <Fragment>
                <strong className="text-2xl">Assignment Instructions</strong>
                <div className="flex gap-2 mb-8">
                  <CiTimer />
                  <label>
                    {lecture?.assignment?.estimatedDuration} minutes
                  </label>
                </div>
                <div className="shadow-xl w-full border">
                  <div className="m-10 space-y-10">
                    {lecture?.assignment?.urlVideoInstructions && (
                      <Fragment>
                        <video
                          src={
                            lecture?.assignment?.urlVideoInstructions
                          }
                          controls
                          className="w-full h-[500px] object-cover"
                        ></video>
                        <hr className="my-4 border-t-2" />
                      </Fragment>
                    )}
                    <p className="text-xl text-gray-600">
                      {lecture?.description}
                    </p>
                    <div>
                      <strong>Questions for this assignment:</strong>
                      <p
                        dangerouslySetInnerHTML={{
                          __html: stateToHTML(textView.getCurrentContent()),
                        }}
                      />
                    </div>
                    <div>
                      <strong>Download resource files:</strong>
                      {fileURL && (
                        <a className="flex gap-1" href={fileURL} download={"resourceFile.pdf"}>
                          <FaFolderOpen className="text-xl" />
                          <p>
                            {
                              lecture?.assignment?.urlFileResource?.split(
                                "_"
                              )[1]
                            }
                          </p>
                        </a>
                      )}
                    </div>
                  </div>
                </div>
              </Fragment>
            )}
            {progress === 2 && (
              <Fragment>
                <strong className="text-2xl">Assignment submissions</strong>
                <div className="flex gap-2 mb-8">
                  <label>Save or submit your work</label>
                </div>
                {assignmentHistory?.evaluation && (
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
                            __html: stateToHTML(textViewEvaluation.getCurrentContent()),
                          }}
                        />
                      </p>
                    </div>
                    {/* <label className="italic mr-2">You can submit again</label>
                    {isChangeAssignment ? (
                      <div>
                        <button
                          className="bg-gray-200 text-black px-2 py-1 rounded-md"
                          onClick={() => {
                            setIsChangeAssignment(false);
                            setIsReview(true);
                          }}
                        >
                          Close
                        </button>
                      </div>
                    ) : (
                      <Button
                        onClick={() => {
                          setIsChangeAssignment(true);
                          setIsReview(false);
                        }}
                      >
                        Change
                      </Button>
                    )} */}
                  </div>
                )}
                <div className="shadow-xl w-full border">
                  <div className="m-10 space-y-10">
                    <div>
                      <strong>Questions:</strong>
                      <p
                        dangerouslySetInnerHTML={{
                          __html: stateToHTML(textView.getCurrentContent()),
                        }}
                      />
                    </div>
                    <div className="space-y-1">
                      <strong>Upload File Resource:</strong>
                      {assignmentHistory?.urlFileAnswer &&
                      !isChangeAssignment ? (
                        <a className="flex gap-2 items-center" href={fileURLAnswer} download={"answerFileResource.pdf"}>
                          <IoMdDownload />
                          {(assignmentHistory?.urlFileAnswer as string)
                            .split("_")
                            .pop()}
                        </a>
                      ) : (
                        <Fragment>
                          {!assignmentHistory || isChangeAssignment ? (
                            <Input
                              type="file"
                              accept=".pdf"
                              onChange={(e) =>
                                handleUploadFile(e.target.files?.[0] as File)
                              }
                              className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black w-1/4"
                              // disabled={
                              //   !isPermissionGranted(
                              //     roleDetail as RoleDetail[],
                              //     PermissionName.CAN_CREATE,
                              //     ModuleName.COURSE
                              //   )
                              // }
                            />
                          ) : (
                            <div>No file uploaded</div>
                          )}
                        </Fragment>
                      )}
                    </div>
                    <div>
                      <strong>Your Answer:</strong>
                      {(isOpenInputEditor && !assignmentHistory) ||
                      isChangeAssignment ? (
                        <InputEditor
                          text={answer}
                          setTextInput={setAnswer}
                          setIsOpenInputEditor={setIsOpenInputEditor}
                          isAssignmentHistory={true}
                        />
                      ) : (
                        <p
                          dangerouslySetInnerHTML={{
                            __html: stateToHTML(answerView.getCurrentContent()),
                          }}
                        />
                      )}
                    </div>
                  </div>
                </div>
              </Fragment>
            )}
            {progress === 3 && (
              <Fragment>
                <strong className="text-2xl">How did you do?</strong>
                <div className="flex gap-2 mb-8">
                  <label>
                    Compare the instructor&apos;s example to your own
                  </label>
                </div>

                <div className="shadow-xl w-full border">
                  <div className="m-10 space-y-10">
                    <strong>Instructor example</strong>
                    <div>
                      <strong>Câu hỏi: </strong>
                      <p
                        dangerouslySetInnerHTML={{
                          __html: stateToHTML(textView.getCurrentContent()),
                        }}
                      />
                    </div>
                    <div>
                      <strong>Câu trả lời: </strong>
                      <p
                        dangerouslySetInnerHTML={{
                          __html: stateToHTML(
                            textViewSolution.getCurrentContent()
                          ),
                        }}
                      />
                    </div>
                    {lecture?.assignment?.urlVideoInstructions && (
                      <Fragment>
                        <video
                          src={
                            lecture?.assignment?.urlVideoInstructions
                          }
                          controls
                          className="w-full h-[500px] object-cover"
                        ></video>
                        <hr className="my-4 border-t-2" />
                      </Fragment>
                    )}
                    <div>
                      <strong>Download resource files:</strong>
                      {fileURL && (
                        <a className="flex gap-1" href={fileURL} download={"resourceFile.pdf"}>
                          <FaFolderOpen className="text-xl" />
                          <p>
                            {
                              lecture?.assignment?.urlFileResource?.split(
                                "_"
                              )[1]
                            }
                          </p>
                        </a>
                      )}
                    </div>
                  </div>
                </div>
                <div className="shadow-xl w-full border mt-4">
                  <div className="m-10 space-y-10">
                    <strong>Your submission</strong>
                    <div>
                      <strong>Câu hỏi: </strong>
                      <p
                        dangerouslySetInnerHTML={{
                          __html: stateToHTML(textView.getCurrentContent()),
                        }}
                      />
                    </div>
                    <div className="space-y-1">
                      <strong>Upload File Resource:</strong>
                      {assignmentHistory?.urlFileAnswer ? (
                        <a className="flex gap-2 items-center" href={fileURLAnswer} download={"answerFileResource.pdf"}>
                        <IoMdDownload />
                        {(assignmentHistory?.urlFileAnswer as string)
                          .split("_")
                          .pop()}
                      </a>
                      ) : (
                        <Fragment>
                          {!assignmentHistory ? (
                            <Input
                              type="file"
                              accept=".pdf"
                              onChange={(e) =>
                                handleUploadFile(e.target.files?.[0] as File)
                              }
                              className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black w-1/4"
                              // disabled={
                              //   !isPermissionGranted(
                              //     roleDetail as RoleDetail[],
                              //     PermissionName.CAN_CREATE,
                              //     ModuleName.COURSE
                              //   )
                              // }
                            />
                          ) : (
                            <div>No file uploaded</div>
                          )}
                        </Fragment>
                      )}
                    </div>
                    <div className="space-y-1">
                      <strong>Your Answer:</strong>
                      <p
                        dangerouslySetInnerHTML={{
                          __html: stateToHTML(answerView.getCurrentContent()),
                        }}
                      />
                    </div>
                  </div>
                </div>
              </Fragment>
            )}
          </div>
        </Fragment>
      )}

      {getAssignmentHistorySuccess &&
        !(assignmentHistoryData?.data as AssignmentHistory[])?.some(
          (item) => !item.score
        ) && (
          <div className="flex-end gap-4 items-center pr-5 border-y-2 border-gray-400 py-1 sticky bottom-0 bg-white">
            {!isStart && !assignmentHistory ? (
              <div className="flex gap-4">
                {/* <button
                  className="bg-gray-200 text-black px-2 py-2"
                  // onClick={() => setIsOpenInputEditor(false)}
                >
                  Skip assignment
                </button> */}
                <Button onClick={() => handleStart()} className="rounded-none">
                  Start assignment
                </Button>
              </div>
            ) : (
              <Fragment>
                <Button
                  onClick={() => {
                    if (progress > 1) {
                      setProgress(progress - 1);
                    }
                  }}
                  disabled={progress === 1}
                  className="rounded-none"
                >
                  Previous
                </Button>
                <Button
                  onClick={() => {
                    if (progress < 3) {
                      setProgress(progress + 1);
                    } else if (progress === 2 && !assignmentHistory) {
                      showToast(ToastStatus.INFO, ToastMessage.ENTER_ANSWER);
                    }
                  }}
                  disabled={progress === 3}
                  className="rounded-none"
                >
                  Next
                </Button>
              </Fragment>
            )}

            {!isFullscreen ? (
              <AiOutlineFullscreen
                onClick={toggleFullscreen}
                className="text-xl"
              />
            ) : (
              <AiOutlineFullscreenExit
                onClick={toggleFullscreen}
                className="text-xl"
              />
            )}
          </div>
        )}
    </div>
  );
}

export default AssignmentPractice;
