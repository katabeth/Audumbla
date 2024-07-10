package com.sparta.audumbla.audumblaworldjpa.repositories;

import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryLanguageRepository extends JpaRepository<Countrylanguage, Integer> {
}
