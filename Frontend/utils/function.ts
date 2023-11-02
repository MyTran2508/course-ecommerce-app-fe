import { Cart } from "@/types/cart.type";

export const totalPrice = (carts: Cart[]) => {
    const checkedItems = carts.filter((item) => item.checked);

    const totalPrice = checkedItems.reduce(
      (total, item) => total + item.price,
      0
    );

    return totalPrice;
};
  
export const convertVNDtoUSD = (moneyVND: number) => {
    const exchangeRate = 24580;

    const convertedAmount = (moneyVND / exchangeRate).toFixed(1);

    return parseFloat(convertedAmount);
}

export const isURLValid = (url: string) => {
  try {
    new URL(url);
    return true;
  } catch (error) {
    return false;
  }
};