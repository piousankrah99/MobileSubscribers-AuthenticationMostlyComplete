package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriberCount {

    private long totalCount;

    private long totalPrepaidCount;

    private long totalPostpaidCount;
}
