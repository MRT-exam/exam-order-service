package com.mtgo.exam.orderservice.unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mtgo.exam.orderservice.dto.CustomerInfoDto;
import com.mtgo.exam.orderservice.dto.OrderDto;
import com.mtgo.exam.orderservice.dto.OrderLineDto;
import com.mtgo.exam.orderservice.dto.OrderRequestDto;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;
import com.mtgo.exam.orderservice.model.OrderLine;
import com.mtgo.exam.orderservice.repository.IOrderRepository;
import com.mtgo.exam.orderservice.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private List<OrderLineDto> orderLineDtoList;

    private CustomerInfoDto customerInfoDto;

    @BeforeEach
    public void setup() {
        order = this.readOrderFromJson();
        orderLineDtoList = new ArrayList<>();
        orderLineDtoList.add(
                OrderLineDto.builder()
                .itemId("mongoDbItemId")
                .itemName("Kage")
                .price(new BigDecimal(200.00))
                .quantity(3)
                .build()
        );

        customerInfoDto = customerInfoDto.builder()
                .userId(1)
                .firstName("Jane")
                .lastName("Doe")
                .phone(43547690)
                .address("Elm Street 4")
                .build();
    }

    @Test
    void createOrder() {
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .restaurantId("restaurant1")
                .orderDateTime(LocalDateTime.of(2023, 12,4,15,30))
                .orderLineDtoList(orderLineDtoList)
                .comment("Uden birkes")
                .customerInfoDto(customerInfoDto)
                .build();

        when(orderRepository.save((Mockito.any(Order.class)))).thenReturn(order);

        OrderDto savedOrder = orderService.createOrder(orderRequestDto);
        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getId()).isEqualTo(1);
        Assertions.assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void calcTotalPrice() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<OrderLine> orderLines = order.getOrderLines();
        BigDecimal actual = (BigDecimal) getCalcTotalPriceMethod().invoke(orderService, orderLines);
        BigDecimal expected = new BigDecimal(600.00).setScale(2);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test void getOrdersByStatus() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepository.findByRestaurantIdAndStatus(order.getRestaurantId(), order.getStatus())).thenReturn(orders);
        List<OrderDto> actualList = orderService.getOrdersByStatus("restaurant1", OrderStatus.PENDING);
        Assertions.assertThat(actualList.get(0).getOrderNumber()).isEqualTo(order.getOrderNumber());
    }

    @Test void updateOrderByStatus() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.ofNullable(order));
        orderService.updateOrderStatus(order.getId(), OrderStatus.ACCEPTED);
        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    private Method getCalcTotalPriceMethod() throws NoSuchMethodException {
        Method method = OrderService.class.getDeclaredMethod("calcTotalPrice", List.class);
        method.setAccessible(true);
        return method;
    }

    private Order readOrderFromJson() {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
                        try{
                            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));
                        } catch (DateTimeParseException e){
                            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        }
                    }).create();
            Reader reader = Files.newBufferedReader(Paths.get("src/test/resources/data/order.json"));
            order = gson.fromJson(reader, Order.class);
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return order;
    }
}