package com.mtgo.exam.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtgo.exam.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String orderNumber;
    private OrderStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd-HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime orderDateTime;
    private String restaurantId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;
    private int totalPrice;
    private String comment;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id", referencedColumnName = "id")
    private UserInfo userInfo;
}
