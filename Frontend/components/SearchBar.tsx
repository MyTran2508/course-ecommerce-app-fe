"use client";
import React, { useState } from "react";
import SearchBarManufacturer from "./SearchBarManufacturer";

function SearchBar() {
  const [manufacturer, setManufacturer] = useState("");
  const handleSubmit = () => {
    console.log(manufacturer);
  };

  return (
    <form className="search-bar w-96" onSubmit={handleSubmit}>
      <div className="search-bar-items">
        <SearchBarManufacturer
          manufacturer={manufacturer}
          setManufacturer={setManufacturer}
        />
      </div>
    </form>
  );
}

export default SearchBar;
