import React, { useState } from "react";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { toast } from "react-toastify";
import {
  Constant,
  LectureType,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import showToast from "@/utils/showToast";
import { useAddSectionMutation } from "@/redux/services/contentApi";
import { Question, Section } from "@/types/section.type";

interface CreateTitleProps {
  handleIsOpen?: (isOpen: boolean) => void;
  type: string;
  parentId: string;
}

function CreateTitle(props: CreateTitleProps) {
  const { handleIsOpen, type, parentId } = props;
  const [name, setName] = useState<string>();
  const [addSection] = useAddSectionMutation();
  console.log(type);

  const handleSubmit = () => {
    if (name) {
      if (type === Constant.SECTION) {
        const newSection: Section = {
          name: name,
          ordinalNumber: 1,
          lectures: [],
          content: {
            id: parentId,
          },
        };
        handleAddSection(newSection);
      }
    } else {
      showToast(ToastStatus.WARNING, "Vui lòng điền đầy đủ thông tin");
    }
  };

  const handleAddSection = async (newSection: Section) => {
    await addSection(newSection)
      .unwrap()
      .then((fulfilled) => {
        console.log(fulfilled);
        // handleToast(fulfilled);
        // setCreated([]);
      });
  };
  return (
    <div className=" bg-slate-200 p-2 border-2">
      <div className="flex items-center gap-1 ">
        <div className="w-max">{type + " MỚI: "}</div>
        <Input onChange={(e) => setName(e.target.value)}></Input>
      </div>
      <div className="flex-end mt-2 gap-2">
        <Button
          className="bg-slate-200 hover:bg-slate-200 text-black"
          onClick={() => handleIsOpen?.(false)}
        >
          Hủy
        </Button>
        <Button onClick={() => handleSubmit()}>Thêm {type}</Button>
      </div>
    </div>
  );
}

export default CreateTitle;
