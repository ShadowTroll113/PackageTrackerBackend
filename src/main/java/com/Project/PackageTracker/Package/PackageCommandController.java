package com.Project.PackageTracker.Package;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/packages")
public class PackageCommandController {

    @Autowired
    private PackageService packageService;

    // Crear un nuevo paquete
    @PostMapping
    public Package createPackage(@RequestBody Package newPackage) {
        return packageService.createPackage(newPackage);
    }

    // Actualizar un paquete existente
    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Long id, @RequestBody Package packageDetails) {
        return packageService.updatePackage(id, packageDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un paquete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        return packageService.deletePackage(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
