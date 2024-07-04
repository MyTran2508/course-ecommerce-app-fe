import Footer from "@/components/Footer/Footer";
import Navbar from "@/components/Navbar/Navbar";

import React, { Fragment, Suspense } from "react";
import Loading from "./user/personal/loading";

function layout({ children }: { children: React.ReactNode }) {
  return (
    <Fragment>
      <Navbar />
      <Suspense fallback={<Loading />}>{children}</Suspense>
      <Footer />
    </Fragment>
  );
}

export default layout;
