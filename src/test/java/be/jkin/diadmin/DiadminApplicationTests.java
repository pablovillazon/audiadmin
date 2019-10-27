package be.jkin.diadmin;

import be.jkin.diadmin.exception.RecordNotFoundException;
import be.jkin.diadmin.model.EmployeeEntity;
import be.jkin.diadmin.repository.EmployeeRepository;
import be.jkin.diadmin.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiadminApplicationTests {
	@Mock
	EmployeeService service;

	@Autowired
	private EmployeeRepository employeeRepository;

	@LocalServerPort
	private int port;

	@Mock
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@BeforeEach
	void initUseCase()
	{
		service = new EmployeeService(employeeRepository);
	}

	@Test
	@DisplayName("Test GetAll employees")
	public void getEmployees() {
		List<EmployeeEntity> employees = service.getAllEmployees();
		assertNotNull(employees);
	}

	@Test
	@DisplayName("Test Add Employee")
	public void addEmployee()
	{
		int initialSize = 0;
		int finalSize = 0;

		List<EmployeeEntity> employees = service.getAllEmployees();
		initialSize = employees.size();

		//Create a new Employee
		EmployeeEntity newEmployee = new EmployeeEntity("Pablo", "Villazon2", "pablo@jkin.be");
		service.createOrUpdateEmployee(newEmployee);

		finalSize = service.getAllEmployees().size();

		assertTrue(finalSize > initialSize);
	}

	@Test
	@DisplayName("Test Add Employee consistency")
	public void addEmployeeConsistency() throws RecordNotFoundException {
		//Create a new Employee
		EmployeeEntity newEmployee = new EmployeeEntity("Pablo", "Villazon3", "pablo@jkin.be");
		newEmployee = service.createOrUpdateEmployee(newEmployee);

		assertEquals(service.getEmployeeById(newEmployee.getId()).getLastName(), "Villazon3");
	}

	@Test
	@DisplayName("Test Delete employee")
	public void deleteEmployee() throws RecordNotFoundException {
		//Create a new Employee
		EmployeeEntity newEmployee = new EmployeeEntity("Pablo", "Villazon4", "pablo@jkin.be");
		newEmployee = service.createOrUpdateEmployee(newEmployee);

		assertNotNull(newEmployee.getId());

		EmployeeEntity finalNewEmployee = newEmployee;
		assertDoesNotThrow(() -> service.deleteEmployeeById(finalNewEmployee.getId()));
	}

	@Test
	@DisplayName("Test edit employee")
	public void testEditEmployee()
	{
		//Create a new Employee
		EmployeeEntity newEmployee = new EmployeeEntity("Pablo", "Villazon5", "pablo@jkin.be");
		newEmployee = service.createOrUpdateEmployee(newEmployee);

		assertNotNull(newEmployee.getId());

		newEmployee.setLastName("Villazon6");

		//Update employee
		newEmployee = service.createOrUpdateEmployee(newEmployee);

		assertEquals(newEmployee.getLastName(), "Villazon6");

	}

}
