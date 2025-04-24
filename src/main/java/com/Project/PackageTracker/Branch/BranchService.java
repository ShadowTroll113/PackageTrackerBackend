package com.Project.PackageTracker.Branch;

import com.Project.PackageTracker.Product.Inventory.ProductInventory;
import com.Project.PackageTracker.Product.Inventory.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch createBranch(BranchDto branchDto) {
        Branch branch = new Branch();
        mapDtoToEntity(branchDto, branch);
        return branchRepository.save(branch);
    }

    public Optional<Branch> updateBranch(Long id, BranchDto branchDto) {
        return branchRepository.findById(id).map(existingBranch -> {
            mapDtoToEntity(branchDto, existingBranch);
            return branchRepository.save(existingBranch);
        });
    }

    public boolean deleteBranch(Long id) {
        if (branchRepository.existsById(id)) {
            branchRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void mapDtoToEntity(BranchDto dto, Branch entity) {
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setStreetNumber(dto.getStreetNumber());
        entity.setStreetName(dto.getStreetName());
        entity.setLocality(dto.getLocality());
        entity.setAutonomousCommunity(dto.getAutonomousCommunity());
        entity.setCountry(dto.getCountry());
        entity.setPostalCode(dto.getPostalCode());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
    }
}
