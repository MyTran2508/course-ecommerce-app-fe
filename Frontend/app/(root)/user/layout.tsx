import React from "react";
import SideBar from "@/components/SideBar";

function layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex">
      <SideBar />
      {children}
    </div>
  );
}

export default layout;
