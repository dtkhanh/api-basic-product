package cartbasic.cartbasic.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float totalMoney;
    private String customer;
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<CartItem> cartItemSet = new HashSet<>();
}
