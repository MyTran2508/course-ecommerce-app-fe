package com.programming.courseservice.domain.persistent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "secret_key")
public class SecretKey {
    @Id
    private String id;
    @Column(name = "key_secret")
    private String keySecret;
    @Column(name = "value_secret")
    private String valueSecret;
}
