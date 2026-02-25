package com.example.ems.repository;

import com.example.ems.entity.Employee;
import com.example.ems.entity.NormalEmployee;
import com.example.ems.entity.Hod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM NormalEmployee e")
    List<NormalEmployee> findAllNormalEmployees();

    @Query("SELECT h FROM Hod h")
    List<Hod> findAllHods();

    @Query("""
        SELECT DISTINCT e
        FROM Employee e
        LEFT JOIN e.departments d
        WHERE
            (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:email IS NULL OR LOWER(e.email) LIKE LOWER(CONCAT('%', :email, '%')))
        AND (:city IS NULL OR LOWER(e.address.city) LIKE LOWER(CONCAT('%', :city, '%')))
        AND (:department IS NULL OR LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :department, '%')))
        AND (:minSalary IS NULL OR e.salary >= :minSalary)
        AND (:maxSalary IS NULL OR e.salary <= :maxSalary)
    """)
    List<Employee> searchEmployees(
            @Param("name") String name,
            @Param("email") String email,
            @Param("city") String city,
            @Param("department") String department,
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary
    );
}
