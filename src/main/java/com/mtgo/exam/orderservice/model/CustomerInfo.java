package com.mtgo.exam.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "customer_info")
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_info_id")
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private int phone;
    private String address;
    // TODO: Maybe Change relationship
    @OneToOne(mappedBy = "customerInfo")
    private Order order;
}
