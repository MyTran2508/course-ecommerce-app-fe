import { DataResponse, ListResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { add } from "lodash";
import { Roles } from "@/types/roles.type";
import { SearchRequest } from "@/types/request.type";
import { NotificationDTO } from "@/types/notification.type";
import { SearchOrderDto } from "@/types/order.type";

export const notificationApi = createApi({
  reducerPath: "notificationApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Notification"],
  endpoints: (builder) => ({
    getNotifications: builder.mutation<ListResponse, SearchOrderDto>({
      query: (data: SearchOrderDto) => {
        return {
          url: `api/courses/notification/search-by-keyword`,
          method: "POST",
          body: data,
        };
      },
    }),
    updateNotification: builder.mutation<DataResponse, NotificationDTO>({
      query: (data: NotificationDTO) => {
        return {
          url: `api/courses/notification/update/${data.id}`,
          method: "PUT",
          body: data,
        };
      },
    }),
    updateIsViewed: builder.mutation<DataResponse, string>({
      query: (id: string) => {
        return {
          url: `api/courses/notification/set-is-viewed/${id}`,
          method: "POST",
        };
      },
    }),
  }),
});

export const {
  useGetNotificationsMutation,
  useUpdateNotificationMutation,
  useUpdateIsViewedMutation,
} = notificationApi;
