package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;

import java.util.List;

public interface PlanService {

    PlanResponse create(PlanRequest request);

    PlanResponse update(Long id, PlanRequest request);

    void delete(Long id);

    List<PlanResponse> getAll();

    List<PlanResponse> getActivePlans();

    List<PlanResponse> getActivePlansByType(ServiceType serviceType);

    List<PlanResponse> getPlansByOperator(Long operatorId);
}