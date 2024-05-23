"use client";
import RoleDetails from "@/components/Role/RoleDetails";
import {
  useAddRoleMutation,
  useGetAllRoleQuery,
  useUpdateRoleMutation,
} from "@/redux/services/roleApi";
import { DataResponse } from "@/types/response.type";
import { RoleDetail, Roles } from "@/types/roles.type";
import { Action, ToastMessage, ToastStatus } from "@/utils/resources";
import showToast from "@/utils/showToast";
import { set } from "lodash";
import React, { useEffect, useState } from "react";
import { FaPen } from "react-icons/fa";
import { IoMdAddCircle } from "react-icons/io";
import { IoReloadCircleOutline } from "react-icons/io5";
import { MdDelete } from "react-icons/md";

function RolePage() {
  const [roleDetails, setRoleDetails] = useState<RoleDetail[]>([]);
  const [roleName, setRoleName] = useState<string>("");
  const [roleDescription, setRoleDescription] = useState<string>("");
  const [roleId, setRoleId] = useState<string>();
  const { data: roles, isSuccess: getAllRolesSuccess } = useGetAllRoleQuery();
  const [addRoles] = useAddRoleMutation();
  const [updateRoles] = useUpdateRoleMutation();

  useEffect(() => {
    console.log(roles?.data as Roles[]);
  }, [roles]);

  const handleRoleClick = (role: Roles) => {
    setRoleId(role.id as string);
    setRoleName(role.name as string);
    setRoleDescription(role.description as string);
    setRoleDetails(role.roleDetails as RoleDetail[]);
  };

  const handleAction = (action: Action) => {
    if (roleName === "" && roleDescription === "" && roleDetails.length === 0) {
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
      }).then((res) => {
        console.log(res);
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
  };

  return (
    <div className="w-full px-10 flex gap-5 mt-5">
      <div>
        {" "}
        <div className="flex gap-5 justify-around p-2 bg-slate-200 rounded-sm ">
          <div className="flex gap-2 text-center">
            <p className="mb-2 text-sm font-medium text-gray-900 dark:text-white text-center flex items-center justify-center">
              Tên Quyền
            </p>
            <input
              type="text"
              id="first_name"
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-1 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Nhập tên quyền"
              value={roleName}
              onChange={(e) => setRoleName(e.target.value)}
              required
            />
          </div>
          <div className="flex gap-2 text-center">
            <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white w-max">
              Mô Tả
            </label>
            <input
              type="text"
              id="last_name"
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-1 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Nhập mô tả"
              value={roleDescription}
              onChange={(e) => setRoleDescription(e.target.value)}
              required
            />
          </div>
        </div>
        <RoleDetails
          roleDetails={roleDetails}
          setRoleDetails={setRoleDetails}
          action={Action.CREATE}
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
      <div className="flex w-full border border-black rounded-sm flex-col p-2">
        <div className="flex justify-center">
          <h1 className="font-bold text-lg">Quyền</h1>
        </div>
        <div>
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
                  .filter(
                    (role) => (role.roleDetails as RoleDetail[]).length > 0
                  )
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
    </div>
  );
}

export default RolePage;
