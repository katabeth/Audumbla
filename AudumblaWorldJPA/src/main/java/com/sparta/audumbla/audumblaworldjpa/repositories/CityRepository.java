package com.sparta.audumbla.audumblaworldjpa.repositories;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>, PagingAndSortingRepository<City, Integer> {
}
