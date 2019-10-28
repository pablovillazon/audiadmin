package be.jkin.diadmin;

import be.jkin.diadmin.model.EmployeeEntity;
import be.jkin.diadmin.repository.EmployeeRepository;
import be.jkin.diadmin.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import javax.xml.ws.Response;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DiadminApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    EmployeeService service;

    @Autowired
    private EmployeeRepository employeeRepository;


    @BeforeEach
    void initUseCase()
    {
        service = new EmployeeService(employeeRepository);
    }


    @Sql({"schema.sql", "data.sql"})
    @Test
    @DisplayName("Test Integration")
    public void testAllEmployees()
    {
        EmployeeEntity employee = restTemplate.getForObject("http://localhost:"+port+"/employees", EmployeeEntity.class);
        String last = employee.getLastName();

        assertNull(last);
    }

    @Test
    public void testAddEmployee()
    {
        String url = "http://localhost:"+port+"/employees";
        EmployeeEntity employeeEntity = new EmployeeEntity("Pablo", "Villazon", "pablo@jkin.be");
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, employeeEntity, String.class);
        assertEquals(302, responseEntity.getStatusCodeValue());
    }
}
