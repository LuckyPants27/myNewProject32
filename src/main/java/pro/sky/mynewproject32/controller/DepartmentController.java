package pro.sky.mynewproject32.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.mynewproject32.model.Employee;
import pro.sky.mynewproject32.service.DepartmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> findEmployeesFromDepartment(@PathVariable("id") int department){
        return departmentService.findEmployeesFromDepartment(department);
    }

    @GetMapping("/{id}/salary/sum")
    public int calculateSumOfSalariesOfEmployeesFromDepartment(@PathVariable("id") int department){
        return departmentService.calculateSumOfSalariesOfEmployeesFromDepartment(department);
    }

    @GetMapping("/{id}/salary/max")
    public int findMaxSalaryFromDepartment(@PathVariable("id") int department){
        return departmentService.findMaxSalaryFromDepartment(department);
    }

    @GetMapping("/{id}/salary/min")
    public int findMinSalaryFromDepartment(@PathVariable("id") int department){
        return departmentService.findMinSalaryFromDepartment(department);
    }

    @GetMapping("/employees")
    public Map<Integer,List<Employee>> findEmployeesGroupedByDepartment(){
        return departmentService.findEmployeesGroupedByDepartment();
    }
}
