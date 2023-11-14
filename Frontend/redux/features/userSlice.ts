
import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import { User } from '@/types/user.type';

let initialState: User = {
    id: "",
    username: "",
    firstName: "",
    lastName: "",
    photos: "",
    email: "",
    roles:null,
    password:"",
    telephone:"",
    addresses:[]
};


export const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<User>) => {
            return {...state, ...action.payload}
        }
    }
})

export const {setUser } = userSlice.actions;

export default userSlice.reducer;