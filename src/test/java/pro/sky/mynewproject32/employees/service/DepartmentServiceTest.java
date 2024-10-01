package pro.sky.mynewproject32.employees.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.mynewproject32.exception.EmployeeNotFoundException;
import pro.sky.mynewproject32.model.Employee;
import pro.sky.mynewproject32.service.DepartmentService;
import pro.sky.mynewproject32.service.EmployeeService;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    private final List<Employee> employees = List.of(
            new Employee("Иван", "Иванов", 50_000, 1),
            new Employee("Пётр", "Иванов", 60_000, 1),
            new Employee("Алексей", "Иванов", 70_000, 2),
            new Employee("Андрей", "Иванов", 80_000, 2),
            new Employee("Александр", "Иванов", 90_000, 3)
    );

    @BeforeEach
    public void beforeEach() {
        when(employeeService.findAll()).thenReturn(employees);
    }

    @Test
    public void findEmployeesFromDepartmentTest() {
        assertThat(departmentService.findEmployeesFromDepartment(1))
                .containsExactlyInAnyOrder(
                        new Employee("Иван", "Иванов", 50_000, 1),
                        new Employee("Пётр", "Иванов", 60_000, 1)
                );
    }

    @Test
    public void calculateSumOfSalariesOfEmployeesFromDepartmentTest() {
        assertThat(departmentService.calculateSumOfSalariesOfEmployeesFromDepartment(2))
                .isEqualTo(150_000);
    }

    @Test
    public void calculateSumOfSalariesOfEmployeesFromDepartmentNegativeTest() {
        assertThat(departmentService.calculateSumOfSalariesOfEmployeesFromDepartment(4))
                .isEqualTo(0);
    }

    @Test
    public void findMaxSalaryFromDepartmentTest() {
        assertThat(departmentService.findMaxSalaryFromDepartment(2))
                .isEqualTo(80_000);
    }

    @Test
    public void findMaxSalaryFromDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findMaxSalaryFromDepartment(4));
    }

    @Test
    public void findMinSalaryFromDepartmentTest() {
        assertThat(departmentService.findMinSalaryFromDepartment(1))
                .isEqualTo(50_000);
    }

    @Test
    public void findMinSalaryFromDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findMinSalaryFromDepartment(4));
    }

    @Test
    public void findEmployeesGroupedByDepartmentTest() {
        assertThat(departmentService.findEmployeesGroupedByDepartment())
                .containsExactlyInAnyOrderEntriesOf(
                        Map.of(
                                1,List.of(new Employee("Иван", "Иванов", 50_000, 1), new Employee("Пётр", "Иванов", 60_000, 1)),
                                2,List.of(new Employee("Алексей", "Иванов", 70_000, 2), new Employee("Андрей", "Иванов", 80_000, 2)),
                                3,List.of(new Employee("Александр", "Иванов", 90_000, 3))
                        )
                );
    }
}
