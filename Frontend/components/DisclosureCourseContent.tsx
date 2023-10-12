import { Disclosure } from "@headlessui/react";
import { useEffect, useState } from "react";
import { HiChevronUp } from "react-icons/hi";

function DisclosureCourseContent(props: any) {
  useEffect(() => {}, [props.openAll]);

  return (
    <div className="mb-2">
      <Disclosure defaultOpen={props.openAll}>
        {({ open }) => (
          <>
            <Disclosure.Button className="flex w-full justify-between rounded-lg bg-gray-100 px-2 py-4 text-left text-sm font-medium hover:bg-orange-200 focus:outline-none focus-visible:ring focus-visible:ring-purple-500 focus-visible:ring-opacity-75">
              <div className="flex-start gap-2">
                <HiChevronUp
                  className={`${
                    open ? "rotate-180 transform" : ""
                  } h-5 w-5 text-orange-500`}
                />
                <span className="text-sm">1. Giới Thiệu</span>
              </div>
              <div>3 bài học</div>
            </Disclosure.Button>
            <div className="text-sm text-gray-500 bg-gray-50">
              {open ? (
                <>
                  <div className="px-2 pt-4 pb-2 pl-10">
                    <Disclosure.Panel>1. Giới thiệu khóa học</Disclosure.Panel>
                  </div>
                  <hr className="pl-10 ml-11" />
                  <div className="px-2 pt-4 pb-2 bg-gray-50 pl-10">
                    <Disclosure.Panel>2. Cài đặt Dev - C++</Disclosure.Panel>
                  </div>
                </>
              ) : null}
            </div>
          </>
        )}
      </Disclosure>
    </div>
  );
}

export default DisclosureCourseContent;
