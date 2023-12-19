import { Section } from "@/types/section.type";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";


export interface ContentState {
  section: Section[]
}

const initialState: ContentState = {
    section: []
}

export const section = createSlice({
  name: "section",
  initialState,
  reducers: {
    setSections: (state, action: PayloadAction<Section[]>) => {
      state.section = action.payload
    },
  },
});

export const {setSections} = section.actions;

export default section.reducer;
