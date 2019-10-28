package be.jkin.diadmin.web;

import be.jkin.diadmin.exception.RecordNotFoundException;
import be.jkin.diadmin.model.EmployeeEntity;
import be.jkin.diadmin.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class EmployeeMvcController {
    @Autowired
    EmployeeService service;

    @GetMapping(path="/employees", produces = "application/json")
    public List<EmployeeEntity> getEmployees()
    {
        return service.getAllEmployees();
    }

    @RequestMapping
    public String getAllEmployees(Model model)
    {
        List<EmployeeEntity> list = service.getAllEmployees();

        model.addAttribute("employees", list);
        return "list-employees";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editEmployeeById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        if (id.isPresent()) {
            EmployeeEntity entity = service.getEmployeeById(id.get());
            model.addAttribute("employee", entity);
        } else {
            model.addAttribute("employee", new EmployeeEntity());
        }
        return "add-edit-employee";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        service.deleteEmployeeById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createEmployee", method = RequestMethod.POST)
    public String createOrUpdateEmployee(EmployeeEntity employee)
    {
        service.createOrUpdateEmployee(employee);
        return "redirect:/";
    }

    @PostMapping("/employees")
    public String createEmployee(@Valid @RequestBody EmployeeEntity employeeEntity)
    {
        EmployeeEntity employee = service.createOrUpdateEmployee(employeeEntity);
        //return  employee;
        return "redirect:/";
    }
}
