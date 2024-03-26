
import { QuizType } from "@/utils/resources";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";


export interface QuizState {
    typeQuizCreate: QuizType | null; 
}

const initialState:  QuizState = {
  typeQuizCreate: null
}

export const quiz = createSlice({
  name: "quiz",
  initialState,
  reducers: {
    setTypeQuizCreate: (state, action: PayloadAction<QuizType | null>) => {
      state.typeQuizCreate = action.payload
    }
  },
});

export const {setTypeQuizCreate} = quiz.actions;

export default quiz.reducer;
