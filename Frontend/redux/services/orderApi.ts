import { DataResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import {baseQueryWithToken } from "../baseQuery";
import { Order } from "@/types/order.type";


export const orderApi = createApi({
  reducerPath: "orderApi",
  baseQuery: baseQueryWithToken,
  endpoints: (builder) => ({
    addOrder: builder.mutation<DataResponse, Order>({
      query: (data: Order) => {
        return {
          url: "/api/users/order/add",
          method: "POST",
          body: data
        }
      }
    }),
    getOrderById: builder.query<DataResponse, string>({
      query: (id: string) => {
         return {
           url: `/api/users/order/get-by-id`,
           params: {
             id: id
           }
         }
      },
    }),
  }),
});

export const {
    useAddOrderMutation,
    useGetOrderByIdQuery
} = orderApi;
