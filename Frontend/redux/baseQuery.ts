import { RootState } from './store';
import { fetchBaseQuery } from "@reduxjs/toolkit/query";
import Cookies from "js-cookie"

const baseUrl = "http://localhost:8080"

export const baseQueryWithToken = fetchBaseQuery({
    baseUrl: baseUrl,
    prepareHeaders: (headers) => {
        // const token = (getState() as RootState).authReducer.token;
        const userCookie = Cookies.get("user");
        const user = userCookie ? JSON.parse(userCookie) : null;
        console.log(user.token)
        if (user) {
			headers.set('authorization', `Bearer ${user.token}`);
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
     prepareHeaders: (headers) => {
        headers.set("Access-Control-Allow-Origin", "http://localhost:3000/"),
        headers.set("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, DELETE, OPTIONS"),
        headers.set("Access-Control-Allow-Headers", "Origin, X-Api-Key, X-Requested-With, Content-Type, Accept, Authorization"),
        headers.set("Access-Control-Allow-Credentials", "true")

		return headers;
    }
});