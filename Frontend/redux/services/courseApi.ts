import { DataResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import {baseQueryWithToken } from "../baseQuery";

export const courseApi = createApi({
  reducerPath: "courseApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Course"],
  endpoints: (builder) => ({
    loginUser: builder.mutation<DataResponse, any>({
      query: (body: LoginRequest) => {
        return {
          url: "api/users/user/login",
          method: "POST",
          body: body,
        };
      },
    }),
  }),
});

export const {
  useLoginUserMutation,
} = courseApi;
