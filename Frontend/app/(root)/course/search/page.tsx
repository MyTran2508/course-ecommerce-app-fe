"use client";
import CourseCardSearch from "@/components/CourseCardSearch";
import SideBarFilter from "@/components/SideBarFilter";
import { useFilterCourseMutation } from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import { SearchCourseRequest } from "@/types/request.type";
import { usePathname, useSearchParams } from "next/navigation";
import React, { Fragment, useEffect, useState } from "react";
import { MdFilterList, MdFilterListOff } from "react-icons/md";

function SearchPage() {
  const searchParams = useSearchParams();
  const query = searchParams.get("q");
  const [keyword, setKeyword] = useState(query);
  const [isOpenFilter, setOpenFilter] = useState(true);
  const [searchRequest, setSearchRequest] = useState<SearchCourseRequest>({
    keyword: keyword,
    pageIndex: 0,
    pageSize: 10,
  });
  const [coursesSearch, setCourseSearch] = useState<Course[]>([]);
  const [searchCourse] = useFilterCourseMutation();

  const handleClickOpenFilter = () => {
    setOpenFilter(!isOpenFilter);
  };
  const handleSearch = async () => {
    await searchCourse(searchRequest)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        setCourseSearch(fulfilled.data as Course[]);
      });
  };
  useEffect(() => {
    handleSearch();
  }, []);

  useEffect(() => {
    console.log(searchRequest);
    handleSearch();
  }, [searchRequest]);

  useEffect(() => {
    if (query !== keyword) {
      setKeyword(query as string);
      setSearchRequest((prevSearchRequest) => ({
        ...prevSearchRequest,
        keyword: query,
      }));
    }
  }, [query]);

  return (
    <div>
      <div>
        <div className="mx-24 mt-10">
          <div>
            {isOpenFilter ? (
              <Fragment>
                <div
                  className="flex gap-2 border px-2 py-3 items-center hover: cursor-pointer border-black w-max"
                  onClick={() => handleClickOpenFilter()}
                >
                  <MdFilterList /> Filter
                </div>
              </Fragment>
            ) : (
              <Fragment>
                <div
                  className="flex gap-2 border px-2 py-3 items-center hover: cursor-pointer border-black w-max"
                  onClick={() => handleClickOpenFilter()}
                >
                  <MdFilterListOff /> Filter
                </div>
              </Fragment>
            )}
          </div>
          <div className={`flex mt-2 ${isOpenFilter ? "gap-5" : ""}`}>
            <div
              className={`${
                isOpenFilter ? "w-3/12" : "transform -translate-x-full "
              } transition-transform duration-300`}
            >
              <div className={`${isOpenFilter ? "" : "hidden"}`}>
                <SideBarFilter setSearchRequest={setSearchRequest} />
              </div>
            </div>
            <div className="w-full">
              {coursesSearch.map((course, index) => {
                return (
                  <div key={index}>
                    <CourseCardSearch course={course} />
                    {coursesSearch.length - 1 !== index ? <hr /> : ""}
                  </div>
                );
              })}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchPage;
