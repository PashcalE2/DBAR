package main.isbd.services.legacy;

import main.isbd.data.dto.material.MaterialInfoInterface;
import main.isbd.data.dto.material.MaterialShortInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.product.ProductShortInfoInterface;
import main.isbd.data.model.Factory;
import main.isbd.repositories.legacy.LegacyFactoryRepository;
import main.isbd.utils.CheckRightsInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Deprecated
@Service
@Transactional
@ApplicationScope
public class LegacyFactoryService implements CheckRightsInterface {
    public final LegacyFactoryRepository legacyFactoryRepository;

    public LegacyFactoryService(LegacyFactoryRepository legacyFactoryRepository) {
        this.legacyFactoryRepository = legacyFactoryRepository;
    }

    public Boolean checkIfUserIsAuthorized(Integer factory_id, String password) {
        return legacyFactoryRepository.checkIfFactoryIsAuthorized(factory_id, password);
    }

    public Factory getFactoryByIdAndPassword(Integer factory_id, String password) {
        return legacyFactoryRepository.getFactoryByIdAndPassword(factory_id, password);
    }

    public List<ProductShortInfoInterface> getAllProductsShortInfo() {
        return legacyFactoryRepository.getAllProductsShortInfo();
    }

    public ProductInfoInterface getProductInfoById(Integer product_id) {
        return legacyFactoryRepository.getProductInfoById(product_id);
    }

    public void setProductInfoById(Integer product_id, String name, String description, Float price) {
        legacyFactoryRepository.setProductInfoById(product_id, name, description, price);
    }

    public List<MaterialShortInfoInterface> getAllMaterialsShortInfo() {
        return legacyFactoryRepository.getAllMaterialsShortInfo();
    }

    public MaterialInfoInterface getMaterialInfoById(Integer product_id) {
        return legacyFactoryRepository.getMaterialInfoById(product_id);
    }

    public void setMaterialInfoById(Integer product_id, String name, String description, Float price) {
        legacyFactoryRepository.setMaterialInfoById(product_id, name, description, price);
    }
}
