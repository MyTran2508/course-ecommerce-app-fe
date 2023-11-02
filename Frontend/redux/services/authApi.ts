import { DataResponse } from "@/types/dataResponse.type";
import { LoginRequest } from "@/types/login.type";
import { User } from "@/types/user.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQuery } from "../baseQuery";

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: baseQuery,
  tagTypes:['User'],
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
          url: "api/users/user/register/send-otp",
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
          url: "api/users/user/register/verify-save",
          method: "POST",
          body: data,
          params: {
            email: data.email,
            otp: otp,
          },
        };
      },
    }),
    // getByUserName: builder.query<DataResponse, string>({
    //   query: (username) => `api/users/user/get-by-username/${username}`,
    //   providesTags() {
    //     return [{ type: 'User', id: "user" }]
    //   }
    // }),
  }),
});

export const {
  useLoginUserMutation,
  useRegisterUserMutation,
  useValidateOTPMutation,
  // useGetByUserNameQuery,
} = authApi;
