package se.lexicon.subscriptionapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.lexicon.subscriptionapi.domain.constant.Role;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final OperatorRepository operatorRepository;
    private final PlanRepository planRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdminUser();
        seedRegularUser();
        seedOperatorsAndPlans();
    }

    private void seedAdminUser() {
        String adminEmail = "admin@example.com";
        if (!customerRepository.existsByEmail(adminEmail)) {
            Customer admin = new Customer();
            admin.setEmail(adminEmail);
            admin.setFirstName("Admin");
            admin.setLastName("Adminson");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
            customerRepository.save(admin);
        }
    }

    private void seedRegularUser() {
        String userEmail = "user@example.com";
        if (!customerRepository.existsByEmail(userEmail)) {
            Customer user = new Customer();
            user.setEmail(userEmail);
            user.setFirstName("User");
            user.setLastName("Userson");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(Role.ROLE_USER));
            customerRepository.save(user);
        }
    }

    private void seedOperatorsAndPlans() {
        if (operatorRepository.count() > 0) {
            return;
        }

        Operator telia = new Operator();
        telia.setName("Telia");

        Operator telenor = new Operator();
        telenor.setName("Telenor");

        operatorRepository.save(telia);
        operatorRepository.save(telenor);

        createPlan("Fiber 50", new BigDecimal("299.00"), ServiceType.INTERNET, 50, true, telia);
        createPlan("Fiber 100", new BigDecimal("399.00"), ServiceType.INTERNET, 100, true, telia);
        createPlan("Mobile Basic", new BigDecimal("149.00"), ServiceType.MOBILE, 10, true, telia);
        createPlan("Old Mobile Plan", new BigDecimal("99.00"), ServiceType.MOBILE, 5, false, telia);

        createPlan("Fiber 300", new BigDecimal("599.00"), ServiceType.INTERNET, 300, true, telenor);
        createPlan("Mobile Plus", new BigDecimal("249.00"), ServiceType.MOBILE, 50, true, telenor);
        createPlan("Mobile Unlimited", new BigDecimal("399.00"), ServiceType.MOBILE, null, true, telenor);
        createPlan("Inactive Fiber", new BigDecimal("199.00"), ServiceType.INTERNET, 30, false, telenor);
    }

    private void createPlan(String name, BigDecimal price, ServiceType serviceType, Integer dataLimit, boolean active, Operator operator) {
        Plan plan = new Plan();
        plan.setName(name);
        plan.setPrice(price);
        plan.setServiceType(serviceType);
        plan.setDataLimit(dataLimit);
        plan.setActive(active);
        plan.setOperator(operator);
        planRepository.save(plan);
    }
}