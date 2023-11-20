"use client";
import { Lecture, Section } from "@/types/section.type";
import {
  convertLongToTime,
  handleCountFieldsInSection,
} from "@/utils/function";
import { Disclosure } from "@headlessui/react";
import React from "react";
import { AiFillCheckCircle } from "react-icons/ai";
import { HiChevronUp } from "react-icons/hi";
import { IoDocumentTextSharp } from "react-icons/io5";
import { MdOutlineOndemandVideo } from "react-icons/md";

interface CourseContentLearningProps {
  section: Section;
  setLecture: React.Dispatch<React.SetStateAction<Lecture | undefined>>;
}

function CourseContentLearning(props: CourseContentLearningProps) {
  const { section, setLecture } = props;
  const { totalDurationCourse, totalLectureCount } = handleCountFieldsInSection(
    [section]
  );

  const handleClick = (lecture: Lecture) => {
    setLecture(lecture);
  };
  return (
    <div>
      <Disclosure>
        {({ open }) => (
          <>
            <Disclosure.Button className="flex w-full justify-between border-b-2 bg-gray-100 px-2 pt-1 text-left text-sm font-medium focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75 items-center sticky top-0">
              <div className="flex flex-col pl-2">
                <span className="text-sm">
                  {section.ordinalNumber}. {section.name}
                </span>
                <div className="flex gap-1 text-[10px]">
                  <div>0/{totalLectureCount} |</div>
                  <div>{totalDurationCourse}</div>
                </div>
              </div>
              <HiChevronUp
                className={`${
                  open ? "rotate-180 transform" : ""
                } h-5 w-5 text-3xl`}
              />
            </Disclosure.Button>
            <div className="text-sm text-gray-500 bg-gray-50">
              {open ? (
                <>
                  <div className="pt-1 pb-1 pl-5 ">
                    {section?.lectures
                      .filter((lecture) => lecture.ordinalNumber !== -1)
                      .map((lecture, index) => {
                        let durationLecture: string = "";
                        let isVideo: boolean = false;
                        if (lecture.videoDuration !== 0) {
                          durationLecture = convertLongToTime(
                            lecture.videoDuration as number
                          );
                          isVideo = true;
                        }
                        return (
                          <div
                            key={lecture.id}
                            className="flex gap-3 hover:cursor-pointer"
                            onClick={() => handleClick(lecture)}
                          >
                            <div className="flex items-center justify-center">
                              {isVideo ? (
                                <MdOutlineOndemandVideo />
                              ) : (
                                <IoDocumentTextSharp />
                              )}
                            </div>
                            <div className="w-full">
                              <Disclosure.Panel>
                                {lecture.ordinalNumber}. {lecture.name}
                              </Disclosure.Panel>

                              <div className="text-[11px] flex-between ">
                                {durationLecture ? (
                                  durationLecture
                                ) : (
                                  <div>doc</div>
                                )}
                                {lecture?.isSuccess ? (
                                  <AiFillCheckCircle className="text-xl text-green-700 mr-6 mb-1 pl-1" />
                                ) : null}
                              </div>
                              {section?.lectures.filter(
                                (lecture) => lecture.ordinalNumber !== -1
                              ).length -
                                1 !==
                              index ? (
                                <hr />
                              ) : null}
                            </div>
                          </div>
                        );
                      })}
                  </div>
                </>
              ) : null}
            </div>
          </>
        )}
      </Disclosure>
    </div>
  );
}

export default CourseContentLearning;
