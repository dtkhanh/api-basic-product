package cartbasic.cartbasic.viewmodel;

import java.util.List;

public record CartPostVm(String name, List<CartItemVm> cartItemVms) {}
