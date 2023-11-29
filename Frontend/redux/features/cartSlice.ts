import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import { Cart } from "@/types/cart.type";

let initialState: Cart[] = [];

// const storedState = localStorage.getItem("cartState");
// if (storedState) {
//   initialState = JSON.parse(storedState);
// }

export const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: {
        addToCart: (state, action: PayloadAction<Cart>) => {
            const newCartItem = action.payload;
            state.push(newCartItem);
        },

        removeFromCart: (state, action: PayloadAction<string>) => {
            const index = state.findIndex(item => item.id === action.payload)
            
            if (index != -1) {
                state.splice(index, 1);
            }

        },

        setCart: (state, action: PayloadAction<Cart[]>) => {
            return action.payload;
        },

        setCheckedFormCart: (state, action: PayloadAction<{ id: string; checked: boolean }>) => {
            const { id, checked } = action.payload;
            const cartItem = state.find(item => item.id === id);

            if (cartItem) {
                cartItem.checked = checked;
            }
        }
    }
})

export const { addToCart, removeFromCart, setCheckedFormCart, setCart } = cartSlice.actions;

export default cartSlice.reducer;