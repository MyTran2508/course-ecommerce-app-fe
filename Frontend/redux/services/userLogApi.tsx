import { use } from "react";
import {
  DataResponse,
  ListResponse,
  PageResponse,
} from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { SearchOrderDto } from "@/types/order.type";

export const userLogApi = createApi({
  reducerPath: "userLog",
  baseQuery: baseQueryWithToken,
  endpoints: (builder) => ({
    getUserLog: builder.mutation<ListResponse, SearchOrderDto>({
      query: (data: SearchOrderDto) => {
        return {
          url: `/api/users/user-log/filter`,
          body: data,
          method: "POST",
        };
      },
    }),
  }),
});

export const { useGetUserLogMutation } = userLogApi;
