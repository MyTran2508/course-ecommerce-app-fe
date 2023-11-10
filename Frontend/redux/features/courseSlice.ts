import { Course } from "@/types/course.type";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";


export interface CourseState {
  saveCourseStatus: boolean,
  manageCourse: Course
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
  } ,
}

export const course = createSlice({
  name: "course",
  initialState,
  reducers: {
    setStatusSaveCourse: (state, action: PayloadAction<boolean>) => {
      state.saveCourseStatus = action.payload
    },
    setManageCourse: (state, action: PayloadAction<Course>) => {
       return {...state,  manageCourse: action.payload}
    }
  },
});

export const {setStatusSaveCourse , setManageCourse} = course.actions;

export default course.reducer;
