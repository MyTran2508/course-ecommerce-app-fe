import { DataResponse, ListResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { Order, SearchOrderDto } from "@/types/order.type";

export const orderApi = createApi({
  reducerPath: "orderApi",
  baseQuery: baseQueryWithToken,
  endpoints: (builder) => ({
    addOrder: builder.mutation<DataResponse, Order>({
      query: (data: Order) => {
        return {
          url: "/api/users/order/add",
          method: "POST",
          body: data,
        };
      },
    }),
    getOrderById: builder.query<DataResponse, string>({
      query: (id: string) => {
        return {
          url: `/api/users/order/get-by-id`,
          params: {
            id: id,
          },
        };
      },
    }),
    monthlySales: builder.query<DataResponse, number>({
      query: (targetYear: number) => {
        return {
          url: `/api/users/order/monthly-sales`,
          params: {
            targetYear: targetYear,
          },
        };
      },
    }),
    salesSamePeriod: builder.query<DataResponse, number>({
      query: (targetYear: number) => {
        return {
          url: `/api/users/order/sales-same-period`,
          params: {
            targetYear: targetYear,
          },
        };
      },
    }),
    filterOrder: builder.mutation<ListResponse, SearchOrderDto>({
      query: (data: any) => {
        return {
          url: `/api/users/order/filter-order`,
          method: "POST",
          body: data,
        };
      },
    }),
  }),
});

export const {
  useAddOrderMutation,
  useGetOrderByIdQuery,
  useMonthlySalesQuery,
  useSalesSamePeriodQuery,
  useLazyMonthlySalesQuery,
  useLazySalesSamePeriodQuery,
  useFilterOrderMutation,
} = orderApi;
