package com.Project.PackageTracker.Branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch")
public class BranchCommandController {

    @Autowired
    private BranchService branchService;

    @PostMapping
    public Branch createBranch(@RequestBody BranchDto branchDto) {
        return branchService.createBranch(branchDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDto) {
        return branchService.updateBranch(id, branchDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        return branchService.deleteBranch(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}