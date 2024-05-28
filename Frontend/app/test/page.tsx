"use client";
import React, { Fragment, useEffect, useRef, useState } from "react";
import { useGetAllCourseQuery } from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import { Combobox, Transition } from "@headlessui/react";
import { useRouter } from "next/navigation";
import { BiSearchAlt } from "react-icons/bi";

import { MdClear } from "react-icons/md";
import {
  ComboBoxType,
  ComparisonOperators,
  KeywordTypeSearchCourse,
  KeywordTypeSearchType,
} from "@/utils/data";
import { DEFAULT_HISTORY_SEARCH } from "@/utils/resources";
import { iconMap } from "@/utils/map";
import Navbar from "@/components/Navbar/Navbar";

var stompClient: any = null;

function Page() {
  // const connect = () => {
  //   let Sock = new SockJS("http://localhost:8080/ws/courses");
  //   stompClient = over(Sock);
  //   stompClient.connect({}, onConnected, onError);
  // };

  // const onConnected = () => {
  //   console.log("Connected");
  // };
  // const onError = (err: any) => {
  //   console.log(err);
  // };

  const inputRef = useRef<HTMLInputElement>(null);
  const [manufacturer, setManufacturer] = useState("");
  const router = useRouter();
  const [courses, setCourses] = useState<Course[]>([]);
  const [keywordTypeSearchCourse, setKeywordTypeSearchCourse] =
    useState<KeywordTypeSearchType | null>();
  const [comparisonOperators, setComparisonOperators] =
    useState<KeywordTypeSearchType | null>();

  const [processCreateSearch, setProcessCreateSearch] = useState<number>(0);
  const [keywordTypeSearchCourseLength, setKeywordTypeSearchCourseLength] =
    useState<number>(0);
  const [keyword, setKeyword] = useState<string>("");
  const [isCreateSearch, setIsCreateSearch] = useState<boolean>(false);
  const [listKeywordTypeSearchCourse, setListKeywordTypeSearchCourse] =
    useState<
      {
        keyword: string;
        keywordTypeSearchCourse: string;
        comparisonOperators: string;
      }[]
    >([]);

  const [isSelectKeywordTypeSearchCourse, setIsSelectKeywordTypeSearchCourse] =
    useState<boolean>(false);
  const [isSelectComparisonOperators, setIsSelectComparisonOperators] =
    useState<boolean>(false);

  const { data: courseData, isSuccess } = useGetAllCourseQuery(null);

  useEffect(() => {
    if (isSuccess) {
      setCourses(courseData?.data as Course[]);
    }
  }, [courseData]);

  useEffect(() => {
    if (processCreateSearch === 3) {
      listKeywordTypeSearchCourse.push({
        keyword: keyword,
        keywordTypeSearchCourse: keywordTypeSearchCourse?.name as string,
        comparisonOperators: comparisonOperators?.name as string,
      });
      console.log(listKeywordTypeSearchCourse);
      handleRefresh();
    }
  }, [processCreateSearch]);

  const handleRefresh = () => {
    setKeyword("");
    setKeywordTypeSearchCourse(null);
    setComparisonOperators(null);
    setProcessCreateSearch(0);
    setIsCreateSearch(false);
  };

  const handleDeleteKeywordTypeSearchCourse = (index: number) => {
    listKeywordTypeSearchCourse.splice(index, 1);
    setListKeywordTypeSearchCourse([...listKeywordTypeSearchCourse]);
    // handleRefresh();
  };

  const filterManufacturer =
    manufacturer === ""
      ? courses
      : courses.filter((index) =>
          index.name
            .toLowerCase()
            .replace("/s+/g", "")
            .includes(manufacturer.toLowerCase().replace("/s+/g", ""))
        );
  const handleOptionSelect = (item: Course) => {
    const courseURL = `/course/${item.id}`;
    router.push(courseURL);
  };

  const handleOpenKeywordTypeSearchCourse = () => {
    setIsSelectKeywordTypeSearchCourse(!isSelectKeywordTypeSearchCourse);
  };

  const handleOpenComparisonOperators = () => {
    setIsSelectComparisonOperators(!isSelectComparisonOperators);
  };

  const handleCreateSearch = () => {
    setIsCreateSearch(!isCreateSearch);
  };

  useEffect(() => {
    console.log(keywordTypeSearchCourseLength);
  }, [keywordTypeSearchCourseLength]);

  return (
    <div className="flex mt-20 justify-center items-center gap-1 flex-col">
      <Navbar />
      <div>
        <select
          className="border border-gray-300 rounded-md p-[10px] "
          onChange={(e) => console.log(e.target.value)}
          defaultValue={DEFAULT_HISTORY_SEARCH}
        >
          <option value={DEFAULT_HISTORY_SEARCH} className="hidden">
            {DEFAULT_HISTORY_SEARCH}
          </option>
          <option value="volvo">Volvo</option>
          <option value="saab">Saab12312312313123</option>
          <option value="fiat">Fiat</option>
          <option value="audi">Audi</option>
        </select>
      </div>

      <div className="search-manufacturer relative w-full">
        <div>
          {processCreateSearch === 0 && isCreateSearch && (
            <Fragment>
              <div className="absolute mt-[45px] mx-[10px] bg-gray-100 w-1/3 rounded-sm">
                {KeywordTypeSearchCourse.map((item) => (
                  <div
                    key={item.name}
                    className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2"
                    onClick={() => {
                      // setIsSelectKeywordTypeSearchCourse(false);
                      setKeywordTypeSearchCourse(item);
                      setProcessCreateSearch(processCreateSearch + 1);
                    }}
                  >
                    {item.icon &&
                      React.createElement(iconMap[item.icon], {
                        className: "mr-2 text-lg",
                      })}

                    {item.name}
                  </div>
                ))}
              </div>
            </Fragment>
          )}
          {processCreateSearch === 1 && isCreateSearch && (
            <Fragment>
              <div className="absolute mt-[45px] mx-[10px] bg-gray-100 w-1/3 rounded-sm">
                {ComparisonOperators.map((item) => (
                  <div
                    key={item.name}
                    className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2"
                    onClick={() => {
                      setComparisonOperators(item);
                      setProcessCreateSearch(processCreateSearch + 1);
                    }}
                  >
                    {item.icon &&
                      React.createElement(iconMap[item.icon], {
                        className: "mr-2 text-lg",
                      })}

                    {item.name}
                  </div>
                ))}
              </div>
            </Fragment>
          )}
          {processCreateSearch === 2 && isCreateSearch && (
            <Fragment>
              <div className="absolute mt-[45px] mx-[10px] bg-gray-100 w-1/3 rounded-sm">
                {KeywordTypeSearchCourse.map((item) => (
                  <div
                    key={item.name}
                    className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2"
                    onClick={() => {
                      setKeyword(item.name as string);
                      setProcessCreateSearch(processCreateSearch + 1);
                    }}
                  >
                    {item.icon &&
                      React.createElement(iconMap[item.icon], {
                        className: "mr-2 text-lg",
                      })}

                    {item.name}
                  </div>
                ))}
              </div>
            </Fragment>
          )}
        </div>
        {processCreateSearch < 2 && (
          <div
            className={"absolute w-full z-10 h-[40px]"}
            onClick={handleCreateSearch}
          ></div>
        )}

        <div className="flex gap-1 border rounded-sm p-1 overflow-x-auto custom-scrollbar">
          <div className="top-[8px] flex justify-center items-center">
            <BiSearchAlt className="text-2xl text-right ml-2" />
          </div>
          <div className="flex">
            {listKeywordTypeSearchCourse.map((item, index) => (
              <div key={index} className="flex gap-1 mx-2">
                <div
                  className={`${"w-max z-10 h-max rounded-sm py-[2px] px-1  border bg-gray-600 text-white"}`}
                >
                  {item.keywordTypeSearchCourse}
                </div>
                <div
                  className={`${"w-max z-10 h-max rounded-sm p-[2px] px-1 border bg-gray-600 text-white"}`}
                >
                  {item.comparisonOperators}
                </div>
                <div
                  className={`${"w-max z-10 h-max rounded-sm p-[2px] px-1 border bg-gray-600 text-white"}`}
                >
                  <div className="flex gap-2 px-2 justify-center items-center">
                    {item.keyword}
                    <MdClear
                      onClick={() => handleDeleteKeywordTypeSearchCourse(index)}
                      className="hover:cursor-pointer"
                    />
                  </div>
                </div>
              </div>
            ))}
            <div
              className={`${
                keywordTypeSearchCourse &&
                "w-max z-10 h-max rounded-sm p-[2px] border bg-gray-600 text-white"
              }`}
            >
              {keywordTypeSearchCourse && (
                <div className="flex gap-2 px-2 justify-center items-center">
                  {keywordTypeSearchCourse?.name}
                  <MdClear
                    onClick={() => handleRefresh()}
                    className="hover:cursor-pointer"
                  />
                </div>
              )}
            </div>
            <div
              className={`${
                comparisonOperators &&
                " w-max z-10 h-max rounded-sm p-[2px] border bg-gray-600 text-white"
              }`}
            >
              {comparisonOperators && (
                <div className="flex gap-2 px-2 justify-center items-center">
                  {comparisonOperators?.name}
                  <MdClear
                    onClick={() => {
                      setComparisonOperators(null);
                      setProcessCreateSearch(processCreateSearch - 1);
                    }}
                    className="hover:cursor-pointer"
                  />
                </div>
              )}
            </div>
          </div>
          <div className="relative w-full">
            <input
              className={
                "w-full h-8 placeholder:font-normal text-sm focus:outline-none min-w-[150px]"
              }
              placeholder="Tìm Kiếm Khóa Học"
              value={manufacturer}
              onChange={(e) => setManufacturer(e.target.value)}
              autoComplete="off"
            ></input>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Page;
