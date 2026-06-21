package se.lexicon.subscriptionapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.dto.request.ChangePlanRequest;
import se.lexicon.subscriptionapi.dto.request.SubscribeRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public SubscriptionResponse subscribe(
            @PathVariable Long customerId,
            @Valid @RequestBody SubscribeRequest request
    ) {
        return subscriptionService.subscribe(customerId, request);
    }

    @GetMapping
    public List<SubscriptionResponse> getCustomerSubscriptions(@PathVariable Long customerId) {
        return subscriptionService.getCustomerSubscriptions(customerId);
    }

    @PutMapping("/{subscriptionId}/change-plan")
    public SubscriptionResponse changePlan(
            @PathVariable Long customerId,
            @PathVariable Long subscriptionId,
            @Valid @RequestBody ChangePlanRequest request
    ) {
        return subscriptionService.changePlan(customerId, subscriptionId, request);
    }

    @DeleteMapping("/{subscriptionId}")
    public void cancelSubscription(
            @PathVariable Long customerId,
            @PathVariable Long subscriptionId
    ) {
        subscriptionService.cancelSubscription(customerId, subscriptionId);
    }
}