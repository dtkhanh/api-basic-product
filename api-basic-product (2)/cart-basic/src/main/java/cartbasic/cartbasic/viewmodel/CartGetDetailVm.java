package cartbasic.cartbasic.viewmodel;

import cartbasic.cartbasic.model.Cart;

import java.util.List;

public record CartGetDetailVm(
        Long id,
        String customer,
        List<CartDetailVm> cartDetailVms
) {
    public static CartGetDetailVm fromModel(Cart cart){
        return new CartGetDetailVm(
                cart.getId(),
                cart.getCustomer(),
                cart.getCartItemSet().stream().map(CartDetailVm::fromModel).toList()
        );
    }
}
