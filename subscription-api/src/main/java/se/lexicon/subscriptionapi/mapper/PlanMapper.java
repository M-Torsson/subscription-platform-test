package se.lexicon.subscriptionapi.mapper;

import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;

@Component
public class PlanMapper {

    public Plan toEntity(PlanRequest request, Operator operator) {
        Plan plan = new Plan();
        plan.setName(request.name());
        plan.setPrice(request.price());
        plan.setServiceType(request.serviceType());
        plan.setDataLimit(request.dataLimit());
        plan.setActive(request.active());
        plan.setOperator(operator);
        return plan;
    }

    public PlanResponse toResponse(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getPrice(),
                plan.getServiceType(),
                plan.getDataLimit(),
                plan.isActive(),
                plan.getOperator().getId(),
                plan.getOperator().getName()
        );
    }
}