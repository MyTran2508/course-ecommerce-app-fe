import { User } from "./user.type";

export interface DataResponse {
    timestamp: number,
    statusCode: number,
    statusMessage: string,
    data: string | User
}