package se.lexicon.subscriptionapi.mapper;

import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

@Component
public class SubscriptionMapper {

    public SubscriptionResponse toResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getCustomer().getId(),
                subscription.getCustomer().getEmail(),
                subscription.getPlan().getId(),
                subscription.getPlan().getName(),
                subscription.getPlan().getOperator().getName(),
                subscription.getPlan().getServiceType(),
                subscription.getStatus(),
                subscription.getStartDate(),
                subscription.getCancellationDate()
        );
    }
}