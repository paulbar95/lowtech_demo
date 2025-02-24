package com.lowtech.webshop.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;


/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 14:15
 */
@Setter
@Getter
@Entity
public class Product extends AbstractEntity {

    private String name;

    @Column(length = 1000)
    private String description;

    private int price; // cents

    private String category;

    private String imageUrl; // relative image path in bucket

}