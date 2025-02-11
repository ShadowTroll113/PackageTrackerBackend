package com.Project.PackageTracker.Package;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageQueryController {

    @Autowired
    private PackageService packageService;

    // Obtener todos los paquetes
    @GetMapping
    public List<Package> getAllPackages() {
        return packageService.getAllPackages();
    }

    // Obtener un paquete por ID
    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        return packageService.getPackageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
