package com.sparta.audumbla.audumblaworldjpa.repositories;

import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    void deleteByCode(String code);
}
