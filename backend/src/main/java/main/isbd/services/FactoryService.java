package main.isbd.services;

import main.isbd.data.dto.material.MaterialInfoInterface;
import main.isbd.data.dto.material.MaterialShortInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.product.ProductShortInfoInterface;
import main.isbd.data.model.Factory;
import main.isbd.services.interfaces.FactoryServiceInterface;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class FactoryService implements FactoryServiceInterface {
    @Override
    public Boolean checkIfUserIsAuthorized(Integer factoryId, String password) {
        return null;
    }

    @Override
    public Factory getFactoryByIdAndPassword(Integer factoryId, String password) {
        return null;
    }

    @Override
    public List<ProductShortInfoInterface> getAllProductsShortInfo() {
        return List.of();
    }

    @Override
    public ProductInfoInterface getProductInfoById(Integer productId) {
        return null;
    }

    @Override
    public void setProductInfoById(Integer productId, String name, String description, Float price) {

    }

    @Override
    public List<MaterialShortInfoInterface> getAllMaterialsShortInfo() {
        return List.of();
    }

    @Override
    public MaterialInfoInterface getMaterialInfoById(Integer productId) {
        return null;
    }

    @Override
    public void setMaterialInfoById(Integer productId, String name, String description, Float price) {

    }
}
