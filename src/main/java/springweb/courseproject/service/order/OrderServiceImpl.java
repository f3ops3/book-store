package springweb.courseproject.service.order;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springweb.courseproject.dto.order.CreateOrderRequestDto;
import springweb.courseproject.dto.order.OrderResponseDto;
import springweb.courseproject.dto.order.UpdateOrderRequestDto;
import springweb.courseproject.dto.orderitem.OrderItemResponseDto;
import springweb.courseproject.exception.EntityNotFoundException;
import springweb.courseproject.exception.OrderProcessingException;
import springweb.courseproject.mapper.OrderItemMapper;
import springweb.courseproject.mapper.OrderMapper;
import springweb.courseproject.model.Order;
import springweb.courseproject.model.OrderItem;
import springweb.courseproject.model.ShoppingCart;
import springweb.courseproject.model.Status;
import springweb.courseproject.repository.order.OrderRepository;
import springweb.courseproject.repository.orderitem.OrderItemRepository;
import springweb.courseproject.repository.shoppingcart.ShoppingCartRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public OrderResponseDto createOrder(Long userId, CreateOrderRequestDto createOrderRequestDto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart with user id "
                        + userId + " not found")
        );
        if (cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("You can't place an order with empty shopping cart");
        }
        Order order = prepareOrder(cart, createOrderRequestDto);
        orderRepository.save(order);
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);
        return orderMapper.toDto(order);
    }

    @Override
    public Page<OrderResponseDto> getAllUsersOrders(Pageable pageable, Long userId) {
        Page<Order> ordersPage = orderRepository.findAllByUserId(pageable, userId);
        return ordersPage.map(orderMapper::toDto);
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, UpdateOrderRequestDto updateOrderRequestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order with order id " + orderId + " not found")
        );
        order.setStatus(updateOrderRequestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsById(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(
                () -> new EntityNotFoundException("Order with order id " + orderId
                        + " not found or doesn't belong to user with id " + userId)
        );
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponseDto getOrderItemFromOrderByIds(Long orderId,
                                                           Long userId, Long orderItemId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(
                () -> new EntityNotFoundException("Order with order id " + orderId
                        + " not found or doesn't belong to user with id " + userId)
        );
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(
                () -> new EntityNotFoundException("Order item with id "
                        + orderItemId + " not found")
        );
        if (!order.getOrderItems().contains(orderItem)) {
            throw new OrderProcessingException("Order item with id " + orderItemId
                    + " is in another order");
        }
        return orderItemMapper.toDto(orderItem);
    }

    private Order prepareOrder(ShoppingCart cart, CreateOrderRequestDto createOrderRequestDto) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(createOrderRequestDto.getShippingAddress());
        Set<OrderItem> orderItemSet = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = orderItemMapper.toOrderItem(cartItem);
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toSet());
        order.setOrderItems(orderItemSet);
        BigDecimal total = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getBook().getPrice().multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        return order;
    }
}
