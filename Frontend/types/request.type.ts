import { KeywordTypeSearchType, VideoDuration } from "./../utils/data";
import { use } from "react";
import { Question } from "./section.type";

export interface LoginRequest {
  username: string;
  password: string;
}

export interface ChangePasswordRequest {
  userId: string;
  oldPassword: string;
  newPassword: string;
}

export interface ForgotPasswordRequest {
  otp: string;
  email: string;
  newPassword: string;
}

export interface SearchCourseRequest {
  levelIds?: string[];
  languageIds?: string[];
  topicIds?: string[];
  filterSortBy?: string;
  isFree?: boolean | null;
  ratingsLevel?: string | null;
  videoDuration?: string | null;
  searchCourseKeywordDtoList?:
    | {
        keyword?: string;
        keywordTypeSearchCourse?: string;
      }[]
    | null;
  pageIndex?: number;
  pageSize?: number;
}

export interface SearchRequest {
  sortBy?: string;
  isDecrease?: boolean;
  keyword?: string[] | null[];
  searchChooseList?: SearchConditionDto[];
  pageIndex: number;
  pageSize: number;
  searchKeywordDtoList?: SearchConditionDto[];
}

export interface SearchConditionDto {
  ordinalNumber?: number;
  keyword?: string;
  keywordType?: number;
}

export interface UserAnswerRequest {
  currentAnswer?: string;
  question?: Question;
}

export interface UserQuizRequest {
  userId?: string;
  startTime?: number;
  limitTime?: number;
  exQuizId?: string;
}
export interface KeywordTypeSearchRequest {
  ordinalNumber?: number;
  keyword?: string;
  keywordTypeSearch?: KeywordTypeSearchType;
  comparisonOperators?: KeywordTypeSearchType;
  isSuggestion?: boolean;
  value?: number;
}
