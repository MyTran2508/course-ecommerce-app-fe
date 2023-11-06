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