package main.isbd.controllers;

import main.isbd.data.AppInfoResponse;
import main.isbd.data.dto.order.ChatMessage;
import main.isbd.data.dto.order.OrderInfo;
import main.isbd.data.dto.product.ProductInOrderInfo;
import main.isbd.data.dto.product.ProductInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.dto.users.ActorRegister;
import main.isbd.data.dto.users.AdminContacts;
import main.isbd.data.dto.users.ClientRegResponse;
import main.isbd.data.model.enums.OrderStatusEnum;
import main.isbd.exception.BaseAppException;
import main.isbd.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/profile/register")
    public @ResponseBody ResponseEntity<ClientRegResponse> register(@RequestBody ActorRegister clientRegister)
            throws BaseAppException {
        return new ResponseEntity<>(clientService.registerClient(clientRegister), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/product/get_all_short")
    public @ResponseBody ResponseEntity<List<ProductShortInfo>> getProductsShortInfo() {
        System.out.println("Запрос на получение данных о всей продукции\n");
        return ResponseEntity.ok(clientService.getProductsShortInfo());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/product/get")
    public @ResponseBody ResponseEntity<ProductInfo> getProductInfo(@RequestParam Integer product_id)
            throws BaseAppException {
        System.out.printf("Запрос на получение данных о продукции (%d)\n", product_id);
        return ResponseEntity.ok(clientService.getProductInfo(product_id));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/product/add_to_order")
    public @ResponseBody ResponseEntity<AppInfoResponse> addProductToOrder(
            @RequestParam(defaultValue = "0") Integer orderId,
            @RequestParam(defaultValue = "0") Integer productId
    ) throws BaseAppException {
        System.out.printf("Запрос на добавление продукции (%d) в заказ (%d)\n", productId, orderId);
        clientService.addProductTypeToOrder(orderId, productId);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/product/remove_from_order")
    public @ResponseBody ResponseEntity<AppInfoResponse> removeProductFromOrder(
            @RequestParam(defaultValue = "0") Integer orderId,
            @RequestParam(defaultValue = "0") Integer productId
    ) throws BaseAppException {
        System.out.printf("Запрос на удаление продукции (%d) из заказа (%d)\n", productId, orderId);
        clientService.removeProductTypeFromOrder(orderId, productId);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/order/get_all_info")
    public @ResponseBody ResponseEntity<List<OrderInfo>> getAllOrdersInfo(
            @AuthenticationPrincipal UserDetails userDetails) throws BaseAppException {
        System.out.printf("Запрос на получение информации о заказах клиента (%s)\n", userDetails.getUsername());
        return ResponseEntity.ok(clientService.getAllClientOrdersInfo(userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/order/get")
    public @ResponseBody ResponseEntity<OrderInfo> getOrderInfo(
            @RequestParam Integer order_id) throws BaseAppException {
        System.out.printf("Запрос на получение информации о заказе (%d)\n", order_id);
        return ResponseEntity.ok(clientService.getOrderInfo(order_id));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/order/get_current")
    public @ResponseBody ResponseEntity<OrderInfo> getCurrentOrder(@AuthenticationPrincipal UserDetails userDetails)
            throws BaseAppException {
        System.out.printf("Запрос на получение информации о формирующемся заказе клиента (%s)\n",
                userDetails.getUsername());
        return ResponseEntity.ok(clientService.getClientCurrentOrder(userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/order/get_products")
    public @ResponseBody ResponseEntity<List<ProductInOrderInfo>> getAllProductsInOrder(
            @RequestParam(defaultValue = "0") Integer order_id) throws BaseAppException {
        System.out.printf("Запрос на получение информации о заказе (%d)\n", order_id);
        return ResponseEntity.ok(clientService.findAllProductsInOrder(order_id));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/order/set_product_count")
    public @ResponseBody ResponseEntity<AppInfoResponse> setProductCountInOrder(
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam(defaultValue = "0") Integer product_id,
            @RequestParam(defaultValue = "0") Integer count) throws BaseAppException {
        System.out.printf("Запрос на изменение количества продукции (%d) в заказе (%d)\n",
                count, order_id);
        clientService.installProductCountInOrder(product_id, order_id, count);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/order/accept")
    public @ResponseBody ResponseEntity<AppInfoResponse> acceptOrder(@RequestParam(defaultValue = "0") Integer order_id)
            throws BaseAppException {
        System.out.printf("Запрос на принятие заказа (%d) в обработку\n", order_id);
        clientService.changeOrderStatus(order_id, OrderStatusEnum.AWAITS_PAYMENT);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/order/pay")
    public @ResponseBody ResponseEntity<AppInfoResponse> payForOrder(@RequestParam(defaultValue = "0") Integer order_id)
            throws BaseAppException {
        System.out.printf("Запрос на оплату заказа (%d)\n", order_id);
        clientService.changeOrderStatus(order_id, OrderStatusEnum.IN_PROGRESS);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/order/cancel")
    public @ResponseBody ResponseEntity<AppInfoResponse> cancelOrder(@RequestParam(defaultValue = "0") Integer order_id)
            throws BaseAppException {
        System.out.printf("Запрос на отмену заказа (%d)\n", order_id);
        clientService.changeOrderStatus(order_id, OrderStatusEnum.CANCELED);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/chat/get_admin")
    public @ResponseBody ResponseEntity<AdminContacts> getChatAdmin(
            @RequestParam(defaultValue = "0") Integer order_id, @AuthenticationPrincipal UserDetails userDetails)
            throws BaseAppException {
        System.out.printf("Запрос на получение информации о консультанте заказа (%d)\n", order_id);
        return ResponseEntity.ok(clientService.findOrderAdmin(order_id, userDetails.getPassword()));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/chat/get_messages")
    public @ResponseBody ResponseEntity<List<ChatMessage>> getChatMessages(
            @RequestParam(defaultValue = "0") Integer order_id) throws BaseAppException {
        System.out.printf("Запрос на получение сообщений заказа (%d)\n", order_id);
        return ResponseEntity.ok(clientService.findAllOrderMessages(order_id));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/chat/post_message")
    public @ResponseBody ResponseEntity<AppInfoResponse> postChatMessage(
            @RequestParam(defaultValue = "0") Integer order_id, @RequestParam String content) throws BaseAppException {
        System.out.printf("Запрос на отправку сообщения в заказе (%d)\n", order_id);
        clientService.postMessageToOrderChat(order_id, content);
        return ResponseEntity.ok(new AppInfoResponse("Успех!", HttpStatus.OK));
    }

}
