package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubscribeRequest(
        @NotNull(message = "Plan id is required")
        Long planId
) {
}