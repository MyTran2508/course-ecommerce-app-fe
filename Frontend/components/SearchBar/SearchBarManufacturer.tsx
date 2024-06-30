import { setKeywordSearchCourse } from "@/redux/features/courseSlice";
import { useAppDispatch, useAppSelector } from "@/redux/hooks/reduxHooks";
import {
  useLazyGetKeywordLectureNameQuery,
  useLazyGetKeywordUserNameQuery,
} from "@/redux/services/assignmentHistoryApi";
import {
  useGetAllCourseQuery,
  useLazyGetAllCourseQuery,
  useLazyGetCourseSearchQuery,
} from "@/redux/services/courseApi";
import {
  useAddRecentSearchMutation,
  useDeleteRecentSearchMutation,
  useLazyGetRecentSearchQuery,
  useLazyGetUserSearchQuery,
} from "@/redux/services/userApi";
import { Course } from "@/types/course.type";
import { SearchOrderDto } from "@/types/order.type";
import {
  KeywordTypeSearchRequest,
  SearchConditionDto,
  SearchRequest,
} from "@/types/request.type";
import { RecentSearchHistoryDto, User } from "@/types/user.type";
import {
  ComparisonOperators,
  KeywordTypeSearchAssignmentHistory,
  KeywordTypeSearchBill,
  KeywordTypeSearchCourse,
  KeywordTypeSearchType,
  KeywordTypeSearchUser,
} from "@/utils/data";
import { iconMap } from "@/utils/map";
import {
  Action,
  DEFAULT_HISTORY_SEARCH,
  Fields,
  ModuleSearch,
  StatusCode,
} from "@/utils/resources";
import Image from "next/image";
import { useRouter } from "next/navigation";
import React, { Fragment, useEffect, useRef, useState } from "react";
import { BiSearchAlt } from "react-icons/bi";
import { FaAngleDown, FaSortAmountDown, FaSortAmountUp } from "react-icons/fa";
import { MdClear } from "react-icons/md";
import { TiTickOutline } from "react-icons/ti";

interface SearchBarManufacturerProps {
  action: Action;
  setSearchQuery?: (query: SearchRequest & SearchOrderDto) => void;
  startDate?: number | null;
  endDate?: number | null;
}

function SearchBarManufacturer(props: SearchBarManufacturerProps) {
  const { action, setSearchQuery, startDate, endDate } = props;
  const router = useRouter();
  const username = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const dispatch = useAppDispatch();
  const [getCourseSearch, { data: courses, isSuccess: getCourseSuccess }] =
    useLazyGetCourseSearchQuery();
  const [getUserSearch, { data: users, isSuccess: getUserSuccess }] =
    useLazyGetUserSearchQuery();
  const [
    getKeywordLectureName,
    { data: lectureNameList, isSuccess: getLectureNameListSuccess },
  ] = useLazyGetKeywordLectureNameQuery();
  const [
    getKeywordUserName,
    { data: userNameList, isSuccess: getUserNameListSuccess },
  ] = useLazyGetKeywordUserNameQuery();
  const [
    getRecentSearch,
    { data: recentSearch, isSuccess: getRecentSearchSuccess },
  ] = useLazyGetRecentSearchQuery();
  const [addRecentSearch] = useAddRecentSearchMutation();
  const [deleteRecentSearch] = useDeleteRecentSearchMutation();
  const [keywordTypeSearchRequest, setKeywordTypeSearchRequest] =
    useState<KeywordTypeSearchType | null>();
  const [comparisonOperators, setComparisonOperators] =
    useState<KeywordTypeSearchType | null>(
      keywordTypeSearchRequest?.name !== Fields.Price
        ? ComparisonOperators[0]
        : null
    );
  const [manufacturer, setManufacturer] = useState("");
  const [keyword, setKeyword] = useState<string>("");
  const [unSuggestedSearchFields, setUnSuggestedSearchFields] = useState<
    string[]
  >([]);

  const [processCreateSearch, setProcessCreateSearch] = useState<number>(0);
  const [isCreateSearch, setIsCreateSearch] = useState<boolean>(false);
  const [isOpenHistorySearch, setIsOpenHistorySearch] =
    useState<boolean>(false);
  const [listKeywordTypeSearchRequest, setListKeywordTypeSearchRequest] =
    useState<KeywordTypeSearchRequest[]>([]);
  //sort
  const [isOpenSort, setIsOpenSort] = useState<boolean>(false);
  const [sort, setSort] = useState<string>("created");
  const [isDecrease, setIsDecrease] = useState<boolean>(true);
  //search by history
  const [isSearchByHistory, setIsSearchByHistory] = useState<boolean>(false);
  //search price
  const [value, setValue] = useState<number>(0);
  const [minPrice, setMinPrice] = useState<number | null>(null);
  const [maxPrice, setMaxPrice] = useState<number | null>(null);
  const [price, setPrice] = useState<number | null>(null);
  //search course admin
  const searchStatusCourse = useAppSelector(
    (state) => state.courseReducer.searchStatusCourse
  );
  const [searchCourseAdmin, setSearchCourseAdmin] = useState<
    SearchConditionDto[]
  >([
    {
      keyword: "True",
      keywordType: 5,
    },
  ]);
  //search assignment history

  useEffect(() => {
    const updatedSearchCourseAdmin = [...searchCourseAdmin];

    const findIsApproved = updatedSearchCourseAdmin.find(
      (i) => i.keywordType === 3
    );
    if (findIsApproved) {
      if (searchStatusCourse.isApproved === null) {
        updatedSearchCourseAdmin.splice(
          updatedSearchCourseAdmin.findIndex((item) => item.keywordType === 3),
          1
        );
      } else {
        updatedSearchCourseAdmin.forEach((item) => {
          if (item.keywordType === 3) {
            item.keyword = searchStatusCourse.isApproved as string;
          }
        });
      }
    } else if (searchStatusCourse.isApproved !== null) {
      updatedSearchCourseAdmin.push({
        keyword: searchStatusCourse.isApproved as string,
        keywordType: 3,
      });
    }

    const findIsAwaitingApproval = updatedSearchCourseAdmin.find(
      (i) => i.keywordType === 4
    );
    if (findIsAwaitingApproval) {
      if (searchStatusCourse.isAwaitingApproval === null) {
        updatedSearchCourseAdmin.splice(
          updatedSearchCourseAdmin.findIndex((item) => item.keywordType === 4),
          1
        );
      } else {
        updatedSearchCourseAdmin.forEach((item) => {
          if (item.keywordType === 4) {
            item.keyword = searchStatusCourse.isApproved as string;
          }
        });
      }
    } else if (searchStatusCourse.isAwaitingApproval !== null) {
      updatedSearchCourseAdmin.push({
        keyword: searchStatusCourse.isApproved as string,
        keywordType: 4,
      });
    }

    setSearchCourseAdmin(updatedSearchCourseAdmin);
  }, [searchStatusCourse]);

  useEffect(() => {
    if (
      processCreateSearch === 3 &&
      keywordTypeSearchRequest?.name !== Fields.Price
    ) {
      const keywordTypeSearch: KeywordTypeSearchRequest = {
        keyword: keyword,
        keywordTypeSearch: keywordTypeSearchRequest as KeywordTypeSearchType,
        comparisonOperators: comparisonOperators as KeywordTypeSearchType,
        ordinalNumber: listKeywordTypeSearchRequest.length + 1,
      };

      if (keyword == manufacturer) {
        keywordTypeSearch.isSuggestion = false;
      } else {
        keywordTypeSearch.isSuggestion = true;
      }
      listKeywordTypeSearchRequest.push(keywordTypeSearch);
      handleRefresh();
    }
    if (
      processCreateSearch === 3 &&
      keywordTypeSearchRequest?.name === Fields.Price
    ) {
      const keywordTypeSearch: KeywordTypeSearchRequest = {
        keywordTypeSearch: keywordTypeSearchRequest as KeywordTypeSearchType,
        comparisonOperators: comparisonOperators as KeywordTypeSearchType,
        value: value,
        isSuggestion: false,
        ordinalNumber: listKeywordTypeSearchRequest.length + 1,
      };
      listKeywordTypeSearchRequest.push(keywordTypeSearch);
      handleRefresh();
    }
  }, [processCreateSearch]);

  useEffect(() => {
    handleSearch();
  }, [sort, isDecrease, searchCourseAdmin]);

  useEffect(() => {
    if (isSearchByHistory) {
      handleSearch(true);
      setIsSearchByHistory(false);
    }
  }, [isSearchByHistory]);

  const handleRefresh = () => {
    setKeyword("");
    setManufacturer("");
    setKeywordTypeSearchRequest(null);
    setComparisonOperators(ComparisonOperators[0]);
    setProcessCreateSearch(0);
    setIsCreateSearch(false);
    setPrice(null);
    setMaxPrice(null);
    setMinPrice(null);
    setValue(0);
    setIsDecrease(true);
    setSort(Action.SORT_BY_CREATED);
  };

  useEffect(() => {
    if (manufacturer.length > 0) {
      if (
        (keywordTypeSearchRequest?.name === Fields.USERNAME &&
          action !== Action.SEARCH_ASSIGNMENT_HISTORY) ||
        keywordTypeSearchRequest?.name === Fields.FullName ||
        keywordTypeSearchRequest?.name === Fields.Author
      ) {
        getUserSearch({
          "type-search": keywordTypeSearchRequest?.id as string,
          keyword: manufacturer,
        });
      }
      if (keywordTypeSearchRequest?.name === Fields.LectureName) {
        getKeywordLectureName({
          creator: "manager",
          lectureName: manufacturer as string,
        });
      }
      if (
        keywordTypeSearchRequest?.name === Fields.USERNAME &&
        action === Action.SEARCH_ASSIGNMENT_HISTORY
      ) {
        getKeywordUserName({
          creator: "manager",
          username: manufacturer as string,
        });
      }
    }
    if (
      keywordTypeSearchRequest?.name !== Fields.Author &&
      (action === Action.SEARCH_COURSE_CLIENT ||
        action === Action.SEARCH_COURSE_ADMIN)
    ) {
      getCourseSearch({
        typeSearch: keywordTypeSearchRequest?.id as string,
        keyword: manufacturer,
      });
    }
  }, [manufacturer, keywordTypeSearchRequest]);

  const handleDeleteKeywordTypeSearchCourse = (index: number) => {
    const keywordTypeSearchDelete = listKeywordTypeSearchRequest[index];
    if (!keywordTypeSearchDelete.isSuggestion) {
      setUnSuggestedSearchFields(
        unSuggestedSearchFields.filter(
          (i) => i !== keywordTypeSearchDelete.keywordTypeSearch?.name
        )
      );
    }

    listKeywordTypeSearchRequest.splice(index, 1);
    setListKeywordTypeSearchRequest([...listKeywordTypeSearchRequest]);
  };

  const handleCreateSearch = () => {
    setIsCreateSearch(!isCreateSearch);
    if (isOpenHistorySearch) {
      setIsOpenHistorySearch(false);
    }
  };

  const handleSetValueInput = (value?: number | string) => {
    if (value || value === "") {
      if (keywordTypeSearchRequest?.name === Fields.Price) {
        if (comparisonOperators?.name === "=") {
          setPrice(value as number);
        } else if (comparisonOperators?.name === ">=") {
          setMinPrice(value as number);
        } else {
          setMaxPrice(value as number);
        }
      } else {
        setManufacturer(value as string);
      }
    } else {
      if (keywordTypeSearchRequest?.name === Fields.Price) {
        if (comparisonOperators?.name === "=") {
          return price || 0;
        } else if (comparisonOperators?.name === ">=") {
          return minPrice || 0;
        } else {
          return maxPrice || 0;
        }
      }
      return manufacturer;
    }
  };

  const handleSearch = (searchByHistory?: boolean) => {
    const keywordSuggestion: SearchConditionDto[] =
      listKeywordTypeSearchRequest
        .filter((item) => item.isSuggestion)
        .map((item) => {
          return {
            ordinalNumber: item.ordinalNumber,
            keyword: item.keyword,
            keywordType: parseFloat(item.keywordTypeSearch?.id as string),
          };
        }) || [];

    const searchKeywordDtoList: SearchConditionDto[] =
      listKeywordTypeSearchRequest
        .filter(
          (item) =>
            !item.isSuggestion && item.keywordTypeSearch?.name !== Fields.Price
        )
        .map((item) => {
          return {
            ordinalNumber: item.ordinalNumber,
            keyword: item.keyword,
            keywordType: parseFloat(item.keywordTypeSearch?.id as string),
          };
        });

    if (action === Action.SEARCH_BILL && setSearchQuery) {
      const findMaxPrice = listKeywordTypeSearchRequest.find(
        (item) =>
          item.keywordTypeSearch?.name === Fields.Price &&
          item.comparisonOperators?.name === "<="
      );
      const findMinPrice = listKeywordTypeSearchRequest.find(
        (item) =>
          item.keywordTypeSearch?.name === Fields.Price &&
          item.comparisonOperators?.name === ">="
      );
      const findPrice = listKeywordTypeSearchRequest.find(
        (item) =>
          item.keywordTypeSearch?.name === Fields.Price &&
          item.comparisonOperators?.name === "="
      );

      setSearchQuery({
        searchChooseList: keywordSuggestion,
        searchKeywordDtoList: searchKeywordDtoList,
        minPrice: findMinPrice ? findMinPrice.value : null,
        maxPrice: findMaxPrice ? findMaxPrice.value : null,
        price: findPrice ? findPrice.value : null,
        pageIndex: 0,
        pageSize: 10,
        sortBy: sort,
        isDecrease: isDecrease,
        startDate: startDate !== null ? startDate : undefined,
        endDate: endDate !== null ? endDate : undefined,
      });
    }
    if (action === Action.SEARCH_COURSE_CLIENT) {
      const listKeywordTypeSearch = listKeywordTypeSearchRequest.map((item) => {
        return {
          keyword: item.keyword,
          keywordTypeSearchCourse: parseInt(
            item.keywordTypeSearch?.id as string
          ),
        };
      });
      dispatch(setKeywordSearchCourse(listKeywordTypeSearch as any));
      if (listKeywordTypeSearch.length > 0) {
        router.push("/course/search?q=");
      }
    }

    if (
      (action === Action.SEARCH_USER ||
        action === Action.SEARCH_ASSIGNMENT_HISTORY) &&
      setSearchQuery
    ) {
      setSearchQuery({
        keyword: [],
        searchChooseList: keywordSuggestion,
        pageIndex: 0,
        pageSize: 10,
        searchKeywordDtoList: searchKeywordDtoList,
        isDecrease: isDecrease,
        sortBy: sort,
      });
    }

    if (action === Action.SEARCH_COURSE_ADMIN && setSearchQuery) {
      setSearchQuery({
        keyword: [],
        searchChooseList: keywordSuggestion.concat(searchCourseAdmin),
        pageIndex: 0,
        pageSize: 10,
        searchKeywordDtoList: searchKeywordDtoList,
        isDecrease: isDecrease,
        sortBy: sort,
      });
    }

    //set history
    if (
      !searchByHistory &&
      (keywordSuggestion.length > 0 || searchKeywordDtoList.length > 0) &&
      sort === Action.SORT_BY_CREATED &&
      isDecrease &&
      action !== Action.SEARCH_COURSE_CLIENT
    ) {
      const addSearchHistory: RecentSearchHistoryDto = {
        searchChooseList: keywordSuggestion,
        searchKeywordDtoList: searchKeywordDtoList,
        moduleSearch: (() => {
          if (action === Action.SEARCH_USER) {
            return ModuleSearch.USER;
          }
          if (action === Action.SEARCH_BILL) {
            return ModuleSearch.ORDER;
          }
          if (action === Action.SEARCH_COURSE_ADMIN) {
            return ModuleSearch.COURSE;
          }
          if (action === Action.SEARCH_ASSIGNMENT_HISTORY) {
            return ModuleSearch.ASSIGNMENT_HISTORY;
          }
        })(),
        username: username,
      };
      addRecentSearch(addSearchHistory);
    }
  };

  const handleOpenHistory = () => {
    setIsOpenHistorySearch(!isOpenHistorySearch);
    getRecentSearch({
      username: username,
      moduleSearch: (() => {
        if (action === Action.SEARCH_USER) {
          return ModuleSearch.USER;
        }
        if (action === Action.SEARCH_BILL) {
          return ModuleSearch.ORDER;
        }
        if (action === Action.SEARCH_COURSE_ADMIN) {
          return ModuleSearch.COURSE;
        }
        if (action === Action.SEARCH_ASSIGNMENT_HISTORY) {
          return ModuleSearch.ASSIGNMENT_HISTORY;
        }
        return "";
      })(),
    });
  };
  const handleOpenSort = () => {
    setIsOpenSort(!isOpenSort);
  };

  return (
    <div className="flex gap-2 w-full">
      {action !== Action.SEARCH_COURSE_CLIENT && (
        <div className="relative inline-block">
          <button
            className="
              border border-gray-300 p-[8px] w-max flex gap-1 items-center p-4
              hover:border-gray-600 hover:bg-gray-200 duration-200 ease-linear text-gray-500
            "
            onClick={() => handleOpenHistory()}
          >
            {DEFAULT_HISTORY_SEARCH}
            <FaAngleDown className="text-gray-600 text-sm"/>
          </button>
          {isOpenHistorySearch && (
            <div className="absolute mt-[2px] bg-[#fff] w-[450px] z-10 rounded-sm shadow-md z-50 border border-gray-300">
              {getRecentSearchSuccess &&
              recentSearch.statusCode === StatusCode.REQUEST_SUCCESS ? (
                <Fragment>
                  {(recentSearch?.data as RecentSearchHistoryDto[])?.map(
                    (item) => {
                      let listKeywordTypeSearchRequest: KeywordTypeSearchRequest[] =
                        (item.searchChooseList as SearchConditionDto[]).map(
                          (i) => {
                            return {
                              keyword: i.keyword,
                              keywordTypeSearch: (() => {
                                if (action === Action.SEARCH_USER) {
                                  return KeywordTypeSearchUser.find(
                                    (item) => item.id == i.keywordType
                                  );
                                }
                                if (action === Action.SEARCH_BILL) {
                                  return KeywordTypeSearchBill.find(
                                    (item) => item.id == i.keywordType
                                  );
                                }
                                if (action === Action.SEARCH_COURSE_ADMIN) {
                                  return KeywordTypeSearchCourse.find(
                                    (item) => item.id == i.keywordType
                                  );
                                }
                                if (
                                  action === Action.SEARCH_ASSIGNMENT_HISTORY
                                ) {
                                  return KeywordTypeSearchAssignmentHistory.find(
                                    (item) => item.id == i.keywordType
                                  );
                                }
                              })(),
                              comparisonOperators: ComparisonOperators[0],
                              isSuggestion: true,
                              ordinalNumber: i.ordinalNumber,
                            };
                          }
                        );

                      listKeywordTypeSearchRequest = [
                        ...listKeywordTypeSearchRequest,
                        ...(
                          item.searchKeywordDtoList as SearchConditionDto[]
                        ).map((i) => {
                          return {
                            keyword: i.keyword,
                            keywordTypeSearch: (() => {
                              if (action === Action.SEARCH_USER) {
                                return KeywordTypeSearchUser.find(
                                  (item) => item.id == i.keywordType
                                );
                              }
                              if (action === Action.SEARCH_BILL) {
                                return KeywordTypeSearchBill.find(
                                  (item) => item.id == i.keywordType
                                );
                              }
                              if (action === Action.SEARCH_COURSE_ADMIN) {
                                return KeywordTypeSearchCourse.find(
                                  (item) => item.id == i.keywordType
                                );
                              }
                              if (action === Action.SEARCH_ASSIGNMENT_HISTORY) {
                                return KeywordTypeSearchAssignmentHistory.find(
                                  (item) => item.id == i.keywordType
                                );
                              }
                            })(),
                            comparisonOperators: ComparisonOperators[0],
                            isSuggestion: false,
                            ordinalNumber: i.ordinalNumber,
                          };
                        }),
                      ];

                      listKeywordTypeSearchRequest =
                        listKeywordTypeSearchRequest.sort(
                          (a, b) =>
                            (a.ordinalNumber as number) -
                            (b.ordinalNumber as number)
                        );

                      let resultText = listKeywordTypeSearchRequest
                        .map((item) => {
                          let text = item.keywordTypeSearch?.name + ": = ";
                          if (item.isSuggestion) {
                            text += "@" + item.keyword;
                          } else {
                            text += item.keyword;
                          }

                          return text;
                        })
                        .join(", ");

                      let trimText = resultText;

                      if (resultText.length > 50) {
                        trimText = resultText.slice(0, 50) + "...";
                      }

                      return (
                        <div
                          key={item.id}
                          className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2 lowercase text-sm"
                          title={resultText}
                          onClick={() => {
                            setListKeywordTypeSearchRequest(
                              listKeywordTypeSearchRequest
                            );
                            setIsOpenHistorySearch(false);
                            setIsSearchByHistory(true);
                          }}
                        >
                          {trimText}
                        </div>
                      );
                    }
                  )}
                  <div
                    className="cursor-pointer py-2 px-4 mt-1 text-sm text-[#737278] hover:bg-gray-200 flex text-center gap-2 
                    border-t-[1px] border-gray-300"
                    onClick={() =>
                      deleteRecentSearch({
                        username: username,
                        moduleSearch: (() => {
                          if (action === Action.SEARCH_USER) {
                            return ModuleSearch.USER;
                          }
                          if (action === Action.SEARCH_BILL) {
                            return ModuleSearch.ORDER;
                          }
                          if (action === Action.SEARCH_COURSE_ADMIN) {
                            return ModuleSearch.COURSE;
                          }
                          if (action === Action.SEARCH_ASSIGNMENT_HISTORY) {
                            return ModuleSearch.ASSIGNMENT_HISTORY;
                          }
                          return "";
                        })(),
                      })
                    }
                  >
                    Clear recent searches
                  </div>
                </Fragment>
              ) : (
                <div className="cursor-pointer py-2 px-4 flex text-center items-center justify-center 
                gap-2 border-t-[1px] text-sm rounded-sm text-sm text-[#737278]">
                  You don't have any recent searches
                </div>
              )}
            </div>
          )}
        </div>
      )}
      <div className="search-manufacturer relative w-full">
        <div>
          {processCreateSearch === 0 && isCreateSearch && (
            <Fragment>
              <div className="absolute mt-[45px] bg-gray-200 w-full rounded-sm z-10">
                {action === Action.SEARCH_USER && (
                  <Fragment>
                    {KeywordTypeSearchUser.filter(
                      (item) =>
                        !unSuggestedSearchFields.some((i) => i === item.name) ||
                        item.name === Fields.USERNAME
                    ).map((item) => (
                      <div
                        key={item.name}
                        className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2 "
                        onClick={() => {
                          // setIsSelectKeywordTypeSearchCourse(false);
                          setKeywordTypeSearchRequest(item);
                          setProcessCreateSearch(processCreateSearch + 2);
                        }}
                      >
                        {item.icon &&
                          React.createElement(iconMap[item.icon], {
                            className: "mr-2 text-lg",
                          })}

                        {item.name}
                      </div>
                    ))}
                  </Fragment>
                )}
                {action === Action.SEARCH_ASSIGNMENT_HISTORY && (
                  <Fragment>
                    {KeywordTypeSearchAssignmentHistory.map((item) => (
                      <div
                        key={item.name}
                        className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2 "
                        onClick={() => {
                          // setIsSelectKeywordTypeSearchCourse(false);
                          setKeywordTypeSearchRequest(item);
                          setProcessCreateSearch(processCreateSearch + 2);
                        }}
                      >
                        {item.icon &&
                          React.createElement(iconMap[item.icon], {
                            className: "mr-2 text-lg",
                          })}

                        {item.name}
                      </div>
                    ))}
                  </Fragment>
                )}
                {(action === Action.SEARCH_COURSE_CLIENT ||
                  action === Action.SEARCH_COURSE_ADMIN) && (
                  <Fragment>
                    {KeywordTypeSearchCourse.map((item) => (
                      <div
                        key={item.name}
                        className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2 "
                        onClick={() => {
                          // setIsSelectKeywordTypeSearchCourse(false);
                          setKeywordTypeSearchRequest(item);
                          setProcessCreateSearch(processCreateSearch + 2);
                        }}
                      >
                        {item.icon &&
                          React.createElement(iconMap[item.icon], {
                            className: "mr-2 text-lg",
                          })}

                        {item.name}
                      </div>
                    ))}
                  </Fragment>
                )}
                {action === Action.SEARCH_BILL && (
                  <Fragment>
                    {KeywordTypeSearchBill.filter((i) => {
                      if (i.name !== Fields.Price) {
                        return true;
                      }

                      const checkCompare = listKeywordTypeSearchRequest.find(
                        (item) => item.keywordTypeSearch?.name === Fields.Price
                      );

                      if (checkCompare) {
                        if (
                          checkCompare?.comparisonOperators?.name === ">=" &&
                          i.name === Fields.Price
                        )
                          return true;
                      } else {
                        return true;
                      }
                    }).map((item) => (
                      <div
                        key={item.name}
                        className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2 "
                        onClick={() => {
                          // setIsSelectKeywordTypeSearchCourse(false);
                          setKeywordTypeSearchRequest(item);
                          if (item.name === Fields.Price) {
                            setProcessCreateSearch(processCreateSearch + 1);
                          } else {
                            setProcessCreateSearch(processCreateSearch + 2);
                          }
                        }}
                      >
                        {item.icon &&
                          React.createElement(iconMap[item.icon], {
                            className: "mr-2 text-lg",
                          })}

                        {item.name}
                      </div>
                    ))}
                  </Fragment>
                )}
              </div>
            </Fragment>
          )}

          {processCreateSearch === 1 && isCreateSearch && (
            <Fragment>
              <div className="absolute mt-[45px] bg-gray-100 w-full rounded-sm z-10">
                {action === Action.SEARCH_BILL &&
                  keywordTypeSearchRequest?.name === Fields.Price && (
                    <Fragment>
                      {ComparisonOperators.filter((i) => {
                        const checkCompare = listKeywordTypeSearchRequest.find(
                          (item) =>
                            item.keywordTypeSearch?.name === Fields.Price
                        );

                        if (checkCompare) {
                          if (
                            checkCompare?.comparisonOperators?.name === ">=" &&
                            i.name === "<="
                          )
                            return true;
                        } else {
                          return true;
                        }
                      }).map((item) => (
                        <div
                          key={item.name}
                          className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2"
                          onClick={() => {
                            setComparisonOperators(item);
                            setProcessCreateSearch(processCreateSearch + 1);
                          }}
                        >
                          {item.name}
                        </div>
                      ))}
                    </Fragment>
                  )}
              </div>
            </Fragment>
          )}
          {processCreateSearch === 2 && isCreateSearch && (
            <Fragment>
              <div className="absolute mt-[45px] bg-gray-100 w-full rounded-sm z-10">
                {manufacturer.length > 0 && (
                  <Fragment>
                    {!unSuggestedSearchFields.some(
                      (i) =>
                        (i === Fields.USERNAME ||
                          i === Fields.NAME ||
                          i === Fields.LectureName) &&
                        i === keywordTypeSearchRequest?.name
                    ) && (
                      <div
                        className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2 "
                        onClick={() => {
                          setUnSuggestedSearchFields([
                            ...unSuggestedSearchFields,
                            keywordTypeSearchRequest?.name as string,
                          ]);

                          setKeyword(manufacturer);
                          setProcessCreateSearch(processCreateSearch + 1);
                        }}
                      >
                        <TiTickOutline className="text-lg text-green-700 mt-[2px]" />
                        {manufacturer}
                      </div>
                    )}
                    {((keywordTypeSearchRequest?.name === Fields.USERNAME &&
                      action !== Action.SEARCH_ASSIGNMENT_HISTORY) ||
                      keywordTypeSearchRequest?.name === Fields.FullName ||
                      keywordTypeSearchRequest?.name === Fields.Author) && (
                      <Fragment>
                        {getUserSuccess &&
                          (users?.data as User[])?.map((user: User) => (
                            <div
                              key={user.id}
                              className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2"
                              onClick={() => {
                                setKeyword(user.username as string);
                                setProcessCreateSearch(processCreateSearch + 1);
                              }}
                            >
                              <div className="flex gap-3 items-center">
                                <Image
                                  src={
                                    user.photos
                                      ? `data:image/jpeg;base64,${user.photos}`
                                      : "/banner.jpg"
                                  }
                                  width={400}
                                  height={400}
                                  alt="avt"
                                  className="w-8 h-8 rounded-full"
                                />
                                <div>
                                  <h1 className="font-bold  text-sm flex">
                                    {user.username}
                                  </h1>
                                  <h4 className="text-sm flex">
                                    {" "}
                                    {user.firstName + " " + user.lastName}
                                  </h4>
                                </div>
                              </div>
                            </div>
                          ))}
                      </Fragment>
                    )}

                    {keywordTypeSearchRequest?.name === Fields.USERNAME &&
                      action === Action.SEARCH_ASSIGNMENT_HISTORY && (
                        <Fragment>
                          {getUserNameListSuccess &&
                            (userNameList?.data as string[])?.map(
                              (username: string) => (
                                <div
                                  key={username}
                                  className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2"
                                  onClick={() => {
                                    setKeyword(username);
                                    setProcessCreateSearch(
                                      processCreateSearch + 1
                                    );
                                  }}
                                >
                                  <div className="flex gap-3 items-center">
                                    <h1 className="font-bold  text-sm flex">
                                      {username}
                                    </h1>
                                  </div>
                                </div>
                              )
                            )}
                        </Fragment>
                      )}
                    {keywordTypeSearchRequest?.name === Fields.LectureName && (
                      <Fragment>
                        {getLectureNameListSuccess &&
                          (lectureNameList?.data as string[])?.map(
                            (lectureName: string) => (
                              <div
                                key={lectureName}
                                className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2"
                                onClick={() => {
                                  setKeyword(lectureName as string);
                                  setProcessCreateSearch(
                                    processCreateSearch + 1
                                  );
                                }}
                              >
                                <div className="flex gap-3 items-center">
                                  <h1 className="font-bold  text-sm flex">
                                    {lectureName}
                                  </h1>
                                </div>
                              </div>
                            )
                          )}
                      </Fragment>
                    )}

                    {keywordTypeSearchRequest?.name !== Fields.Author &&
                      (action === Action.SEARCH_COURSE_CLIENT ||
                        action === Action.SEARCH_COURSE_ADMIN) && (
                        <Fragment>
                          {getCourseSuccess &&
                            (courses?.data as Course[])
                              ?.slice(0, 4)
                              .map((course: Course) => (
                                <div
                                  key={course.id}
                                  className="cursor-pointer py-2 px-4 hover:bg-gray-300"
                                  onClick={() => {
                                    if (
                                      action === Action.SEARCH_COURSE_CLIENT
                                    ) {
                                      handleRefresh();
                                      router.push(`/course/${course.id}`);
                                    } else {
                                      setKeyword(course.name as string);
                                      setProcessCreateSearch(
                                        processCreateSearch + 1
                                      );
                                    }
                                  }}
                                >
                                  {course.name}
                                  {keywordTypeSearchRequest?.name ==
                                    "Subtitle" && (
                                    <div>
                                      {(course.subTitle as string)?.length > 90
                                        ? (course.subTitle as string)?.slice(
                                            0,
                                            90
                                          ) + "..."
                                        : (course.subTitle as string)}
                                    </div>
                                  )}
                                </div>
                              ))}
                        </Fragment>
                      )}
                  </Fragment>
                )}
                {(price || maxPrice || minPrice) && (
                  <div
                    className="cursor-pointer py-2 px-4 hover:bg-gray-300 flex text-center gap-2 "
                    onClick={() => {
                      if (price) {
                        setValue(price);
                      }
                      if (minPrice) {
                        setValue(minPrice);
                      }
                      if (maxPrice) {
                        setValue(maxPrice);
                      }
                      setProcessCreateSearch(processCreateSearch + 1);
                    }}
                  >
                    <TiTickOutline className="text-lg text-green-700 mt-[2px]" />
                    {comparisonOperators?.name === "=" && price}
                    {comparisonOperators?.name === ">=" && minPrice}
                    {comparisonOperators?.name === "<=" && maxPrice}
                  </div>
                )}
              </div>
            </Fragment>
          )}
        </div>

        {(processCreateSearch === 0 ||
          (processCreateSearch === 1 &&
            keywordTypeSearchRequest?.name === Fields.Price)) && (
          <div
            className={"absolute w-[88%] z-10 h-[40px]"}
            onClick={handleCreateSearch}
          ></div>
        )}
        <div className="flex gap-1 min-w-[600px]">
          <div className="flex gap-1 border rounded-sm p-1 overflow-x-auto custom-scrollbar bg-white w-full">
            <div className="flex">
              {listKeywordTypeSearchRequest.map((item, index) => (
                <div key={index} className="flex gap-1 mx-2">
                  <div
                    className={`${"w-max z-10 h-max rounded-sm py-[2px] px-1  border bg-gray-600 text-white"}`}
                  >
                    {item.keywordTypeSearch?.name}
                  </div>

                  <div
                    className={`${"w-max z-10 h-max rounded-sm py-[2px] px-1  border bg-gray-600 text-white"}`}
                  >
                    {item.comparisonOperators?.name}
                  </div>
                  {item.keywordTypeSearch?.name === Fields.Price ? (
                    <div
                      className={`${"w-max z-10 h-max rounded-sm p-[2px] px-1 border bg-gray-600 text-white"}`}
                    >
                      <div className="flex gap-2 px-2 justify-center items-center">
                        {item.value}
                        <MdClear
                          onClick={() =>
                            handleDeleteKeywordTypeSearchCourse(index)
                          }
                          className="hover:cursor-pointer"
                        />
                      </div>
                    </div>
                  ) : (
                    <div
                      className={`${"w-max z-10 h-max rounded-sm p-[2px] px-1 border bg-gray-600 text-white"}`}
                    >
                      <div className="flex gap-2 px-2 justify-center items-center">
                        {item.keyword}
                        <MdClear
                          onClick={() =>
                            handleDeleteKeywordTypeSearchCourse(index)
                          }
                          className="hover:cursor-pointer"
                        />
                      </div>
                    </div>
                  )}
                </div>
              ))}
              {keywordTypeSearchRequest && (
                <Fragment>
                  <div
                    className={`${"w-max z-10 h-max rounded-sm p-[2px] border bg-gray-600 text-white"}`}
                  >
                    {keywordTypeSearchRequest && (
                      <div className="flex gap-2 px-2 justify-center items-center">
                        {keywordTypeSearchRequest?.name}
                        <MdClear
                          onClick={() => handleRefresh()}
                          className="hover:cursor-pointer"
                        />
                      </div>
                    )}
                  </div>
                  {comparisonOperators && (
                    <Fragment>
                      <div
                        className={`${"w-max z-10 h-max rounded-sm p-[2px] border bg-gray-600 text-white"}`}
                      >
                        <div className="flex gap-2 px-2 justify-center items-center">
                          {comparisonOperators?.name}
                          {keywordTypeSearchRequest?.name === Fields.Price && (
                            <MdClear
                              onClick={() => {
                                setComparisonOperators(ComparisonOperators[0]);
                                setProcessCreateSearch(1);
                              }}
                              className="hover:cursor-pointer"
                            />
                          )}
                        </div>
                      </div>
                    </Fragment>
                  )}
                </Fragment>
              )}
            </div>
            <div className="relative w-full">
              <input
                className={
                  "w-full h-8 placeholder:font-normal text-sm focus:outline-none min-w-[150px]"
                }
                placeholder="Search..."
                type={
                  keywordTypeSearchRequest?.name === Fields.Price
                    ? "number"
                    : "text"
                }
                min={keywordTypeSearchRequest?.name === Fields.Price ? 0 : ""}
                value={handleSetValueInput()}
                onChange={(e) => handleSetValueInput(e.target.value)}
                autoComplete="off"
              ></input>
            </div>
            {listKeywordTypeSearchRequest.length > 1 && (
              <div
                onClick={() => {
                  handleRefresh();
                  setListKeywordTypeSearchRequest([]);
                }}
                className="flex items-center"
              >
                <MdClear className="hover:cursor-pointer text-black text-2xl" />
              </div>
            )}
          </div>
          <div
            className="right-0 flex justify-center items-center hover:cursor-pointer py-1 px-2 rounded-sm bg-gray-400 h-[40px]"
            onClick={() => handleSearch()}
          >
            <BiSearchAlt className="text-2xl text-right" />
          </div>
        </div>
      </div>

      {action !== Action.SEARCH_COURSE_CLIENT && (
        <div className="relative inline-block">
          <button
            className="border border-gray-300 rounded-md p-[8px] w-max flex gap-2 items-center"
            onClick={() => handleOpenSort()}
          >
            Sắp xếp theo
            <FaAngleDown />
          </button>
          {isOpenSort && (
            <div className="absolute mt-[2px] bg-white border border-gray-300 shadow-md w-max rounded-sm z-10 overflow-hidden">
              <div
                className="cursor-pointer py-2 px-4 hover:bg-gray-200  flex text-center gap-2 text-xs"
                onClick={() => setSort(Action.SORT_BY_CREATED)}
              >
                {sort === Action.SORT_BY_CREATED ? (
                  <TiTickOutline className="text-md text-green-700 mt-[2px]" />
                ) : (
                  <TiTickOutline className="text-md text-gray-400 mt-[2px]" />
                )}
                Ngày Tạo
              </div>
              <div
                className={`${
                  sort === Action.SORT_BY_CREATED
                    ? "cursor-pointer py-2 px-4 hover:bg-gray-200 flex text-center gap-2 text-xs"
                    : "cursor-pointer py-2 px-4 hover:bg-gray-200 flex text-center gap-2 text-xs"
                }`}
                onClick={() => setSort(Action.SORT_BY_UPDATED)}
              >
                {sort === Action.SORT_BY_UPDATED ? (
                  <TiTickOutline className="text-md text-green-700 mt-[2px]" />
                ) : (
                  <TiTickOutline className="text-md text-gray-400 mt-[2px]" />
                )}
                Ngày Cập Nhật
              </div>
            </div>
          )}
        </div>
      )}
      {action !== Action.SEARCH_COURSE_CLIENT && (
        <div
          className="border border-gray-300 rounded-md py-2 w-max flex gap-2 items-center hover:cursor-pointer h-[40px] px-3"
          onClick={() => {
            setIsOpenSort(false);
            setIsDecrease(!isDecrease);
          }}
        >
          {isDecrease ? <FaSortAmountDown /> : <FaSortAmountUp />}
        </div>
      )}
    </div>
  );
}

export default SearchBarManufacturer;
