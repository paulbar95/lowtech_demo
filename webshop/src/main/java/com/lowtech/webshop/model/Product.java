package com.lowtech.webshop.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 14:15
 */
@Entity
public class Product extends AbstractEntity {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private int price; // cents

    @Getter @Setter
    private String category;

    private String imageUrl; // relative image path in bucket

    public String getImageUrl(Environment environment) {
        // prefix with current s3 bucket
        return environment.getProperty("env.data.imageS3url") + imageUrl;
    }
    public void setImageUrl(String url) {
        // remove base url before storage
        imageUrl = url.substring(url.lastIndexOf('/') + 1);
    }
}
