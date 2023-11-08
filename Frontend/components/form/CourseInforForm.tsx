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

const formSchema = formCourseInformationSchema;

function CourseInforForm(props: any) {
  const formRef = useRef<HTMLFormElement>(null);
  const [imageFile, setImageFile] = useState<File>();
  const [videoFile, setVideoFile] = useState<File>();
  const saveStatus = useAppSelector(
    (state) => state.courseReducer.saveCourseStatus
  );
  const dispatch = useAppDispatch();

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
    },
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    console.log(values);
  }
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
                          onChange={(e) => {
                            setVideoFile(e.target.files?.[0]);
                          }}
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
