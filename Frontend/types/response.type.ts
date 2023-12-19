import Content from "./content.type";
import { Course } from "./course.type";
import { CourseProcess } from "./courseProcess.type";
import { User } from "./user.type";

export interface DataResponse {
    timestamp: number,
    statusCode: number,
    statusMessage: string,
    data: string | string[] | User | Course | Content | Course[] | boolean | CourseProcess
}

export interface ListResponse {
    timestamp: number,
    statusCode: number,
    statusMessage: string,
    totalRecords: number,
    totalPages: number,
    data:  Course[] | User[]
}