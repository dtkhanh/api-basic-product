package cartbasic.cartbasic.viewmodel;

import cartbasic.cartbasic.model.CartItem;

public record CartDetailVm(Long id, Long idProduct, int quantity) {
    public static CartDetailVm fromModel(CartItem cartItem){
       return new CartDetailVm(
               cartItem.getId(),
               cartItem.getProductId(),
               cartItem.getQuantity()
       );
    }
}
