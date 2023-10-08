import Link from "next/link";
import React from "react";

function Footer() {
  return (
    <footer className="flex justify-between border-t-2 p-2 max-md:flex-col ">
      <p>Copy right @ 2023 | ALL Right Reserved</p>
      <div className="flex gap-x-10">
        <Link href={"/"}>Terms & Conditions</Link>
        <Link href={"/"}>Privacy Policy</Link>
      </div>
    </footer>
  );
}

export default Footer;
