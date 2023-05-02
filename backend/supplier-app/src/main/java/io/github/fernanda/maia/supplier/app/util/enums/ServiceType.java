package io.github.fernanda.maia.supplier.app.util.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum ServiceType {
    PF("PF"),
    PJ("PJ");

    private final String description;
}
