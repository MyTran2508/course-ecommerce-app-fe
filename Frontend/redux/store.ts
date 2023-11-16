import { configureStore } from "@reduxjs/toolkit"
import { authApi } from "./services/authApi";
import authReducer from "./features/authSlice";
import cartReducer from "./features/cartSlice";
import userReducer from "./features/userSlice";
import courseReducer from "./features/courseSlice";
import contentReducer from "./features/contentSlice";
import { setupListeners } from "@reduxjs/toolkit/query";
import { rtkQueryErrorLogger } from "@/config/middleware";
import { userApi } from "./services/userApi";
import { courseApi } from "./services/courseApi";
import { contentApi } from "./services/contentApi";
import { sectionApi } from "./services/sectionApi";


export const store = configureStore({
    reducer: {
        cartReducer,
        authReducer,
        userReducer,
        courseReducer,
        contentReducer,
        [authApi.reducerPath]: authApi.reducer,
        [userApi.reducerPath]: userApi.reducer,
        [courseApi.reducerPath]: courseApi.reducer,
        [contentApi.reducerPath]: contentApi.reducer,
        [sectionApi.reducerPath]: sectionApi.reducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({}).concat([authApi.middleware, userApi.middleware, courseApi.middleware, contentApi.middleware, sectionApi.middleware, rtkQueryErrorLogger]),
});
setupListeners(store.dispatch);
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch