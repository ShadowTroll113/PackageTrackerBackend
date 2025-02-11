package com.Project.PackageTracker.Package;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public Optional<Package> getPackageById(Long id) {
        return packageRepository.findById(id);
    }

    public Package createPackage(Package newPackage) {
        return packageRepository.save(newPackage);
    }

    public Optional<Package> updatePackage(Long id, Package packageDetails) {
        return packageRepository.findById(id).map(pkg -> {
            pkg.setSender(packageDetails.getSender());
            pkg.setReceiver(packageDetails.getReceiver());
            pkg.setRoute(packageDetails.getRoute());
            pkg.setProducts(packageDetails.getProducts());
            pkg.setStatus(packageDetails.getStatus());
            return packageRepository.save(pkg);
        });
    }

    public boolean deletePackage(Long id) {
        return packageRepository.findById(id).map(pkg -> {
            packageRepository.delete(pkg);
            return true;
        }).orElse(false);
    }
}
