package com.Project.PackageTracker.Branch;

import com.Project.PackageTracker.Product.Inventory.ProductInventory;
import com.Project.PackageTracker.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
public class BranchQueryController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> list = branchService.getAllBranches();
        if (list == null || list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }
}
