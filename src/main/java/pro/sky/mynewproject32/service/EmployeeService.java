package pro.sky.mynewproject32.service;

import org.springframework.stereotype.Service;
import pro.sky.mynewproject32.exception.EmployeeAlreadyExistsException;
import pro.sky.mynewproject32.exception.EmployeeNotFoundException;
import pro.sky.mynewproject32.exception.EmployeeStorageIsFullException;
import pro.sky.mynewproject32.model.Employee;

import java.util.*;

@Service
public class EmployeeService {

    private final int max = 5;
    private final Map<String, Employee> employees = new HashMap<>();

    private final ValidationService validationService;

    public EmployeeService(ValidationService validationService) {
        this.validationService = validationService;
    }

    public Employee add(String name, String surname, int salary, int department) {
        if (employees.size()>=max){
            throw new EmployeeStorageIsFullException();
        }
        String key = buildKey(name, surname);
        if (employees.containsKey(key)){
            throw new EmployeeAlreadyExistsException();
        }
        Employee employee = new Employee(
                validationService.validate(name),
                validationService.validate(surname),
                salary,
                department);
        employees.put(key, employee);
        return employee;
    }

    public Employee find(String name, String surname) {
        String key = buildKey(name, surname);
        if (!employees.containsKey(key)){
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    public Employee remove(String name, String surname) {
        String key = buildKey(name, surname);
        if (!employees.containsKey(key)){
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);
    }

    public Collection<Employee> findAll(){
        return new ArrayList<>(employees.values());
    }

    private String buildKey(String name, String surname) {
        return name + surname;
    }

}
