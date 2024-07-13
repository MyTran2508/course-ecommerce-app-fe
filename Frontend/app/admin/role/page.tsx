"use client";
import RoleDetails from "@/components/Role/RoleDetails";
import withAuth from "@/hoc/withAuth";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import {
  useAddRoleMutation,
  useGetAllRoleQuery,
  useUpdateRoleMutation,
} from "@/redux/services/roleApi";
import { RoleDetail, Roles } from "@/types/roles.type";
import { isPermissionGranted } from "@/utils/function";
import {
  Action,
  ModuleName,
  PermissionName,
  Role,
  RoleUser,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import showToast from "@/utils/showToast";
import React, { useEffect, useState } from "react";
import { FaPen } from "react-icons/fa";
import { IoMdAddCircle } from "react-icons/io";
import { IoReloadCircleOutline } from "react-icons/io5";
import { MdDelete } from "react-icons/md";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

function RolePage() {
  const [roleDetails, setRoleDetails] = useState<RoleDetail[]>([]);
  const [roleName, setRoleName] = useState<string>("");
  const [roleDescription, setRoleDescription] = useState<string>("");
  const [roleId, setRoleId] = useState<string>();
  const [roleUser, setRoleUser] = useState<string>("");
  const { data: roles, isSuccess: getAllRolesSuccess } = useGetAllRoleQuery();
  const [addRoles] = useAddRoleMutation();
  const [updateRoles] = useUpdateRoleMutation();
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const roleDetail = role?.roleDetails;

  useEffect(() => {
    if (roleUser == " ") {
      setRoleDetails([]);
    }
  }, [roleUser]);

  const handleRoleClick = (role: Roles) => {
    setRoleId(role.id as string);
    setRoleName(role.name as string);
    setRoleDescription(role.description as string);
    setRoleDetails(role.roleDetails as RoleDetail[]);
    setRoleUser(role.roleUser as string);
  };

  const handleAction = (action: Action) => {
    if (
      !(
        isPermissionGranted(
          roleDetail as RoleDetail[],
          action,
          ModuleName.ROLE
        ) || role?.name == Role.ADMIN
      )
    ) {
      showToast(ToastStatus.WARNING, ToastMessage.NO_PERMISSION);
      return;
    }

    if (
      roleName === "" ||
      roleDescription === "" ||
      roleDetails.length === 0 ||
      roleUser === " "
    ) {
      showToast(ToastStatus.WARNING, ToastMessage.PLEASE_SELECT_PERMISSION);
      return;
    }
    if (roleId && action === Action.CREATE) {
      showToast(ToastStatus.WARNING, ToastMessage.CAN_NOT_CREATE_ROLE);
      return;
    }
    if (!roleId && action === Action.UPDATE) {
      showToast(ToastStatus.WARNING, ToastMessage.CAN_NOT_UPDATE_ROLE);
      return;
    }

    if (action === Action.CREATE) {
      addRoles({
        name: roleName,
        description: roleDescription,
        roleDetails: roleDetails,
        roleUser: roleUser,
      }).then((res) => {
        if ("data" in res) {
          if (res.data.statusCode === 200) {
            showToast(ToastStatus.SUCCESS, ToastMessage.CREATE_ROLE_SUCCESS);
            handleRefresh();
          } else {
            showToast(ToastStatus.ERROR, res.data.data as string);
          }
        }
      });
    }
    if (action === Action.UPDATE) {
      updateRoles({
        id: roleId,
        name: roleName,
        description: roleDescription,
        roleDetails: roleDetails,
        roleUser: roleUser,
      }).then((res) => {
        if ("data" in res) {
          if (res.data.statusCode === 200) {
            showToast(ToastStatus.SUCCESS, ToastMessage.UPDATE_ROLE_SUCCESS);
            handleRefresh();
          } else {
            showToast(ToastStatus.ERROR, res.data.data as string);
          }
        }
      });
    }
  };
  const handleRefresh = () => {
    setRoleName("");
    setRoleDescription("");
    setRoleDetails([]);
    setRoleId("");
    setRoleUser(" ");
  };

  return (
    <div className="w-full px-10 flex gap-5 mt-5">
      <div>
        <div className="flex gap-5 justify-around p-2 bg-slate-200 rounded-sm ">
          <div className="flex gap-1 items-center">
            <div className="text-sm font-medium text-gray-900 dark:text-white text-center flex items-center justify-center">
              Tên Quyền
            </div>
            <input
              type="text"
              id="first_name"
              className="py-[9px] bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-1 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Nhập tên quyền"
              value={roleName}
              onChange={(e) => setRoleName(e.target.value)}
              required
            />
          </div>
          <div className="flex gap-1 items-center">
            <label className=" text-sm font-medium text-gray-900 dark:text-white w-max">
              Mô Tả
            </label>
            <input
              type="text"
              id="last_name"
              className="py-[9px] bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-1 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Nhập mô tả"
              value={roleDescription}
              onChange={(e) => setRoleDescription(e.target.value)}
              required
            />
          </div>
          <div className="flex gap-1 justify-center items-center">
            <div className="text-sm font-medium text-gray-900 dark:text-white w-max">
              Quyền
            </div>
            <Select
              defaultValue={roleUser as string}
              value={roleUser as string}
              onValueChange={setRoleUser}
            >
              <SelectTrigger
                className={`disabled:opacity-1 disabled:cursor-default w-[100px] `}
              >
                <SelectValue placeholder="Chọn quyền" />
              </SelectTrigger>
              <SelectContent>
                <SelectGroup>
                  <SelectItem value=" " className="hidden">
                    Chưa chọn
                  </SelectItem>
                  <SelectItem value={RoleUser.ADMIN}>
                    {RoleUser.ADMIN}
                  </SelectItem>
                  <SelectItem value={RoleUser.MANAGER}>
                    {RoleUser.MANAGER}
                  </SelectItem>
                </SelectGroup>
              </SelectContent>
            </Select>
          </div>
        </div>
        <RoleDetails
          selectRoleParent={roleUser}
          roleDetails={roleDetails}
          setRoleDetails={setRoleDetails}
          action={Action.CREATE}
          isCreate={
            isPermissionGranted(
              roleDetail as RoleDetail[],
              Action.CREATE,
              ModuleName.ROLE
            ) || role?.name == Role.ADMIN
          }
        />
        <div className="grid grid-cols-2 w-full gap-5">
          <div
            className="hover:cursor-pointer bg-transparent hover:bg-green-500 text-green-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded flex justify-center gap-2 items-center"
            onClick={() => handleAction(Action.CREATE)}
          >
            <IoMdAddCircle className="text-2xl" />
            Thêm
          </div>
          <div className="hover:cursor-pointer bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded flex justify-center gap-2 items-center">
            <MdDelete className="text-2xl" />
            Xóa
          </div>
          <div
            className=" hover:cursor-pointer bg-transparent hover:bg-yellow-500 text-yellow-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded flex justify-center gap-2 items-center"
            onClick={() => handleAction(Action.UPDATE)}
          >
            <FaPen className="text-2xl" />
            Sửa
          </div>
          <div
            className="hover:cursor-pointer bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded flex justify-center gap-2 items-center"
            onClick={() => handleRefresh()}
          >
            <IoReloadCircleOutline className="text-2xl" />
            Làm mới
          </div>
        </div>
      </div>
      <div className="flex w-full border border-black rounded-sm flex-col p-2 m-2">
        <div className="flex justify-center">
          <h1 className="font-bold text-lg">Quyền</h1>
        </div>

        <table className="text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 table-auto">
          <thead className="text-xs text-gray-700 uppercase dark:text-gray-400">
            <tr>
              <th
                scope="col"
                className="px-6 py-3 bg-gray-50 dark:bg-gray-800 w-1/2"
              >
                Tên quyền
              </th>
              <th scope="col" className="px-6 py-3 bg-slate-300 w-1/2">
                Mô tả
              </th>
            </tr>
          </thead>
          <tbody>
            {getAllRolesSuccess &&
              (roles?.data as Roles[])
                .filter((role) => (role.roleDetails as RoleDetail[]).length > 0)
                .map((role) => (
                  <tr
                    key={role.id}
                    className="border-b border-gray-200 dark:border-gray-700 cursor-pointer"
                    onClick={() => handleRoleClick(role)}
                  >
                    <th
                      scope="row"
                      className={`${
                        roleId === role.id
                          ? "px-6 py-4 font-medium text-gray-900 bg-orange-200 dark:text-white dark:bg-gray-800"
                          : "px-6 py-4 font-medium text-gray-900 bg-gray-50 dark:text-white dark:bg-gray-800"
                      }`}
                    >
                      {role.name}
                    </th>
                    <td
                      className={`${
                        roleId === role.id
                          ? "px-6 py-4 bg-orange-300"
                          : "px-6 py-4 bg-slate-300"
                      }`}
                    >
                      {role.description}
                    </td>
                  </tr>
                ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default withAuth(RolePage, ModuleName.ROLE);
