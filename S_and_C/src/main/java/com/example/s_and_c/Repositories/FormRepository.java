package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Integer> {

    List<Form> findByStudent(Student student);

    List<Form> findByInternshipAndCompanyAndFormType(Internship internship, Company company, FormType formType);

    List<Form> findByInternshipAndStudentAndFormType(Internship internship,Student student, FormType formType);

    List<Form> findByStudentAndFormType(Student student, FormType formType);

    List<Form> findByInternship(Internship internship);

    List<Form> findByInternshipAndFormType(Internship internship, FormType formType);

    List<Form> findByStudentAndInternshipAndFormType(Student student, Internship internship, FormType formType);

    List<Form> findByCompanyAndFormTypeAndStudent(Company company, FormType formType, Student student);
}
