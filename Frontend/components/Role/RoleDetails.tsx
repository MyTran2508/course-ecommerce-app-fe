import { RoleDetail } from "@/types/roles.type";
import {
  Action,
  ModuleName,
  PermissionName,
  RoleUser,
} from "@/utils/resources";
import React, { useEffect, useState } from "react";

interface RoleDetailsProps {
  roleDetails: RoleDetail[];
  setRoleDetails: (roleDetails: any) => void;
  action: Action;
  isCreate: boolean;
  selectRoleParent?: string;
}

function RoleDetails(props: RoleDetailsProps) {
  const { roleDetails, setRoleDetails, action, isCreate, selectRoleParent } =
    props;

  useEffect(() => {
    setRoleDetails(roleDetails);
  }, [roleDetails]);

  const handleCheckboxChange =
    (value: number, type: string) =>
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setRoleDetails((prevState: RoleDetail[]) => {
        const existingRoleDetail = prevState.find(
          (roleDetail) => roleDetail.module?.id == value
        );

        if (existingRoleDetail) {
          return prevState.map((roleDetail) => {
            if (roleDetail.module?.id == value) {
              return {
                ...roleDetail,
                [type]: event.target.checked,
              };
            }
            return roleDetail;
          });
        } else {
          return [
            ...prevState,
            {
              canCreate: false,
              canDelete: false,
              canApproveCourse: false,
              canAssignment: false,
              canUpdate: false,
              canView: false,
              canRemove: false,
              module: {
                id: value,
              },
              [type]: event.target.checked,
            },
          ];
        }
      });
    };
  return (
    <div>
      <div className="flex flex-col">
        <div className="overflow-x-auto sm:-mx-6 lg:-mx-8">
          <div className="inline-block min-w-full py-2 sm:px-6 lg:px-8">
            <div className="overflow-hidden">
              <table className="min-w-full text-left text-sm font-light text-surface dark:text-white">
                <tbody>
                  {Object.values(ModuleName).map((value, index) => {
                    let isUserRoleManager = false;
                    if (
                      selectRoleParent &&
                      selectRoleParent == RoleUser.MANAGER &&
                      (value === ModuleName.ROLE || value == ModuleName.USER)
                    ) {
                      isUserRoleManager = true;
                    }

                    return (
                      <tr
                        key={value}
                        className="border-b border-neutral-200 dark:border-white/10 "
                      >
                        <td className="whitespace-nowrap px-6 py-4 font-bold">
                          {value}
                        </td>
                        <td className="whitespace-nowrap px-6 py-4">
                          <div className="flex gap-2">
                            <input
                              id={PermissionName.CAN_VIEW + value}
                              type="checkbox"
                              checked={
                                roleDetails.find(
                                  (roleDetail) => roleDetail.module?.id == index
                                )?.canView || false
                              }
                              onChange={handleCheckboxChange(
                                index,
                                PermissionName.CAN_VIEW
                              )}
                              disabled={
                                !isCreate ||
                                isUserRoleManager ||
                                selectRoleParent == " "
                              }
                            />
                            <label
                              htmlFor={PermissionName.CAN_VIEW + value}
                              className="hover:cursor-pointer"
                            >
                              Xem
                            </label>
                          </div>
                        </td>
                        {!(
                          value == ModuleName.ORDER ||
                          value == ModuleName.STATISTIC ||
                          value == ModuleName.USER_LOG
                        ) && (
                          <div className="flex">
                            {value == ModuleName.COURSE_ADMIN ? (
                              <div>
                                <td className="whitespace-nowrap px-6 py-4 pr-[32px]">
                                  <div className="flex gap-2">
                                    <input
                                      id={
                                        PermissionName.CAN_APPROVE_COURSE +
                                        value
                                      }
                                      type="checkbox"
                                      checked={
                                        roleDetails.find(
                                          (roleDetail) =>
                                            roleDetail.module?.id == index
                                        )?.canApproveCourse || false
                                      }
                                      disabled={
                                        !isCreate ||
                                        isUserRoleManager ||
                                        selectRoleParent == " "
                                      }
                                      onChange={handleCheckboxChange(
                                        index,
                                        PermissionName.CAN_APPROVE_COURSE
                                      )}
                                    />
                                    <label
                                      htmlFor={
                                        PermissionName.CAN_APPROVE_COURSE +
                                        value
                                      }
                                      className="hover:cursor-pointer"
                                    >
                                      Phê duyệt khóa học
                                    </label>
                                  </div>
                                </td>
                              </div>
                            ) : (
                              <div className="flex">
                                <td className="whitespace-nowrap px-6 py-4 ">
                                  <div className="flex gap-2">
                                    <input
                                      id={PermissionName.CAN_CREATE + value}
                                      type="checkbox"
                                      checked={
                                        roleDetails.find(
                                          (roleDetail) =>
                                            roleDetail.module?.id == index
                                        )?.canCreate || false
                                      }
                                      disabled={
                                        !isCreate ||
                                        isUserRoleManager ||
                                        selectRoleParent == " "
                                      }
                                      onChange={handleCheckboxChange(
                                        index,
                                        PermissionName.CAN_CREATE
                                      )}
                                    />
                                    <label
                                      htmlFor={
                                        PermissionName.CAN_CREATE + value
                                      }
                                      className="hover:cursor-pointer"
                                    >
                                      Thêm
                                    </label>
                                  </div>
                                </td>
                                <td className="whitespace-nowrap px-6 py-4 ">
                                  <div className="flex gap-2">
                                    <input
                                      id={PermissionName.CAN_UPDATE + value}
                                      type="checkbox"
                                      checked={
                                        roleDetails.find(
                                          (roleDetail) =>
                                            roleDetail.module?.id == index
                                        )?.canUpdate || false
                                      }
                                      disabled={
                                        !isCreate ||
                                        isUserRoleManager ||
                                        selectRoleParent == " "
                                      }
                                      onChange={handleCheckboxChange(
                                        index,
                                        PermissionName.CAN_UPDATE
                                      )}
                                    />
                                    <label
                                      htmlFor={
                                        PermissionName.CAN_UPDATE + value
                                      }
                                      className="hover:cursor-pointer"
                                    >
                                      Sửa
                                    </label>
                                  </div>
                                </td>
                              </div>
                            )}
                            {value == ModuleName.COURSE_MANAGER && (
                              <td className="whitespace-nowrap px-6 py-4">
                                <div className="flex gap-2">
                                  <input
                                    id={PermissionName.CAN_ASSIGNMENT + value}
                                    type="checkbox"
                                    checked={
                                      roleDetails.find(
                                        (roleDetail) =>
                                          roleDetail.module?.id == index
                                      )?.canAssignment || false
                                    }
                                    disabled={
                                      !isCreate ||
                                      isUserRoleManager ||
                                      selectRoleParent == " "
                                    }
                                    onChange={handleCheckboxChange(
                                      index,
                                      PermissionName.CAN_ASSIGNMENT
                                    )}
                                  />
                                  <label
                                    htmlFor={
                                      PermissionName.CAN_ASSIGNMENT + value
                                    }
                                    className="hover:cursor-pointer"
                                  >
                                    Chấm bài tự luận
                                  </label>
                                </div>
                              </td>
                            )}
                            {!(
                              value == ModuleName.COURSE_ADMIN ||
                              value == ModuleName.COURSE_MANAGER
                            ) && (
                              <td className="whitespace-nowrap px-6 py-4">
                                <div className="flex gap-2">
                                  <input
                                    id={PermissionName.CAN_REMOVE + value}
                                    type="checkbox"
                                    checked={
                                      roleDetails.find(
                                        (roleDetail) =>
                                          roleDetail.module?.id == index
                                      )?.canRemove || false
                                    }
                                    disabled={
                                      !isCreate ||
                                      isUserRoleManager ||
                                      selectRoleParent == " "
                                    }
                                    onChange={handleCheckboxChange(
                                      index,
                                      PermissionName.CAN_REMOVE
                                    )}
                                  />
                                  <label
                                    htmlFor={PermissionName.CAN_REMOVE + value}
                                    className="hover:cursor-pointer"
                                  >
                                    Xóa
                                  </label>
                                </div>
                              </td>
                            )}
                          </div>
                        )}
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RoleDetails;
