import { ChangePasswordRequest } from './../../types/request.type';
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { DataResponse } from "@/types/response.type";
import { User } from "@/types/user.type";

export const userApi = createApi({
  reducerPath: "userApi",
  baseQuery: baseQueryWithToken,
  tagTypes:['User'],
  endpoints: (builder) => ({
    getByUserName: builder.query<DataResponse, string>({
      query: (username) => `api/users/user/get-by-username/${username}`,
      providesTags() {
        return [{ type: 'User', id: "user" }]
      }
    }),
    updateUser: builder.mutation<DataResponse,  Omit<User, "re_password" | "password">>({
      query: (data: Omit<User, "re_password" | "password">) => {
        return {
          url: `api/users/user/update/${data.id}`,
          method: "PUT",
          body: data,
        };
      },
      invalidatesTags: (error, data) => [{ type: 'User', id: "user" }]
    }),
    changePassword: builder.mutation<DataResponse, ChangePasswordRequest >({
      query: (data) => {
        return {
          url: `api/users/user/change-password/${data.userId}`,
          method: "PUT",
          body: data
        }
      }
    })
  }), 
});

export const { useUpdateUserMutation,
  useGetByUserNameQuery,
  useChangePasswordMutation
} = userApi;
