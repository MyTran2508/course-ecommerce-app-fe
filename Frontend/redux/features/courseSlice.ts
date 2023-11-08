import { createSlice, PayloadAction } from "@reduxjs/toolkit";


export interface CourseState {
    saveCourseStatus: boolean
}

const initialState: CourseState = {
    saveCourseStatus: false
}

export const course = createSlice({
  name: "course",
  initialState,
  reducers: {
      setStatusSaveCourse: (state, action: PayloadAction<boolean>) => {
          state.saveCourseStatus = action.payload
    }
  },
});

export const {setStatusSaveCourse } = course.actions;

export default course.reducer;
