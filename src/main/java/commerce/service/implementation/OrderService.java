package commerce.service.implementation;

import commerce.dto.request.OrderRequest;
import commerce.dto.response.OrderResponse;
import commerce.entities.*;
import commerce.exceptions.EmptyCartException;
import commerce.exceptions.LockedCartException;
import commerce.exceptions.ProductUnavailableException;
import commerce.exceptions.ResourceNotFoundException;
import commerce.mappers.contracts.IOrderMapper;
import commerce.repositories.CartRepository;
import commerce.repositories.CustomerAddressRepository;
import commerce.repositories.OrderRepository;
import commerce.service.contract.IOrderService;
import commerce.service.util.OrderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderHelper helper;
    private final CartRepository cartRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final IOrderMapper mapper;
    private final CartService cartService;

    @Override
    public OrderResponse checkout(OrderRequest request) {
        Customer customer = helper.getContextCustomerWithCart();

        OrderResponse response = placeOrder(request, customer);
        helper.sendOrderConfirmationEmail(customer.getUser().getEmail(), response.totalAmount(), customer.getUser().getFirstName());
        return response;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request, Customer customer) {
        Cart cart = customer.getCart();

        CustomerAddress address = customerAddressRepository.findById(request.addressId()).
                orElseThrow(() -> new ResourceNotFoundException("Address", request.addressId()));

        if (cart == null || cart.getCartItems().isEmpty())
            throw new EmptyCartException();
        if (Boolean.TRUE.equals(cart.getIsLocked()))
            throw new LockedCartException();

        cart.setIsLocked(true);
        cartRepository.saveAndFlush(cart);
        Set<OrderItem> orderItems = createOrderItems(cart.getCartItems());
        Order order = Order.builder().customer(customer).
                orderDate(LocalDateTime.now()).
                totalAmount(
                        orderItems.stream().
                                map(OrderItem::getSubtotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ).note(request.orderNote())
                .address(address)
                .orderItems(orderItems).build();
        orderItems.forEach(item -> item.setOrder(order));
        Order newOrder =
                orderRepository.saveAndFlush(order);
        cartService.clearCart(cart);
        return mapper.entityToResponse(newOrder);
    }


    private Set<OrderItem> createOrderItems(Set<CartItem> items) {
        return items.stream().map(item -> {
            if (!item.getProduct().getIsAvailable())
                throw new ProductUnavailableException(item.getProduct().getName());

            return OrderItem.builder().
                    product(item.getProduct()).
                    quantity(item.getQuantity()).
                    price(item.getProduct().getPrice())
                    .subtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).build();

        }).collect(Collectors.toSet());
    }


}
