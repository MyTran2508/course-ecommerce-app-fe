import { RoleDetail } from "@/types/roles.type";
import { Action, ModuleName, Permission } from "@/utils/resources";
import React, { useEffect, useState } from "react";

interface RoleDetailsProps {
  roleDetails: RoleDetail[];
  setRoleDetails: (roleDetails: any) => void;
  action: Action;
}

function RoleDetails(props: RoleDetailsProps) {
  const { roleDetails, setRoleDetails, action } = props;
  //   const [roleDetails, setRoleDetails] = useState<RoleDetail[]>(roleDetails);

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
              canStatistic: false,
              canUpdate: false,
              canView: false,
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
                              id={Permission.CAN_VIEW + value}
                              type="checkbox"
                              checked={
                                roleDetails.find(
                                  (roleDetail) => roleDetail.module?.id == index
                                )?.canView || false
                              }
                              onChange={handleCheckboxChange(
                                index,
                                Permission.CAN_VIEW
                              )}
                            />
                            <label
                              htmlFor={Permission.CAN_VIEW + value}
                              className="hover:cursor-pointer"
                            >
                              Xem
                            </label>
                          </div>
                        </td>
                        <td className="whitespace-nowrap px-6 py-4 ">
                          <div className="flex gap-2">
                            <input
                              id={Permission.CAN_CREATE + value}
                              type="checkbox"
                              checked={
                                roleDetails.find(
                                  (roleDetail) => roleDetail.module?.id == index
                                )?.canCreate || false
                              }
                              onChange={handleCheckboxChange(
                                index,
                                Permission.CAN_CREATE
                              )}
                            />
                            <label
                              htmlFor={Permission.CAN_UPDATE + value}
                              className="hover:cursor-pointer"
                            >
                              Thêm
                            </label>
                          </div>
                        </td>
                        <td className="whitespace-nowrap px-6 py-4">
                          <div className="flex gap-2">
                            <input
                              id={Permission.CAN_REMOVE + value}
                              type="checkbox"
                              checked={
                                roleDetails.find(
                                  (roleDetail) => roleDetail.module?.id == index
                                )?.canRemove || false
                              }
                              onChange={handleCheckboxChange(
                                index,
                                Permission.CAN_REMOVE
                              )}
                            />
                            <label
                              htmlFor={Permission.CAN_REMOVE + value}
                              className="hover:cursor-pointer"
                            >
                              Xóa
                            </label>
                          </div>
                        </td>
                        <td className="whitespace-nowrap px-6 py-4 ">
                          <div className="flex gap-2">
                            <input
                              id={Permission.CAN_UPDATE + value}
                              type="checkbox"
                              checked={
                                roleDetails.find(
                                  (roleDetail) => roleDetail.module?.id == index
                                )?.canUpdate || false
                              }
                              onChange={handleCheckboxChange(
                                index,
                                Permission.CAN_UPDATE
                              )}
                            />
                            <label
                              htmlFor={Permission.CAN_UPDATE + value}
                              className="hover:cursor-pointer"
                            >
                              Sửa
                            </label>
                          </div>
                        </td>
                        <td className="whitespace-nowrap px-6 py-4 ">
                          <div className="flex gap-2">
                            <input
                              id={Permission.CAN_STATISTICS + value}
                              type="checkbox"
                              checked={
                                roleDetails.find(
                                  (roleDetail) => roleDetail.module?.id == index
                                )?.canStatistics || false
                              }
                              onChange={handleCheckboxChange(
                                index,
                                Permission.CAN_STATISTICS
                              )}
                            />
                            <label
                              htmlFor={Permission.CAN_STATISTICS + value}
                              className="hover:cursor-pointer"
                            >
                              Thống kê
                            </label>
                          </div>
                        </td>
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
