package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Integer> {
    //List<Form> findByInternship(Internship internship);


    //Optional<Form> findByFormId(long formId);

    //List<Form> findByInternshipAndStudent(Internship internship, Student student);

    List<Form> findByStudent(Student student);

    List<Form> findByInternshipAndCompanyAndFormType(Internship internship, Company company, FormType formType);

    List<Form> findByInternshipAndStudentAndFormType(Internship internship,Student student, FormType formType);

    List<Form> findByInternshipAndCompanyAndStudentAndFormType(Internship internship,Company company, Student student, FormType formType);

    List<Form> findByStudentAndFormType(Student student, FormType formType);
}
