import { DataResponse } from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { Question } from "@/types/section.type";

export const quizApi = createApi({
  reducerPath: "quizApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["Quiz"],
  endpoints: (builder) => ({
    getExQuizById: builder.query<DataResponse, string>({
      query: (id: string) => {
        return {
          url: `/api/courses/question/get-by-ex-quiz-id/${id}/0/10`,
        };
      },
      providesTags: (result, error, id) =>
        result
          ? [
              { type: "Quiz" as const, id: id },
              { type: "Quiz" as const, id: "Quiz" },
            ]
          : [{ type: "Quiz" as const, id: "Quiz" }],
    }),
    addQuestion: builder.mutation<DataResponse, { id: string; data: Question }>(
      {
        query: ({ id, data }) => {
          return {
            url: `/api/courses/question/add/${id}`,
            method: "POST",
            body: data,
          };
        },
        invalidatesTags: () => [{ type: "Quiz" as const, id: "Quiz" }],
      }
    ),
    updateListQuestion: builder.mutation<
      DataResponse,
      { id: string; data: Question[] }
    >({
      query: ({ id, data }) => {
        return {
          url: `/api/courses/question/update-list/${id}`,
          method: "PUT",
          body: data,
        };
      },
      invalidatesTags: () => [{ type: "Quiz" as const, id: "Quiz" }],
    }),
  }),
});

export const {
  useGetExQuizByIdQuery,
  useAddQuestionMutation,
  useUpdateListQuestionMutation,
} = quizApi;
