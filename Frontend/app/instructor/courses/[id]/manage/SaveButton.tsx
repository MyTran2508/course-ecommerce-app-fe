import { Button } from "@/components/ui/button";
import { setStatusSaveCourse } from "@/redux/features/courseSlice";
import { useAppDispatch } from "@/redux/hooks";
import React from "react";

function SaveButton() {
  const dispatch = useAppDispatch();
  const handleClickSave = () => {
    dispatch(setStatusSaveCourse(true));
  };
  return (
    <Button
      type="submit"
      className="bg-white text-black hover:bg-white"
      onClick={() => handleClickSave()}
    >
      Lưu
    </Button>
  );
}

export default SaveButton;
