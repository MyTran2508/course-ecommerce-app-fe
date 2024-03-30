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
import { User } from "./user.type";
import { Question } from "./section.type";

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
    | Question[];
}

export interface ListResponse {
  timestamp: number;
  statusCode: number;
  statusMessage: string;
  totalRecords: number;
  totalPages: number;
  data: Course[] | User[];
}
