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
import { Lecture, Question, UserQuiz } from "./section.type";
import { Review } from "./review.type";
import { ForumLecture } from "./forumLecture";
import { Roles, UserRoles } from "./roles.type";
import { Order } from "./order.type";
import { NotificationDTO } from "./notification.type";
import { UserLog } from "./userLog.type";

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
    | Roles
    | UserRoles
    | Lecture;
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
    | Order[]
    | NotificationDTO[]
    | UserLog[];
}

export interface PageResponse {
  pageIndex: number;
  pageSize: number;
}

export interface AvatarResponse {
  rawAvatar: string;
}
