import { Cart } from "@/types/cart.type";
import { Lecture, Section } from "@/types/section.type";
import path from "path";

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

export const checkFileExtension = (file: File):boolean =>{
  const allowedExtensions: string[] = ['mp4'];
  const fileName: string = file.name;
  const fileExtension: string = fileName.split('.').pop()!.toLowerCase();
  return allowedExtensions.includes(fileExtension);
}

export const handleGetDurationFormVideo = async (file: File) => {
    if (checkFileExtension(file)) {
      const videoElement = document.createElement("video");
      const videoURL = URL.createObjectURL(file);
      videoElement.src = videoURL;

      await new Promise<void>((resolve) => {
        videoElement.addEventListener("loadedmetadata", () => {
          resolve();
        });
      });

      return Math.round(videoElement.duration);
    }

    return 0;
};
  
export const handleCountObject = (array: Lecture[] | Section[]): number => {
  const filteredArray = array.filter(item => 'ordinalNumber' in item && item.ordinalNumber !== -1);
  return filteredArray.length;
}