package se.lexicon.subscriptionapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public PlanResponse create(@Valid @RequestBody PlanRequest request) {
        return planService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PlanResponse update(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        return planService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        planService.delete(id);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PlanResponse> getAll() {
        return planService.getAll();
    }

    @GetMapping
    public List<PlanResponse> getActivePlans() {
        return planService.getActivePlans();
    }

    @GetMapping("/type/{serviceType}")
    public List<PlanResponse> getActivePlansByType(@PathVariable ServiceType serviceType) {
        return planService.getActivePlansByType(serviceType);
    }

    @GetMapping("/operator/{operatorId}")
    public List<PlanResponse> getPlansByOperator(@PathVariable Long operatorId) {
        return planService.getPlansByOperator(operatorId);
    }
}