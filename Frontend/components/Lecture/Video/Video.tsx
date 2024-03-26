import { Input } from "@/components/ui/input";
import React, { useState } from "react";

function VideoComponent() {
  const [isUpload, setUpload] = useState(false);
  const handleVideoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      console.log(file);
      setUpload(true);
      handleUploadVideo(file);
    }
  };

  const handleUploadVideo = (file: File) => {
    setTimeout(() => {
      setUpload(false);
    }, 5000);
    // Your additional code here
  };

  return (
    <div>
      <h4 className="font-bold">Vui lòng chọn video:</h4>
      <div className="flex gap-2">
        <strong>Lưu ý: </strong>
        <p className="mb-5">Video phải có định dạng .mp4</p>
      </div>
      {isUpload && <p>Video đang được tải lên</p>}
      <Input
        className="px-5"
        type="file"
        accept=".mp4"
        placeholder="Video URL"
        onChange={handleVideoChange}
      />
    </div>
  );
}

export default VideoComponent;
