import { DataResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import Content from "@/types/content.type";
import { Lecture, Section } from "@/types/section.type";
import { StatusCode } from "@/utils/resources";
import { Assignment } from "@/types/assignment.type";

export const contentApi = createApi({
  reducerPath: "contentApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Content", "Lecture"],
  endpoints: (builder) => ({
    getContentByCourseId: builder.query<DataResponse, string>({
      query: (id: string) => {
        return {
          url: `/api/courses/content/get-by-course-id`,
          params: {
            id: id,
          },
        };
      },
      providesTags: (result, error, id) =>
        result && result?.statusCode !== StatusCode.DATA_NOT_FOUND
          ? [
              ...((result.data as Content).sections as Section[]).map(
                (section) => ({ type: "Content" as const, id: section.id })
              ),
              ...((result.data as Content).sections as Section[]).flatMap(
                (section) =>
                  (section.lectures as Lecture[]).map((lecture) => ({
                    type: "Content" as const,
                    id: lecture.id,
                  }))
              ),
              { type: "Content" as const, id: "Content" },
              // {type: "Content" as const, id: "Lecture"}
            ]
          : [{ type: "Content" as const, id: "Content" }],
    }),
    addContent: builder.mutation<DataResponse, Content>({
      query: (data: Content) => {
        return {
          url: "/api/courses/content/add",
          method: "POST",
          body: data,
        };
      },
      invalidatesTags: () => [{ type: "Content" as const, id: "Content" }],
    }),
    updateContent: builder.mutation<DataResponse, Content>({
      query: (data: Content) => {
        return {
          url: `/api/courses/content/update/${data.id}`,
          method: "PUT",
          body: data,
        };
      },
      invalidatesTags: () => [{ type: "Content", id: "Content" }],
    }),
    addSection: builder.mutation<DataResponse, Section>({
      query: (data: Section) => {
        return {
          url: "/api/courses/section/add",
          method: "POST",
          body: data,
        };
      },
      invalidatesTags: (result, error, arg) => [
        { type: "Content" as const, id: "Content" },
      ],
    }),
    updateListSection: builder.mutation<
      DataResponse,
      { id: string; data: Section[] }
    >({
      query: ({ id, data }) => {
        return {
          url: `/api/courses/section/update-list/${id}`,
          method: "PUT",
          body: data,
        };
      },
      // invalidatesTags: () => [{type: "Content" as const, id: "Lecture"}]
    }),
    updateSectionById: builder.mutation<DataResponse, Section>({
      query: (data: Section) => {
        return {
          url: `/api/courses/section/update/${data.id}`,
          method: "PUT",
          body: data,
        };
      },
      invalidatesTags: (result, error, arg) => [
        { type: "Content" as const, id: arg.id },
      ],
    }),
    addLecture: builder.mutation<DataResponse, { id: string; data: Lecture }>({
      query: ({ id, data }) => {
        return {
          url: `/api/courses/lecture/add/${id}`,
          method: "POST",
          body: data,
        };
      },
      invalidatesTags: (result, error, arg) => [
        { type: "Content" as const, id: arg.id },
      ],
    }),
    updateListLecture: builder.mutation<
      DataResponse,
      { id: string; data: Lecture[] }
    >({
      query: ({ id, data }) => {
        return {
          url: `/api/courses/lecture/update-list/${id}`,
          method: "PUT",
          body: data,
        };
      },
      // invalidatesTags: () => [{type: "Content" as const, id: "Lecture"}]
    }),
    updateLecture: builder.mutation<DataResponse, Lecture>({
      query: (data: Lecture) => {
        return {
          url: `/api/courses/lecture/update/${data.id}`,
          method: "PUT",
          body: data,
        };
      },
      invalidatesTags: (result, error, arg) => [
        { type: "Content" as const, id: arg.id },
        { type: "Lecture" as const },
      ],
    }),
    getLectureById: builder.query<DataResponse, string>({
      query: (id: string) => {
        return {
          url: `/api/courses/lecture/get-by-id`,
          params: {
            id: id,
          },
        };
      },
      providesTags: (result, error, id) =>
        result && result?.statusCode !== StatusCode.DATA_NOT_FOUND
          ? [{ type: "Lecture" as const }]
          : [],
    }),

    addExQuiz: builder.mutation<DataResponse, { id: string; data: Lecture }>({
      query: ({ id, data }) => {
        return {
          url: `/api/courses/ex-quiz/add/${id}`,
          method: "POST",
          body: data,
        };
      },
      invalidatesTags: (result, error, arg) => [
        { type: "Content" as const, id: arg.id },
      ],
    }),
    addAssignment: builder.mutation<DataResponse, Assignment>({
      query: (data: Assignment) => {
        return {
          url: `/api/courses/assignment/add`,
          method: "POST",
          body: data,
        };
      },
      invalidatesTags: (result, error, arg) => [{ type: "Lecture" as const }],
    }),
    updateAssignment: builder.mutation<DataResponse, Assignment>({
      query: (data: Assignment) => {
        return {
          url: `/api/courses/assignment/update/${data.id}`,
          method: "PUT",
          body: data,
        };
      },
      invalidatesTags: (result, error, arg) => [{ type: "Lecture" as const }],
    }),
  }),
});

export const {
  useAddContentMutation,
  useUpdateSectionByIdMutation,
  useGetContentByCourseIdQuery,
  useUpdateContentMutation,
  useAddSectionMutation,
  useAddLectureMutation,
  useUpdateListLectureMutation,
  useUpdateLectureMutation,
  useAddExQuizMutation,
  useUpdateListSectionMutation,
  useGetLectureByIdQuery,
  useAddAssignmentMutation,
  useUpdateAssignmentMutation,
} = contentApi;
