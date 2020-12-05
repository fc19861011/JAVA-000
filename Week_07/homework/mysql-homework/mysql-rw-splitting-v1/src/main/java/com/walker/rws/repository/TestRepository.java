package com.walker.rws.repository;

import com.walker.rws.domain.TestEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEnity, Integer> {
}
