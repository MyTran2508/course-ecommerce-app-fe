import React from "react";

function ForbiddenPage() {
  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100 w-full">
      <h1 className="text-6xl font-bold text-red-600">403</h1>
      <h2 className="text-3xl font-bold mb-2">Forbidden</h2>
      <p className="text-gray-700">
        Sorry, you do not have access to this page.
      </p>
    </div>
  );
}

export default ForbiddenPage;
