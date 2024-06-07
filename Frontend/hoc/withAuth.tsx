import ForbiddenPage from "@/components/ForbiddenPage";
import { store } from "@/redux/store";
import { RoleDetail, Roles } from "@/types/roles.type";
import { isPermissionGranted } from "@/utils/function";
import { PermissionName, Role } from "@/utils/resources";
import React from "react";

export default function withAuth(
  WrappedComponent: React.ComponentType,
  moduleName: string
) {
  const roles = store.getState().persistedReducer.userReducer.user.roles;
  const roleDetail = roles ? roles[0].roleDetails : [];

  function WithAuth(props: any) {
    if (
      !isPermissionGranted(
        roleDetail as RoleDetail[],
        PermissionName.CAN_VIEW,
        moduleName
      ) &&
      roles?.[0].name !== Role.ADMIN
    ) {
      return <ForbiddenPage />;
    }

    return <WrappedComponent {...props} />;
  }

  return WithAuth;
}
