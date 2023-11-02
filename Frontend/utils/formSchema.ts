import * as z from "zod";
import { phoneNumberRegExp, urlRegex } from "./regex";

export const formSchemaSignUp = z.object({
  username: z.string().min(6, "Username must contain at least 6 character(s)"),
  email: z
    .string()
    .min(10, "Email must contain at least 10 character(s)")
    .max(30)
    .email(),
  password: z
    .string()
    .min(1, "Password is required")
    .min(8, "Password must have than 8 character(s)"),
  re_password: z
    .string()
    .min(1, "Password is required")
    .min(8, "Password must have than 8 character(s)"),
  telephone: z.string().refine((value) => phoneNumberRegExp.test(value), {
    message: "Invalid phone number",
  }),
  firstName: z.string(),
  lastName: z.string(),
  addressLine: z.string(),
  photos: z.string().refine((value) => urlRegex.test(value), {
    message: "Invalid Url"
  }),
});

export const validationSchemaSignUp = z
  .object({
    username: formSchemaSignUp.shape.username,
    email: formSchemaSignUp.shape.email,
    password: formSchemaSignUp.shape.password,
    re_password: formSchemaSignUp.shape.re_password,
  })
  .strict()
  .refine((data) => data.password === data.re_password, {
    path: ["re_password"],
    message: "Passwords do not match",
  });

export const formSchemaLogin = z.object({
  username: z.string().min(1),
  password: z
    .string()
    .min(1, "Password is required")
    .min(8, "Password must have than 8 character(s)"),
});

export const formSchemaPersonal = z.object({
  username: formSchemaSignUp.shape.username,
  email: formSchemaSignUp.shape.email,
  firstName: formSchemaSignUp.shape.firstName,
  lastName: formSchemaSignUp.shape.lastName,
  addressLine: formSchemaSignUp.shape.addressLine,
  telephone: formSchemaSignUp.shape.telephone,
  photos: formSchemaSignUp.shape.photos
}).strict()

export const formSchemaResetPassword = z.object({
  old_password: formSchemaSignUp.shape.password,
  new_password: formSchemaSignUp.shape.password,
  re_password: formSchemaSignUp.shape.re_password
}).strict().refine((data) => data.new_password === data.re_password, {
  path: ["re_password"],
  message: "Passwords do not match",
})