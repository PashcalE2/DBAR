package main.isbd.controllers;

import main.isbd.data.dto.order.OrderInfo;
import main.isbd.data.dto.product.ProductInOrderInfo;
import main.isbd.data.dto.product.ProductInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.dto.users.AdminContacts;
import main.isbd.data.dto.users.ClientLogin;
import main.isbd.data.dto.users.ClientProfile;
import main.isbd.data.dto.users.ClientRegister;
import main.isbd.data.model.Client;
import main.isbd.data.model.enums.OrderStatusEnum;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/profile/check_rights")
    public @ResponseBody ResponseEntity<String> checkClientRights(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) throws BadCredentialsException {
        System.out.printf("Запрос проверки прав доступа для клиента (%d)\n", client_id);
        clientService.authorize(client_id, password);
        return new ResponseEntity<>("Успех!", HttpStatus.OK);
    }

    @PostMapping("/profile/login")
    public @ResponseBody ResponseEntity<Client> login(@RequestBody ClientLogin clientLogin) throws BaseAppException {
        return new ResponseEntity<>(clientService.loginClient(clientLogin), HttpStatus.OK);
    }

    @PostMapping("/profile/register")
    public @ResponseBody ResponseEntity<Client> register(@RequestBody ClientRegister clientRegister)
            throws BaseAppException {
        return new ResponseEntity<>(clientService.registerClient(clientRegister), HttpStatus.CREATED);
    }

    @GetMapping("/profile/get")
    public @ResponseBody ResponseEntity<ClientProfile> getProfile(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) throws BadCredentialsException {
        System.out.printf("Запрос на получение данных профиля клиента (%d)\n", client_id);
        return ResponseEntity.ok(clientService.getClientProfile(client_id, password));
    }

    @PostMapping("/profile/set")
    public @ResponseBody ResponseEntity<ClientProfile> setProfile(
            @RequestParam(defaultValue = "1") Integer client_id,
            @RequestParam String password,
            @RequestParam String phone_number,
            @RequestParam String email
    ) throws BaseAppException {
        System.out.printf("Запрос на изменение данных профиля клиента (%d)\n", client_id);
        ClientProfile profile = new ClientProfile(phone_number, email);
        return ResponseEntity.ok(clientService.setClientProfile(client_id, password, profile));
    }

    @GetMapping("/product/get_all_short")
    public @ResponseBody ResponseEntity<List<ProductShortInfo>> getProductsShortInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) throws BadCredentialsException {
        System.out.println("Запрос на получение данных о всей продукции\n");
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.getProductsShortInfo());
    }

    @GetMapping("/product/get")
    public @ResponseBody ResponseEntity<ProductInfo> getProductInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam Integer product_id
    ) throws BaseAppException {
        System.out.printf("Запрос на получение данных о продукции (%d)\n", product_id);
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.getProductInfo(product_id));
    }

    @PostMapping("/product/add_to_order")
    public @ResponseBody ResponseEntity<?> addProductToOrder(
            @RequestParam(defaultValue = "0") Integer clientId,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer orderId,
            @RequestParam(defaultValue = "0") Integer productId
    ) throws BaseAppException {
        System.out.printf("Запрос на добавление продукции (%d) в заказ (%d)\n", productId, orderId);
        clientService.authorize(clientId, password);
        clientService.addProductTypeToOrder(orderId, productId);
        return ResponseEntity.ok("Успех!");
    }

    @PostMapping("/product/remove_from_order")
    public @ResponseBody ResponseEntity<?> removeProductFromOrder(
            @RequestParam(defaultValue = "0") Integer clientId,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer orderId,
            @RequestParam(defaultValue = "0") Integer productId
    ) throws BaseAppException {
        System.out.printf("Запрос на удаление продукции (%d) из заказа (%d)\n", productId, orderId);
        clientService.authorize(clientId, password);
        clientService.removeProductTypeFromOrder(orderId, productId);
        return ResponseEntity.ok("Успех!");
    }

    @GetMapping("/order/get_all_info")
    public @ResponseBody ResponseEntity<List<OrderInfo>> getAllOrdersInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) throws BaseAppException {
        System.out.printf("Запрос на получение информации о заказах клиента (%d)\n", client_id);
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.getAllClientOrdersInfo(client_id));
    }

    @GetMapping("/order/get")
    public @ResponseBody ResponseEntity<OrderInfo> getOrderInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam Integer order_id
    ) throws BaseAppException {
        System.out.printf("Запрос на получение информации о заказе (%d)\n", order_id);
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.getOrderInfo(order_id));
    }

    @GetMapping("/order/get_current")
    public @ResponseBody ResponseEntity<OrderInfo> getCurrentOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password
    ) throws BaseAppException {
        System.out.printf("Запрос на получение информации о формирующемся заказе клиента (%d)\n", client_id);
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.getClientCurrentOrder(client_id));
    }

    @GetMapping("/order/get_products")
    public @ResponseBody ResponseEntity<List<ProductInOrderInfo>> getAllProductsInOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        System.out.printf("Запрос на получение информации о заказе (%d) клиента (%d)\n", order_id, client_id);
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.findAllProductsInOrder(order_id));
    }

    @PostMapping("/order/set_product_count")
    public @ResponseBody ResponseEntity<?> setProductCountInOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam(defaultValue = "0") Integer product_id,
            @RequestParam(defaultValue = "0") Integer count
    ) throws BaseAppException {
        System.out.printf("Запрос на изменение количества продукции (%d) в заказе (%d) клиента (%d)\n",
                count, order_id, client_id);
        clientService.authorize(client_id, password);
        clientService.installProductCountInOrder(product_id, order_id, count);
        return ResponseEntity.ok("Успех!");
    }

    @PostMapping("/order/accept")
    public @ResponseBody ResponseEntity<?> acceptOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        System.out.printf("Запрос на принятие заказа (%d) в обработку\n", order_id);
        clientService.authorize(client_id, password);
        clientService.changeOrderStatus(order_id, OrderStatusEnum.AWAITS_PAYMENT);
        return ResponseEntity.ok("Успех");
    }

    @PostMapping("/order/pay")
    public @ResponseBody ResponseEntity<?> payForOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        System.out.printf("Запрос на оплату заказа (%d)\n", order_id);
        clientService.authorize(client_id, password);
        clientService.changeOrderStatus(order_id, OrderStatusEnum.IN_PROGRESS);
        return ResponseEntity.ok("Успех");
    }

    @PostMapping("/order/cancel")
    public @ResponseBody ResponseEntity<?> cancelOrder(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        System.out.printf("Запрос на отмену заказа (%d)\n", order_id);
        clientService.authorize(client_id, password);
        clientService.changeOrderStatus(order_id, OrderStatusEnum.CANCELED);
        return ResponseEntity.ok("Успех");
    }

    @GetMapping("/chat/get_admin")
    public @ResponseBody ResponseEntity<AdminContacts> getChatAdminInfo(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        System.out.printf("Запрос на получение информации о консультанте заказа (%d)\n", order_id);
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.findOrderAdminContacts(order_id));
    }

    @GetMapping("/chat/get_messages")
    public @ResponseBody ResponseEntity<?> getChatMessages(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id
    ) throws BaseAppException {
        System.out.printf("Запрос на получение сообщений заказа (%d)\n", order_id);
        clientService.authorize(client_id, password);
        return ResponseEntity.ok(clientService.findAllOrderMessages(order_id));
    }

    @PostMapping("/chat/post_message")
    public @ResponseBody ResponseEntity<?> postChatMessage(
            @RequestParam(defaultValue = "0") Integer client_id,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer order_id,
            @RequestParam String content
    ) throws BaseAppException {
        System.out.printf("Запрос на отправку сообщения в заказе (%d)\n", order_id);
        clientService.authorize(client_id, password);
        clientService.postMessageToOrderChat(order_id, content);
        return ResponseEntity.ok("Успех!");
    }

}
