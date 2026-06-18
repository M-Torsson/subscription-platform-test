package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.dto.request.ChangePlanRequest;
import se.lexicon.subscriptionapi.dto.request.SubscribeRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponse subscribe(Long customerId, SubscribeRequest request);

    List<SubscriptionResponse> getCustomerSubscriptions(Long customerId);

    SubscriptionResponse changePlan(
            Long customerId,
            Long subscriptionId,
            ChangePlanRequest request
    );

    void cancelSubscription(Long customerId, Long subscriptionId);
}