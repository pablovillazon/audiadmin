package be.jkin.diadmin;

import be.jkin.diadmin.model.EmployeeEntity;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class CucumberIntegrationTests {
    private final String SERVER_URL = "http://localhost";
    private final String EMPLOYEES_ENDPOINT = "/employees";

    private RestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    public CucumberIntegrationTests(){
        this.restTemplate = new RestTemplate();
    }
    private String employeesEndpoint(){
        return SERVER_URL+":"+port+EMPLOYEES_ENDPOINT;
    }

    int put(EmployeeEntity employeeEntity)
    {
        return restTemplate.postForEntity(employeesEndpoint(), employeeEntity, Void.class).getStatusCodeValue();
    }

    List<EmployeeEntity> getEmployees(){
        return restTemplate.getForEntity(employeesEndpoint(), List.class).getBody();
    }
    void clean(){
        restTemplate.delete(employeesEndpoint());
    }

}
