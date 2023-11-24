import { DataResponse, ListResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import {baseQueryWithToken } from "../baseQuery";
import { Course } from "@/types/course.type";
import { SearchCourseRequest } from "@/types/request.type";


export const courseApi = createApi({
  reducerPath: "courseApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Course"],
  endpoints: (builder) => ({
    uploadCourseImage: builder.mutation<DataResponse, File>({
      query: (image: File) => {
        var bodyFormData = new FormData();
        bodyFormData.append("file", image);
        return {
          url: "/api/courses/course/images",
          method: "POST",
          body: bodyFormData,
          formData: true,
          responseHandler: "content-type"
        };
      },
    }),
    uploadCourseVideo: builder.mutation<DataResponse, File>({
      query: (video: File) => {
        var bodyFormData = new FormData();
        bodyFormData.append("file", video);
        return {
          url: "/api/courses/course/videos",
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
    createCourse: builder.mutation<DataResponse, Course>({
      query: (data: Course) => {
        return {
          url: "/api/courses/course/add",
          method: "POST",
          body: data
        }
      }
    }),
    updateCourseById: builder.mutation<DataResponse, Course>({
       query: (data: Course) => {
         return {
           url: `/api/courses/course/update/${data.id}`,
           method: "PUT",
           body: data
         }
      },
      invalidatesTags: () => [{type: "Course", id: "course"}]
     }),
    getCourseById: builder.query<DataResponse, string>({
      query: (id: string) => {
         return {
           url: `/api/courses/course/get-by-id`,
           params: {
             id: id
           }
         }
      },
      providesTags() {
        return [{type: "Course", id: "course"}]
      }
    }),
    getNewestCourse: builder.query<DataResponse, {topicId: number , size: number}>({
      query: ({ topicId, size }) => {
        return {
          url: `/api/courses/course/newest/${topicId}/${size}`,
        }
      }
    }),
    getCourseByUserId: builder.query<ListResponse, string>({
      query: (id: string) => {
         return {
           url: `/api/courses/course/get-all-by-user-id`,
           params: {
             userId: id
           }
         }
      },
    }),
    getAllCourse: builder.query<ListResponse, null>({
      query: () => {
         return {
           url: `/api/courses/course/get-all`,
         }
      },
    }),
    getCourseAccess: builder.query<DataResponse, {courseId: string, userId: string}>({
      query: ({courseId, userId}) => {
         return {
           url: `/api/courses/course-access/has-access-to-course`,
           params: {
             userId: userId,
             courseId: courseId
           }
         }
      },
    }),
    filterCourse: builder.mutation<ListResponse, SearchCourseRequest>({
      query: (data: SearchCourseRequest) => {
        return {
          url: `/api/courses/course/filter`,
          method: "POST",
          body: data
        }
      }
    })
  }),

});

export const {
  useLoadFileFromCloudQuery,
  useUploadCourseImageMutation,
  useUploadCourseVideoMutation,
  useCreateCourseMutation,
  useGetCourseByIdQuery,
  useUpdateCourseByIdMutation,
  useGetNewestCourseQuery,
  useGetCourseByUserIdQuery,
  useGetAllCourseQuery,
  useGetCourseAccessQuery,
  useFilterCourseMutation
} = courseApi;
