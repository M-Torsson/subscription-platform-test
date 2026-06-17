package se.lexicon.subscriptionapi.dto.response;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;

import java.time.LocalDate;

public record SubscriptionResponse(
        Long id,
        Long customerId,
        String customerEmail,
        Long planId,
        String planName,
        String operatorName,
        ServiceType serviceType,
        SubscriptionStatus status,
        LocalDate startDate,
        LocalDate cancellationDate
) {
}