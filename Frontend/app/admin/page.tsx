"use client";
import withAuth from "@/hoc/withAuth";
import { ModuleName } from "@/utils/resources";
import Image from "next/image";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";

function AdminPage() {
  return (
    <div className="flex flex-col items-center h-screen pt-[100px] bg-gray-100 w-full">
      <div className="grid grid-cols-4 gap-4 mb-4">
        <Image
          src="https://images.unsplash.com/photo-1517180102446-f3ece451e9d8"
          alt="Admin"
          width={200}
          height={200}
          className="rounded-md"
        />
        <Image
          src="https://images.unsplash.com/photo-1521575107034-e0fa0b594529"
          alt="Admin"
          width={200}
          height={200}
          className="rounded-md"
        />
        <Image
          src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f"
          alt="Admin"
          width={200}
          height={200}
          className="rounded-md"
        />
        <Image
          src="https://images.unsplash.com/photo-1517694712202-14dd9538aa97"
          alt="Admin"
          width={200}
          height={200}
          className="rounded-md"
        />
      </div>
      <h1 className="text-3xl font-bold mb-2">
        Welcome to the Admin Dashboard
      </h1>
      <p className="text-gray-700">
        Manage and organize your application data efficiently.
      </p>
    </div>
  );
}

export default withAuth(AdminPage, ModuleName.ADMIN_PAGE);
