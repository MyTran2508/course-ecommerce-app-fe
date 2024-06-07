"use client";
import { zodResolver } from "@hookform/resolvers/zod";
import React, { useEffect, useState } from "react";
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
import { FiLogIn } from "react-icons/fi";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import { useParams, useRouter, useSearchParams } from "next/navigation";
import Link from "next/link";
import { useLoginUserMutation } from "@/redux/services/authApi";
import { LoginRequest } from "@/types/request.type";
import { DataResponse } from "@/types/response.type";
import { useAppDispatch } from "@/redux/hooks/reduxHooks";
import { setCredential } from "@/redux/features/authSlice";
import showToast from "@/utils/showToast";
import { Constant, Role, ToastMessage, ToastStatus } from "@/utils/resources";
import { formLoginSchema } from "@/utils/formSchema";
import "../../components/style/loginForm.scss";
import { Roles, UserRoles } from "@/types/roles.type";
import { useLazyGetRolesByUserNameQuery } from "@/redux/services/roleApi";
import {
  useLazyGetAvatarQuery,
  useLazyGetByUserNameQuery,
} from "@/redux/services/userApi";
import { User } from "@/types/user.type";
import { loadUser, setUser } from "@/redux/features/userSlice";

function LoginForm() {
  const formSchema = formLoginSchema;
  const route = useRouter();
  const searchParams = useSearchParams();
  const redirect = searchParams.get("redirect");
  const [openEye, setOpenEye] = useState(false);
  const [isLogin, setIsLogin] = useState(false);
  const [loginUser, loginUserResults] = useLoginUserMutation();
  const dispatch = useAppDispatch();
  const [getRoles, { data: roles, isSuccess: getRolesSuccess }] =
    useLazyGetRolesByUserNameQuery();
  const [getByUserName, { data: userNameData, isSuccess: userNameSuccess }] =
    useLazyGetByUserNameQuery();
  const [getAvatar, { data: avatarData, isSuccess: avatarSuccess }] =
    useLazyGetAvatarQuery();

  useEffect(() => {
    if (!isLogin) return;
    if (redirect) {
      route.push(redirect);
    } else {
      if (getRolesSuccess && roles) {
        const userRole: UserRoles = roles.data as UserRoles;

        if ((userRole.roles as Roles[])[0].name == Role.ADMIN) {
          route.push(Constant.ADMIN_DASHBOARD_PATH);
          return;
        }
        if ((userRole.roles as Roles[])[0].name == Role.MANAGER) {
          route.push(Constant.MANAGER_COURSE_PATH);
          return;
        }

        route.push("/");
      }
    }
  }, [roles, isLogin, getRolesSuccess]);

  useEffect(() => {
    if (userNameSuccess) {
      const userState: Pick<
        User,
        "username" | "photos" | "email" | "id" | "roles"
      > = {
        id: (userNameData?.data as User).id,
        username: (userNameData?.data as User).username,
        photos: avatarData?.rawAvatar as string,
        email: (userNameData?.data as User).email,
        roles: (userNameData?.data as User).roles,
      };
      dispatch(setUser(userState));
      dispatch(loadUser());
    }
  }, [userNameData]);

  const toggle = () => {
    setOpenEye(!openEye);
  };

  const handleLogin = async (data: LoginRequest) => {
    await loginUser(data)
      .unwrap()
      .then((fulfilled) => {
        handleSaveToken(fulfilled, data.username);
      });
    await getRoles(data.username);
    await getByUserName(data.username);
    await getAvatar(data.username);
  };

  const handleSaveToken = (dataResult: DataResponse, user: string) => {
    if (dataResult?.statusCode === 200) {
      const token = dataResult.data as string;
      const auth = {
        username: user,
        token: token,
      };
      dispatch(setCredential(auth));
      showToast(ToastStatus.SUCCESS, ToastMessage.LOGIN_SUCCESS);
      setIsLogin(true);
    } else {
      showToast(ToastStatus.ERROR, ToastMessage.LOGIN_FAIL);
    }
  };

  const form = useForm<z.infer<typeof formSchema>>({
    shouldUnregister: true,
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      password: undefined,
    },
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    handleLogin(values);
  }

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="xs: xs:w-4/5 w-full h-full xl:flex login-form"
      >
        <div className="h-1/2 p-5 my-1 w-2/5 lg:w-1/2 2xs:text-[10px] xl:text-sm mt-16 left-login-form">
          <div className="font-mono mb-2 flex-center flex-col logo-login-form ">
            <div>
              <img
                src="/mediafire-logo-transparent.png"
                className="w-80 mb-12"
              />
            </div>
          </div>
          <div className="mb-2 w-4/5 flex flex-col justify-center items-center m-auto">
            <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <FormItem className="mb-3 w-full">
                  <FormLabel className="text-back font-medium">
                    Tên Tài Khoản
                  </FormLabel>
                  <FormControl>
                    <Input
                      className="h-11 text-lg border-black"
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem className="w-full mt-2">
                  <FormLabel className="text-back font-medium">
                    Mật Khẩu
                  </FormLabel>
                  <FormControl>
                    <div className="relative">
                      <div className="absolute text-2xl right-1 cursor-pointer mt-2">
                        {openEye === false ? (
                          <AiOutlineEyeInvisible onClick={toggle} />
                        ) : (
                          <AiOutlineEye onClick={toggle} />
                        )}
                      </div>
                      <Input
                        type={openEye === false ? "password" : "text"}
                        className="h-11 text-lg border-black"
                        {...field}
                      />
                    </div>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />

            <Button
              type="submit"
              className="w-full hover:scale-110 transition duration-700 gap-2 mt-6 btn-login-form"
            >
              <FiLogIn />
              Đăng Nhập
            </Button>
            <Link
              href={"forget-password"}
              className="hover:cursor-pointer text-back font-medium text-sm mt-4"
            >
              Quên mật khẩu
            </Link>
            <div className="flex gap-3 m-2 items-center justify-center w-3/5">
              <hr className="border-gray-400 flex-1" />
              <p className="text-center w-auto">OR</p>
              <hr className="border-gray-400 flex-1" />
            </div>
            {/* <CustomButton
            title="Login with Google"
            icon="FcGoogle"
            iconStyles="mr-2 xl:text-2xl "
            containerStyles=" xs:text-[10px] bg-white border py-1 w-full rounded-xl mt-2 flex justify-center items-center text-sm font-bold hover:scale-110 duration-300 "
          /> */}
            <div className="text-[12px] mt-2 flex-between xs:text-[10px]">
              <Link href={"/signup"}>
                <button className="hover:scale-110 transition duration-700 secondary-btn btn-register">
                  Đăng ký tài khoản mới
                </button>
              </Link>
            </div>
            {/* <div id="main-footer"> */}
            {/* <footer id="main-footer">
              <p>Copyright &copy; 2024, All Rights Reserved</p>
              <div>
                <a>terms of use</a> | <a>Privacy Policy</a>
              </div>
            </div>
            </footer> */}
          </div>
        </div>
        <div className="w-3/5 lg:block hidden right-login-form">
          <div id="showcase">
            <div className="showcase-content">
              <h1 className="showcase-text">
                {"Let's create the future"} <strong>together</strong>
              </h1>
              <a href="#" className="secondary-btn">
                Start a free trial with us
              </a>
            </div>
          </div>
        </div>
      </form>
    </Form>
  );
}

export default LoginForm;
