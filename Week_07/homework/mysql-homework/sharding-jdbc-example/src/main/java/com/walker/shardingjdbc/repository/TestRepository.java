package com.walker.shardingjdbc.repository;

import com.walker.shardingjdbc.domain.TestEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEnity, Integer> {
}
