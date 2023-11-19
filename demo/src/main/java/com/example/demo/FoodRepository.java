package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FoodRepository extends JpaRepository<Food, Long> {
	void deleteById(Long id);
}
