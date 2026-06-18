package se.lexicon.subscriptionapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.request.ChangePlanRequest;
import se.lexicon.subscriptionapi.dto.request.SubscribeRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.exception.BusinessRuleException;
import se.lexicon.subscriptionapi.exception.ResourceNotFoundException;
import se.lexicon.subscriptionapi.mapper.SubscriptionMapper;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.repository.SubscriptionRepository;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionResponse subscribe(Long customerId, SubscribeRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + request.planId()));

        if (!plan.isActive()) {
            throw new BusinessRuleException("Cannot subscribe to inactive plan");
        }

        boolean hasActiveSubscription = subscriptionRepository.existsByCustomerIdAndPlanServiceTypeAndStatus(
                customerId,
                plan.getServiceType(),
                SubscriptionStatus.ACTIVE
        );

        if (hasActiveSubscription) {
            throw new BusinessRuleException("Customer already has an active subscription for service type: " + plan.getServiceType());
        }

        Subscription subscription = new Subscription();
        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartDate(LocalDate.now());

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(savedSubscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getCustomerSubscriptions(Long customerId) {
        return subscriptionRepository.findByCustomerId(customerId).stream()
                .map(subscriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubscriptionResponse changePlan(Long customerId, Long subscriptionId, ChangePlanRequest request) {
        Subscription subscription = subscriptionRepository.findByCustomerIdAndId(customerId, subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new BusinessRuleException("Only active subscriptions can be changed");
        }

        Plan newPlan = planRepository.findById(request.newPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + request.newPlanId()));

        if (!newPlan.isActive()) {
            throw new BusinessRuleException("Cannot change to inactive plan");
        }

        if (!subscription.getPlan().getOperator().getId().equals(newPlan.getOperator().getId())) {
            throw new BusinessRuleException("Plan change is allowed only within the same operator");
        }

        if (subscription.getPlan().getServiceType() != newPlan.getServiceType()) {
            throw new BusinessRuleException("Plan change is allowed only within the same service type");
        }

        subscription.setPlan(newPlan);

        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(updatedSubscription);
    }

    @Override
    @Transactional
    public void cancelSubscription(Long customerId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findByCustomerIdAndId(customerId, subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));

        if (subscription.getStatus() == SubscriptionStatus.CANCELLED) {
            throw new BusinessRuleException("Subscription is already cancelled");
        }

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setCancellationDate(LocalDate.now());

        subscriptionRepository.save(subscription);
    }
}