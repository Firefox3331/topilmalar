package com.topilmalar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LostItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Type type;
    @ManyToOne
    private SubType subType;
    @ManyToOne
    private Region region;
    @ManyToOne
    private District district;
    private Boolean found;
    private LocalDateTime lostDate;
    private String organization;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Users users;
    private String description;
    private String registry;
    @ManyToOne
    private Users owner;
    private boolean foundstatus;

}