package com.ciresafabio.carrental.domain.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RentalDto {

    private Long customerId;

    private Long carId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int distanceInKilometers;

}
