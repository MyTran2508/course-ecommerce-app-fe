import { MonthlySale, SalesByTopic, SalesSamePeriod, SalesSamePeriodByTopics } from "@/app/admin/preview/page";
import Content from "./content.type";
import { Course } from "./course.type";
import { CourseProcess } from "./courseProcess.type";
import { User } from "./user.type";

export interface DataResponse {
    timestamp: number,
    statusCode: number,
    statusMessage: string,
    data: string |number | string[] | User | Course | Content | Course[] | boolean | CourseProcess | SalesByTopic[] | MonthlySale[] | SalesSamePeriod[] | SalesSamePeriodByTopics[]
}

export interface ListResponse {
    timestamp: number,
    statusCode: number,
    statusMessage: string,
    totalRecords: number,
    totalPages: number,
    data:  Course[] | User[]
}