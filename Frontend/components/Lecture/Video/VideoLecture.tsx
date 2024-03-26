import { Disclosure } from "@headlessui/react";
import React, { Fragment, useState } from "react";
import { HiChevronUp } from "react-icons/hi";
import { IoIosMenu } from "react-icons/io";
import { CiCircleQuestion } from "react-icons/ci";
import { Button } from "../../ui/button";
import { IoAddOutline } from "react-icons/io5";
import VideoComponent from "./Video";

function VideoLecture() {
  const [isSelectContentType, setSelectContentType] = useState(false);
  const handleSelectType = (close: boolean = false) => {
    setSelectContentType(!isSelectContentType);
  };

  const handleAddQuestion = () => {};
  return (
    <div className="relative">
      <div
        className={
          "flex w-full border border-black bg-white px-2 py-4 text-sm font-medium"
        }
      >
        <div className="flex gap-2">
          <p>Video 1: </p>
          <CiCircleQuestion className={"text-xl"} />
          <label>Video 1</label>
        </div>
        {isSelectContentType ? (
          <div className="right-1/4 border-b-0 border absolute py-2 px-2 border-black flex gap-2 w-[180px] justify-center items-center">
            <h1>Thêm Video</h1>
            <IoAddOutline
              className="transform rotate-45 text-xl text-black hover:cursor-pointer"
              onClick={() => handleSelectType(true)}
            />
          </div>
        ) : (
          <Fragment>
            <IoIosMenu className={"text-xl ml-auto"} />
          </Fragment>
        )}
      </div>

      <Disclosure>
        {({ open }) => (
          <>
            <div
              className={
                !open
                  ? "border-y-0"
                  : "flex flex-col border-t-0 border border-black p-2 bg-white"
              }
            >
              {!isSelectContentType ? (
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
              <div className="text-sm bg-white ">
                {open ? (
                  <>
                    {isSelectContentType ? (
                      <VideoComponent />
                    ) : (
                      <Fragment>
                        <div className="mb-2">
                          <div className="flex items-center gap-2">
                            <h1>Câu hỏi:</h1>
                            <Button
                              className="bg-white hover:bg-slate-200 text-black rounded-none border border-black"
                              onClick={() => handleSelectType()}
                            >
                              Thêm Video
                            </Button>
                          </div>
                        </div>
                        {/* <Sortable
                          key={"1"}
                          data={ques}
                          type={Constant.QUESTION}
                        /> */}
                      </Fragment>
                    )}
                  </>
                ) : null}
              </div>
            </div>
          </>
        )}
      </Disclosure>
    </div>
  );
}

export default VideoLecture;
