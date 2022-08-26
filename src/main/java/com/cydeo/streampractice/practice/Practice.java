package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() { //--------------------->> 1.OK
        return employeeService.readAll();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {//--------------------->> 2.OK

        return countryService.readAll();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {//--------------------->> 3.OK

        return departmentService.readAll();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {//--------------------->> 4.OK

        return jobService.readAll();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {//--------------------->> 5.OK

        return locationService.readAll();

    }

    // Display all the regions
    public static List<Region> getAllRegions() {//--------------------->> 6.OK

        return regionService.readAll();

    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {//--------------------->> 7.OK

        return jobHistoryService.readAll();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {//--------------------->> 8.OK

        return employeeService.readAll().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());

    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() { //--------------------->> 9.OK
        return countryService.readAll().stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());

    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {//--------------------->> 10.OK

        return departmentService.readAll().stream()
                //.map(department -> department.getManager().getFirstName())
                .map(Department::getManager)
                .map(Employee::getFirstName)
                .collect(Collectors.toList());



    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {//--------------------->> 11.OK

        return departmentService.readAll().stream()
                .filter(department -> department.getManager().getFirstName().equals("Steven"))
                //.anyMatch(department -> department.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());

    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {//--------------------->> 12.OK

        return departmentService.readAll().stream()
                .filter(department -> department.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());

    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {//--------------------->> 13.OK BUT review again???

        Optional<Region> region = departmentService.readAll().stream()
                .filter(department -> department.getDepartmentName().equals("IT"))
                .map(department -> department.getLocation())
                .map(location -> location.getCountry())
                .map(country -> country.getRegion())
                .findFirst();

        if (region.isPresent()){
            return region.get();
        }

        return null;
    }


    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {//--------------------->> 14.OK

       return departmentService.readAll().stream()
                .filter(department -> department.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .collect(Collectors.toList());

    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() {//--------------------->> 15.OK

        return employeeService.readAll().stream()
                .noneMatch(employee -> employee.getSalary() < 1000);

    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {//--------------------->> 16.OK

        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
                .allMatch(employee -> employee.getSalary() > 2000);


    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {//--------------------->> 17.OK

        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary() < 5000)
                .collect(Collectors.toList());
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {//--------------------->> 18.OK

        return employeeService.readAll().stream()
                .filter(employee -> (employee.getSalary() > 6000 && employee.getSalary() < 7000))
                .collect(Collectors.toList());

    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {//--------------------->> 19.OK

         return employeeService.readAll().stream()
                .filter(employee -> employee.getFirstName().equals("Douglas") && employee.getLastName().equals("Grant"))
                .map(employee -> employee.getSalary())
                 .reduce(Long :: max).get();
                 //.findFirst().get();
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {//--------------------->> 20.OK

        /*
        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .findFirst().get().getSalary();


         */
        return employeeService.readAll().stream()
                .map(Employee::getSalary)
                .reduce(Long :: max).get();

    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {//--------------------->> 21.OK

        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(1).collect(Collectors.toList());


    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {//--------------------->> 22.OK

        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .findFirst().get().getJob();

    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {//--------------------->> 23.OK

        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .map(Employee::getSalary)
                .findFirst().get();


    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {//--------------------->> 24.OK

        return employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .map(Employee::getSalary)
                .skip(1)
                .findFirst().get();


    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {//--------------------->> 25.OK

        Long secondMax =  employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .map(Employee::getSalary)
                .skip(1)
                .findFirst().get();

        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary().equals(secondMax))
                .collect(Collectors.toList());


    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {//--------------------->> 26.OK

        return  employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .findFirst().get();

    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {//--------------------->> 27.OK

       Long minSalary =  employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .findFirst().get();

       return employeeService.readAll().stream()
               .filter(employee -> employee.getSalary().equals(minSalary))
               .collect(Collectors.toList());

    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception { //--------------------->> 28.OK

        Long minSalary =  employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .findFirst().get();

       Long count =  employeeService.readAll().stream().filter(employee -> employee.getSalary().equals(minSalary)).count();

       return employeeService.readAll().stream()
               .sorted(Comparator.comparing(Employee::getSalary))
               .map(Employee::getSalary)
               .skip(count)
               .findFirst().get();

    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() { //--------------------->> 29.OK
        Long minSalary =  employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .findFirst().get();

        Long count =  employeeService.readAll().stream().filter(employee -> employee.getSalary().equals(minSalary)).count();

        Long secondMinSalary =  employeeService.readAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .map(Employee::getSalary)
                .skip(count)
                .findFirst().get();

        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary().equals(secondMinSalary))
                .collect(Collectors.toList());


    }

    // Display the average salary of the employees
    public static Double getAverageSalary() { //--------------------->> 30.OK

        return employeeService.readAll().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {//--------------------->> 31.OK

        Double avr = employeeService.readAll().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary() > avr)
                .collect(Collectors.toList());
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {//--------------------->> 32.OK

        Double avr = employeeService.readAll().stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary() < avr)
                .collect(Collectors.toList());

    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {//--------------------->> 33.OK

       return employeeService.readAll().stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment().getId()));


    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {//--------------------->> 34.OK

        return departmentService.readAll().stream().count();

    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception { //--------------------->> 35.OK

        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("Sales"))
                .filter(employee -> employee.getManager().getFirstName().equals("Eleni"))
                .filter(employee -> employee.getFirstName().equals("Alyssa"))
                .findFirst().get();

    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {//--------------------->> 36.OK

        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());

    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {//--------------------->> 37.OK
        return jobHistoryService.readAll().stream()
                .sorted(Comparator.comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {//--------------------->> 38.OK

        return jobHistoryService.readAll().stream()
                .filter(jobHistoryService -> jobHistoryService.getStartDate().isAfter(LocalDate.of(2005,1,1)))
                .collect(Collectors.toList());

    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {//--------------------->> 39.OK

        return jobHistoryService.readAll().stream()
                .filter(jobHistory -> jobHistory.getJob().getJobTitle().equals("Programmer"))
                .filter(jobHistory -> jobHistory.getEndDate().isEqual(LocalDate.of(2007,12,31)))
                .collect(Collectors.toList());

    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        //--------------------->> 40.OK

        return jobHistoryService.readAll().stream()
                .filter(jobHistory -> jobHistory.getStartDate().isEqual(LocalDate.of(2007,1,1)) && jobHistory.getEndDate().isEqual(LocalDate.of(2007,12,31)) )
                .map(JobHistory::getEmployee)
                .findFirst().get();

    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        //--------------------->> 41.OK
        return employeeService.readAll().stream()
                .filter(employee -> employee.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        //--------------------->> 42.OK
        return employeeService.readAll().stream()
                .filter(employee -> employee.getJob().getId().contains("IT"))
                .collect(Collectors.toList());

    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        //--------------------->> 43.OK

        return employeeService.readAll().stream()
                .filter(employee -> employee.getJob().getJobTitle().equals("Programmer"))
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
                .count();

    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        //--------------------->> 44.OK

        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getId().equals(50L)
                        ||  employee.getDepartment().getId().equals(80L)
                        ||  employee.getDepartment().getId().equals(100L))
                .collect(Collectors.toList());

    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        //--------------------->> 45.OK
        return employeeService.readAll().stream()
                .map(employee -> employee.getFirstName().charAt(0) + "" + employee.getLastName().charAt(0))
                .collect(Collectors.toList());

    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        //--------------------->> 46.OK
        return employeeService.readAll().stream()
                .map(employee -> employee.getFirstName() +" " + employee.getLastName())
                .collect(Collectors.toList());


    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        //--------------------->> 47. OK

        return getAllEmployeesFullNames().stream()
                .map(String::length)
                .reduce(Integer::max).orElse(-1);



    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        //--------------------->> 48.?????

        Integer max = getAllEmployeesFullNames().stream()
                .map(String::length)
                .reduce(Integer::max).get();

        return employeeService.readAll().stream()
                .filter(employee -> (employee.getFirstName().length() + employee.getLastName().length() == max))
                .collect(Collectors.toList());

    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        //--------------------->> 49.OK

        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getId().equals(90l) ||
                        employee.getDepartment().getId().equals(60l) ||
                        employee.getDepartment().getId().equals(100l) ||
                        employee.getDepartment().getId().equals(120l)||
                        employee.getDepartment().getId().equals(130l) )
                .collect(Collectors.toList());

    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        //--------------------->> 50.OK
        return employeeService.readAll().stream()
                .filter(employee -> !(employee.getDepartment().getId().equals(90l) ||
                        employee.getDepartment().getId().equals(60l) ||
                        employee.getDepartment().getId().equals(100l) ||
                        employee.getDepartment().getId().equals(120l)||
                        employee.getDepartment().getId().equals(130l) ))
                .collect(Collectors.toList());

    }

}
