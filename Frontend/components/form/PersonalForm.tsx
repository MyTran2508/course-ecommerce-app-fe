import { formPersonalSchema } from "@/utils/formSchema";
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
import {
  useGetAvatarQuery,
  useUploadImageMutation,
  useUpdateUserMutation,
} from "@/redux/services/userApi";
import showToast from "@/utils/showToast";
import { ToastMessage, ToastStatus } from "@/utils/resources";

const formSchema = formPersonalSchema;

interface PersonalProps {
  userInfor: Omit<User, "re_password">;
}

const handleSetDefaultValueFrom = (value: Omit<User, "re_password">) => {
  return {
    username: value.username,
    email: value.email,
    firstName: value.firstName,
    lastName: value.lastName,
    photos: "",
    telephone: value.telephone,
    addressLine: value.addresses[0]?.addressLine,
  };
};

async function PersonalForm(props: PersonalProps) {
  const { userInfor } = props;
  const [allowInput, setAllowInput] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const [imageError, setImageError] = useState(false);
  const [defaultValueForm, setDefaultValueFrom] = useState(
    handleSetDefaultValueFrom(userInfor)
  );
  const [file, setFile] = useState<File>();
  const [currentAvatar, setCurrentAvatar] = useState();

  const [updateAvatar] = useUploadImageMutation();
  const [updateUser, updateUserResult] = useUpdateUserMutation();
  const { data: avatarData, isSuccess: avatarSuccess } = useGetAvatarQuery(
    userInfor.username
  );

  const handleImageError = (type: boolean) => {
    setImageError(type);
  };

  useEffect(() => {
    if (allowInput && inputRef.current) {
      inputRef.current.focus();
    }
  }, [allowInput]);

  useEffect(() => {}, [updateUserResult]);
  useEffect(() => {
    setDefaultValueFrom(handleSetDefaultValueFrom(userInfor));
    handleImageError(false);
    setCurrentAvatar(avatarData);
  }, [userInfor, avatarData]);

  const handleClickEdit = () => {
    setAllowInput(!allowInput);
    form.clearErrors();
  };

  const handleClickCancel = () => {
    setAllowInput(!allowInput);
    form.reset();
    form.clearErrors();
  };

  const handleUpdateUser = async (
    data: Omit<User, "re_password" | "password" | "roles" | "photos">
  ) => {
    let updateSuccess = true;

    await Promise.all([
      updateUser(data).unwrap(),
      file ? updateAvatar({ username: data.username, image: file }) : null,
    ])
      .then(([updateUserResponse, updateAvatarResponse]) => {
        console.log(updateUserResponse);
        console.log(updateAvatarResponse);
      })
      .catch((error) => {
        console.error(error);
        updateSuccess = false;
      });

    if (updateSuccess) {
      setAllowInput(!allowInput);
      showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_USER_SUCCESS);
    } else {
      showToast(ToastStatus.ERROR, ToastMessage.UPDATE_USER_FAIL);
    }
  };

  const form = useForm<z.infer<typeof formSchema>>({
    shouldUnregister: true,
    resolver: zodResolver(formSchema),
    defaultValues: defaultValueForm,
  });
  function onSubmit(values: z.infer<typeof formSchema>) {
    const updateUser: Omit<
      User,
      "re_password" | "password" | "roles" | "photos"
    > = {
      id: userInfor.id,
      username: userInfor.username,
      email: userInfor.email,
      firstName: values.firstName,
      lastName: values.lastName,
      telephone: values.telephone,
      addresses: [
        {
          addressLine: values.addressLine,
          postalCode: null,
          defaultAddress: null,
        },
      ],
    };
    handleUpdateUser(updateUser);
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
                    onClick={() => handleClickCancel()}
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
                        disabled={!allowInput}
                        type="file"
                        onChange={(e) => {
                          setFile(e.target.files?.[0]);
                        }}
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
                      src={
                        currentAvatar !== "Error"
                          ? `data:image/png;base64,${currentAvatar}`
                          : "/banner.jpg"
                      }
                      width={100}
                      height={100}
                      alt=""
                      className="w-20 h-20 rounded-full"
                    />
                  </Fragment>
                ) : (
                  <Fragment>
                    <Image
                      src={
                        currentAvatar !== "Error"
                          ? `data:image/png;base64,${currentAvatar}`
                          : "/banner.jpg"
                      }
                      width={100}
                      height={100}
                      alt=""
                      onError={() => handleImageError(true)}
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
