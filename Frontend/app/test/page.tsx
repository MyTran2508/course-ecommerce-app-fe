"use client";
import React, { useState, useRef } from "react";
import Image from "next/image";

const VideoOverlay = () => {
  const [isVideoVisible, setIsVideoVisible] = useState(false);
  const iframeRef = useRef<HTMLIFrameElement | null>(null);

  const toggleVideo = () => {
    if (iframeRef.current) {
      iframeRef.current.src =
        "https://www.youtube.com/embed/IIvoiO7Zujk?modestbranding=1&autohide=1&showinfo=0&controls=1&rel=0&iv_load_policy=3";
      setIsVideoVisible(true);
    }
  };

  return (
    <div className="relative min-h-[500px]">
      {isVideoVisible ? (
        <iframe
          ref={iframeRef}
          src=""
          className="w-full h-full min-h-[600px]"
          allow="autoplay; encrypted-media"
        ></iframe>
      ) : (
        <div className="relative">
          <Image
            src="/next.svg"
            alt="Hình ảnh"
            width={400}
            height={400}
            className="top-0 left-0 w-1/3 h-1/3 cursor-pointer fixed"
            onClick={toggleVideo}
          />
          <div className="overlay absolute top-0 left-0 w-full h-full bg-black bg-opacity-50 z-10 flex items-center justify-center">
            <div className="text-white">Click to Play</div>
          </div>
        </div>
      )}
    </div>
  );
};

export default VideoOverlay;
