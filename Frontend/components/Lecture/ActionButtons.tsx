import React from "react";
import { IoIosMenu } from "react-icons/io";
import { MdDelete, MdModeEditOutline } from "react-icons/md";

interface ActionButtonsProps {
  handleEdit?: (isEdit: boolean) => void;
  handleDelete?: (isDelete: boolean) => void;
  handleMenu?: () => void;
  attributes?: any;
  listeners?: any;
}
function ActionButtons(props: ActionButtonsProps) {
  const { handleEdit, handleDelete, handleMenu, attributes, listeners } = props;

  return (
    <div className="hidden group-hover:block flex-grow">
      <div className="flex justify-between items-end">
        <div className="flex gap-4">
          <MdModeEditOutline
            className={"text-xl hidden group-hover:block "}
            onClick={() => {
              handleEdit && handleEdit(true);
            }}
          />
          <MdDelete
            className={"text-xl hidden group-hover:block"}
            onClick={() => handleDelete && handleDelete(true)}
          />
        </div>
        <div {...attributes} {...listeners}>
          <IoIosMenu className={"text-xl hidden group-hover:block"} />
        </div>
      </div>
    </div>
  );
}

export default ActionButtons;
