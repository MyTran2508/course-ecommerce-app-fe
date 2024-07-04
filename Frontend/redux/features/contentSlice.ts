import { Assignment } from "@/types/assignment.type";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { set } from "lodash";

export interface ContentState {
  contentId: string;
  lectureIdForAssignment: string;
  assignment: Assignment | null;
}

const initialState: ContentState = {
  contentId: "",
  lectureIdForAssignment: "",
  assignment: null,
};

export const content = createSlice({
  name: "content",
  initialState,
  reducers: {
    setContentId: (state, action: PayloadAction<string>) => {
      state.contentId = action.payload;
    },
    setLectureIdForAssignment: (state, action: PayloadAction<string>) => {
      state.lectureIdForAssignment = action.payload;
    },
    setAssignment: (state, action: PayloadAction<Assignment | null>) => {
      state.assignment = action.payload;
    },
  },
});

export const { setContentId, setLectureIdForAssignment, setAssignment } =
  content.actions;

export default content.reducer;
