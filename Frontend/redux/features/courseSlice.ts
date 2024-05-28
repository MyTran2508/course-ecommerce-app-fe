import { Course } from "@/types/course.type";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { set } from "lodash";

export interface CourseState {
  saveCourseStatus: boolean;
  manageCourse: Course;
  courseId: string;
  updateCourse: boolean;
  searchStatusCourse: {
    isApproved: string | null;
    isAwaitingApproval: string | null;
  };
  searchCourseKeywordDtoList:
    | {
        keyword?: string;
        keywordTypeSearchCourse?: number;
      }[]
    | null;
}

const initialState: CourseState = {
  saveCourseStatus: false,
  manageCourse: {
    name: "",
    language: {
      id: "0",
    },
    level: {
      id: "0",
    },
    topic: {
      id: "0",
    },
  },
  courseId: "",
  updateCourse: false,
  searchStatusCourse: {
    isApproved: null,
    isAwaitingApproval: null,
  },
  searchCourseKeywordDtoList: null,
};

export const course = createSlice({
  name: "course",
  initialState,
  reducers: {
    setStatusSaveCourse: (state, action: PayloadAction<boolean>) => {
      state.saveCourseStatus = action.payload;
    },
    setManageCourse: (state, action: PayloadAction<Course>) => {
      return { ...state, manageCourse: action.payload };
    },
    setParamCourseId: (state, action: PayloadAction<string>) => {
      state.courseId = action.payload;
    },
    updateCourse: (state) => {
      state.updateCourse = !state.updateCourse;
    },
    setIsApprovedSearch: (state, action: PayloadAction<string | null>) => {
      state.searchStatusCourse.isApproved = action.payload;
    },
    setIsAwaitingApprovalSearch: (
      state,
      action: PayloadAction<string | null>
    ) => {
      state.searchStatusCourse.isAwaitingApproval = action.payload;
    },
    setKeywordSearchCourse: (
      state,
      action: PayloadAction<
        { keyword: string; keywordTypeSearchCourse: number }[]
      >
    ) => {
      state.searchCourseKeywordDtoList = action.payload;
    },
  },
});

export const {
  setStatusSaveCourse,
  setManageCourse,
  setParamCourseId,
  updateCourse,
  setIsApprovedSearch,
  setIsAwaitingApprovalSearch,
  setKeywordSearchCourse,
} = course.actions;

export default course.reducer;
