package cartbasic.cartbasic.repository;

import cartbasic.cartbasic.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("select c from Cart c where c.customer = ?1")
    Cart findCartByCustomer(String customer);

}
