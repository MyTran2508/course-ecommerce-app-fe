import ForbiddenPage from "@/components/ForbiddenPage";
import { store } from "@/redux/store";
import { RoleDetail, Roles } from "@/types/roles.type";
import { isPermissionGranted } from "@/utils/function";
import { ModuleName, PermissionName, Role, RoleUser } from "@/utils/resources";
import React from "react";

export default function withAuthManager(
  WrappedComponent: React.ComponentType,
  moduleName: string
) {
  const roles = store.getState().persistedReducer.userReducer.user.roles;
  const roleDetail = roles ? roles[0].roleDetails : [];

  function WithAuthManager(props: any) {
    if (
      !(
        isPermissionGranted(
          roleDetail as RoleDetail[],
          PermissionName.CAN_VIEW,
          moduleName
        ) ||
        isPermissionGranted(
          roleDetail as RoleDetail[],
          PermissionName.CAN_APPROVE_COURSE,
          ModuleName.COURSE_ADMIN
        )
      ) &&
      roles?.[0].name == Role.USER
    ) {
      return <ForbiddenPage />;
    }

    return <WrappedComponent {...props} />;
  }

  return WithAuthManager;
}
