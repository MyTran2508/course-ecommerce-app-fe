import Footer from "@/components/Footer";
import Navbar from "@/components/Navbar";
import React from "react";
import { Providers } from "@/redux/provider";

function layout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <Navbar />
      <Providers>{children}</Providers>
      <Footer />
    </div>
  );
}

export default layout;
