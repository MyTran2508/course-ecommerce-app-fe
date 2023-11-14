import { DataResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import {baseQueryWithToken } from "../baseQuery";
import Content from "@/types/content.type";

export const contentApi = createApi({
  reducerPath: "contentApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Content"],
  endpoints: (builder) => ({
    getContentByCourseId: builder.query<DataResponse, string>({
      query: (id: string) => {
         return {
           url: `/api/courses/content/get-by-course-id`,
           params: {
             id: id
           }
         }
      },
      providesTags() {
        return [{type: "Content", id: "Content"}]
      }
    }),
    addContent: builder.mutation<DataResponse, Content>({
      query: (data: Content) => {
        return {
          url: "/api/courses/content/add",
          method: "POST",
          body: data
        }
      },
      invalidatesTags: ()=> [{type: "Content", id: "Content"}]
    }),
    updateContent: builder.mutation<DataResponse, Content>({
      query: (data: Content) => {
        return {
          url: `/api/courses/content/update/${data.id}`,
          method: "PUT",
          body: data
        }
      },
      invalidatesTags: ()=> [{type: "Content", id: "Content"}]
    }),
  }),

});

export const {
  useAddContentMutation,
  useGetContentByCourseIdQuery,
  useUpdateContentMutation
} = contentApi;
