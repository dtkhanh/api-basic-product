package cartbasic.cartbasic.repository;

import cartbasic.cartbasic.model.Cart;
import cartbasic.cartbasic.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long > {
    Set<CartItem> findAllByCart(Cart cart);


}
