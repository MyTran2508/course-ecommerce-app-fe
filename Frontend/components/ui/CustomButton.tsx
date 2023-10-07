import { CustomButtonProps } from "@/types";
import React from "react";

function CustomButton({
  title,
  containerStyles,
  handleClick,
}: CustomButtonProps) {
  return (
    <button
      disabled={false}
      type="button"
      className={`custom-btn ${containerStyles}`}
      onClick={handleClick}
    >
      {title}
    </button>
  );
}

export default CustomButton;
