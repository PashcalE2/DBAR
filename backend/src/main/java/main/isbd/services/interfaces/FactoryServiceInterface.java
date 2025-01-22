package main.isbd.services.interfaces;

import main.isbd.data.dto.material.MaterialShortInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.model.Factory;
import main.isbd.data.model.MaterialType;
import main.isbd.data.model.ProductType;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.EntityNotFoundException;

import java.util.List;

public interface FactoryServiceInterface {
    Boolean checkIfUserIsAuthorized(Integer factoryId, String password) throws BadCredentialsException;

    Factory getFactoryByIdAndPassword(Integer factoryId, String password) throws EntityNotFoundException;

    List<ProductShortInfo> getAllProductsShortInfo();

    ProductType getProductInfoById(Integer productId) throws EntityNotFoundException;

    void setProductInfoById(Integer productId, String name, String description, Float price) throws EntityNotFoundException;

    List<MaterialShortInfo> getAllMaterialsShortInfo();

    MaterialType getMaterialInfoById(Integer materialId) throws EntityNotFoundException;

    void setMaterialInfoById(Integer materialId, String name, String description, Float price) throws EntityNotFoundException;
}
