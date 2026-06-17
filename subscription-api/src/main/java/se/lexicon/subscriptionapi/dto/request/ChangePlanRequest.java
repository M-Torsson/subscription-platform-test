package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangePlanRequest(
        @NotNull(message = "New plan id is required")
        Long newPlanId
) {
}