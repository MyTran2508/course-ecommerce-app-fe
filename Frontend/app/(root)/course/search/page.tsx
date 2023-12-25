"use client";
import CourseCardSearch from "@/components/CourseCardSearch";
import Paginate from "@/components/Paginate";
import SideBarFilter from "@/components/SideBarFilter";
import { useFilterCourseMutation } from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import { SearchCourseRequest } from "@/types/request.type";
import { usePathname, useSearchParams } from "next/navigation";
import React, { Fragment, useEffect, useState } from "react";
import { MdFilterList, MdFilterListOff } from "react-icons/md";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { FilterSortBy } from "@/utils/resources";

function SearchPage() {
  const searchParams = useSearchParams();
  const query = searchParams.get("q");
  const [keyword, setKeyword] = useState(query);
  const [isOpenFilter, setOpenFilter] = useState(true);
  const [sortBy, setSortBy] = useState(FilterSortBy.NEWEST.toString());
  const [searchRequest, setSearchRequest] = useState<SearchCourseRequest>({
    keyword: keyword,
    pageIndex: 0,
    pageSize: 5,
    filterSortBy: sortBy,
  });
  const [page, setPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
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
        setTotalPage(fulfilled.totalPages);
      });
  };
  // useEffect(() => {
  //   handleSearch();
  // }, []);

  useEffect(() => {
    console.log(searchRequest);
    handleSearch();
  }, [searchRequest]);

  useEffect(() => {
    setSearchRequest((prevSearchRequest) => ({
      ...prevSearchRequest,
      pageIndex: page - 1,
    }));
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  }, [page]);

  useEffect(() => {
    if (query !== keyword) {
      setKeyword(query as string);
      setSearchRequest((prevSearchRequest) => ({
        ...prevSearchRequest,
        keyword: query,
      }));
    }
  }, [query]);

  useEffect(() => {
    if (sortBy !== FilterSortBy.NONE) {
      setSearchRequest((prevSearchRequest) => ({
        ...prevSearchRequest,
        filterSortBy: sortBy,
      }));
    }
  }, [sortBy]);

  return (
    <div>
      <div>
        <div className="mx-24 mt-10">
          <div>
            {isOpenFilter ? (
              <Fragment>
                <div className="flex gap-2">
                  <div
                    className="flex gap-2 border px-2 py-3 items-center hover: cursor-pointer border-black w-max"
                    onClick={() => handleClickOpenFilter()}
                  >
                    <MdFilterList /> Filter
                  </div>
                  <div
                    className="flex gap-2 border px-2 py-3 items-center hover: cursor-pointer border-black w-max"
                    onClick={() => handleClickOpenFilter()}
                  >
                    Sort by
                    <Select
                      onValueChange={(e) => setSortBy(e)}
                      defaultValue={FilterSortBy.NEWEST}
                    >
                      <SelectTrigger
                        className={` disabled:opacity-1 disabled:cursor-default w-[150px]`}
                      >
                        <SelectValue
                          placeholder="Sort by"
                          className="text-sm w-full"
                        />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectGroup>
                          <SelectLabel>Sort by</SelectLabel>

                          <SelectItem value={FilterSortBy.NEWEST}>
                            {FilterSortBy.NEWEST}
                          </SelectItem>
                          <SelectItem value={FilterSortBy.POPULAR}>
                            {FilterSortBy.POPULAR}
                          </SelectItem>
                        </SelectGroup>
                      </SelectContent>
                    </Select>
                  </div>
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
              <div className={`${isOpenFilter ? "" : "hidden"} sticky top-20 `}>
                <SideBarFilter setSearchRequest={setSearchRequest} />
              </div>
            </div>
            <div className="w-full flex flex-col">
              {coursesSearch.length !== 0 ? (
                <Fragment>
                  {coursesSearch.map((course, index) => {
                    return (
                      <div key={index}>
                        <CourseCardSearch course={course} />
                        {coursesSearch.length - 1 !== index ? <hr /> : ""}
                      </div>
                    );
                  })}
                </Fragment>
              ) : (
                <div className="text-2xl flex-center">
                  Không tìm thấy kết quả phù hợp
                </div>
              )}
              <div className="flex-center">
                {coursesSearch.length !== 0 ? (
                  <Paginate
                    totalPage={totalPage}
                    currentPage={page}
                    setCurrentPage={setPage}
                  />
                ) : null}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchPage;
