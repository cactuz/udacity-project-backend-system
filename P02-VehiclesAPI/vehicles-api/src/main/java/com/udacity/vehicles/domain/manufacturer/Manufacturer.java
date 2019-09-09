package com.udacity.vehicles.domain.manufacturer;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Declares class to hold car manufacturer information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="MANUFACTURER")
public class Manufacturer {

    @Id
    private Integer code;
    private String name;
}
