package main.isbd.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.isbd.data.dto.users.AdminLogin;
import main.isbd.data.model.Admin;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.EntityNotFoundException;
import main.isbd.services.AdminService;
import main.isbd.utils.CheckRightsWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/profile/check_rights")
    public ResponseEntity<?> checkAdminRights(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password
    ) throws BadCredentialsException {
        log.info("Запрос проверки прав доступа для консультанта ({})", admin_id);
        adminService.checkIfUserIsAuthorized(admin_id, password);
        return new ResponseEntity<>("Успех!", HttpStatus.OK);
    }

    @PostMapping("/profile/login")
    public ResponseEntity<?> login(@RequestBody AdminLogin admin) throws BaseAppException {
        return new ResponseEntity<>(adminService.loginAdmin(admin), HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<?> getProductInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam Integer product_id
    ) throws BaseAppException {
        log.info("Запрос на получение данных о продукции ({})", product_id);
        return new ResponseEntity<>(adminService.getProductInfoById(admin_id, password, product_id), HttpStatus.OK);
    }

    @GetMapping("/order/all_info")
    public ResponseEntity<?> getAllOrdersInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password
    ) throws EntityNotFoundException {
        log.info("Запрос на получение информации о заказах консультанта ({})", admin_id);
        return new ResponseEntity<>(adminService.getAllOrdersByAdminId(admin_id, password), HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrderInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        log.info("Запрос на получение информации о заказе ({})", order_id);
        return new ResponseEntity<>(adminService.getOrderByOrderId(admin_id, password, order_id), HttpStatus.OK);
    }

    @GetMapping("/order/products")
    public ResponseEntity<?> getAllProductsInOrder(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        log.info("Запрос на получение информации о заказе ({}) консультанта ({})", order_id, admin_id);
        return new ResponseEntity<>(adminService.getAllProductsInOrder(admin_id, password, order_id), HttpStatus.OK);
    }

    @PostMapping("/order/ask-for-assembling")
    public ResponseEntity<?> assemblyOrder(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        log.info("Запрос на сборку заказа ({})", order_id);
        adminService.askForOrderAssembling(admin_id, password, order_id);
        return new ResponseEntity<>("Что-то возможно получилось", HttpStatus.OK);
    }

    @GetMapping("/chat/client")
    public ResponseEntity<?> getChatAdminInfo(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws EntityNotFoundException {
        log.info("Запрос на получение информации о клиенте заказа ({})", order_id);
        return new ResponseEntity<>(adminService.getClientContactsInChat(admin_id, password, order_id), HttpStatus.OK);
    }

    @GetMapping("/chat/messages")
    public ResponseEntity<?> getChatMessages(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) {
        log.info("Запрос на получение сообщений заказа ({})", order_id);
        return new ResponseEntity<>(adminService.getMessagesInChat(admin_id, password, order_id), HttpStatus.OK);
    }

    @PostMapping("/chat/message")
    public ResponseEntity<?> postChatMessage(
            @RequestParam(defaultValue = "0") Integer admin_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam String content
    ) throws EntityNotFoundException {
        log.info("Запрос на отправку сообщения в заказе ({})", order_id);
        adminService.postMessageInChat(admin_id, password, order_id, content, new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>("Успех!", HttpStatus.OK);
    }
}
