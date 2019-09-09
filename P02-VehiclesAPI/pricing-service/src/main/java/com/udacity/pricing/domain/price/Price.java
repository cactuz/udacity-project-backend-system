package com.udacity.pricing.domain.price;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Price {
    @Id
    @Column(name ="VEHICLE_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long vehicleId;
    private String currency;
    private BigDecimal price;
}
