import SideBarAssignment from "@/components/SideBar/SideBarAssignment";
import Footer from "@/components/Footer/Footer";
import React, { Fragment, Suspense } from "react";
import NavbarAssignment from "@/components/Navbar/NavbarAssignment";

function layout({ children }: { children: React.ReactNode }) {
  return (
    <Fragment>
      <NavbarAssignment />
      <div className="flex gap-5 mx-10">
        <SideBarAssignment />
        <div className="order-2 w-full">{children}</div>
      </div>
      <Footer />
    </Fragment>
  );
}

export default layout;
