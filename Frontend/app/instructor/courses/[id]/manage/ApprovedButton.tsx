import { Button } from "@/components/ui/button";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import React, { Fragment, useEffect, useState } from "react";
import Message from "./Message";

function ApprovedButton() {
  const course = useAppSelector((state) => state.courseReducer.manageCourse);
  const [isOpen, setOpen] = useState(false);
  const dispatch = useAppDispatch();

  const handleClickOpen = () => {
    setOpen(!isOpen);
  };
  useEffect(() => {
    console.log(course);
  }, [course]);
  const handleApproveCourse = () => {};
  return (
    <div>
      {isOpen ? (
        <Fragment>
          <div className="flex gap-3">
            <Button
              className="bg-orange-400 text-white hover:bg-orange-300"
              onClick={handleApproveCourse}
            >
              Chấp nhận
            </Button>
            <Message />
            <Button
              className="bg-white text-black hover:bg-red-400"
              onClick={handleClickOpen}
            >
              Hủy
            </Button>
          </div>
        </Fragment>
      ) : (
        <Fragment>
          <div className="flex gap-5">
            <Button
              className="bg-white text-black hover:bg-white"
              onClick={() => handleClickOpen()}
            >
              Xét Duyệt
            </Button>
          </div>
        </Fragment>
      )}
    </div>
  );
}

export default ApprovedButton;
