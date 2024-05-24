package com.topilmalar.payload;

public record LostItemDto(
        Long typeId,
        Long subTypeId,
        Long regionId,
        Long districtId,
        String organization,
        String status,
        String description  ) {
}
