package com.liferay.clock.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liferay.clock.api.model.Punch;

@Repository
public interface IPunchRepository extends JpaRepository<Punch, Long>{

}
