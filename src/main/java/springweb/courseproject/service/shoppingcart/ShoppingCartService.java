package springweb.courseproject.service.shoppingcart;

import springweb.courseproject.dto.cartitem.CreateCartItemRequestDto;
import springweb.courseproject.dto.cartitem.UpdateCartItemRequestDto;
import springweb.courseproject.dto.shoppingcart.ShoppingCartResponseDto;
import springweb.courseproject.model.User;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCartResponseDto getShoppingCartForCurrentUser(Long userId);

    ShoppingCartResponseDto addCartItem(
            CreateCartItemRequestDto createCartItemRequestDto, Long userId);

    ShoppingCartResponseDto updateCartItemById(
            Long cartId, UpdateCartItemRequestDto updateCartItemRequestDto, Long userId);

    void removeCartItemById(Long cartId, Long userId);
}
