import { Disclosure, RadioGroup } from "@headlessui/react";
import React, { Fragment, useEffect, useState } from "react";
import {
  Language,
  Level,
  Price,
  Topic,
  Rating,
  VideoDuration,
} from "../../utils/data";
import DiscussionFilter from "../Discussion/DiscussionFilter";
import { SearchCourseRequest } from "@/types/request.type";

interface SideBarFilterProps {
  setSearchRequest: React.Dispatch<React.SetStateAction<SearchCourseRequest>>;
  setPage: (page: number) => void;
  topicId?: string;
}

function SideBarFilter(props: SideBarFilterProps) {
  const { setSearchRequest, setPage, topicId } = props;
  const [selectedLanguages, setSelectedLanguages] = useState<string[]>([]);
  const [selectedTopics, setSelectedTopics] = useState<string[]>(
    topicId ? [topicId] : []
  );
  const [selectedLevels, setSelectedLevels] = useState<string[]>([]);
  const [selectedPriceIsFree, setSelectedPriceIsFree] = useState<string[]>([]);
  const [selectedRating, setSelectedRating] = useState<string | null>(null);
  const [selectedDuration, setSelectedDuration] = useState<string | null>(null);

  useEffect(() => {
    setSearchRequest((prevSearchRequest) => ({
      ...prevSearchRequest,
      languageIds: selectedLanguages,
      topicIds: selectedTopics,
      levelIds: selectedLevels,
      ratingsLevel: selectedRating,
      videoDuration: selectedDuration,
      pageIndex: 0,
    }));
    setPage(1);
  }, [
    selectedLanguages,
    selectedTopics,
    selectedLevels,
    selectedRating,
    selectedDuration,
  ]);

  useEffect(() => {
    if (selectedPriceIsFree.length === 0 || selectedPriceIsFree.length === 2) {
      setSearchRequest((prevSearchRequest) => ({
        ...prevSearchRequest,
        isFree: null,
        pageIndex: 0,
      }));
      return;
    }
    if (selectedPriceIsFree[0] === "Free") {
      setSearchRequest((prevSearchRequest) => ({
        ...prevSearchRequest,
        isFree: true,
        pageIndex: 0,
      }));
    } else {
      setSearchRequest((prevSearchRequest) => ({
        ...prevSearchRequest,
        isFree: false,
        pageIndex: 0,
      }));
    }
    setPage(1);
  }, [selectedPriceIsFree]);

  return (
    <div className="p-1">
      <DiscussionFilter
        data={Rating}
        selected={selectedRating}
        label={"Ratings"}
        setSelected={setSelectedRating}
      />
      <DiscussionFilter
        data={Language}
        selectedList={selectedLanguages}
        label={"Language"}
        setSelectedList={setSelectedLanguages}
      />
      <DiscussionFilter
        data={Level}
        selectedList={selectedLevels}
        label={"Level"}
        setSelectedList={setSelectedLevels}
      />
      <DiscussionFilter
        data={Topic}
        selectedList={selectedTopics}
        label={"Topic"}
        setSelectedList={setSelectedTopics}
      />
      <DiscussionFilter
        data={Price}
        selectedList={selectedPriceIsFree}
        label={"Price"}
        setSelectedList={setSelectedPriceIsFree}
      />
      <DiscussionFilter
        data={VideoDuration}
        selected={selectedDuration}
        label={"Video Duration"}
        setSelected={setSelectedDuration}
      />
    </div>
  );
}

export default SideBarFilter;
