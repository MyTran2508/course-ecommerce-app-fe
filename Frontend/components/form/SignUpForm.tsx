"use client";
import { zodResolver } from "@hookform/resolvers/zod";
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import * as z from "zod";
import { BsPencilSquare } from "react-icons/bs";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import CustomButton from "../CustomButton";
import { useRouter } from "next/navigation";

const phoneNumberRegExp = /^0\d{9}$/;

const formSchema = z.object({
  username: z.string().min(5, "Username must contain at least 5 character(s)"),
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
  phone: z.string().refine((value) => phoneNumberRegExp.test(value), {
    message: "Invalid phone number",
  }),
  address_line: z
    .string()
    .min(1, "Password is required")
    .min(8, "Password must have than 8 character(s)"),
  ward: z.string().min(1),
  province: z.string().min(1),
});

const validationSchema = z
  .object({
    username: formSchema.shape.username,
    email: formSchema.shape.email,
    password: formSchema.shape.password,
    re_password: formSchema.shape.re_password,
  })
  .strict()
  .refine((data) => data.password === data.re_password, {
    path: ["re_password"],
    message: "Passwords do not match",
  });

function SignUpForm() {
  const [openEye, setOpenEye] = useState(false);
  const [changPage, setChangePage] = useState(false);
  const [changeSchema, setChangeSchema] = useState(false);

  const toggle = () => {
    setOpenEye(!openEye);
  };

  const handleChangeForm = () => {
    setChangePage(!changPage);
    setChangeSchema(!changeSchema);
  };

  const handleNext = (values: z.infer<typeof formSchema>) => {
    const { username, email, password, re_password } = values;

    const validationResult = validationSchema.safeParse({
      username,
      email,
      password,
      re_password,
    });
    console.log(values);

    if (validationResult.success) {
      console.log(values);
      handleChangeForm();
    }
  };

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(
      changeSchema === false ? validationSchema : formSchema
    ),
    defaultValues: {},
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    console.log(values);
  }

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="w-1/2 h-4/5 xs:h-4/6 xs:w-4/5 min-h-[470px] bg-gray-100 rounded-3xl xl:flex"
      >
        <div className="h-1/2 p-5 my-1 w-full lg:w-1/2 2xs:text-[10px] xl:text-xs ">
          <div className="font-mono mb-2 flex-center flex-col ">
            <div className="text-xl mb-2 "> SignUp</div>
            <p>Create Account So Easy!!!</p>
          </div>
          {changPage === false ? (
            <div className="mb-2">
              <FormField
                key={"username"}
                control={form.control}
                name="username"
                render={({ field }) => (
                  <FormItem className="mb-1 ">
                    <FormLabel className="text-black xl:text-xs ">
                      Username
                    </FormLabel>
                    <FormControl>
                      <Input
                        className="text-black xl:text-xs h-7"
                        placeholder="username"
                        {...field}
                      ></Input>
                    </FormControl>
                    <FormMessage className="text-[10px]"></FormMessage>
                  </FormItem>
                )}
              />
              <FormField
                key={"email"}
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem className="mb-1 ">
                    <FormLabel className="text-black xl:text-xs ">
                      Email
                    </FormLabel>
                    <FormControl>
                      <Input
                        className="text-black xl:text-xs h-7"
                        placeholder="email"
                        {...field}
                      ></Input>
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <FormField
                key={"password"}
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem className="mb-1 ">
                    <FormLabel className="text-black xl:text-xs">
                      Password
                    </FormLabel>
                    <FormControl>
                      <div className="relative">
                        <div className="absolute text-xl right-1 cursor-pointer mt-1">
                          {openEye === false ? (
                            <AiOutlineEyeInvisible onClick={toggle} />
                          ) : (
                            <AiOutlineEye onClick={toggle} />
                          )}
                        </div>
                        <Input
                          className="text-black xl:text-xs h-7"
                          type={openEye === false ? "password" : "text"}
                          placeholder="password"
                          {...field}
                        />
                      </div>
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <FormField
                key={"re_password"}
                control={form.control}
                name="re_password"
                render={({ field }) => (
                  <FormItem className="mb-2">
                    <FormLabel className="text-black xl:text-xs h-7">
                      Re-Password
                    </FormLabel>
                    <FormControl>
                      <Input
                        className="text-black xl:text-xs h-7"
                        type={openEye === false ? "password" : "text"}
                        placeholder="password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <div className="text-[12px] mt-2 flex justify-end xs:text-[10px]">
                <CustomButton
                  title="Next"
                  type="summit"
                  containerStyles="xs:text-[10px] py-1 px-4 bg-white border rounded-xl hover:scale-110 duration-300"
                  handleClick={() => handleNext(form.getValues())}
                />
              </div>
            </div>
          ) : (
            <div>
              <FormField
                key={"phone"}
                control={form.control}
                name="phone"
                render={({ field }) => (
                  <FormItem className="mb-1 ">
                    <FormLabel className="text-black xl:text-xs h-7">
                      Phone
                    </FormLabel>
                    <FormControl>
                      <Input
                        className="text-black xl:text-xs h-7"
                        placeholder="phone"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <FormField
                key={"address_line"}
                control={form.control}
                name="address_line"
                render={({ field }) => (
                  <FormItem className="mb-1 ">
                    <FormLabel className="text-black xl:text-xs h-7">
                      Address Line
                    </FormLabel>
                    <FormControl>
                      <Input
                        className="text-black xl:text-xs h-7"
                        placeholder="address line"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />

              <FormField
                key="ward"
                control={form.control}
                name="ward"
                render={({ field }) => (
                  <FormItem className="mb-1 ">
                    <FormLabel className="text-black xl:text-xs h-7">
                      Ward
                    </FormLabel>
                    <FormControl>
                      <Input
                        className="text-black xl:text-xs h-7"
                        placeholder="ward"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <FormField
                key={"province"}
                control={form.control}
                name="province"
                render={({ field }) => (
                  <FormItem className="mb-1 ">
                    <FormLabel className="text-black xl:text-xs h-7">
                      Province
                    </FormLabel>
                    <FormControl>
                      <Input
                        className="text-black xl:text-xs h-7"
                        placeholder="province"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <Button
                type="submit"
                className="hover:scale-110 transition duration-700 gap-2 mt-1 w-full h-7"
              >
                <BsPencilSquare />
                Register
              </Button>
              <div className="text-[12px] mt-2 flex-end xs:text-[10px]">
                <CustomButton
                  title="Back"
                  containerStyles="xs:text-[10px] py-1 px-4 bg-white border rounded-xl hover:scale-110 duration-300"
                  handleClick={() => {
                    handleChangeForm();
                  }}
                />
              </div>
            </div>
          )}
        </div>
        <div className="w-1/2 lg:block hidden">
          <div className="bg-signup bg-center bg-cover h-full rounded-r-3xl w-full"></div>
        </div>
      </form>
    </Form>
  );
}

export default SignUpForm;
