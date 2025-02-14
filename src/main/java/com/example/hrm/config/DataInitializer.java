package com.example.hrm.config;

import com.example.hrm.domain.Company;
import com.example.hrm.repository.CompanyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    public DataInitializer(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (companyRepository.count() == 0) {
            Company c1 = new Company(null, "Company A", null);
            Company c2 = new Company(null, "Company B", null);
            Company c3 = new Company(null, "Company C", null);
            List<Company> companies = Arrays.asList(c1, c2, c3);
            companyRepository.saveAll(companies);
        }
    }
}
