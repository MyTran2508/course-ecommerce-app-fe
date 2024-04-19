import { QuizType } from "@/utils/resources";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface QuizState {
  typeQuizCreate: QuizType | null;
  isEdit: boolean;
}

const initialState: QuizState = {
  typeQuizCreate: null,
  isEdit: false,
};

export const quiz = createSlice({
  name: "quiz",
  initialState,
  reducers: {
    setTypeQuizCreate: (state, action: PayloadAction<QuizType | null>) => {
      state.typeQuizCreate = action.payload;
    },
    setQuestionIsEdit: (state, action: PayloadAction<boolean>) => {
      state.isEdit = action.payload;
    },
  },
});

export const { setTypeQuizCreate, setQuestionIsEdit } = quiz.actions;

export default quiz.reducer;
