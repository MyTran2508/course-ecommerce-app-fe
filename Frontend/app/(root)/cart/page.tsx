"use client";
import CartItem from "@/components/CartItem";
// import Router from "@/components/router/router";
import { Button } from "@/components/ui/button";
import { useAppDispatch, useAppSelector } from "@/redux/hooks";
import { totalPrice } from "@/utils/function";
import { useRouter } from "next/navigation";
import React, { Fragment } from "react";

const PageCart = () => {
  const route = useRouter();
  const carts = useAppSelector((state) => state.persistedReducer.cartReducer);

  const renderCartItem = () => {
    return (
      <div>
        {carts.map((cartItem, index) => (
          <Fragment key={index}>
            <CartItem cartItem={cartItem} />
            {index !== carts.length - 1 && <hr />}
          </Fragment>
        ))}
      </div>
    );
  };

  const handleChangeRouteCheckout = () => {
    route.push("/payment/checkout");
  };

  return (
    // <Router>
    <div className="mx-28">
      <div className="flex mt-10">
        <div className="w-3/4">
          <div className="text-2xl font-bold mb-10">Shopping Cart</div>
          <div className="font-bold text-[12px] mb-2">
            {carts.length} Courses in Cart
          </div>
          <hr />
          {renderCartItem()}
        </div>
        <div className="mt-20 ml-8 w-1/4">
          <div className="opacity-50">Total:</div>
          <div className="text-2xl font-bold mt-4">
            {totalPrice(carts).toLocaleString()} đ
          </div>
          <Button
            className="w-full rounded-none mt-10"
            onClick={() => handleChangeRouteCheckout()}
          >
            Checkout
          </Button>
        </div>
      </div>
    </div>
    // </Router>
  );
};

export default PageCart;
