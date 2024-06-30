import React from "react";
import SideBar from "./SideBar";
import NotificationPopUp from "@/components/Notification/Notification";

function layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex relative">
      <SideBar />
      {children}
      <div className="absolute right-5 top-5">
        <NotificationPopUp isAdmin={true} />
      </div>
    </div>
  );
}

export default layout;
