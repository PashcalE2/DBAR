package main.isbd.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.isbd.data.AppInfoResponse;
import main.isbd.data.dto.users.AdminRegResponse;
import main.isbd.data.dto.users.AdminRegister;
import main.isbd.data.dto.users.ClientContacts;
import main.isbd.data.model.*;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.EntityNotFoundException;
import main.isbd.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('FACTORY')")
    @PostMapping("/profile/register")
    public @ResponseBody ResponseEntity<AdminRegResponse> register(
            @RequestBody AdminRegister adminRegister, @AuthenticationPrincipal UserDetails userDetails)
            throws BaseAppException {
        return new ResponseEntity<>(adminService.registerAdmin(adminRegister, userDetails.getPassword()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product")
    public ResponseEntity<ProductType> getProductInfo(
            @RequestParam Integer product_id
    ) throws BaseAppException {
        log.info("Запрос на получение данных о продукции ({})", product_id);
        return new ResponseEntity<>(adminService.getProductInfoById(product_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order/all_info")
    public ResponseEntity<List<Order>> getAllOrdersInfo(@AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotFoundException {
        log.info("Запрос на получение информации о заказах консультанта ({})", userDetails.getUsername());
        return new ResponseEntity<>(adminService.getAllOrdersByAdmin(userDetails.getUsername()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order")
    public ResponseEntity<Order> getOrderInfo(@RequestParam(defaultValue = "0") Integer order_id) throws BaseAppException {
        log.info("Запрос на получение информации о заказе ({})", order_id);
        return new ResponseEntity<>(adminService.getOrderByOrderId(order_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order/products")
    public ResponseEntity<List<ProductInOrder>> getAllProductsInOrder(@RequestParam(defaultValue = "0") Integer order_id)
            throws BaseAppException {
        log.info("Запрос на получение информации о продуктах в заказе ({})", order_id);
        return new ResponseEntity<>(adminService.getAllProductsInOrder(order_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/order/ask-for-assembling")
    public ResponseEntity<AppInfoResponse> assemblyOrder(@RequestParam(defaultValue = "0") Integer order_id) throws BaseAppException {
        log.info("Запрос на сборку заказа ({})", order_id);
        adminService.askForOrderAssembling(order_id);
        return ResponseEntity.ok(new AppInfoResponse("Что-то возможно получилось!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chat/client")
    public ResponseEntity<ClientContacts> getChatAdminInfo(@RequestParam(defaultValue = "0") Integer order_id)
            throws EntityNotFoundException {
        log.info("Запрос на получение информации о клиенте заказа ({})", order_id);
        return new ResponseEntity<>(adminService.getClientContactsInChat(order_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chat/messages")
    public ResponseEntity<List<Message>> getChatMessages(@RequestParam(defaultValue = "0") Integer order_id) {
        log.info("Запрос на получение сообщений заказа ({})", order_id);
        return new ResponseEntity<>(adminService.getMessagesInChat(order_id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/chat/message")
    public ResponseEntity<AppInfoResponse> postChatMessage(@RequestParam(defaultValue = "0") Integer order_id,
                                             @RequestParam String content) throws EntityNotFoundException {
        log.info("Запрос на отправку сообщения в заказе ({})", order_id);
        adminService.postMessageInChat(order_id, content);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }
}
