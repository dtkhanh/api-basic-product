package cartbasic.cartbasic.controller;

import cartbasic.cartbasic.repository.CartItemRepository;
import cartbasic.cartbasic.repository.CartRepository;
import cartbasic.cartbasic.service.CartService;
import cartbasic.cartbasic.viewmodel.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

@RestController
public class CartController {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    public CartController(CartRepository cartRepository, CartService cartService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping("/api/cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CartGetDetailVm.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<CartGetDetailVm> addToCart(@Valid  @RequestBody @NotEmpty CartPostVm cartPostVm){
        CartGetDetailVm cartGetDetailVm = cartService.addToCart(cartPostVm);
        return ResponseEntity.ok(cartGetDetailVm);
    }
    @GetMapping("/api/cart")
    public ResponseEntity<List<CartListVm>> getCarts(){
        return ResponseEntity.ok(cartService.getCarts());
    }
    @GetMapping("/api/cart/{id}")
    public ResponseEntity<CartGetDetailVm> getCartById(@PathVariable("id") long id){
        return ResponseEntity.ok(cartService.getCartById(id));
    }

}
