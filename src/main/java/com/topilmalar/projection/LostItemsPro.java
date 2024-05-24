package com.topilmalar.projection;

import com.topilmalar.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostItemsPro {
    private Long id;
    private String type;
    private String subType;
    private String region;
    private String district;
    private boolean found;
    private LocalDateTime lostDate;
    private String organization;
    private Status status;
    private String username;
    private String description;
    private String registry;
    private Long ownerid;
}
