import { ComboBoxType } from "@/utils/data";
import { Disclosure } from "@headlessui/react";
import React, { Fragment } from "react";
import { FaRegStar, FaStar, FaStarHalfAlt } from "react-icons/fa";
import { HiChevronUp } from "react-icons/hi";
import { v4 as uuidv4 } from "uuid";

interface DiscussionFilterProps {
  data: ComboBoxType[];
  label: string;
  selectedList?: string[];
  setSelectedList?: React.Dispatch<React.SetStateAction<string[]>>;
  selected?: string | null;
  setSelected?: React.Dispatch<React.SetStateAction<string | null>>;
}

function DiscussionFilter(props: DiscussionFilterProps) {
  const { data, label, setSelectedList, selectedList, selected, setSelected } =
    props;

  const handleCheckboxChange = (
    event: React.ChangeEvent<HTMLInputElement>,
    itemId: string
  ) => {
    const isChecked = event.target.checked;
    if (setSelected) {
      if (isChecked) {
        setSelected(itemId);
      }
    }
    if (setSelectedList) {
      if (isChecked) {
        setSelectedList((prevItems) => [...prevItems, itemId]);
      } else {
        setSelectedList((prevItems) => prevItems.filter((id) => id !== itemId));
      }
    }
  };

  return (
    <div>
      <Disclosure
        defaultOpen={(selectedList?.length !== 0 && selectedList) || label == "Ratings" || label =="Language" ? true : false}
      >
        {({ open }) => (
          <>
            <Disclosure.Button className="border-t flex w-full justify-between py-4 text-left text-sm font-medium focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75 border-back">
              <div className="flex flex-between w-full">
                <span className="text-md ">{label}</span>
                <HiChevronUp
                  className={`${open ? "rotate-180 transform" : ""} h-5 w-5`}
                />
              </div>
            </Disclosure.Button>
            <div className="text-sm">
              {open ? (
                <Fragment>
                  <div className="mb-6">
                    {data.map((item) => {
                      const checkId = uuidv4();
                      return (
                        <div
                          key={item.id}
                          className="flex item-center gap-3 mb-3"
                        >
                          <input
                            type={setSelected ? "radio" : "checkbox"}
                            id={checkId}
                            value={item.id}
                            checked={
                              (selectedList &&
                                selectedList.includes(item.id)) ||
                              selected === item.id
                            }
                            onChange={(event) =>
                              handleCheckboxChange(event, item.id)
                            }
                            style={{}}
                            className={`peer h-4 w-4shrink-0 rounded-sm border border-primary ring-offset-background focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 data-[state=checked]:bg-primary data-[state=checked]:text-primary-foreground items-center`}
                          />
                          <label htmlFor={checkId} className="flex gap-1">
                            {label === "Ratings" ? (
                              <Fragment>
                                {[...Array(5)].map((_, index) => {
                                  const star = Math.floor(Number(item.name));
                                  const halfStar = Number(item.name) - star;
                                  if (index < star) {
                                    return (
                                      <FaStar key={index} fill={"#FF8C00"} />
                                    );
                                  } else if (index === star && halfStar > 0) {
                                    return (
                                      <FaStarHalfAlt
                                        key={index}
                                        fill={"#FF8C00"}
                                      />
                                    );
                                  } else {
                                    return (
                                      <FaRegStar key={index} fill={"#FF8C00"} />
                                    );
                                  }
                                })}
                                {item.name + " & up"}
                              </Fragment>
                            ) : (
                              <Fragment>{item.name}</Fragment>
                            )}
                          </label>
                        </div>
                      );
                    })}
                  </div>
                </Fragment>
              ) : null}
            </div>
          </>
        )}
      </Disclosure>
    </div>
  );
}

export default DiscussionFilter;
