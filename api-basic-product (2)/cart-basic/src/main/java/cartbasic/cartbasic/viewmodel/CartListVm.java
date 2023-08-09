package cartbasic.cartbasic.viewmodel;

import cartbasic.cartbasic.model.Cart;

public record CartListVm(Long id, String customer) {
    public static CartListVm fromModel(Cart cart){
        return new CartListVm(cart.getId(),cart.getCustomer());
    }

}
