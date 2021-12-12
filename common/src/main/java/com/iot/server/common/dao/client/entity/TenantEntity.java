package com.iot.server.common.dao.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantEntity {
    private UUID id;
    private UUID userId;
    private String email;
    private String title;
    private String address;
    private String phone;
    private String country;
    private String city;
    private String state;
    private boolean deleted;
    private UUID updateUid;
    private UUID createUid;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
