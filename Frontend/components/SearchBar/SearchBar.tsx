"use client";
import React, { useEffect, useState } from "react";
import SearchBarManufacturer from "./SearchBarManufacturer";
import { Action } from "@/utils/resources";

function SearchBar() {
  return (
    <form className="search-bar w-[600px]">
      <div className="search-bar-items">
        <SearchBarManufacturer action={Action.SEARCH_COURSE_CLIENT} />
      </div>
    </form>
  );
}

export default SearchBar;
