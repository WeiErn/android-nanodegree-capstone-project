package com.udacity.findaflight.data;

import java.util.Date;
import java.util.List;

public class OutboundLeg {
    private List<Integer> carrierIds;
    private int originId;
    private int destinationId;
    private Date departureDate;

    public OutboundLeg(List<Integer> carrierIds, int originId, int destinationId, Date departureDate) {
        this.carrierIds = carrierIds;
        this.originId = originId;
        this.destinationId = destinationId;
        this.departureDate = departureDate;
    }

    public List<Integer> getCarrierIds() {
        return carrierIds;
    }

    public int getOriginId() {
        return originId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }
}
