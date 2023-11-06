"use client";
import {
  AnyAction,
  isRejectedWithValue,
  Middleware,
  isRejected,
  MiddlewareAPI,
  Dispatch,
} from "@reduxjs/toolkit";
import showToast from "@/utils/showToast";
import { ToastStatus } from "@/utils/resources";
import { useRouter, useParams, usePathname } from "next/navigation";
import { Role } from "@/types/user.type";
import { Router } from "next/router";
import { RootState } from "@/redux/store";

function isPayloadErrorMessage(payload: unknown): payload is {
  data: {
    error: string;
  };
  status: number;
} {
  return (
    typeof payload === "object" &&
    payload !== null &&
    "data" in payload &&
    typeof (payload as any).data?.error === "string"
  );
}

export const rtkQueryErrorLogger: Middleware =
  (api: MiddlewareAPI) => (next) => (action: AnyAction) => {
    if (isRejectedWithValue(action)) {
      if (isPayloadErrorMessage(action.payload)) {
        showToast(ToastStatus.WARNING, action.payload.data.error);
      }
    }
    if (isRejected(action)) {
      showToast(ToastStatus.WARNING, (action.payload as any).error);
    }
    return next(action);
  };

