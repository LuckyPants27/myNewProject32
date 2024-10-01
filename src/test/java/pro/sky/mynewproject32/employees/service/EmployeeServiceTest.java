package pro.sky.mynewproject32.employees.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.mynewproject32.exception.EmployeeAlreadyExistsException;
import pro.sky.mynewproject32.exception.EmployeeNotFoundException;
import pro.sky.mynewproject32.exception.EmployeeStorageIsFullException;
import pro.sky.mynewproject32.model.Employee;
import pro.sky.mynewproject32.service.EmployeeService;
import pro.sky.mynewproject32.service.ValidationService;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService(new ValidationService());
    private final List<Employee> employees = List.of(
            new Employee("Иван", "Иванов", 50_000, 1),
            new Employee("Пётр", "Иванов", 60_000, 1),
            new Employee("Алексей", "Иванов", 70_000, 1)
    );

    @BeforeEach
    public void beforeEach() {
        employees.forEach(employee -> employeeService.add(employee.getName(), employee.getSurname(), employee.getSalary(), employee.getDepartment()));
    }

    @AfterEach
    public void afterEach() {
        employeeService.findAll().forEach(employee -> employeeService.remove(employee.getName(), employee.getSurname()));
    }

    @Test
    public void addTest() {
        Employee expected = new Employee("Андрей", "Иванов",  80_000,2);

        Employee actual = employeeService.add("Андрей", "Иванов",  80_000,2);

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualTo(employeeService.find("Андрей", "Иванов"));
        assertThat(actual).isIn(employeeService.findAll());
    }

    @Test
    public void whenEmployeeServiceIsFullThenEmployeeStorageIsFullExceptionWillBeThrown() {
        employeeService.add("Андрей", "Иванов",  80_000,2);
        employeeService.add("Артём", "Иванов",  90_000,2);

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add("Александр", "Иванов", 100_000, 3));
    }

    @Test
    public void when_employeeService_contains_employee_then_EmployeeAlreadyExistsException_will_be_thrown() {
        assertThatExceptionOfType(EmployeeAlreadyExistsException.class)
                .isThrownBy(() -> employeeService.add("Иван", "Иванов", 50_000, 1));
    }

    @Test
    public void findTest() {
        Employee expected = new Employee("Иван", "Иванов", 50_000, 1);
        assertThat(employeeService.findAll()).contains(expected);

        Employee actual = employeeService.find("Иван", "Иванов");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Андрей", "Иванов"));
    }

    @Test
    public void removeTest() {
        Employee expected = new Employee("Иван", "Иванов", 50_000, 1);
        assertThat(employeeService.findAll()).contains(expected);
        assertThat(employeeService.find("Иван", "Иванов")).isEqualTo(expected);


        Employee actual = employeeService.remove("Иван", "Иванов");

        assertThat(actual).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Иван", "Иванов"));
        assertThat(actual).isNotIn(employeeService.findAll());
    }

    @Test
    public void removeNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.remove("Андрей", "Иванов"));
    }

    @Test
    public void findAllTest() {
        assertThat(employeeService.findAll())
                .containsExactlyInAnyOrderElementsOf(employees);

    }
}
