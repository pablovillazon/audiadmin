package be.jkin.diadmin;

import be.jkin.diadmin.model.EmployeeEntity;

import io.cucumber.java8.En;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


public class EmployeeCucumberDefinitions extends CucumberIntegrationTests implements En {
    private final Logger log = LoggerFactory.getLogger(EmployeeCucumberDefinitions.class);

    public EmployeeCucumberDefinitions(){
        Given("the list is empty", () -> {
            clean();
            assertThat(getEmployees().isEmpty()).isTrue();
        });

        When("I put {int} {employee} in the list", (Integer quantity, EmployeeEntity employeeEntity) -> {
            IntStream.range(0, quantity).peek(n -> log.info("Putting a {} in the list, number {}", employeeEntity, quantity))
                    .map(ignore -> put(employeeEntity))
                    .forEach(statusCode -> assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value()));
        });

        Then("the list should contain only {int} {employee}", (Integer quantity, EmployeeEntity employeeEntity) -> {
            assertNumberOfTimes(quantity, employeeEntity, true);
        });

        Then("the bag should contain {int} {employee}", (Integer quantity, EmployeeEntity employeeEntity) -> {
            assertNumberOfTimes(quantity, employeeEntity, false);
        });

    }
    private void assertNumberOfTimes(final int quantity, final EmployeeEntity employeeEntity, final boolean onlyThat) {
        final List<EmployeeEntity> employees = getEmployees();
        log.info("Expecting {} times {}. The list contains {}", quantity, employeeEntity, employees);
        final int timesInList = Collections.frequency(employees, employeeEntity);
        assertThat(timesInList).isEqualTo(quantity);
        if (onlyThat) {
            assertThat(timesInList).isEqualTo(employees.size());
        }
    }

}
