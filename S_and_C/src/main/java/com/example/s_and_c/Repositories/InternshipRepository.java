package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Integer> {
    List<Internship> findByCompany(Company company);

    @Query("SELECT DISTINCT i FROM Internship i " +
            "WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(i.company.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "EXISTS (SELECT 1 FROM i.qualification_required q WHERE LOWER(q.qualificationName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Internship> findInternshipsByKeyword(@Param("keyword") String keyword);
    List<Internship> findByAppliedStudentsContainingIgnoreCase(Student student);

    List<Internship> findByAcceptedStudentsContainingIgnoreCase(Student student);

    /*@Modifying
    @Query("UPDATE InternshipAppliedStudents i SET i.student = :newStudent WHERE i.student = :oldStudent")
    void updateStudentInInternships(@Param("oldStudent") String oldStudent,
                                    @Param("newStudent") String newStudent);*/
}