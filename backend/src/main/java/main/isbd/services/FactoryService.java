package main.isbd.services;

import lombok.AllArgsConstructor;
import main.isbd.data.dto.material.MaterialInfoInterface;
import main.isbd.data.dto.material.MaterialShortInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.model.Factory;
import main.isbd.data.model.MaterialType;
import main.isbd.data.model.ProductType;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.EntityNotFoundException;
import main.isbd.repositories.FactoryRepository;
import main.isbd.repositories.MaterialTypeRepository;
import main.isbd.repositories.ProductTypeRepository;
import main.isbd.services.interfaces.FactoryServiceInterface;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@AllArgsConstructor
public class FactoryService implements FactoryServiceInterface {
    private final FactoryRepository factoryRepository;
    private final ProductTypeRepository productTypeRepository;
    private final MaterialTypeRepository materialTypeRepository;

    @Override
    public Boolean checkIfUserIsAuthorized(Integer factoryId, String password) throws BadCredentialsException {
        Optional<Factory> factory = factoryRepository.findById(factoryId);
        return factory.map(value -> value.getPassword().equals(password))
                .orElseThrow(() -> new BadCredentialsException("Владелец не авторизован"));
    }

    @Override
    public Factory getFactoryByIdAndPassword(Integer factoryId, String password) throws EntityNotFoundException {
        return factoryRepository.findByIdAndPassword(factoryId, password)
                .orElseThrow(() -> new EntityNotFoundException("Владелец не найден"));
    }

    @Override
    public List<ProductShortInfo> getAllProductsShortInfo() {
        return productTypeRepository.findAll().stream()
                .map(productType -> new ProductShortInfo(productType.getId(), productType.getName(), productType.getPrice()))
                .toList();
    }

    @Override
    public ProductType getProductInfoById(Integer productId) throws EntityNotFoundException {
        return productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));
    }

    @Override
    public void setProductInfoById(Integer productId, String name, String description, Float price) throws EntityNotFoundException {
        ProductType productType = productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));

        productType.setName(name);
        productType.setDescription(description);
        productType.setPrice(price);
        productTypeRepository.save(productType);
    }

    @Override
    public List<MaterialShortInfo> getAllMaterialsShortInfo() {
        return materialTypeRepository.findAll().stream()
                .map(materialType -> new MaterialShortInfo(materialType.getId(), materialType.getName(), materialType.getPrice()))
                .toList();
    }

    @Override
    public MaterialType getMaterialInfoById(Integer materialId) throws EntityNotFoundException {
        return materialTypeRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException("Материал не найден"));
    }

    @Override
    public void setMaterialInfoById(Integer materialId, String name, String description, Float price) throws EntityNotFoundException {
        MaterialType materialType = materialTypeRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException("Материал не найден"));

        materialType.setName(name);
        materialType.setDescription(description);
        materialType.setPrice(price);
        materialTypeRepository.save(materialType);
    }
}
