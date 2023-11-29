import Image from "next/image";
import React, { useState } from "react";
import { BsDot, BsFillTagFill } from "react-icons/bs";
import { Button } from "./ui/button";
import { Cart } from "@/types/cart.type";
import { useAppDispatch } from "@/redux/hooks";
import { removeFromCart, setCheckedFormCart } from "@/redux/features/cartSlice";
import showToast from "@/utils/showToast";
import { ToastMessage, ToastStatus } from "@/utils/resources";
import { Checkbox } from "./ui/checkbox";
import { useLoadFileFromCloudQuery } from "@/redux/services/courseApi";

interface CartItemProps {
  cartItem: Cart;
}

function CartItem(props: CartItemProps) {
  const { cartItem } = props;
  const dispatch = useAppDispatch();
  const [isChecked, setChecked] = useState(cartItem.checked);
  const { data: image } = useLoadFileFromCloudQuery(cartItem.image);

  const handleDeleteFromCart = () => {
    dispatch(removeFromCart(cartItem.id));
    showToast(ToastStatus.SUCCESS, ToastMessage.DELETE_CART_SUCCESS);
  };

  const handleCheckboxChange = () => {
    setChecked((prevChecked) => {
      const newChecked = !prevChecked;
      dispatch(setCheckedFormCart({ id: cartItem.id, checked: newChecked }));
      return newChecked;
    });
  };

  return (
    <div className="my-2">
      <div className="flex justify-between">
        <div className="flex gap-4">
          <Image
            src={image ? `data:image/png;base64,${image}` : "/banner.jpg"}
            alt="course"
            className="w-24 h-14 pb-2"
            width={40}
            height={20}
          />
          <div>
            <div className="text-[15px] font-bold mb-1">
              {cartItem.nameCourse}
            </div>
            <div className="flex-start xl:gap-2 xs:gap-0.5 text-[10px] opacity-50">
              <div>{cartItem.totalSection} chương</div>
              <BsDot className="text-[12px]" />
              <div>{cartItem.totalLecture} bài học</div>
              <BsDot className="text-[12px]" />
              <div>Thời lượng {cartItem.totalDurationCourse}</div>
            </div>
          </div>
        </div>
        {/* <div className="flex items-center ">
          <Button
            className="w-12 h-6 text-[8px] bg-rose-400 flex-shrink-0"
            onClick={() => handleDeleteFromCart()}
          >
            Remove
          </Button>
        </div> */}
        <div className="flex gap-16 items-center">
          <Button
            className="w-12 h-6 text-[8px] bg-rose-400"
            onClick={() => handleDeleteFromCart()}
          >
            Remove
          </Button>
          <div className="flex gap-4 items-center">
            <div className="text-violet-800 font-bold flex gap-2 w-[200px] xs:w-[100px] ml-auto flex-row-reverse">
              <BsFillTagFill className="pt-1 text-xl" />
              {cartItem.price && cartItem.price.toLocaleString()}đ
            </div>
            <Checkbox
              className="mr-2"
              onClick={() => handleCheckboxChange()}
              checked={isChecked}
            ></Checkbox>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CartItem;
