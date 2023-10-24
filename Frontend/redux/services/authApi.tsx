import { DataResponse } from "@/types/dataResponse.type";
import { LoginRequest } from "@/types/login.type";
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const userApi = createApi({
  reducerPath: "userApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8082",
  }),
  endpoints: (builder) => ({
    loginUser: builder.mutation<DataResponse, LoginRequest>({
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

export const { useLoginUserMutation } = userApi;
