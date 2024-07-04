"use client";
import React, { useEffect, useState } from "react";
import { Menu, Transition } from "@headlessui/react";
import { FaRegBell } from "react-icons/fa";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import { useAppSelector } from "@/redux/hooks/reduxHooks";
import { NotificationDTO } from "@/types/notification.type";
import Image from "next/image";
import {
  useGetNotificationsMutation,
  useUpdateIsViewedMutation,
  useUpdateNotificationMutation,
} from "@/redux/services/notificationApi";
import { StatusCode } from "@/utils/resources";
import { ListResponse } from "@/types/response.type";
import { useLazyGetAllUsernameAdminQuery } from "@/redux/services/userApi";
import { forEach } from "lodash";
import { useRouter } from "next/navigation";
var stompClient: any = null;

interface NotificationPopUpProps {
  hidden?: boolean;
  isAdmin?: boolean;
}

function NotificationPopUp(props: NotificationPopUpProps) {
  const { hidden, isAdmin } = props;
  const username = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.username
  );
  const route = useRouter();
  const [
    getNotifications,
    { data: notificationData, isSuccess: getNotificationSuccess },
  ] = useGetNotificationsMutation();
  const [updateIsViewed] = useUpdateIsViewedMutation();
  const [
    getAllUsernameAdmin,
    { data: allUsernameAdmin, isSuccess: getAllUsernameAdminSuccess },
  ] = useLazyGetAllUsernameAdminQuery();
  const [notifications, setNotifications] = useState<NotificationDTO[]>([]);

  useEffect(() => {
    connect();
    getNotifications({
      keyword: [username],
      pageIndex: 0,
      pageSize: 5,
      sortBy: "created",
      isDecrease: false,
    });

    getAllUsernameAdmin(null);
  }, []);

  useEffect(() => {
    if (
      getNotificationSuccess &&
      notificationData?.statusCode === StatusCode.REQUEST_SUCCESS
    ) {
      setNotifications(notificationData?.data as NotificationDTO[]);
    }
  }, [getNotificationSuccess]);

  const connect = async () => {
    // let Sock = new SockJS(process.env.NEXT_PUBLIC_END_POINT + "/ws/courses");
    let Sock = new SockJS("http://localhost:8081/ws/courses");
    stompClient = await over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onMessageReceived = (payload: any) => {
    var payloadData = JSON.parse(payload.body);
    const newNotification = (payloadData as ListResponse)
      .data as NotificationDTO;
    // console.log(newNotification);
    setNotifications((prevNotifications) => {
      const isExist = prevNotifications?.some(
        (notice) => notice?.id === newNotification?.id
      );
      if (isExist) return prevNotifications;
      return [newNotification, ...prevNotifications];
    });
  };

  const onConnected = () => {
    stompClient.subscribe(
      `/rt/response/courses/notification/${username}`,
      onMessageReceived
    );
  };

  const onError = (err: any) => {
    console.log(err);
  };

  return (
    <div className={`${hidden ? "hidden" : ""}`}>
      {/* <button
        onClick={() =>
          sendNotification(username, ["manager"], "oktesst/nOkcondee")
        }
      >
        send
      </button>
      <button
        onClick={() =>
          sendNotification(
            username,
            allUsernameAdmin?.data as string[],
            "oktesst/nOkcondee"
          )
        }
      >
        send
      </button> */}
      <Menu>
        <Menu.Button>
          <div className="relative hover:cursor-pointer">
            {notifications?.some((notice) => !notice?.isViewed) && (
              <div className="absolute top-0 bg-red-500 rounded-full w-2 h-2">
                &nbsp;
              </div>
            )}

            <FaRegBell className="text-2xl" />
          </div>
        </Menu.Button>
        <Menu.Items
          className={`absolute mt-1 ${
            isAdmin ? "right-5" : "right-56"
          }  w-[400px] origin-top-right divide-x divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none p-2`}
        >
          <div className="px-1 py-1 h-[400px] custom-scrollbar overflow-y-auto">
            <Transition
              enter="transition ease-out duration-100"
              enterFrom="transform opacity-0 scale-95"
              enterTo="transform opacity-100 scale-100"
              leave="transition ease-in duration-75"
              leaveFrom="transform opacity-100 scale-100"
              leaveTo="transform opacity-0 scale-95"
            >
              <div>
                <h1 className="text-lg font-bold">Notifications</h1>
                {notifications ? (
                  <div>
                    {notifications?.map((notification) => (
                      <div
                        key={notification.id}
                        className={`${
                          !notification?.isViewed
                            ? "bg-orange-200 "
                            : "hover:bg-slate-300"
                        } hover:cursor-pointer rounded-lg flex py-2 px-2 items-center gap-4 mb-2`}
                        onClick={async () => {
                          await updateIsViewed(notification?.id as string);
                          await getNotifications({
                            keyword: [username],
                            pageIndex: 0,
                            pageSize: 5,
                            sortBy: "created",
                            isDecrease: false,
                          });
                          if (notification.link) {
                            route.push(notification.link);
                          }
                        }}
                      >
                        <Image
                          src={"/banner.jpg"}
                          width={400}
                          height={400}
                          className="w-12 h-12 rounded-full"
                          alt="avatar"
                        />
                        <div>
                          <h1 className="font-bold">{notification?.sender}</h1>
                          <p className="text-sm">{notification?.content}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                ) : (
                  <div className="w-full">
                    <h1 className="flex justify-center items-center italic mt-3">
                      No Notification
                    </h1>
                  </div>
                )}
              </div>
            </Transition>
          </div>
        </Menu.Items>
      </Menu>
    </div>
  );
}

export const sendNotification = (
  sender: string,
  chanel: string[],
  message: string,
  link?: string
) => {
  console.log(chanel);
  if (stompClient) {
    if (chanel?.length > 1 && chanel) {
      const listNotification: NotificationDTO[] = [];
      forEach(chanel, (username) => {
        const newMessage: NotificationDTO = {
          sender: sender,
          recipient: username,
          content: message,
          link: link,
          isViewed: false,
        };
        listNotification.push(newMessage);
      });
      stompClient.send(
        `/rt/request/courses/notification/add-list`,
        {},
        JSON.stringify(listNotification)
      );
    } else {
      const newMessage: NotificationDTO = {
        sender: sender,
        recipient: chanel[0],
        content: message,
        link: link,
        isViewed: false,
      };
      stompClient.send(
        `/rt/request/courses/notification/add`,
        {},
        JSON.stringify(newMessage)
      );
    }
  }
};

export default NotificationPopUp;
