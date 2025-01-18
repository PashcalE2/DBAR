package main.isbd.services.interfaces;

import main.isbd.data.dto.material.MaterialInfoInterface;
import main.isbd.data.dto.material.MaterialShortInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.product.ProductShortInfoInterface;
import main.isbd.data.model.Factory;

import java.util.List;

public interface FactoryServiceInterface {
    public Boolean checkIfUserIsAuthorized(Integer factoryId, String password);

    public Factory getFactoryByIdAndPassword(Integer factoryId, String password);

    public List<ProductShortInfoInterface> getAllProductsShortInfo();

    public ProductInfoInterface getProductInfoById(Integer productId);

    public void setProductInfoById(Integer productId, String name, String description, Float price);

    public List<MaterialShortInfoInterface> getAllMaterialsShortInfo();

    public MaterialInfoInterface getMaterialInfoById(Integer productId);

    public void setMaterialInfoById(Integer productId, String name, String description, Float price);
}
