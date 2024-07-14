"use client";
import { formAssignmentBasicInfoSchema } from "@/utils/formSchema";
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
import { zodResolver } from "@hookform/resolvers/zod";
import React, { Fragment, use, useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { Button } from "../ui/button";
import { useUpdateForumLectureMutation } from "@/redux/services/forumApi";
import { Assignment } from "@/types/assignment.type";
import { Lecture } from "@/types/section.type";
import {
  useAddAssignmentMutation,
  useUpdateAssignmentMutation,
  useUpdateLectureMutation,
} from "@/redux/services/contentApi";

const formSchema = formAssignmentBasicInfoSchema;

const handleSetDefaultValueFrom = (lecture: any) => {
  return {
    title: lecture?.name as string,
    description: lecture?.description as string,
    estimatedDuration: lecture?.assignment?.estimatedDuration as number,
  };
};

interface AssignmentBasicInfoFormProps {
  lecture?: Lecture;
}

function AssignmentBasicInfoForm(props: AssignmentBasicInfoFormProps) {
  const { lecture } = props;
  const formRef = useRef<HTMLFormElement>(null);
  const [defaultValueForm, setDefaultValueForm] = useState(
    handleSetDefaultValueFrom(lecture as Lecture)
  );
  const [updateLectureById] = useUpdateLectureMutation();
  const [addAssignment] = useAddAssignmentMutation();
  const [updateAssignment] = useUpdateAssignmentMutation();

  useEffect(() => {
    setDefaultValueForm(handleSetDefaultValueFrom(lecture as Lecture));
  }, [lecture]);

  const form = useForm<z.infer<typeof formSchema>>({
    shouldUnregister: true,
    resolver: zodResolver(formSchema),
    defaultValues: defaultValueForm,
  });

  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    const updateLecture = { ...lecture };
    let isUpdateLecture = false;
    if (values.title !== updateLecture.name) {
      updateLecture.name = values.title;
      isUpdateLecture = true;
    }
    if (values.description !== updateLecture.description) {
      updateLecture.description = values.description;
      isUpdateLecture = true;
    }
    if (isUpdateLecture) {
      updateLecture.assignment = undefined;
      updateLectureById(updateLecture as Lecture);
    }

    let assignment: Assignment = {};
    if (lecture?.assignment) {
      assignment = { ...lecture.assignment };
      if (assignment.estimatedDuration !== values.estimatedDuration || !assignment.estimatedDuration) {
        assignment.estimatedDuration = values.estimatedDuration;
        updateAssignment(assignment as Assignment);
      }
    } else {
      assignment.estimatedDuration = values.estimatedDuration;
      assignment.lecture = {
        id: lecture?.id,
      };
      addAssignment(assignment as Assignment);
    }
  };

  return (
    <div>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} ref={formRef}>
          <FormField
            control={form.control}
            name="title"
            render={({ field }) => (
              <FormItem className="mb-10 mt-3">
                <FormLabel className="text-black font-bold">Title</FormLabel>
                <FormControl>
                  <Input
                    className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                    {...field}
                    // disabled={
                    //   !isPermissionGranted(
                    //     roleDetail as RoleDetail[],
                    //     PermissionName.CAN_CREATE,
                    //     ModuleName.COURSE
                    //   )
                    // }
                  ></Input>
                </FormControl>

                <FormMessage className="text-[10px]" />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="description"
            render={({ field }) => (
              <FormItem className="mb-10 mt-3">
                <FormLabel className="text-black font-bold">
                  Description
                </FormLabel>
                <FormControl>
                  <Input
                    className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black"
                    {...field}
                    // disabled={
                    //   !isPermissionGranted(
                    //     roleDetail as RoleDetail[],
                    //     PermissionName.CAN_CREATE,
                    //     ModuleName.COURSE
                    //   )
                    // }
                  ></Input>
                </FormControl>

                <FormMessage className="text-[10px]" />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="estimatedDuration"
            render={({ field }) => (
              <FormItem className="mb-10 mt-3">
                <FormLabel className="text-black font-bold">
                  Estimated Duration
                </FormLabel>
                <FormControl>
                  <Input
                    type="number"
                    className="rounded-none focus-visible:ring-0 disabled:opacity-1 disabled:cursor-default border-black w-1/4"
                    {...field}
                    // disabled={
                    //   !isPermissionGranted(
                    //     roleDetail as RoleDetail[],
                    //     PermissionName.CAN_CREATE,
                    //     ModuleName.COURSE
                    //   )
                    // }
                  ></Input>
                </FormControl>
                <FormDescription className="text-[10px]">
                  In minutes
                </FormDescription>
                <FormMessage className="text-[10px]" />
              </FormItem>
            )}
          />
          <Button
            type="submit"
            // className=" hover:bg-white"
            //   onClick={() => handleClickOpen()}
          >
            Save
          </Button>
        </form>
      </Form>
    </div>
  );
}

export default AssignmentBasicInfoForm;
