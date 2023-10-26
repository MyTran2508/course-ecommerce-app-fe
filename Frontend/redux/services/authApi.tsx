import { DataResponse } from "@/types/dataResponse.type";
import { LoginRequest } from "@/types/login.type";
import { User } from "@/types/user.type";
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
    registerUser: builder.mutation<DataResponse, Omit<User, "id">>({
      query: (body: Omit<User, "id">) => {
        return {
          url: "api/users/user/send-otp",
          method: "POST",
          body,
          params: {
            email: body.email,
          },
        };
      },
    }),
    validateOTP: builder.mutation<
      DataResponse,
      { data: Omit<User, "id">; otp: string }
    >({
      query: ({ data, otp }) => {
        return {
          url: "api/users/user/register",
          method: "POST",
          body: data,
          params: {
            email: data.email,
            otp: otp,
          },
        };
      },
    }),
  }),
});

export const {
  useLoginUserMutation,
  useRegisterUserMutation,
  useValidateOTPMutation,
} = userApi;
