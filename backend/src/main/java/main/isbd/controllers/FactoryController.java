package main.isbd.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.isbd.data.AppInfoResponse;
import main.isbd.data.dto.material.MaterialInfo;
import main.isbd.data.dto.material.MaterialShortInfo;
import main.isbd.data.dto.product.ProductInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.dto.users.FactoryRegResponse;
import main.isbd.data.dto.users.FactoryRegister;
import main.isbd.data.model.MaterialType;
import main.isbd.data.model.ProductType;
import main.isbd.exception.BaseAppException;
import main.isbd.services.FactoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factory")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class FactoryController {

    private final FactoryService factoryService;

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/profile/register")
    public @ResponseBody ResponseEntity<FactoryRegResponse> register(
            @RequestBody FactoryRegister factoryRegister, @AuthenticationPrincipal UserDetails userDetails)
            throws BaseAppException {
        return new ResponseEntity<>(factoryService.registerFactory(factoryRegister, userDetails.getPassword()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('FACTORY')")
    @GetMapping("/product/all-short")
    public ResponseEntity<List<ProductShortInfo>> getProductsShortInfo() {
        log.info("Запрос на получение данных о всей продукции");
        return new ResponseEntity<>(factoryService.getAllProductsShortInfo(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('FACTORY')")
    @GetMapping("/product")
    public ResponseEntity<ProductType> getProductInfo(@RequestParam Integer product_id) throws BaseAppException {
        log.info("Запрос на получение данных о продукции ({})", product_id);
        return new ResponseEntity<>(factoryService.getProductInfoById(product_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('FACTORY')")
    @PostMapping("/product")
    public ResponseEntity<AppInfoResponse> setProductInfo(@RequestBody ProductInfo product_info)
            throws BaseAppException {
        log.info("Запрос на изменение данных о продукции ({}): {}, {}, {}", product_info.getId(),
                product_info.getName(), product_info.getPrice(), product_info.getDescription());
        factoryService.setProductInfoById(product_info.getId(), product_info.getName(),
                product_info.getDescription(), product_info.getPrice());
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('FACTORY')")
    @GetMapping("/material/all-short")
    public ResponseEntity<List<MaterialShortInfo>> getMaterialShortInfo() {
        log.info("Запрос на получение данных о всех материалах\n");
        return new ResponseEntity<>(factoryService.getAllMaterialsShortInfo(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('FACTORY')")
    @GetMapping("/material")
    public ResponseEntity<MaterialType> getMaterialInfo(@RequestParam Integer material_id) throws BaseAppException {
        log.info("Запрос на получение данных о материале ({})", material_id);
        return new ResponseEntity<>(factoryService.getMaterialInfoById(material_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('FACTORY')")
    @PostMapping("/material")
    public ResponseEntity<AppInfoResponse> setMaterialInfo(@RequestBody MaterialInfo material_info)
            throws BaseAppException {
        log.info("Запрос на изменение данных о материале ({})", material_info.getId());
        factoryService.setMaterialInfoById(material_info.getId(), material_info.getName(),
                material_info.getDescription(), material_info.getPrice());
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }
}
