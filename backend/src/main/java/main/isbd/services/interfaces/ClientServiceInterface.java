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
    public List<String> getAllRegisteredOrganizations();

    public List<String> getAllNotRegisteredOrganizations();

    public Client registerClient(String phoneNumber, String email, String password, String name);

    public Boolean checkIfUserIsAuthorized(Integer id, String password);

    public Client getClientByNameAndPassword(String name, String password);

    public ClientProfileInterface getClientProfileById(Integer clientId);

    public void setClientProfileById(Integer clientId, String phoneNumber, String email);

    public List<ProductShortInfoInterface> getAllProductsShortInfo();

    public OrderInterface getCurrentOrderInfoByClientId(Integer clientId);

    public ProductInfoInterface getProductInfoById(Integer productId);

    public void addProductToOrder(Integer orderId, Integer productId);

    public void removeProductFromOrder(Integer orderId, Integer productId);

    public List<OrderInterface> getAllOrdersInfoByClientId(Integer clientId);

    public OrderInterface getOrderInfoByOrderId(Integer orderId);

    public List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer orderId);

    public void setProductCountInOrder(Integer orderId, Integer productId, Integer count);

    public void acceptOrder(Integer orderId);

    public void payForOrder(Integer orderId);

    public void cancelOrder(Integer orderId, Timestamp doneAt);

    public AdminContactsInterface getAdminContactsInChat(Integer orderId);

    public ArrayList<MessageInterface> getMessagesInChat(Integer orderId);

    public void postMessageInChat(Integer orderId, String content, Timestamp datetime);
}
