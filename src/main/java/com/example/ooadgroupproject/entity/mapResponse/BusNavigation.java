package com.example.ooadgroupproject.entity.mapResponse;

import lombok.Data;

@Data
public class BusNavigation {
    String originStop;
    String destinationStop;
    Integer duration;
    Integer lineId;
}
