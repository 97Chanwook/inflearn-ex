package com.wookis.ex.dev.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DevDeliveryCode {
    private String code;
    private String displayName;
}
