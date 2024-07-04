import React, { useEffect, useState } from "react";
import {
  DndContext,
  closestCenter,
  KeyboardSensor,
  PointerSensor,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import {
  arrayMove,
  SortableContext,
  sortableKeyboardCoordinates,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";
import { SortableItem } from "./SortableItem";
import {
  Constant,
  LectureType,
  ModuleName,
  PermissionName,
} from "@/utils/resources";
import { Lecture, Question, Section } from "@/types/section.type";
import { isPermissionGranted } from "@/utils/function";
import { RoleDetail } from "@/types/roles.type";
import { useAppSelector } from "@/redux/hooks/reduxHooks";

interface SortableProps {
  data: Question[] | Section[] | Lecture[];
  type?: LectureType | Constant;
  onDataChange?: (data: Question[] | Section[] | Lecture[]) => void;
  onDataUpdate?: (isUpdate: boolean) => void;
}

function Sortable(props: SortableProps) {
  const { data, type, onDataChange, onDataUpdate } = props;
  const [items, setItems] = useState<Question[] | Section[] | Lecture[]>(data);
  const sensors = useSensors(
    useSensor(PointerSensor, {
      activationConstraint: {
        distance: 8,
      },
    })
    // useSensor(KeyboardSensor, {
    //   coordinateGetter: sortableKeyboardCoordinates,
    // })
  );
  const role = useAppSelector(
    (state) => state.persistedReducer.userReducer.user.roles?.[0]
  );
  const roleDetail = role?.roleDetails;

  const renderItems = () => {
    return (items as Lecture[])
      .filter((id) => id.ordinalNumber && id.ordinalNumber !== -1)
      .map((id, index) => {
        // console.log(id);
        // console.log(id.lectureType);
        return (
          <SortableItem
            id={id.id as string}
            index={index + 1}
            type={
              type === Constant.LECTURE ? (id.lectureType as LectureType) : type
            }
            key={id.id as string}
            data={id}
          />
        );
      });
  };

  return (
    <DndContext
      sensors={sensors}
      collisionDetection={closestCenter}
      onDragEnd={handleDragEnd}
    >
      {items ? (
        <SortableContext
          items={items
            .filter((item) => (item.ordinalNumber as number) > 0)
            .map((items) => items.id as string)}
          strategy={verticalListSortingStrategy}
          disabled={
            !(
              isPermissionGranted(
                roleDetail as RoleDetail[],
                PermissionName.CAN_CREATE,
                ModuleName.CONTENT
              ) ||
              isPermissionGranted(
                roleDetail as RoleDetail[],
                PermissionName.CAN_UPDATE,
                ModuleName.CONTENT
              )
            )
          }
        >
          {renderItems()}
        </SortableContext>
      ) : null}
    </DndContext>
  );

  function handleDragEnd(event: any) {
    const { active, over } = event;
    if (active.id !== over.id) {
      setItems((items) => {
        const oldIndex = items.indexOf(
          items.find((item) => item.id == active.id) as Question &
            Section &
            Lecture
        );
        const newIndex = items.indexOf(
          items.find((item) => item.id == over.id) as Question &
            Section &
            Lecture
        );

        const newItems = arrayMove(
          items as Question[] & Section[] & Lecture[],
          oldIndex,
          newIndex
        );

        newItems.forEach((item, index) => {
          if (item.ordinalNumber !== -1) {
            item.ordinalNumber = index + 1;
          }
        });

        if (onDataChange) {
          onDataChange(newItems);
          onDataUpdate && onDataUpdate(true);
        }
        return newItems;
      });
    }
  }
}

export default Sortable;
