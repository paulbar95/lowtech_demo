package com.lowtech.webshop.model;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 14:18
 */

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdOn;
}
