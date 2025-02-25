package main.isbd.controllers.legacy;

import main.isbd.data.model.Admin;
import main.isbd.data.dto.users.AdminLogin;
import main.isbd.services.legacy.LegacyAdminService;
import main.isbd.utils.CheckRightsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Timestamp;

@Deprecated
@Controller
@RequestMapping("/legacy")
@CrossOrigin
@ApplicationScope
public class LegacyAdminController {
    @Autowired
    private LegacyAdminService legacyAdminService;

    @PostMapping("/admin/profile/check_rights")
    public @ResponseBody ResponseEntity<?> checkAdminRights(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password
    ) {
        System.out.printf("Запрос проверки прав доступа для консультанта (%d)\n", admin_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @PostMapping("/admin/profile/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody AdminLogin admin) {
        if (admin == null || !admin.isValid()) {
            return new ResponseEntity<>("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }

        System.out.printf("Консультант (%d) пытается зайти\n", admin.getId());
        Admin db_admin;

        try {
            db_admin = legacyAdminService.getAdminByIdAndPassword(admin.getId(), admin.getPassword());
            if (db_admin == null) {
                throw new RuntimeException("Неверный логин или пароль\n");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(db_admin, HttpStatus.OK);
    }

    @GetMapping("/admin/product/get")
    public @ResponseBody ResponseEntity<?> getProductInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam Integer product_id
    ) {
        System.out.printf("Запрос на получение данных о продукции (%d)\n", product_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(legacyAdminService.getProductInfoById(product_id), HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @GetMapping("/admin/order/get_all_info")
    public @ResponseBody ResponseEntity<?> getAllOrdersInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password
    ) {
        System.out.printf("Запрос на получение информации о заказах консультанта (%d)\n", admin_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(legacyAdminService.getAllOrdersInfoByAdminId(admin_id), HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @GetMapping("/admin/order/get")
    public @ResponseBody ResponseEntity<?> getOrderInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam Integer order_id
    ) {
        System.out.printf("Запрос на получение информации о заказе (%d)\n", order_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(legacyAdminService.getOrderInfoByOrderId(order_id), HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @GetMapping("/admin/order/get_products")
    public @ResponseBody ResponseEntity<?> getAllProductsInOrder(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на получение информации о заказе (%d) консультанта (%d)\n", order_id, admin_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(legacyAdminService.getAllProductsInOrder(order_id), HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @PostMapping("/admin/order/ask_for_assembling")
    public @ResponseBody ResponseEntity<?> assemblyOrder(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на сборку заказа (%d)\n", order_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                legacyAdminService.askForOrderAssembling(order_id);
                return new ResponseEntity<>("Что-то возможно получилось", HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @GetMapping("/admin/chat/get_client")
    public @ResponseBody ResponseEntity<?> getChatAdminInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на получение информации о клиенте заказа (%d)\n", order_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(legacyAdminService.getClientContactsInChat(order_id), HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @GetMapping("/admin/chat/get_messages")
    public @ResponseBody ResponseEntity<?> getChatMessages(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        System.out.printf("Запрос на получение сообщений заказа (%d)\n", order_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                return new ResponseEntity<>(legacyAdminService.getMessagesInChat(order_id), HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }

    @PostMapping("/admin/chat/post_message")
    public @ResponseBody ResponseEntity<?> postChatMessage(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam String content
    ) {
        System.out.printf("Запрос на отправку сообщения в заказе (%d)\n", order_id);
        return new CheckRightsWrapper(legacyAdminService) {
            @Override
            public ResponseEntity<?> outer() {
                legacyAdminService.postMessageInChat(order_id, content, new Timestamp(System.currentTimeMillis()));
                return new ResponseEntity<>("Успех!", HttpStatus.OK);
            }
        }.execute(admin_id, password);
    }
}
