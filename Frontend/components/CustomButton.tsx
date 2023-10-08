import { CustomButtonProps } from "@/types";
import { iconMap } from "@/utils/map";
import React from "react";

function CustomButton({
  title,
  containerStyles,
  handleClick,
  icon,
  iconStyles,
}: CustomButtonProps) {
  return (
    <button
      disabled={false}
      type="button"
      className={`custom-btn ${containerStyles}`}
      onClick={handleClick}
    >
      {icon &&
        iconMap[icon] &&
        React.createElement(iconMap[icon], {
          className: iconStyles,
        })}
      {title}
    </button>
  );
}

export default CustomButton;
