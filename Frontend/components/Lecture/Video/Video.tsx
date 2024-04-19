import { Input } from "@/components/ui/input";
import { useFileHooks } from "@/redux/hooks/courseHooks/fileHooks";
import { Lecture } from "@/types/section.type";
import React, { Fragment, useEffect, useState } from "react";
import { handleGetDurationFormVideo } from "@/utils/function";
import { useLectureHooks } from "@/redux/hooks/courseHooks/lectureHooks";
import Loading from "@/app/(root)/user/personal/loading";

interface VideoComponentProps {
  lecture: Lecture;
  onDisplay?: (isSelectType: boolean) => void;
}

function VideoComponent(props: VideoComponentProps) {
  const { lecture, onDisplay } = props;
  const [isUpload, setUpload] = useState(false);
  const { handleUploadFilesForSection } = useFileHooks();
  const { handleUpdateLecture } = useLectureHooks();

  const handleVideoChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    const videoDuration = await handleGetDurationFormVideo(file as File);
    if (file) {
      setUpload(true);
      const response = await handleUploadFilesForSection([file]);
      if (response) {
        const url = (response.data as string[])[0];
        lecture.url = url;
        lecture.fileName = file.name;
        lecture.videoDuration = videoDuration;
        await handleUpdateLecture(lecture);
        onDisplay && onDisplay(false);
        setUpload(false);
      }
    }
  };

  return (
    <div>
      <h4 className="font-bold">Vui lòng chọn video:</h4>
      <div className="flex gap-2">
        <strong>Lưu ý: </strong>
        <p className="mb-5">Video phải có định dạng .mp4</p>
      </div>
      {isUpload ? (
        <Fragment>
          <div className="flex gap-2 items-center">
            <Loading />
            <i>Video đang được tải lên</i>
          </div>
        </Fragment>
      ) : (
        <Input
          className="px-5"
          type="file"
          accept=".mp4"
          placeholder="Video URL"
          onChange={handleVideoChange}
        />
      )}
    </div>
  );
}

export default VideoComponent;
