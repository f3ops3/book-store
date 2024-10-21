package springweb.courseproject.service.order;

import java.util.List;
import org.springframework.data.domain.Pageable;
import springweb.courseproject.dto.order.CreateOrderRequestDto;
import springweb.courseproject.dto.order.OrderResponseDto;
import springweb.courseproject.dto.order.UpdateOrderRequestDto;
import springweb.courseproject.dto.orderitem.OrderItemResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(Long userId, CreateOrderRequestDto createOrderRequestDto);

    List<OrderResponseDto> getAllUsersOrders(Pageable pageable, Long userId);

    OrderResponseDto updateOrder(Long orderId, UpdateOrderRequestDto updateOrderRequestDto);

    List<OrderItemResponseDto> getOrderItemsById(Long orderId, Long userId);

    OrderItemResponseDto getOrderItemFromOrderByIds(Long orderId, Long userId, Long orderItemId);
}
