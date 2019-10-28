package be.jkin.diadmin;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/employee.feature", plugin = {"pretty", "html:target/cucumber"})
public class DiadminCucumberTest {
}
