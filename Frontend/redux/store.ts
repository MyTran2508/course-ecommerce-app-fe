import { configureStore } from "@reduxjs/toolkit"
import { userApi } from "./services/authApi";
import authReducer from "./features/authSlice";
import cartReducer from "./features/cartSlice";
import { setupListeners } from "@reduxjs/toolkit/query";
import { rtkQueryErrorLogger } from "@/config/middleware";

export const store = configureStore({
    reducer: {
        cartReducer,
        authReducer,
        [userApi.reducerPath]: userApi.reducer   
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({}).concat([userApi.middleware, rtkQueryErrorLogger]),
});
setupListeners(store.dispatch);
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch