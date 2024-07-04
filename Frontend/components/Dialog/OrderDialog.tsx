import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { OrderItem } from "@/types/order.type";
import { useLazyGetCourseByIdQuery } from "@/redux/services/courseApi";
import { Course } from "@/types/course.type";
import { useEffect, useState } from "react";

interface OrderDialogProps {
  isOpen: boolean;
  setIsOpen: (isOpen: boolean) => void;
  bill: OrderItem[];
}

function OrderDialog(props: OrderDialogProps) {
  const { isOpen, setIsOpen, bill } = props;
  const [orderItem, setOrderItem] = useState<OrderItem[]>(bill);
  const [courses, setCourses] = useState<Course[]>([]);
  const [getCourseById, { data: course, isSuccess: getCourseSuccess }] =
    useLazyGetCourseByIdQuery();

  useEffect(() => {
    setOrderItem(bill);
  }, [bill]);
  useEffect(() => {
    if (isOpen) {
      orderItem.forEach((item) => {
        getCourseById(item.courseId).then((res) => {
          setCourses([...courses, res.data?.data as Course]);
        });
      });
    }
  }, [isOpen, orderItem, getCourseById]);
  return (
    <Dialog open={isOpen} onOpenChange={() => setIsOpen(false)}>
      <DialogContent className="min-w-[800px] flex-center flex-col">
        <DialogHeader>
          <DialogTitle>Thông tin đơn hàng</DialogTitle>
        </DialogHeader>
        <div className="flex flex-col w-full">
          <div className="flex w-full justify-between">
            <div className="flex flex-col">
              <div className="text-sm font-semibold">Tên khóa học</div>
              {courses.map((item) => {
                return (
                  <div key={item.id} className="text-sm">
                    {item.name}
                  </div>
                );
              })}
            </div>
            <div className="flex flex-col text-end">
              <div className="text-sm font-semibold ">Giá</div>
              {courses.map((item) => (
                <div key={item.id} className="text-sm">
                  {item.price}đ
                </div>
              ))}
            </div>
          </div>
          <div className="flex justify-end mt-4">
            <Button
              onClick={() => {
                setCourses([]);
                setIsOpen(false);
              }}
            >
              Đóng
            </Button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}

export default OrderDialog;
