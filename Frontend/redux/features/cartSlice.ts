import { Action } from './../../utils/resources';
import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import { Cart } from "@/types/cart.type";
import { v4 as uuidv4 } from "uuid";

let initialState: Cart[] = [{
    id: uuidv4().toString(),
    image: "https://www.udemy.com/staticx/udemy/images/v9/hpp-paypal.svg",
    nameCourse: " Khóa Học JavaScript",
    sections: 11,
    totalLength: 120,
    price: 2000000,
    lectures: 11,
    checked: false
  },
];

const storedState = localStorage.getItem("cartState");
if (storedState) {
  initialState = JSON.parse(storedState);
}

export const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: {
        addToCart: (state, action: PayloadAction<Cart>) => {
            const newCartItem = action.payload;
            state.push(newCartItem);
            localStorage.setItem("cartState", JSON.stringify(state));
        },

        removeFromCart: (state, action: PayloadAction<string>) => {
            const index = state.findIndex(item => item.id === action.payload)
            
            if (index != -1) {
                state.splice(index, 1);
                localStorage.setItem("cartState", JSON.stringify(state));
            }

        },

        setCheckedFormCart: (state, action: PayloadAction<{ id: string; checked: boolean }>) => {
            const { id, checked } = action.payload;
            const cartItem = state.find(item => item.id === id);

            if (cartItem) {
                cartItem.checked = checked;
            }
            localStorage.setItem("cartState", JSON.stringify(state));
        }
    }
})

export const { addToCart, removeFromCart, setCheckedFormCart } = cartSlice.actions;

export default cartSlice.reducer;