import { Page } from "react-pdf";
import {
  MonthlySale,
  SalesByTopic,
  SalesSamePeriod,
  SalesSamePeriodByTopics,
  Statistics,
} from "@/app/admin/overview/page";
import Content from "./content.type";
import { Course } from "./course.type";
import { CourseProcess } from "./courseProcess.type";
import { RecentSearchHistoryDto, User } from "./user.type";
import { Question, UserQuiz } from "./section.type";
import { Review } from "./review.type";
import { ForumLecture } from "./forumLecture";
import { Roles } from "./roles.type";
import { Order } from "./order.type";

export interface DataResponse {
  timestamp: number;
  statusCode: number;
  statusMessage: string;
  data:
    | string
    | number
    | string[]
    | User
    | Course
    | Content
    | Course[]
    | boolean
    | CourseProcess
    | SalesByTopic[]
    | MonthlySale[]
    | SalesSamePeriod[]
    | SalesSamePeriodByTopics[]
    | Statistics
    | Question[]
    | UserQuiz
    | Review
    | Roles;
}

export interface ListResponse {
  timestamp: number;
  statusCode: number;
  statusMessage: string;
  totalRecords: number;
  totalPages: number;
  data:
    | Course[]
    | User[]
    | Review[]
    | ForumLecture[]
    | Roles[]
    | RecentSearchHistoryDto[]
    | Order[];
}

export interface PageResponse {
  pageIndex: number;
  pageSize: number;
}

export interface AvatarResponse {
  rawAvatar: string;
}
