package main.isbd.services.interfaces;

import main.isbd.data.dto.order.MessageInterface;
import main.isbd.data.dto.order.OrderInterface;
import main.isbd.data.dto.product.ProductInOrderInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.product.ProductShortInfoInterface;
import main.isbd.data.dto.users.AdminContactsInterface;
import main.isbd.data.dto.users.ClientProfileInterface;
import main.isbd.data.model.Client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public interface ClientServiceInterface {
    List<String> getAllRegisteredOrganizations();

    List<String> getAllNotRegisteredOrganizations();

    Client registerClient(String phoneNumber, String email, String password, String name);

    Boolean checkIfUserIsAuthorized(Integer id, String password);

    Client getClientByNameAndPassword(String name, String password);

    ClientProfileInterface getClientProfileById(Integer clientId);

    void setClientProfileById(Integer clientId, String phoneNumber, String email);

    List<ProductShortInfoInterface> getAllProductsShortInfo();

    OrderInterface getCurrentOrderInfoByClientId(Integer clientId);

    ProductInfoInterface getProductInfoById(Integer productId);

    void addProductToOrder(Integer orderId, Integer productId);

    void removeProductFromOrder(Integer orderId, Integer productId);

    List<OrderInterface> getAllOrdersInfoByClientId(Integer clientId);

    OrderInterface getOrderInfoByOrderId(Integer orderId);

    List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer orderId);

    void setProductCountInOrder(Integer orderId, Integer productId, Integer count);

    void acceptOrder(Integer orderId);

    void payForOrder(Integer orderId);

    void cancelOrder(Integer orderId, Timestamp doneAt);

    AdminContactsInterface getAdminContactsInChat(Integer orderId);

    ArrayList<MessageInterface> getMessagesInChat(Integer orderId);

    void postMessageInChat(Integer orderId, String content, Timestamp datetime);
}
