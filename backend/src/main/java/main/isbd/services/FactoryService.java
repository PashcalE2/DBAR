package main.isbd.services;

import lombok.AllArgsConstructor;
import main.isbd.data.dto.material.MaterialShortInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.dto.users.FactoryLogin;
import main.isbd.data.model.Factory;
import main.isbd.data.model.MaterialType;
import main.isbd.data.model.ProductType;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.EntityNotFoundException;
import main.isbd.repositories.FactoryRepository;
import main.isbd.repositories.MaterialTypeRepository;
import main.isbd.repositories.ProductTypeRepository;
import main.isbd.services.interfaces.FactoryServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
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
    public Factory loginFactory(FactoryLogin factoryLogin) throws BaseAppException {
        if (factoryLogin == null || !factoryLogin.isValid()) {
            throw new BaseAppException("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        return factoryRepository.findByIdAndPassword(factoryLogin.getId(), factoryLogin.getPassword())
                .orElseThrow(() -> new EntityNotFoundException("Владелец не найден"));
    }

    @Override
    public List<ProductShortInfo> getAllProductsShortInfo(Integer factoryId, String password) throws BaseAppException {
        loginFactory(new FactoryLogin(factoryId, password));

        return productTypeRepository.findAll().stream()
                .map(productType -> new ProductShortInfo(productType.getId(), productType.getName(), productType.getPrice()))
                .toList();
    }

    @Override
    public ProductType getProductInfoById(Integer factoryId, String password, Integer productId) throws BaseAppException {
        loginFactory(new FactoryLogin(factoryId, password));

        return productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));
    }

    @Override
    public void setProductInfoById(Integer factoryId, String password, Integer productId, String name, String description, Float price) throws BaseAppException {
        loginFactory(new FactoryLogin(factoryId, password));

        ProductType productType = productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Продукт не найден"));

        productType.setName(name);
        productType.setDescription(description);
        productType.setPrice(price);
        productTypeRepository.save(productType);
    }

    @Override
    public List<MaterialShortInfo> getAllMaterialsShortInfo(Integer factoryId, String password) throws BaseAppException {
        loginFactory(new FactoryLogin(factoryId, password));

        return materialTypeRepository.findAll().stream()
                .map(materialType -> new MaterialShortInfo(materialType.getId(), materialType.getName(), materialType.getPrice()))
                .toList();
    }

    @Override
    public MaterialType getMaterialInfoById(Integer factoryId, String password, Integer materialId) throws BaseAppException {
        loginFactory(new FactoryLogin(factoryId, password));

        return materialTypeRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException("Материал не найден"));
    }

    @Override
    public void setMaterialInfoById(Integer factoryId, String password, Integer materialId, String name, String description, Float price) throws BaseAppException {
        loginFactory(new FactoryLogin(factoryId, password));

        MaterialType materialType = materialTypeRepository.findById(materialId)
                .orElseThrow(() -> new EntityNotFoundException("Материал не найден"));

        materialType.setName(name);
        materialType.setDescription(description);
        materialType.setPrice(price);
        materialTypeRepository.save(materialType);
    }
}
