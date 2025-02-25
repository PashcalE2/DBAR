package main.isbd.services;

import lombok.AllArgsConstructor;
import main.isbd.data.dto.material.MaterialShortInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.dto.users.FactoryRegister;
import main.isbd.data.model.Factory;
import main.isbd.data.model.MaterialType;
import main.isbd.data.model.ProductType;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.EntityNotFoundException;
import main.isbd.repositories.FactoryRepository;
import main.isbd.repositories.MaterialTypeRepository;
import main.isbd.repositories.ProductTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FactoryService {
    private final FactoryRepository factoryRepository;
    private final ProductTypeRepository productTypeRepository;
    private final MaterialTypeRepository materialTypeRepository;
    private final JwtTokenService jwtTokenService;

    public Factory registerFactory(FactoryRegister factoryRegister) throws BaseAppException {
        if (factoryRegister.getAuthToken() == null || factoryRegister.getAuthToken().isEmpty()) {
            throw new BaseAppException("No token from Auth service. Firstly create user-id with login\n",
                    HttpStatus.BAD_REQUEST);
        }
        String login = jwtTokenService
                .extractLoginWithAvailabilityCheck(factoryRegister.getAuthToken(), "ROLE_FACTORY");
        Factory factory = new Factory();
        factory.setLogin(login);
        factory.setName(factoryRegister.getName());
        factory.setAddress(factoryRegister.getAddress());
        Factory savedFactory;
        try {
            savedFactory = factoryRepository.save(factory);
        } catch (Exception e) {
            throw new BaseAppException(e.getMessage(), HttpStatus.CONFLICT);
        }
        return savedFactory;
    }

    public List<ProductShortInfo> getAllProductsShortInfo() {
        return productTypeRepository.findAll().stream()
                .map(productType -> new ProductShortInfo(productType.getId(), productType.getName(),
                        productType.getPrice())).toList();
    }

    public ProductType getProductInfoById(Integer productId) throws BaseAppException {
        return productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));
    }

    public void setProductInfoById(Integer productId, String name, String description, Float price)
            throws BaseAppException {
        ProductType productType = productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));
        productType.setName(name);
        productType.setDescription(description);
        productType.setPrice(price);
        productTypeRepository.save(productType);
    }

    public List<MaterialShortInfo> getAllMaterialsShortInfo() {
        return materialTypeRepository.findAll().stream()
                .map(materialType -> new MaterialShortInfo(materialType.getId(), materialType.getName(),
                        materialType.getPrice())).toList();
    }

    public MaterialType getMaterialInfoById(Integer materialId)
            throws BaseAppException {
        return materialTypeRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException("Материал не найден"));
    }

    public void setMaterialInfoById(Integer materialId, String name, String description, Float price)
            throws BaseAppException {
        MaterialType materialType = materialTypeRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException("Материал не найден"));
        materialType.setName(name);
        materialType.setDescription(description);
        materialType.setPrice(price);
        materialTypeRepository.save(materialType);
    }
}
