package main.isbd.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.isbd.data.dto.material.MaterialInfo;
import main.isbd.data.dto.product.ProductInfo;
import main.isbd.data.dto.users.FactoryLogin;
import main.isbd.data.model.Factory;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.services.FactoryService;
import main.isbd.services.legacy.LegacyFactoryService;
import main.isbd.utils.CheckRightsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@RequestMapping("/factory")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class FactoryController {
    private final FactoryService factoryService;

    @PostMapping("/profile/check_rights")
    public ResponseEntity<?> checkFactoryRights(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password
    ) throws BadCredentialsException {
        log.info("Запрос проверки прав доступа для управления ({})", factory_id);
        factoryService.checkIfUserIsAuthorized(factory_id, password);
        return new ResponseEntity<>("Успех!", HttpStatus.OK);
    }

    @PostMapping("/profile/login")
    public ResponseEntity<?> login(@RequestBody FactoryLogin factory) throws BaseAppException {
        return new ResponseEntity<>(factoryService.loginFactory(factory), HttpStatus.OK);
    }

    @GetMapping("/product/all-short")
    public ResponseEntity<?> getProductsShortInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password
    ) throws BaseAppException {
        log.info("Запрос на получение данных о всей продукции");
        return new ResponseEntity<>(factoryService.getAllProductsShortInfo(factory_id, password), HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<?> getProductInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestParam Integer product_id
    ) throws BaseAppException {
        log.info("Запрос на получение данных о продукции ({})", product_id);
        return new ResponseEntity<>(factoryService.getProductInfoById(factory_id, password, product_id), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> setProductInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestBody ProductInfo product_info
            ) throws BaseAppException {
        log.info("Запрос на изменение данных о продукции ({}): {}, {}, {}", product_info.getId(), product_info.getName(), product_info.getPrice(), product_info.getDescription());
        factoryService.setProductInfoById(factory_id, password, product_info.getId(), product_info.getName(), product_info.getDescription(), product_info.getPrice());
        return new ResponseEntity<>("Успех!", HttpStatus.OK);
    }

    @GetMapping("/material/all-short")
    public ResponseEntity<?> getMaterialShortInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password
    ) throws BaseAppException {
        log.info("Запрос на получение данных о всех материалах\n");
        return new ResponseEntity<>(factoryService.getAllMaterialsShortInfo(factory_id, password), HttpStatus.OK);
    }

    @GetMapping("/material")
    public ResponseEntity<?> getMaterialInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestParam Integer material_id
    ) throws BaseAppException {
        log.info("Запрос на получение данных о материале ({})", material_id);
        return new ResponseEntity<>(factoryService.getMaterialInfoById(factory_id, password, material_id), HttpStatus.OK);
    }

    @PostMapping("/material")
    public ResponseEntity<?> setMaterialInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestBody MaterialInfo material_info
    ) throws BaseAppException {
        log.info("Запрос на изменение данных о материале ({})", material_info.getId());
        factoryService.setMaterialInfoById(factory_id, password, material_info.getId(), material_info.getName(), material_info.getDescription(), material_info.getPrice());
        return new ResponseEntity<>("Успех!", HttpStatus.OK);
    }
}
