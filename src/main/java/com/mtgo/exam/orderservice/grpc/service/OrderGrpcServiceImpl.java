package com.mtgo.exam.orderservice.grpc.service;
/*
import com.mtgo.exam.grpcinterface.*;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.model.OrderLine;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import io.grpc.stub.StreamObserver;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@Slf4j
@GrpcService
public class OrderGrpcServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final IOrderRepository orderRepository;

    @Override
    public void getOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        // Retrieve Order from DB according to provided id
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(NotFoundException::new);

        // Build new OrderResponse and apply values from Order Entity
        OrderResponse.Builder responseBuilder = OrderResponse.newBuilder()
                        .setOrderId(order.getId())
                        .setOrderStatus(order.getStatus().getCode())
                        .setOrderNumber(order.getOrderNumber())
                        .setRestaurantId(order.getRestaurantId());
        // Adding the OrderLines
        for (OrderLine orderLine : order.getOrderLines()) {

            OrderLineResponse.Builder orderLineBuilder = OrderLineResponse.newBuilder()
                    .setItemName(orderLine.getItemName())
                    .setQuantity(orderLine.getQuantity());

            responseBuilder.addOrderLines(orderLineBuilder.build());
        }
        responseBuilder.setComment(order.getComment());

        // Adding the CustomerInfo
        CustomerInfoResponse.Builder customerInfoBuilder = CustomerInfoResponse.newBuilder()
                .setUserId(order.getCustomerInfo().getUserId())
                .setFirstName(order.getCustomerInfo().getFirstName())
                .setLastName(order.getCustomerInfo().getLastName())
                .setPhone(order.getCustomerInfo().getPhone())
                .setAddress(order.getCustomerInfo().getAddress());
        responseBuilder.setCustomerInfo(customerInfoBuilder.build());

        OrderResponse response = responseBuilder.build();

        // Send the response to client stub
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

 */
