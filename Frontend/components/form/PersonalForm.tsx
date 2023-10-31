import { formSchemaPersonal } from "@/utils/formSchema";
import { zodResolver } from "@hookform/resolvers/zod";
import React, { Fragment, useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import * as z from "zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../ui/form";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { User } from "@/types/user.type";
import Image from "next/image";

const formSchema = formSchemaPersonal;

interface PersonalProps {
  userInfor: Omit<User, "password" | "re_password">;
}

function PersonalForm(props: PersonalProps) {
  const { userInfor } = props;
  const [allowInput, setAllowInput] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const [imageError, setImageError] = useState(false);

  const handleImageError = () => {
    setImageError(true);
  };

  useEffect(() => {
    if (allowInput && inputRef.current) {
      inputRef.current.focus();
    }
  }, [allowInput]);

  const handleClickEdit = () => {
    setAllowInput(!allowInput);
    form.reset();
    form.clearErrors();
  };

  const form = useForm<z.infer<typeof formSchema>>({
    shouldUnregister: true,
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: userInfor.username,
      email: userInfor.email,
      firstName: userInfor.firstName,
      lastName: userInfor.lastName,
      photos: userInfor.photos,
      telephone: userInfor.telephone,
      addressLine: userInfor.address.addressLine,
    },
  });
  function onSubmit(values: z.infer<typeof formSchema>) {
    console.log("đã zô");
    console.log(values);
  }
  return (
    <div>
      <Form {...form}>
        <form className="mt-5">
          <div className="flex flex-col  sticky top-[120px] bg-white">
            {!allowInput ? (
              <Fragment>
                <div className="flex justify-end">
                  <Button
                    className="rounded-3xl w-max"
                    type="button"
                    onClick={() => handleClickEdit()}
                  >
                    Chỉnh Sửa
                  </Button>
                </div>
              </Fragment>
            ) : (
              <Fragment>
                <div className="flex gap-2 justify-end">
                  <Button
                    className="text-orange-400 border border-orange-400  bg-white rounded-3xl hover:bg-white w-max"
                    type="button"
                    onClick={form.handleSubmit(onSubmit)}
                  >
                    Lưu
                  </Button>
                  <Button
                    className=" bg-none rounded-3xl w-max"
                    type="button"
                    onClick={() => handleClickEdit()}
                  >
                    Hủy
                  </Button>
                </div>
              </Fragment>
            )}

            <hr className="mt-2 border border-b-orange-500" />
          </div>
          <div>
            <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <FormItem className="mb-14 mt-3">
                  <FormLabel className="text-black">User Name</FormLabel>
                  <FormControl>
                    <Input
                      className="border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default "
                      //   className={`border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default ${
                      //     allowInput ? "border-b-blue-700 " : " "
                      //   }`}
                      disabled={true}
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem className="mb-14 mt-3">
                  <FormLabel className="text-black">Email</FormLabel>
                  <FormControl>
                    <Input
                      className="border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default "
                      //   className={`border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default ${
                      //     allowInput ? "border-b-blue-700 " : " "
                      //   }`}
                      disabled={true}
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="firstName"
              render={({ field }) => (
                <FormItem className="mb-14 mt-3">
                  <FormLabel className="text-black">First Name</FormLabel>
                  <FormControl>
                    <Input
                      ref={inputRef}
                      className={`border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default ${
                        allowInput ? "border-b-blue-700 " : " "
                      }`}
                      disabled={!allowInput}
                      value={field.value}
                      onChange={field.onChange}
                    ></Input>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="lastName"
              render={({ field }) => (
                <FormItem className="mb-14 mt-3">
                  <FormLabel className="text-black">Last Name</FormLabel>
                  <FormControl>
                    <Input
                      className={`border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default ${
                        allowInput ? "border-b-blue-700 " : " "
                      }`}
                      disabled={!allowInput}
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="addressLine"
              render={({ field }) => (
                <FormItem className="mb-14 mt-3">
                  <FormLabel className="text-black">Address Line</FormLabel>
                  <FormControl>
                    <Input
                      className={`border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default ${
                        allowInput ? "border-b-blue-700 " : " "
                      }`}
                      disabled={!allowInput}
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="telephone"
              render={({ field }) => (
                <FormItem className="mb-14 mt-3">
                  <FormLabel className="text-black">Phone Number</FormLabel>
                  <FormControl>
                    <Input
                      className={`border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default ${
                        allowInput ? "border-b-blue-700 " : " "
                      }`}
                      disabled={!allowInput}
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <div className="flex-between gap-10">
              <FormField
                control={form.control}
                name="photos"
                render={({ field }) => (
                  <FormItem className="mb-14 mt-3 w-full">
                    <FormLabel className="text-black">Avatar</FormLabel>
                    <FormControl>
                      <Input
                        className={`border-x-0 border-t-0 rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default ${
                          allowInput ? "border-b-blue-700 " : " "
                        }`}
                        disabled={!allowInput}
                        {...field}
                      ></Input>
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <div className="w-full">
                {imageError ? (
                  <Fragment>
                    <Image
                      src={"/banner.jpg"}
                      width={100}
                      height={100}
                      alt=""
                      className="w-20 h-20 rounded-full"
                    />
                  </Fragment>
                ) : (
                  <Fragment>
                    <Image
                      src={"/123"}
                      width={100}
                      height={100}
                      alt=""
                      onError={() => handleImageError()}
                      className="w-20 h-20 rounded-full"
                    />
                  </Fragment>
                )}
              </div>
            </div>
          </div>
        </form>
      </Form>
    </div>
  );
}

export default PersonalForm;
