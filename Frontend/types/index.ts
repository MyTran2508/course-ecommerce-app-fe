import { MouseEventHandler } from "react"

export interface CustomButtonProps{
    title: string,
    containerStyles?: string
    handleClick?: MouseEventHandler<HTMLButtonElement>
}
export interface SearchManufacturerProps {
    manufacturer: string,
    setManufacturer: (manufacturer:string) => void
}

export interface CourseCardProps {
    id: string;
    title: string;
    image: string;
    price: string;
}