package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InternshipRepository extends JpaRepository<Internship, Integer> {
    Optional<Internship> findInternshipByInternshipId(long internshipId);

    List<Internship> findByCompany(Company company);

    @Query("SELECT DISTINCT i FROM Internship i " +
            "LEFT JOIN i.company c " +
            "LEFT JOIN i.qualification_required q " +
            "WHERE (LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(q.qualificationName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:minStart IS NULL OR i.startDate >= :minStart) " +
            "AND (:maxEnd IS NULL OR i.endDate <= :maxEnd) " +
            "AND (:minSalary IS NULL OR i.salary >= :minSalary)")
    List<Internship> findInternshipsBySearch(
            @Param("keyword") String keyword,
            @Param("minStart") LocalDate minStart,
            @Param("maxEnd") LocalDate maxEnd,
            @Param("minSalary") Integer minSalary
    );
    List<Internship> findByAppliedStudentsContainingIgnoreCase(Student student);

    List<Internship> findByAcceptedStudentsContainingIgnoreCase(Student student);


    List<Internship> findBySelectedStudentsContainingIgnoreCase(Student attr0);

    void deleteByAppliedStudentsContainingIgnoreCase(List<Student> appliedStudents);

    void deleteByAcceptedStudentsContainingIgnoreCase(List<Student> appliedStudents);

    void deleteByAppliedStudentsContainingIgnoreCase(Student appliedStudent);

    void deleteByAcceptedStudentsContainingIgnoreCase(Student selectedStudent);
}