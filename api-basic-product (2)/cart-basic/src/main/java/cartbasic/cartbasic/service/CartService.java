package cartbasic.cartbasic.service;

import cartbasic.cartbasic.exception.NotFoundException;
import cartbasic.cartbasic.model.Cart;
import cartbasic.cartbasic.model.CartItem;
import cartbasic.cartbasic.repository.CartItemRepository;
import cartbasic.cartbasic.repository.CartRepository;
import cartbasic.cartbasic.viewmodel.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public List<CartListVm> getCarts(){
        return cartRepository.findAll().stream().map(CartListVm::fromModel).toList();
    }

    public CartGetDetailVm getCartById(Long id){
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isEmpty()){
            throw new NotFoundException("Cart no existed: " , id);
        }
        return CartGetDetailVm.fromModel(cart.get());
    }

    public CartGetDetailVm addToCart(CartPostVm cartPostVm){
        List<Long> idProducts = cartPostVm.cartItemVms().stream().map(CartItemVm::idProduct).toList();
        List<ProductGetVm> productGetVms = productService.getProducts(idProducts);
        Cart cart = cartRepository.findCartByCustomer(cartPostVm.name());
        Set<CartItem> existedCartItems = new HashSet<>();
        if(productGetVms.isEmpty()){
            throw new NotFoundException("Not found list product: ", cartPostVm.cartItemVms().stream().map(CartItemVm::idProduct).toList());
        }
        if(cart == null){
            cart = Cart.builder()
                    .cartItemSet(existedCartItems)
                    .customer(cartPostVm.name())
                    .build();
        }else{
            existedCartItems = cartItemRepository.findAllByCart(cart);
        }
        for(CartItemVm cartItemVm : cartPostVm.cartItemVms()){
            CartItem cartItem = new CartItem();
            if(existedCartItems != null){
                for(CartItem cartItemOld : existedCartItems){
                    if(Objects.equals(cartItemVm.idProduct(), cartItemOld.getProductId())){
                        cartItem = cartItemOld;
                    }
                }
            }
            if(cartItem.getId() != null){
                cartItem.setQuantity(cartItem.getQuantity() + cartItemVm.quantity());
            }else{
                cartItem.setCart(cart);
                cartItem.setProductId(cartItemVm.idProduct());
                cartItem.setQuantity(cartItemVm.quantity());
                cartItem.setTotalPrice(0.0);
                cart.getCartItemSet().add(cartItem);
            }
        }
        cartRepository.save(cart);
        cartItemRepository.saveAll(cart.getCartItemSet());
        return CartGetDetailVm.fromModel(cart);
    }

}
