
import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import { User } from '@/types/user.type';

const userState: Pick<User, "username" | "photos" | "email" | "id" | "roles"> = {
    username: "",
    photos: "",
    email: "",
    id: "",
    roles: null
};


export const userSlice = createSlice({
    name: "user",
    initialState: userState,
    reducers: {
        setUser: (state, action: PayloadAction<Pick<User, "username" | "photos" | "email" | "id"| "roles">>) => {
            return {...state, ...action.payload}
        },
        removeUser: () => {
            return {...userState}
        },
        
    }
})

export const {setUser, removeUser } = userSlice.actions;

export default userSlice.reducer;