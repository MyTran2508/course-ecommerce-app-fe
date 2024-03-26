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
import { Constant, LectureType } from "@/utils/resources";
import { Lecture, Question, Section } from "@/types/section.type";

interface SortableProps {
  data: Question[] | Section[] | Lecture[];
  type?: LectureType | Constant;
  onDataChange?: (data: Question[] | Section[] | Lecture[]) => void;
}

function Sortable(props: SortableProps) {
  const { data, type, onDataChange } = props;
  const [items, setItems] = useState<Question[] | Section[] | Lecture[]>(data);
  const sensors = useSensors(
    useSensor(PointerSensor, {
      activationConstraint: {
        distance: 8,
      },
    }),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    })
  );

  useEffect(() => {
    setItems(data);
    if (onDataChange) {
      onDataChange(data);
    }
  }, [data, onDataChange]);

  const renderItems = () => {
    return (items as Lecture[])
      .filter((id) => id.ordinalNumber && id.ordinalNumber !== -1)
      .map((id) => (
        <SortableItem
          id={id.ordinalNumber?.toString() as string}
          type={
            type === Constant.LECTURE ? (id.lectureType as LectureType) : type
          }
          key={id.ordinalNumber}
          data={id}
        />
      ));
  };

  return (
    <DndContext
      sensors={sensors}
      collisionDetection={closestCenter}
      onDragEnd={handleDragEnd}
    >
      {items ? (
        <SortableContext
          items={items.map(
            (items) => items.ordinalNumber?.toString() as string
          )}
          strategy={verticalListSortingStrategy}
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
          items.find((item) => item.ordinalNumber == active.id) as Question &
            Section &
            Lecture
        );
        const newIndex = items.indexOf(
          items.find((item) => item.ordinalNumber == over.id) as Question &
            Section &
            Lecture
        );

        const newItems = arrayMove(items, oldIndex, newIndex);

        newItems.forEach((item, index) => {
          if (item.ordinalNumber !== -1) {
            item.ordinalNumber = index + 1;
          }
        });

        if (onDataChange) {
          onDataChange(newItems);
        }
        return newItems;
      });
    }
  }
}

export default Sortable;
