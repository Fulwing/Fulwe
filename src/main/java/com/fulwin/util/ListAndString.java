package com.fulwin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListAndString {
    public static String convertCartStringToLongList(List<Long> cartList) {
        if (cartList != null && !cartList.isEmpty()) {
            return cartList.stream().map(String::valueOf).collect(Collectors.joining(","));
        } else {
            return null; // Set cart to null if it's empty
        }
    }

    public static List<Long> convertCartStringToLongList(String cartString) {
        List<Long> cartList = new ArrayList<>();
        if (cartString != null && !cartString.isEmpty()) {
            String[] cartItems = cartString.split(",");
            for (String cartItem : cartItems) {
                try {
                    Long itemId = Long.parseLong(cartItem);
                    cartList.add(itemId);
                } catch (NumberFormatException e) {
                    // Handle parsing errors if needed
                }
            }
        } else if (cartString != null) {
            // If cartString is not empty but doesn't contain a comma, assume it's a single item
            try {
                Long itemId = Long.parseLong(cartString);
                cartList.add(itemId);
            } catch (NumberFormatException e) {
                // Handle parsing errors if needed
            }
        }
        return cartList;
    }


}
