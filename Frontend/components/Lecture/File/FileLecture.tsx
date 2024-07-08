import { Disclosure } from "@headlessui/react";
import React, { Fragment, use, useEffect, useState } from "react";
import { HiChevronUp } from "react-icons/hi";
import { IoIosDocument, IoIosMenu, IoMdDownload } from "react-icons/io";
import { Button } from "../../ui/button";
import { IoAddOutline } from "react-icons/io5";
import UploadFile from "./UploadFile";
import { Lecture } from "@/types/section.type";
import { MdOutlineOndemandVideo } from "react-icons/md";
import ActionButtons from "../ActionButtons";
import CreateTitle from "../CreateTitle";
import {
  Action,
  LectureType,
  ModuleName,
  PermissionName,
  Role,
  RoleUser,
  ToastMessage,
  ToastStatus,
} from "@/utils/resources";
import { useLectureHooks } from "@/redux/hooks/courseHooks/lectureHooks";
import { useSectionHooks } from "@/redux/hooks/courseHooks/sectionHooks";
import InputEditor from "@/components/Input/InputEditor";
import { stateToHTML } from "draft-js-export-html";
import { EditorState, convertFromRaw } from "draft-js";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { isPermissionGranted } from "@/utils/function";
import { RoleDetail } from "@/types/roles.type";
import showToast from "@/utils/showToast";
interface FileLectureProps {
  data: Lecture;
  index: number;
  attributes?: any;
  listeners?: any;
  type: LectureType;
  canCreate?: boolean;
  canUpdate?: boolean;
}

function FileLecture(props: FileLectureProps) {
  const { data, index, attributes, listeners, type, canCreate, canUpdate } =
    props;
  const [isSelectContentType, setSelectContentType] = useState(false);
  const [isOpenInputEditor, setIsOpenInputEditor] = useState(false);
  const [description, setDescription] = useState<string>(
    data.description || ""
  );
  const [isEdit, setEdit] = useState(false);
  const [isDelete, setDelete] = useState(false);
  const { handleGetFileDocument } = useSectionHooks();
  const [document, setDocument] = useState<any>(null);
  const { handleUpdateLecture } = useLectureHooks();
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const roleDetail = role?.roleDetails;

  useEffect(() => {
    const fetchData = async () => {
      if (type === LectureType.DOCUMENT && data.url) {
        const documentData = await handleGetFileDocument(data.url as string);
        setDocument(documentData);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    if (isDelete) {
      data.ordinalNumber = -1;
      handleUpdateLecture(data);
      setDelete(false);
    }
  }, [isDelete]);

  useEffect(() => {
    // console.log(description, data.description);
    if (description !== data.description) {
      data.description = description;
      handleUpdateLecture(data);
    }
  }, [description]);

  const handleSelectType = (close: boolean = false) => {
    setSelectContentType(!isSelectContentType);
  };

  const handleCreateDescription = () => {
    setIsOpenInputEditor(true);
  };

  return (
    <div className="relative">
      {isEdit ? (
        <CreateTitle
          type={type}
          action={Action.UPDATE}
          data={data}
          handleIsOpen={setEdit}
        />
      ) : (
        <Fragment>
          <div
            className={
              "flex w-full border border-black bg-white px-2 py-4 text-sm font-medium"
            }
          >
            <div className="flex gap-4 group w-full">
              <div className="flex w-max gap-2">
                <p>
                  <strong>{index}.</strong>{" "}
                  {type === LectureType.VIDEO ? "Video" : "Document"}
                </p>
                {type === LectureType.VIDEO ? (
                  <MdOutlineOndemandVideo className={"text-xl"} />
                ) : (
                  <IoIosDocument className={"text-xl"} />
                )}
                <label>{data.name}</label>
              </div>
              {canUpdate && (
                <ActionButtons
                  handleEdit={setEdit}
                  key={data.id}
                  attributes={attributes}
                  listeners={listeners}
                  handleDelete={setDelete}
                />
              )}
            </div>
            {isSelectContentType ? (
              <Fragment>
                <div className="right-1/4 border-b-0 border absolute py-2 px-2 border-black flex gap-2 w-[180px] justify-center items-center">
                  <h1>
                    {type === LectureType.VIDEO
                      ? data.videoDuration !== 0
                        ? "Đổi Video"
                        : "Thêm Video"
                      : data.videoDuration !== 0
                      ? "Đổi Document"
                      : "Thêm Document"}
                  </h1>
                  <IoAddOutline
                    className="transform rotate-45 text-xl text-black hover:cursor-pointer"
                    onClick={() => handleSelectType(true)}
                  />
                </div>
              </Fragment>
            ) : null}
          </div>

          <Disclosure>
            {({ open }) => (
              <>
                <div
                  className={
                    !open
                      ? "border-y-0"
                      : "flex flex-col border-t-0 border border-black p-2 bg-white mb-5 "
                  }
                >
                  {!isSelectContentType && !isEdit ? (
                    <Disclosure.Button>
                      <div className="right-8 top-4 absolute z-20">
                        <HiChevronUp
                          className={`${
                            open ? "" : "rotate-180 transform"
                          } h-5 w-5 text-orange-500`}
                        />
                      </div>
                    </Disclosure.Button>
                  ) : null}
                  <div className="text-sm bg-white">
                    {open ? (
                      <>
                        {(data.url?.length as number) > 0 &&
                        !isSelectContentType ? (
                          <Fragment>
                            <div className="flex gap-2 mx-10 ">
                              {type === LectureType.VIDEO ? (
                                <video
                                  controls
                                  src={data.url as string}
                                  className="w-96 h-[180px] rounded-md my-4 mx-4"
                                />
                              ) : null}
                              <div className="flex justify-between w-full mx-4">
                                <div>
                                  <div className="flex gap-2 items-center justify-center my-5">
                                    <i className="text-xl">Tên file:</i>
                                    <h1 className="font-bold text-xl">
                                      {data.fileName}
                                    </h1>
                                    {type === LectureType.DOCUMENT && (
                                      <a
                                        href={`data:application/pdf;base64,${document}`}
                                        download={`${data.fileName}`}
                                        className="ml-4"
                                      >
                                        <IoMdDownload className={"text-2xl"} />
                                      </a>
                                    )}
                                  </div>
                                  {type === LectureType.VIDEO && (
                                    <div className="flex gap-2">
                                      <i>Thời lượng:</i>
                                      <p> {data.videoDuration} giây</p>
                                    </div>
                                  )}
                                </div>
                                <Button
                                  className="bg-white hover:bg-slate-200 text-black rounded-none border border-black"
                                  onClick={() => handleSelectType()}
                                  disabled={
                                    !(
                                      isPermissionGranted(
                                        roleDetail as RoleDetail[],
                                        PermissionName.CAN_UPDATE,
                                        ModuleName.COURSE
                                      ) || role?.name == Role.MANAGER
                                    )
                                  }
                                >
                                  {type === LectureType.VIDEO
                                    ? "Đổi Video"
                                    : "Đổi Document"}
                                </Button>
                              </div>
                            </div>
                          </Fragment>
                        ) : (
                          <Fragment>
                            {isSelectContentType ? (
                              <UploadFile
                                lecture={data}
                                onDisplay={setSelectContentType}
                                type={type}
                              />
                            ) : (
                              <Fragment>
                                <div className="mb-2">
                                  <div className="flex items-center gap-12">
                                    <h1>
                                      {type === LectureType.VIDEO
                                        ? "Video"
                                        : "Tài liêu"}
                                      :
                                    </h1>
                                    <Button
                                      className="bg-white hover:bg-slate-200 text-black rounded-none border border-black"
                                      onClick={() => handleSelectType()}
                                      disabled={!canCreate}
                                    >
                                      {type === LectureType.VIDEO
                                        ? "Thêm Video"
                                        : "Thêm tài liêu"}
                                    </Button>
                                  </div>
                                </div>
                              </Fragment>
                            )}
                          </Fragment>
                        )}

                        {isOpenInputEditor ? (
                          <Fragment>
                            <h1 className="mb-5">
                              {" "}
                              {description ? "Đổi Mô tả" : "Thêm Mô tả"}
                            </h1>
                            <InputEditor
                              parentId={data.id as string}
                              setIsOpenInputEditor={setIsOpenInputEditor}
                              setTextInput={setDescription}
                              text={description}
                            />
                          </Fragment>
                        ) : (
                          <Fragment>
                            <div className="flex items-center gap-2 mt-10">
                              <h1>
                                {description ? "Đổi Mô tả" : "Thêm Mô tả"}
                              </h1>
                              <Button
                                className="bg-white hover:bg-slate-200 text-black rounded-none border border-black"
                                onClick={() => handleCreateDescription()}
                                disabled={!(canCreate || canUpdate)}
                              >
                                {description ? "Đổi Mô tả" : "Thêm Mô tả"}
                              </Button>
                            </div>
                            {description && (
                              <div
                                className="p-5 border rounded shadow bg-white mt-5"
                                dangerouslySetInnerHTML={{
                                  __html: stateToHTML(
                                    EditorState.createWithContent(
                                      convertFromRaw(JSON.parse(description))
                                    ).getCurrentContent()
                                  ),
                                }}
                              />
                            )}
                          </Fragment>
                        )}
                      </>
                    ) : null}
                  </div>
                </div>
              </>
            )}
          </Disclosure>
        </Fragment>
      )}
    </div>
  );
}

export default FileLecture;
