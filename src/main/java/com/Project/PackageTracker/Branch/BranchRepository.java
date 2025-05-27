package com.Project.PackageTracker.Branch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findBranchById(Long warehouse);
}
