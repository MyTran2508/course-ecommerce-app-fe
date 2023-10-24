import {
  AnyAction,
  isRejectedWithValue,
  Middleware,
  isRejected,
  MiddlewareAPI,
} from "@reduxjs/toolkit";
import { toast } from "react-toastify";
import { isEntityError } from "../error/entityError";

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
        toast.warning(action.payload.data.error), {
        position: "top-right",
        autoClose: 1200,
        theme: "light",
        className: "w-[400px]",};
      }
    } 
    if (isRejected(action)) {
      toast.warning((action.payload as any).error), {
        position: "top-right",
        autoClose: 1200,
        theme: "light",
        className: "w-[400px]",
      };
     
    }
    return next(action);
  };
