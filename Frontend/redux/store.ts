import { configureStore } from "@reduxjs/toolkit"
import { authApi } from "./services/authApi";
import authReducer from "./features/authSlice";
import cartReducer from "./features/cartSlice";
import userReducer from "./features/userSlice";
import courseReducer from "./features/courseSlice";
import { setupListeners } from "@reduxjs/toolkit/query";
import { rtkQueryErrorLogger } from "@/config/middleware";
import { userApi } from "./services/userApi";

export const store = configureStore({
    reducer: {
        cartReducer,
        authReducer,
        userReducer,
        courseReducer,
        [authApi.reducerPath]: authApi.reducer,
        [userApi.reducerPath]: userApi.reducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({}).concat([authApi.middleware, userApi.middleware, rtkQueryErrorLogger]),
});
setupListeners(store.dispatch);
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch