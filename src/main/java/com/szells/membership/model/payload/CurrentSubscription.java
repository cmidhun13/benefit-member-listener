package com.szells.membership.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CurrentSubscription {

    private long id;

    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("end_date")
    private Date endDate;

    private String status;

    @JsonProperty("cancelled_date")
    private Date cancelledDate;

    @JsonProperty("cancellation_reason")
    private String cancellationReason;
}
