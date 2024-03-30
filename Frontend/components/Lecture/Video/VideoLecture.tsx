import { Disclosure } from "@headlessui/react";
import React, { Fragment, useState } from "react";
import { HiChevronUp } from "react-icons/hi";
import { IoIosMenu } from "react-icons/io";
import { Button } from "../../ui/button";
import { IoAddOutline } from "react-icons/io5";
import VideoComponent from "./Video";
import { Lecture } from "@/types/section.type";
import { MdOutlineOndemandVideo } from "react-icons/md";
import ActionButtons from "../ActionButtons";
import CreateTitle from "../CreateTitle";
import { Action, LectureType } from "@/utils/resources";

interface VideoLectureProps {
  lecture: Lecture;
  index: number;
  attributes?: any;
  listeners?: any;
}

function VideoLecture(props: VideoLectureProps) {
  const { lecture, index, attributes, listeners } = props;
  const [isSelectContentType, setSelectContentType] = useState(false);
  const [isEdit, setEdit] = useState(false);
  const handleSelectType = (close: boolean = false) => {
    setSelectContentType(!isSelectContentType);
  };

  return (
    <div className="relative">
      {isEdit ? (
        <CreateTitle
          type={LectureType.VIDEO}
          action={Action.UPDATE}
          data={lecture}
          handleIsOpen={setEdit}
        />
      ) : (
        <Fragment>
          <div
            className={
              "flex w-full border border-black bg-white px-2 py-4 text-sm font-medium"
            }
          >
            <div className="flex gap-4 group w-full">
              <div className="flex w-max gap-2">
                <p>
                  <strong>{index}.</strong> Video{" "}
                </p>
                <MdOutlineOndemandVideo className={"text-xl"} />
                <label>{lecture.name}</label>
              </div>
              <ActionButtons
                handleEdit={setEdit}
                key={lecture.id}
                attributes={attributes}
                listeners={listeners}
              />
            </div>
            {/* {isEdit ? (
          <CreateTitle
            type={LectureType.VIDEO}
            action={Action.UPDATE}
            data={lecture}
            handleIsOpen={setEdit}
          />
        ) : (
          <div className="flex gap-4 group w-full">
            <div className="flex w-max gap-2">
              <p>{index}. Video </p>
              <MdOutlineOndemandVideo className={"text-xl"} />
              <label>{lecture.name}</label>
            </div>
            <ActionButtons handleEdit={setEdit} />
          </div>
        )} */}
            {isSelectContentType ? (
              <div className="right-1/4 border-b-0 border absolute py-2 px-2 border-black flex gap-2 w-[180px] justify-center items-center">
                <h1>
                  {lecture.videoDuration !== 0 ? "Đổi Video" : "Thêm Video"}
                </h1>
                <IoAddOutline
                  className="transform rotate-45 text-xl text-black hover:cursor-pointer"
                  onClick={() => handleSelectType(true)}
                />
              </div>
            ) : null}
          </div>

          <Disclosure>
            {({ open }) => (
              <>
                <div
                  className={
                    !open
                      ? "border-y-0"
                      : "flex flex-col border-t-0 border border-black p-2 bg-white mb-5 "
                  }
                >
                  {!isSelectContentType && !isEdit ? (
                    <Disclosure.Button>
                      <div className="right-8 top-4 absolute z-20">
                        <HiChevronUp
                          className={`${
                            open ? "" : "rotate-180 transform"
                          } h-5 w-5 text-orange-500`}
                        />
                      </div>
                    </Disclosure.Button>
                  ) : null}
                  <div className="text-sm bg-white">
                    {open ? (
                      <>
                        {lecture.videoDuration !== 0 && !isSelectContentType ? (
                          <Fragment>
                            <div className="flex gap-2 mx-10 ">
                              <video
                                controls
                                src={lecture.url as string}
                                className="w-96 h-[180px] rounded-md my-4 mx-4"
                              />
                              <div className="flex justify-between w-full mx-4">
                                <div>
                                  <div className="flex gap-2">
                                    <i className="text-xl">Tên file:</i>
                                    <h1 className="font-bold text-xl mb-5">
                                      {lecture.fileName}
                                    </h1>
                                  </div>
                                  <div className="flex gap-2">
                                    <i>Thời lượng:</i>
                                    <p> {lecture.videoDuration} giây</p>
                                  </div>
                                </div>
                                <Button
                                  className="bg-white hover:bg-slate-200 text-black rounded-none border border-black"
                                  onClick={() => handleSelectType()}
                                >
                                  Đổi Video
                                </Button>
                              </div>
                            </div>
                          </Fragment>
                        ) : (
                          <Fragment>
                            {isSelectContentType ? (
                              <VideoComponent
                                lecture={lecture}
                                onDisplay={setSelectContentType}
                              />
                            ) : (
                              <Fragment>
                                <div className="mb-2">
                                  <div className="flex items-center gap-2">
                                    <h1>Video:</h1>
                                    <Button
                                      className="bg-white hover:bg-slate-200 text-black rounded-none border border-black"
                                      onClick={() => handleSelectType()}
                                    >
                                      Thêm Video
                                    </Button>
                                  </div>
                                </div>
                              </Fragment>
                            )}
                          </Fragment>
                        )}
                      </>
                    ) : null}
                  </div>
                </div>
              </>
            )}
          </Disclosure>
        </Fragment>
      )}
    </div>
  );
}

export default VideoLecture;
