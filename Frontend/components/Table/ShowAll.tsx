import React, { use, useEffect, useRef, useState } from "react";

interface ShowAllProps {
  row: any;
}
function ShowAll(props: ShowAllProps) {
  const { row } = props;
  const [isExpanded, setIsExpanded] = useState(false);
  const [isOverflowing, setIsOverflowing] = useState(false);
  const description = row.getValue("description");
  const textRef = useRef<HTMLDivElement>(null);

  const toggleExpand = () => {
    if (!isOverflowing) return;
    setIsExpanded(!isExpanded);
  };

  useEffect(() => {
    if (textRef.current) {
      const heightDiv = textRef.current.offsetHeight;
      if (heightDiv > 20) {
        setIsOverflowing(true);
      }
    }
  }, [textRef]);

  if (description) {
    return (
      <div
        className={`capitalize ${!isExpanded ? " line-clamp-2 " : ""} ${
          isOverflowing ? "hover:cursor-pointer" : ""
        }`}
        onClick={toggleExpand}
        title={
          isOverflowing
            ? isExpanded
              ? "Click to collapse"
              : "Click to expand"
            : ""
        }
        ref={textRef}
      >
        {(description as string).split("\\n").map((part, index) => (
          <div key={index}>{part || <i>None</i>}</div>
        ))}
      </div>
    );
  }

  return (
    <div className="capitalize">
      <i>None</i>
    </div>
  );
}

export default ShowAll;
