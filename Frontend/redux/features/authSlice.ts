import { User } from "@/types/user.type";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface AuthState {
  user: string | null;
  token: string | null;
}

const initialState: AuthState = {
  user: null,
  token: null,
};

export const auth = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setCredential: (
      state,
      action: PayloadAction<{ user: string; token: string }>
    ) => {
      localStorage.setItem(
        "user",
        JSON.stringify({
          user: action.payload.user,
          token: action.payload.token,
        })
      );
      console.log("token trong state" + state.token)
      state.user = action.payload.user;
      state.token = action.payload.token;
    },
    logout: (state) => {
      localStorage.clear();
      state = initialState;
    },
  },
});

export const { setCredential, logout } = auth.actions;

export default auth.reducer;
