import { baseQuery } from './../baseQuery';
import { ChangePasswordRequest } from './../../types/request.type';
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { DataResponse } from "@/types/response.type";
import { User } from "@/types/user.type";
import { url } from 'inspector';

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
    
    updateUser: builder.mutation<DataResponse,  Omit<User, "re_password" | "password" | "roles" |"photos">>({
      query: (data: Omit<User, "re_password" | "password" | "roles">) => {
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
    }),

    getAvatar: builder.query<any, string>({
      query: (username) => ({
        url: `api/users/user/photos/${username}`,
        responseHandler: "text"
      }),
      providesTags: () => [{ type: "User", id: "avatar" }]
    }),

    uploadImage: builder.mutation<DataResponse, { username: string, image: File }>({
      query: ({username, image}) => {
        var bodyFormData = new FormData();
        bodyFormData.append('image', image);
        console.log(bodyFormData, image)
        return {
          url: `api/users/user/photos/${username}`,
          method: 'POST',
          body: bodyFormData,
          formData:true    
        };
      },
      invalidatesTags: () =>  [{ type: "User", id: "avatar" }]
    })
  }), 
});

export const { useUpdateUserMutation,
  useGetByUserNameQuery,
  useChangePasswordMutation,
  useGetAvatarQuery,
  useUploadImageMutation
} = userApi;
