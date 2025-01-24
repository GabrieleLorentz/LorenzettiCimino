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

    List<Internship> findByNameContainingIgnoreCase(String keyword);

    // Modifica per la lista di qualifiche
    @Query("SELECT DISTINCT i FROM Internship i JOIN i.qualification_required q WHERE LOWER(q) LIKE LOWER(CONCAT('%', :qualification, '%'))")
    List<Internship> findByQualificationContainingIgnoreCase(List<String> qualification);

    List<Internship> findByEndDateIsLessThanEqual(LocalDate maxEnd);
    List<Internship> findByStartDateGreaterThanEqual(LocalDate minStart);

    // Keyword + Company
    @Query("SELECT i FROM Internship i WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND " +
            "LOWER(i.company.name) LIKE LOWER(CONCAT('%', :companyName, '%'))")
    List<Internship> findByKeywordAndCompany(
            @Param("keyword") String keyword,
            @Param("companyName") String companyName
    );

    // Keyword + Date ranges
    @Query("SELECT i FROM Internship i WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND " +
            "(:maxEnd IS NULL OR i.endDate <= :maxEnd) AND " +
            "(:minStart IS NULL OR i.startDate >= :minStart)")
    List<Internship> findByKeywordAndDates(
            @Param("keyword") String keyword,
            @Param("maxEnd") LocalDate maxEnd,
            @Param("minStart") LocalDate minStart
    );

    // Keyword + Qualification
    @Query("SELECT DISTINCT i FROM Internship i JOIN i.qualification_required q WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND " +
            "LOWER(q) LIKE LOWER(CONCAT('%', :qualification, '%'))")
    List<Internship> findByKeywordAndQualification(
            @Param("keyword") String keyword,
            @Param("qualification") List<String> qualification
    );

    // Company + Date ranges
    @Query("SELECT i FROM Internship i WHERE " +
            "LOWER(i.company.name) LIKE LOWER(CONCAT('%', :companyName, '%')) AND " +
            "(:maxEnd IS NULL OR i.endDate <= :maxEnd) AND " +
            "(:minStart IS NULL OR i.startDate >= :minStart)")
    List<Internship> findByCompanyAndDates(
            @Param("companyName") String companyName,
            @Param("maxEnd") LocalDate maxEnd,
            @Param("minStart") LocalDate minStart
    );

    // Company + Qualification
    @Query("SELECT DISTINCT i FROM Internship i JOIN i.qualification_required q WHERE " +
            "LOWER(i.company.name) LIKE LOWER(CONCAT('%', :companyName, '%')) AND " +
            "LOWER(q) LIKE LOWER(CONCAT('%', :qualification, '%'))")
    List<Internship> findByCompanyAndQualification(
            @Param("companyName") String companyName,
            @Param("qualification") List<String> qualification
    );

    // Dates + Qualification
    @Query("SELECT DISTINCT i FROM Internship i JOIN i.qualification_required q WHERE " +
            "(:maxEnd IS NULL OR i.endDate <= :maxEnd) AND " +
            "(:minStart IS NULL OR i.startDate >= :minStart) AND " +
            "LOWER(q) LIKE LOWER(CONCAT('%', :qualification, '%'))")
    List<Internship> findByDatesAndQualification(
            @Param("maxEnd") LocalDate maxEnd,
            @Param("minStart") LocalDate minStart,
            @Param("qualification") List<String> qualification
    );

    // Keyword + Company + Dates
    @Query("SELECT i FROM Internship i WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND " +
            "LOWER(i.company.name) LIKE LOWER(CONCAT('%', :companyName, '%')) AND " +
            "(:maxEnd IS NULL OR i.endDate <= :maxEnd) AND " +
            "(:minStart IS NULL OR i.startDate >= :minStart)")
    List<Internship> findByKeywordAndCompanyAndDates(
            @Param("keyword") String keyword,
            @Param("companyName") String companyName,
            @Param("maxEnd") LocalDate maxEnd,
            @Param("minStart") LocalDate minStart
    );

    // Keyword + Company + Qualification
    @Query("SELECT DISTINCT i FROM Internship i JOIN i.qualification_required q WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND " +
            "LOWER(i.company.name) LIKE LOWER(CONCAT('%', :companyName, '%')) AND " +
            "LOWER(q) LIKE LOWER(CONCAT('%', :qualification, '%'))")
    List<Internship> findByKeywordAndCompanyAndQualification(
            @Param("keyword") String keyword,
            @Param("companyName") String companyName,
            @Param("qualification") List<String> qualification
    );

    // Company + Dates + Qualification
    @Query("SELECT DISTINCT i FROM Internship i JOIN i.qualification_required q WHERE " +
            "LOWER(i.company.name) LIKE LOWER(CONCAT('%', :companyName, '%')) AND " +
            "(:maxEnd IS NULL OR i.endDate <= :maxEnd) AND " +
            "(:minStart IS NULL OR i.startDate >= :minStart) AND " +
            "LOWER(q) LIKE LOWER(CONCAT('%', :qualification, '%'))")
    List<Internship> findByCompanyAndDatesAndQualification(
            @Param("companyName") String companyName,
            @Param("maxEnd") LocalDate maxEnd,
            @Param("minStart") LocalDate minStart,
            @Param("qualification") List<String> qualification
    );

    // Tutte le combinazioni
    @Query("SELECT DISTINCT i FROM Internship i LEFT JOIN i.qualification_required q WHERE " +
            "(:keyword IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:companyName IS NULL OR LOWER(i.company.name) LIKE LOWER(CONCAT('%', :companyName, '%'))) AND " +
            "(:maxEnd IS NULL OR i.endDate <= :maxEnd) AND " +
            "(:minStart IS NULL OR i.startDate >= :minStart) AND " +
            "(:qualification IS NULL OR LOWER(q) LIKE LOWER(CONCAT('%', :qualification, '%')))")
    List<Internship> findByAllCriteria(
            @Param("keyword") String keyword,
            @Param("companyName") String companyName,
            @Param("maxEnd") LocalDate maxEnd,
            @Param("minStart") LocalDate minStart,
            @Param("qualification") List<String> qualification
    );

    List<Internship> findByCompanyNameContainingIgnoreCase(String companyName);

    List<Internship> findByAppliedStudentsContainingIgnoreCase(Student student);
}