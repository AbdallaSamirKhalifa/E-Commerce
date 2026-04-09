package commerce.controllers;

import commerce.dto.request.OrderRequest;
import commerce.dto.response.OrderResponse;
import commerce.service.contract.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> checkout(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.checkout(request));
    }
}
