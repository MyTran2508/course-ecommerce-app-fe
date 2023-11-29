import { Disclosure, RadioGroup } from "@headlessui/react";
import React, { Fragment, useEffect, useState } from "react";
import { HiChevronUp } from "react-icons/hi";
import { Language, Level, Topic } from "../utils/data";
import DiscussionFilter from "./DiscussionFilter";
import { SearchCourseRequest } from "@/types/request.type";

interface SideBarFilterProps {
  setSearchRequest: React.Dispatch<React.SetStateAction<SearchCourseRequest>>;
}

function SideBarFilter(props: SideBarFilterProps) {
  const { setSearchRequest } = props;
  const [selectedLanguages, setSelectedLanguages] = useState<string[]>([]);
  const [selectedTopics, setSelectedTopics] = useState<string[]>([]);
  const [selectedLevels, setSelectedLevels] = useState<string[]>([]);

  useEffect(() => {
    setSearchRequest((prevSearchRequest) => ({
      ...prevSearchRequest,
      languageIds: selectedLanguages,
      topicIds: selectedTopics,
      levelIds: selectedLevels,
    }));
  }, [selectedLanguages, selectedTopics, selectedLevels]);

  return (
    <div>
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
    </div>
  );
}

export default SideBarFilter;
