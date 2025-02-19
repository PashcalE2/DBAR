package main.isbd.services.interfaces;

import main.isbd.data.dto.material.MaterialShortInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.dto.users.FactoryLogin;
import main.isbd.data.model.Factory;
import main.isbd.data.model.MaterialType;
import main.isbd.data.model.ProductType;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.EntityNotFoundException;

import java.util.List;

public interface FactoryServiceInterface {
    Boolean checkIfUserIsAuthorized(Integer factoryId, String password) throws BadCredentialsException;

    Factory loginFactory(FactoryLogin factoryLogin) throws BaseAppException;

    List<ProductShortInfo> getAllProductsShortInfo(Integer factoryId, String password) throws BaseAppException;

    ProductType getProductInfoById(Integer factoryId, String password, Integer productId) throws BaseAppException;

    void setProductInfoById(Integer factoryId, String password, Integer productId, String name, String description, Float price) throws BaseAppException;

    List<MaterialShortInfo> getAllMaterialsShortInfo(Integer factoryId, String password) throws BaseAppException;

    MaterialType getMaterialInfoById(Integer factoryId, String password, Integer materialId) throws BaseAppException;

    void setMaterialInfoById(Integer factoryId, String password, Integer materialId, String name, String description, Float price) throws BaseAppException;
}
