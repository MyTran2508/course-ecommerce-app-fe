import {
  AnyAction,
  isRejectedWithValue,
  Middleware,
  isRejected,
  MiddlewareAPI,
} from "@reduxjs/toolkit";
import { toast } from "react-toastify";
import { isEntityError } from "../error/entityError";
import showToast from "@/utils/showToast";
import { ToastStatus } from "@/utils/resources";

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
