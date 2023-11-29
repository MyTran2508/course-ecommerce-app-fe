import { DataResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import {baseQueryWithToken } from "../baseQuery";
import { Course } from "@/types/course.type";
import { Section } from "@/types/section.type";

export const sectionApi = createApi({
  reducerPath: "sectionApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Section"],
  endpoints: (builder) => ({
    uploadSectionFiles: builder.mutation<DataResponse, File[]>({
      query: (files: File[]) => {
        var bodyFormData = new FormData();
        files.forEach((file) => bodyFormData.append("files",file))
        return {
          url: "/api/courses/section/upload",
          method: "POST",
          body: bodyFormData,
          formData: true
        };
      },
    }),
    loadFileFromCloud: builder.query<string, string>({
      query: (path: string) => {
        return {
          url: "/api/courses/course/download",
          params: {
            path: path
          },
          responseHandler: "text"
        }
      }
    }),
    getSectionById: builder.query<DataResponse, string>({
      query: (id: string) => {
         return {
           url: `/api/courses/course/get-by-id`,
           params: {
             id: id
           }
         }
      },
    }),
  }),

});

export const {
  useUploadSectionFilesMutation
} = sectionApi;
