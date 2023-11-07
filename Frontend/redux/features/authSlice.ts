import { User } from "@/types/user.type";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import Cookies from 'js-cookie';

export interface AuthState {
  username: string | null;
  token: string | null;
}

const initialState: AuthState = {
  username: null,
  token: null,
};

export const auth = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setCredential: (
      state,
      action: PayloadAction<{ username: string; token: string }>
    ) => {

      const auth =  JSON.stringify({
          username: action.payload.username,
          token: action.payload.token,
        })

      localStorage.setItem("user", auth);

      Cookies.set('user', auth, { expires: 1 });  

      state.username = action.payload.username;
      state.token = action.payload.token;
    },
    logout: (state) => {
      localStorage.clear();
      state = initialState;
      Cookies.remove("user");
    },
  },
});

export const { setCredential, logout } = auth.actions;

export default auth.reducer;
