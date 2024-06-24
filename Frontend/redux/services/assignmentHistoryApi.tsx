import {
  DataResponse,
  ListResponse,
  PageResponse,
} from "@/types/response.type";
import { createApi } from "@reduxjs/toolkit/query/react";
import { baseQueryWithToken } from "../baseQuery";
import { Assignment, AssignmentHistory } from "@/types/assignment.type";
import { StatusCode } from "@/utils/resources";
import { SearchOrderDto } from "@/types/order.type";

export const assignmentHistoryApi = createApi({
  reducerPath: "assignmentHistoryApi",
  baseQuery: baseQueryWithToken,
  tagTypes: ["AssignmentHistory"],
  endpoints: (builder) => ({
    getAssignmentHistoryByUserNameAndLectureId: builder.query<
      DataResponse,
      { username: string; lectureId: string }
    >({
      query: ({ username, lectureId }) => {
        return {
          url: `/api/courses/assignment-history/get-by-username-and-lecture-id`,
          params: {
            username,
            lectureId,
          },
        };
      },
      providesTags: (result, error, { username, lectureId }) => [
        { type: "AssignmentHistory" as const },
      ],
    }),
    addAssignmentHistory: builder.mutation<DataResponse, AssignmentHistory>({
      query: (data: AssignmentHistory) => {
        return {
          url: `/api/courses/assignment-history/add`,
          method: "POST",
          body: data,
        };
      },
      invalidatesTags: (result, error, data) => [
        { type: "AssignmentHistory" as const },
      ],
    }),
    updateAssignmentHistory: builder.mutation<DataResponse, AssignmentHistory>({
      query: (data: AssignmentHistory) => {
        return {
          url: `/api/courses/assignment-history/update/${data.id}`,
          method: "PUT",
          body: data,
        };
      },
      invalidatesTags: (result, error, data) => [
        { type: "AssignmentHistory" as const },
      ],
    }),
    getKeywordLectureName: builder.query<
      DataResponse,
      {
        creator: string;
        lectureName: string;
      }
    >({
      query: ({ creator, lectureName }) => {
        return {
          url: `/api/courses/assignment-history/get-keyword-lecture-name`,
          params: {
            creator,
            lectureName,
          },
        };
      },
    }),
    getKeywordUserName: builder.query<
      DataResponse,
      {
        creator: string;
        username: string;
      }
    >({
      query: ({ creator, username }) => {
        return {
          url: `/api/courses/assignment-history/get-keyword-username`,
          params: {
            creator,
            username,
          },
        };
      },
    }),
    filterAssignmentManager: builder.mutation<ListResponse, SearchOrderDto>({
      query: (data) => {
        return {
          url: `/api/courses/assignment-history/search-by-keyword`,
          method: "POST",
          body: data,
        };
      },
    }),
  }),
});

export const {
  useGetAssignmentHistoryByUserNameAndLectureIdQuery,
  useAddAssignmentHistoryMutation,
  useUpdateAssignmentHistoryMutation,
  useFilterAssignmentManagerMutation,
  useLazyGetKeywordLectureNameQuery,
  useLazyGetKeywordUserNameQuery,
} = assignmentHistoryApi;
