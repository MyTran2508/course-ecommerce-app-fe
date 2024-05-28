import { SearchConditionDto } from "./request.type";
import { User } from "./user.type";

export interface Order {
  id?: string;
  totalPrice?: number;
  orderStatus?: OrderStatus;
  shippingMethod?: ShippingMethod;
  orderItems?: OrderItem[];
  user?: Pick<User, "id"> | User;
  created?: number;
}

export interface OrderItem {
  Id?: string;
  courseId: string;
  price: number;
}

export enum OrderStatus {
  CANCELED = "CANCELED",
  PAID = "PAID",
  UNPAID = "UNPAID",
}

export enum ShippingMethod {
  PAYPAL = "PAYPAL",
}

export interface SearchOrderDto {
  searchChooseList?: SearchConditionDto[];
  searchKeywordDtoList?: SearchConditionDto[];
  minPrice?: number | null;
  maxPrice?: number | null;
  price?: number | null;
  startDate?: number;
  endDate?: number;
  pageIndex?: number;
  pageSize?: number;
  sortBy?: string;
  isDecrease?: boolean;
}
