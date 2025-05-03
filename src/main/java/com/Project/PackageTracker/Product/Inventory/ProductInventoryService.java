package com.Project.PackageTracker.Product.Inventory;

import com.Project.PackageTracker.Branch.Branch;
import com.Project.PackageTracker.Branch.BranchRepository;
import com.Project.PackageTracker.Product.Product;
import com.Project.PackageTracker.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    // Crea un registro de inventario para un producto
    public ProductInventory createProductInventory(ProductInventoryDto dto) {
        ProductInventory pi = new ProductInventory();

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        pi.setProduct(product);
        pi.setWarehouse(branch);
        pi.setQuantity(dto.getQuantity());
        pi.setProductName(dto.getProductName());

        return productInventoryRepository.save(pi);
    }

    // Obtiene un registro de inventario por id
    public Optional<ProductInventory> getProductInventoryById(Long id) {
        return productInventoryRepository.findById(id);
    }

    // Obtiene la lista de inventarios para una sucursal
    public List<ProductInventory> getProductInventoryByBranch(Long branchId) {
        return productInventoryRepository.findByWarehouseId(branchId);
    }

    // Disminuye el stock para un producto en una sucursal
    public ProductInventory decreaseStock(Long productId, Long branchId, int quantity) {
        Optional<ProductInventory> optionalPi = productInventoryRepository.findByProductIdAndWarehouseId(productId, branchId);
        if(optionalPi.isPresent()){
            ProductInventory pi = optionalPi.get();
            int newQuantity = pi.getQuantity() - quantity;
            if(newQuantity < 0){
                throw new RuntimeException("Stock insuficiente");
            }
            pi.setQuantity(newQuantity);
            return productInventoryRepository.save(pi);
        } else {
            throw new RuntimeException("No se encontró inventario para el producto " + productId + " en la sucursal " + branchId);
        }
    }
    // Aumenta el stock para un producto en una sucursal
    public ProductInventory increaseStock(Long productId, Long branchId, int quantity) {
        Optional<ProductInventory> optionalPi =
                productInventoryRepository.findByProductIdAndWarehouseId(productId, branchId);

        if (optionalPi.isPresent()) {
            ProductInventory pi = optionalPi.get();
            int newQuantity = pi.getQuantity() + quantity;
            pi.setQuantity(newQuantity);
            return productInventoryRepository.save(pi);
        } else {
            throw new RuntimeException(
                    "No se encontró inventario para el producto " + productId +
                            " en la sucursal " + branchId
            );
        }
    }
}
