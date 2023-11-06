import { RootState } from './store';
import { fetchBaseQuery } from "@reduxjs/toolkit/query";
import { useAppDispatch, useAppSelector } from "./hooks";

const baseUrl = "http://localhost:8082"

export const baseQueryWithToken = fetchBaseQuery({
    baseUrl: baseUrl,
    prepareHeaders: (headers, { getState }) => {
        const token = (getState() as RootState).authReducer.token;
        console.log(token)
        
        if (token) {
			headers.set('authorization', `Bearer ${token}`);
        }
        headers.set("Access-Control-Allow-Origin", "http://localhost:3000/"),
        headers.set("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, DELETE, OPTIONS"),
        headers.set("Access-Control-Allow-Headers", "Origin, X-Api-Key, X-Requested-With, Content-Type, Accept, Authorization"),
        headers.set("Access-Control-Allow-Credentials", "true")

		return headers;
    }
})

export const baseQuery = fetchBaseQuery({
    baseUrl: baseUrl,
});