import Footer from "@/components/Footer";
import Navbar from "@/components/Navbar";
import React from "react";

function layout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <Navbar />
      {children}
      <Footer />
    </div>
  );
}

export default layout;
