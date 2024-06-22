import ForbiddenPage from "@/components/ForbiddenPage";
import { store } from "@/redux/store";
import React from "react";

export default function isUserExisted(WrappedComponent: React.ComponentType) {
  const userName = store.getState().persistedReducer.userReducer.user.username;

  function WithAuth(props: any) {
    if (userName === "") {
      return <ForbiddenPage />;
    }

    return <WrappedComponent {...props} />;
  }

  return WithAuth;
}
