import { formCourseInformationSchema } from "@/utils/formSchema";
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
  FormDescription,
} from "../ui/form";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import MyCombobox from "../MyCombobox";
import Image from "next/image";
import { Topic, Language, Level } from "@/utils/data";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import { setStatusSaveCourse } from "@/redux/features/courseSlice";
import {
  useCreateCourseMutation,
  useUploadCourseImageMutation,
  useUploadCourseVideoMutation,
} from "@/redux/services/courseApi";
import { FileImage } from "lucide-react";
import { DataResponse } from "@/types/response.type";
import { Course } from "@/types/course.type";
import { isFulfilled } from "@reduxjs/toolkit";
import showToast from "@/utils/showToast";
import { StatusCode, ToastMessage, ToastStatus } from "@/utils/resources";

const formSchema = formCourseInformationSchema;

function CourseInforForm(props: any) {
  const formRef = useRef<HTMLFormElement>(null);
  const [imageFile, setImageFile] = useState<File>();
  const [videoFile, setVideoFile] = useState<File>();
  const saveStatus = useAppSelector(
    (state) => state.courseReducer.saveCourseStatus
  );
  const dispatch = useAppDispatch();
  const [uploadCourseImage] = useUploadCourseImageMutation();
  const [uploadCourseVideo] = useUploadCourseVideoMutation();
  const [createCourse] = useCreateCourseMutation();

  const handleUploadFile = async () => {
    try {
      const [uploadCourseImageResponse, uploadCourseVideoResponse] =
        await Promise.all([
          imageFile ? uploadCourseImage(imageFile) : null,
          videoFile ? uploadCourseVideo(videoFile) : null,
        ]);

      console.log(uploadCourseImageResponse);
      console.log(uploadCourseVideoResponse);

      let newUrlCourseImage = "";
      let newUrlCourseVideo = "";

      if (uploadCourseImageResponse && "data" in uploadCourseImageResponse) {
        newUrlCourseImage = uploadCourseImageResponse.data.data as string;
      }

      if (uploadCourseVideoResponse && "data" in uploadCourseVideoResponse) {
        newUrlCourseVideo = uploadCourseVideoResponse.data.data as string;
      }

      return { newUrlCourseImage, newUrlCourseVideo };
    } catch (error) {
      console.error(error);
      return { newUrlCourseImage: "", newUrlCourseVideo: "" };
    }
  };

  const handleCreateCourse = async (
    newCourse: Omit<Course, "id" | "isApproved">
  ) => {
    await createCourse(newCourse)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        handleToast(fulfilled);
      })
      .catch((error) => {
        console.log(error);
        showToast(ToastStatus.ERROR, ToastMessage.CREATE_COURSE_FAIL);
      });
  };

  const handleToast = (dataResult: DataResponse) => {
    if (dataResult?.statusCode === StatusCode.REQUEST_SUCCESS) {
      showToast(ToastStatus.SUCCESS, ToastMessage.CREATE_COURSE_SUCCESS);
    } else {
      showToast(ToastStatus.ERROR, ToastMessage.DATA_COURSE_EXISTED);
    }
  };

  useEffect(() => {
    if (saveStatus && formRef.current) {
      formRef.current.requestSubmit();
      dispatch(setStatusSaveCourse(false));
    }
  }, [saveStatus]);

  const form = useForm<z.infer<typeof formSchema>>({
    shouldUnregister: true,
    resolver: zodResolver(formSchema),
    defaultValues: {
      level: Level[0].id,
      language: Language[0].id,
      topic: Topic[0].id,
      urlCourseImages: "",
      urlPromotionVideos: "",
    },
  });

  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    const { newUrlCourseImage, newUrlCourseVideo } = await handleUploadFile();
    const newCourse: Omit<Course, "id" | "isApproved"> = {
      name: values.name,
      subTitle: values.subTitle,
      price: values.price,
      level: {
        id: values.level,
      },
      language: {
        id: values.language,
      },
      topic: {
        id: values.topic,
      },
      urlCourseImages: newUrlCourseImage,
      urlPromotionVideos: newUrlCourseVideo,
      authorName: "",
    };

    handleCreateCourse(newCourse);
  };
  return (
    <div>
      <Form {...form}>
        <form
          className="mx-5"
          onSubmit={form.handleSubmit(onSubmit)}
          ref={formRef}
        >
          <div>
            <FormField
              control={form.control}
              name="name"
              render={({ field }) => (
                <FormItem className="mb-10 mt-3">
                  <FormLabel className="text-black">Tiêu Đề Khóa Học</FormLabel>
                  <FormControl>
                    <Input
                      className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormDescription className="text-[10px]">
                    Tiêu đề của bạn phải là sự kết hợp giữa thu hút sự chú ý,
                    mang tính thông tin và được tối ưu hóa cho tìm kiếm
                  </FormDescription>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="subTitle"
              render={({ field }) => (
                <FormItem className="mb-10 mt-3">
                  <FormLabel className="text-black">Phụ Đề Khóa Học</FormLabel>
                  <FormControl>
                    <Input
                      className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormDescription className="text-[10px]">
                    Sử dụng 1 hoặc 2 từ khóa liên quan và đề cập đến 3-4 lĩnh
                    vực quan trọng nhất mà bạn đã đề cập trong khóa học của
                    mình.
                  </FormDescription>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="price"
              render={({ field }) => (
                <FormItem className="mb-10 mt-3">
                  <FormLabel className="text-black">Giá Tiền</FormLabel>
                  <FormControl>
                    <Input
                      type="number"
                      className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                      {...field}
                    ></Input>
                  </FormControl>
                  <FormDescription className="text-[10px]">
                    Hãy thiết lập giá bán cho khóa học của bạn
                  </FormDescription>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormLabel className="text-black">Các thông tin khác</FormLabel>
            <div className="flex-between">
              <FormField
                control={form.control}
                name="language"
                render={({ field }) => (
                  <FormItem className="mb-10 mt-3">
                    <FormControl>
                      <MyCombobox data={Language} onChange={field.onChange} />
                    </FormControl>
                    <FormMessage className="text-[10px]" />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="level"
                render={({ field }) => (
                  <FormItem className="mb-10 mt-3">
                    <FormControl>
                      <MyCombobox data={Level} onChange={field.onChange} />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="topic"
                render={({ field }) => (
                  <FormItem className="mb-10 mt-3">
                    <FormControl>
                      <MyCombobox data={Topic} onChange={field.onChange} />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>
            <FormField
              control={form.control}
              name="urlCourseImages"
              render={({ field }) => (
                <FormItem className="mb-10 mt-3">
                  <FormLabel className="text-black">
                    Hình Ảnh Khóa Học
                  </FormLabel>
                  <FormControl>
                    <div className="flex gap-6">
                      <Image
                        src={"/banner.jpg"}
                        alt=""
                        width={400}
                        height={200}
                      />
                      <div className="flex justify-center flex-col">
                        <div className="mb-2 text-xs">
                          Tải lên hình ảnh khóa học của bạn ở đây
                        </div>
                        <Input
                          className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                          type="file"
                          accept=".jpeg, .jpg, .png"
                          onChange={(e) => {
                            setImageFile(e.target.files?.[0]);
                          }}
                        />
                      </div>
                    </div>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="urlPromotionVideos"
              render={({ field }) => (
                <FormItem className="mb-10 mt-3">
                  <FormLabel className="text-black">Video Intro</FormLabel>
                  <FormControl>
                    <div className="flex gap-6">
                      <Image
                        src={"/banner.jpg"}
                        alt=""
                        width={400}
                        height={200}
                      />
                      <div className="flex justify-center flex-col">
                        <div className="mb-2 text-xs">
                          Video quảng cáo của bạn là cách nhanh chóng và hấp dẫn
                          để học viên xem trước những gì họ sẽ học trong khóa
                          học của bạn
                        </div>
                        <Input
                          className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black w-max"
                          type="file"
                          accept=".mp4, .mkv, .wmv"
                          onChange={(e) => setVideoFile(e.target.files?.[0])}
                        />
                      </div>
                    </div>
                  </FormControl>
                  <FormMessage className="text-[10px]" />
                </FormItem>
              )}
            />
          </div>
        </form>
      </Form>
    </div>
  );
}

export default CourseInforForm;
