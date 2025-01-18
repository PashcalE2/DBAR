package main.isbd.controllers.legacy;

import main.isbd.data.dto.material.MaterialInfo;
import main.isbd.data.dto.product.ProductInfo;
import main.isbd.data.model.Factory;
import main.isbd.data.dto.users.FactoryLogin;
import main.isbd.services.legacy.FactoryService;
import main.isbd.utils.CheckRightsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
public class FactoryController {
    @Autowired
    private FactoryService factoryService;

    @PostMapping("/factory/profile/check_rights")
    public @ResponseBody ResponseEntity<?> checkFactoryRights(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password
    ) {
        System.out.printf("Запрос проверки прав доступа для управления (%d)\n", factory_id);
        return new CheckRightsWrapper(factoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(factory_id, password);
    }

    @PostMapping("/factory/profile/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody FactoryLogin factory) {
        if (factory == null || !factory.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        System.out.printf("Руководство (%d) пытается зайти\n", factory.getId());
        Factory db_factory;

        try {
            db_factory = factoryService.getFactoryByIdAndPassword(factory.getId(), factory.getPassword());
            if (db_factory == null) {
                throw new RuntimeException("Неверный логин или пароль\n");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(db_factory, HttpStatus.OK);
    }

    @GetMapping("/factory/product/get_all_short")
    public @ResponseBody ResponseEntity<?> getProductsShortInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password
    ) {
        System.out.println("Запрос на получение данных о всей продукции\n");

        return new CheckRightsWrapper(factoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(factoryService.getAllProductsShortInfo(), HttpStatus.OK);
            }
        }.execute(factory_id, password);
    }

    @GetMapping("/factory/product/get")
    public @ResponseBody ResponseEntity<?> getProductInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestParam Integer product_id
    ) {
        System.out.printf("Запрос на получение данных о продукции (%d)\n", product_id);
        return new CheckRightsWrapper(factoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(factoryService.getProductInfoById(product_id), HttpStatus.OK);
            }
        }.execute(factory_id, password);
    }

    @PostMapping("/factory/product/set")
    public @ResponseBody ResponseEntity<?> setProductInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestBody ProductInfo product_info
            ) {
        System.out.printf("Запрос на изменение данных о продукции (%d): %s, %f, %s\n", product_info.getId(), product_info.getName(), product_info.getPrice(), product_info.getDescription());
        return new CheckRightsWrapper(factoryService) {
            @Override
            public ResponseEntity<?> outer() {
                factoryService.setProductInfoById(product_info.getId(), product_info.getName(), product_info.getDescription(), product_info.getPrice());
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(factory_id, password);
    }

    @GetMapping("/factory/material/get_all_short")
    public @ResponseBody ResponseEntity<?> getMaterialShortInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password
    ) {
        System.out.println("Запрос на получение данных о всех материалах\n");

        return new CheckRightsWrapper(factoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(factoryService.getAllMaterialsShortInfo(), HttpStatus.OK);
            }
        }.execute(factory_id, password);
    }

    @GetMapping("/factory/material/get")
    public @ResponseBody ResponseEntity<?> getMaterialInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestParam Integer material_id
    ) {
        System.out.printf("Запрос на получение данных о материале (%d)\n", material_id);
        return new CheckRightsWrapper(factoryService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(factoryService.getMaterialInfoById(material_id), HttpStatus.OK);
            }
        }.execute(factory_id, password);
    }

    @PostMapping("/factory/material/set")
    public @ResponseBody ResponseEntity<?> setMaterialInfo(
            @RequestParam(defaultValue = "0") Integer factory_id,
            @RequestParam String password,
            @RequestBody MaterialInfo material_info
    ) {
        System.out.printf("Запрос на изменение данных о материале (%d)\n", material_info.getId());
        return new CheckRightsWrapper(factoryService) {
            @Override
            public ResponseEntity<?> outer() {
                factoryService.setMaterialInfoById(material_info.getId(), material_info.getName(), material_info.getDescription(), material_info.getPrice());
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(factory_id, password);
    }
}
