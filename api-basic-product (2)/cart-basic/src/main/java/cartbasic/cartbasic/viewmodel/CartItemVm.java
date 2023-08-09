package cartbasic.cartbasic.viewmodel;

import cartbasic.cartbasic.model.CartItem;

public record CartItemVm(Long idProduct, int quantity) {
    public static CartItemVm fromModel(CartItem cartItem){
        return new CartItemVm(
                cartItem.getProductId(),
                cartItem.getQuantity()
        );
    }
}
