package com.mtgo.exam.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.utils.OrderStatusConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int id;
    private String orderNumber;
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd-HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime orderDateTime;
    private String restaurantId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;
    private BigDecimal totalPrice;
    private String comment;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_info_id", referencedColumnName = "customer_info_id")
    private CustomerInfo customerInfo;
}
