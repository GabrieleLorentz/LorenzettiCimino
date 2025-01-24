package com.example.s_and_c.Service.Impl;


import com.example.s_and_c.DTO.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.DTO.SearchDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.InternshipMapper;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Service.InternshipService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;


    @Override
    public List<InternshipDTO> createInternship(String email, InsertInternshipDTO insertInternshipDTO) {

        Company insertingCompany = companyRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company not found"));

        Internship internship = InternshipMapper.maptoInternship(insertInternshipDTO, insertingCompany);
        internshipRepository.save(internship);

        return getAllInternshipsByEmail(insertingCompany);
    }

    private List<InternshipDTO> getAllInternshipsByEmail(Company insCompany) {
        List<Internship> internships = internshipRepository.findByCompany(insCompany);
        return internships.stream().map(InternshipMapper::maptoInternshipDTO).collect(Collectors.toList());

    }

    @Override
    public InternshipDTO getInternship(int id) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));
        return InternshipMapper.maptoInternshipDTO(internship);
    }

    @Override
    public List<InternshipDTO> getAllInternships() {
        List<Internship> internships = internshipRepository.findAll();
        return internships.stream().map(InternshipMapper::maptoInternshipDTO).collect(Collectors.toList());
    }

    /*@Override
    public InternshipDTO updateInternship(int id, InsertInternshipDTO insertInternshipDTO) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));

        internship.setName(insertInternshipDTO.getName());
        internship.setDescription(insertInternshipDTO.getDescription());
        internship.setStartDate(insertInternshipDTO.getStart_date());
        internship.setEndDate(insertInternshipDTO.getEnd_date());
        internship.setSalary(insertInternshipDTO.getSalary());
        Internship updatedInternship = internshipRepository.save(internship);

        return InternshipMapper.maptoInternshipDTO(updatedInternship);

    }*/

    @Override
    public void deleteInternship(int id) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));

        internshipRepository.delete(internship);
    }

    private LocalDate convertToLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public List<InternshipDTO> findMatch(SearchDTO searchDTO) {
        List<InternshipDTO> results = new ArrayList<>();
        List<Internship> internships;

        LocalDate maxEndLocal = convertToLocalDate(searchDTO.getMax_end());
        LocalDate minStartLocal = convertToLocalDate(searchDTO.getMin_start());

        // Controllo se i campi sono vuoti o null
        boolean hasKeyword = !StringUtils.isEmpty(searchDTO.getKeyword());
        boolean hasCompany = !StringUtils.isEmpty(searchDTO.getCompany_name());
        boolean hasQualification = !searchDTO.getQualification().isEmpty();
        boolean hasMaxEnd = searchDTO.getMax_end() != null;
        boolean hasMinStart = searchDTO.getMin_start() != null;

        // Ricerca in base alle combinazioni di campi non vuoti
        if (hasKeyword && !hasCompany && !hasMaxEnd && !hasMinStart && !hasQualification) {
            // Solo keyword
            internships = internshipRepository.findByNameContainingIgnoreCase(searchDTO.getKeyword());
        }
        else if (!hasKeyword && hasCompany && !hasMaxEnd && !hasMinStart && !hasQualification) {
            // Solo company
            internships = internshipRepository.findByCompanyNameContainingIgnoreCase(searchDTO.getCompany_name());
        }
        else if (!hasKeyword && !hasCompany && hasMaxEnd && !hasMinStart && !hasQualification) {
            // Solo max end date
            internships = internshipRepository.findByEndDateIsLessThanEqual(maxEndLocal);
        }
        else if (!hasKeyword && !hasCompany && !hasMaxEnd && hasMinStart && !hasQualification) {
            // Solo min start date
            internships = internshipRepository.findByStartDateGreaterThanEqual(minStartLocal);
        }
        else if (!hasKeyword && !hasCompany && !hasMaxEnd && !hasMinStart && hasQualification) {
            // Solo qualification
            internships = internshipRepository.findByQualificationContainingIgnoreCase(searchDTO.getQualification());
        }
        else if (hasKeyword && hasCompany && !hasMaxEnd && !hasMinStart && !hasQualification) {
            // Keyword + Company
            internships = internshipRepository.findByKeywordAndCompany(
                    searchDTO.getKeyword(),
                    searchDTO.getCompany_name()
            );
        }
        else if (hasKeyword && !hasCompany && (hasMaxEnd || hasMinStart) && !hasQualification) {
            // Keyword + Dates
            internships = internshipRepository.findByKeywordAndDates(
                    searchDTO.getKeyword(),
                    maxEndLocal,
                    minStartLocal
            );
        }
        else if (hasKeyword && !hasCompany && !hasMaxEnd && !hasMinStart && hasQualification) {
            // Keyword + Qualification
            internships = internshipRepository.findByKeywordAndQualification(
                    searchDTO.getKeyword(),
                    searchDTO.getQualification()
            );
        }
        else if (!hasKeyword && hasCompany && (hasMaxEnd || hasMinStart) && !hasQualification) {
            // Company + Dates
            internships = internshipRepository.findByCompanyAndDates(
                    searchDTO.getCompany_name(),
                    maxEndLocal,
                    minStartLocal
            );
        }
        else if (!hasKeyword && hasCompany && !hasMaxEnd && !hasMinStart && hasQualification) {
            // Company + Qualification
            internships = internshipRepository.findByCompanyAndQualification(
                    searchDTO.getCompany_name(),
                    searchDTO.getQualification()
            );
        }
        else if (!hasKeyword && !hasCompany && (hasMaxEnd || hasMinStart) && hasQualification) {
            // Dates + Qualification
            internships = internshipRepository.findByDatesAndQualification(
                    maxEndLocal,
                    minStartLocal,
                    searchDTO.getQualification()
            );
        }
        else if (hasKeyword && hasCompany && (hasMaxEnd || hasMinStart) && !hasQualification) {
            // Keyword + Company + Dates
            internships = internshipRepository.findByKeywordAndCompanyAndDates(
                    searchDTO.getKeyword(),
                    searchDTO.getCompany_name(),
                    maxEndLocal,
                    minStartLocal
            );
        }
        else if (hasKeyword && hasCompany && !hasMaxEnd && !hasMinStart && hasQualification) {
            // Keyword + Company + Qualification
            internships = internshipRepository.findByKeywordAndCompanyAndQualification(
                    searchDTO.getKeyword(),
                    searchDTO.getCompany_name(),
                    searchDTO.getQualification()
            );
        }
        else if (!hasKeyword && hasCompany && (hasMaxEnd || hasMinStart) && hasQualification) {
            // Company + Dates + Qualification
            internships = internshipRepository.findByCompanyAndDatesAndQualification(
                    searchDTO.getCompany_name(),
                    maxEndLocal,
                    minStartLocal,
                    searchDTO.getQualification()
            );
        }
        else {
            // Se ci sono altre combinazioni o nessun parametro, usa la ricerca generica
            internships = internshipRepository.findByAllCriteria(
                    searchDTO.getKeyword(),
                    searchDTO.getCompany_name(),
                    maxEndLocal,
                    minStartLocal,
                    searchDTO.getQualification()
            );
        }

        // Converte i risultati in DTO
        for (Internship internship : internships) {
            results.add(InternshipMapper.maptoInternshipDTO(internship));
        }

        return results;
    }

    @Override
    public List<InternshipDTO> getMyInternship(String email) {
        Company company = companyRepository.findByEmail(email).orElse(null);
        if(company != null) {
            List<Internship> internships = internshipRepository.findByCompany(company);
            return internships.stream().map(InternshipMapper::maptoInternshipDTO).collect(Collectors.toList());
        }else
            return null;

    }


}
