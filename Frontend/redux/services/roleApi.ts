import { DataResponse, ListResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { add } from "lodash";
import { Roles } from "@/types/roles.type";

export const roleApi = createApi({
  reducerPath: "roleApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Role"],
  endpoints: (builder) => ({
    getAllRole: builder.query<ListResponse, void>({
      query: () => ({
        url: "/api/users/role/get-all",
      }),
      providesTags: ["Role"],
    }),
    addRole: builder.mutation<DataResponse, Roles>({
      query: (roles: Roles) => ({
        url: "/api/users/role/add",
        method: "POST",
        body: roles,
      }),
      invalidatesTags: ["Role"],
    }),
    updateRole: builder.mutation<DataResponse, Roles>({
      query: (roles: Roles) => ({
        url: `/api/users/role/update/${roles.id}`,
        method: "PUT",
        body: roles,
      }),
      invalidatesTags: ["Role"],
    }),
    getRoleById: builder.query<DataResponse, number>({
      query: (id: number) => ({
        url: `/api/users/role/get-by-id`,
        params: { id },
      }),
    }),
    getRolesByUserName: builder.query<DataResponse, string>({
      query: (userName: string) => ({
        url: `/api/users/role/get-user-roles/${userName}`,
      }),
    }),
  }),
});

export const {
  useGetAllRoleQuery,
  useAddRoleMutation,
  useUpdateRoleMutation,
  useGetRoleByIdQuery,
  useGetRolesByUserNameQuery,
} = roleApi;
