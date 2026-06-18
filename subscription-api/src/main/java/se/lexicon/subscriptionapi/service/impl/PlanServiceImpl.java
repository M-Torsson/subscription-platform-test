package se.lexicon.subscriptionapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.PlanMapper;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final OperatorRepository operatorRepository;
    private final PlanMapper planMapper;

    @Override
    @Transactional
    public PlanResponse create(PlanRequest request) {
        Operator operator = operatorRepository.findById(request.operatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + request.operatorId()));

        Plan plan = planMapper.toEntity(request, operator);
        Plan savedPlan = planRepository.save(plan);
        return planMapper.toResponse(savedPlan);
    }

    @Override
    @Transactional
    public PlanResponse update(Long id, PlanRequest request) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));

        Operator operator = operatorRepository.findById(request.operatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + request.operatorId()));

        plan.setName(request.name());
        plan.setPrice(request.price());
        plan.setServiceType(request.serviceType());
        plan.setDataLimit(request.dataLimit());
        plan.setActive(request.active());
        plan.setOperator(operator);

        Plan updatedPlan = planRepository.save(plan);
        return planMapper.toResponse(updatedPlan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));

        planRepository.delete(plan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> getAll() {
        return planRepository.findAll().stream()
                .map(planMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> getActivePlans() {
        return planRepository.findByActiveTrue().stream()
                .map(planMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> getActivePlansByType(ServiceType serviceType) {
        return planRepository.findByActiveTrueAndServiceType(serviceType).stream()
                .map(planMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> getPlansByOperator(Long operatorId) {
        return planRepository.findByOperatorIdAndActiveTrue(operatorId).stream()
                .map(planMapper::toResponse)
                .collect(Collectors.toList());
    }
}