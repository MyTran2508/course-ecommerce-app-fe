export interface LoginRequest{
    username: string,
    password: string
}

export interface ChangePasswordRequest {
    userId: string,
    oldPassword: string,
    newPassword: string
}

export interface ForgotPasswordRequest {
    otp: string,
    email: string,
    newPassword: string
}

export interface SearchCourseRequest {
    levelIds?: string[];
    languageIds?: string[];
    topicIds?: string[];
    keyword: string | null;
    pageIndex: number;
    pageSize: number;
}