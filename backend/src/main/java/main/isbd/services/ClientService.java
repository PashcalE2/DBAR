package main.isbd.services;

import lombok.AllArgsConstructor;
import main.isbd.data.dto.order.MessageInterface;
import main.isbd.data.dto.order.OrderInterface;
import main.isbd.data.dto.product.ProductInOrderInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.product.ProductShortInfoInterface;
import main.isbd.data.dto.users.AdminContactsInterface;
import main.isbd.data.dto.users.ClientProfileInterface;
import main.isbd.data.model.Client;
import main.isbd.services.interfaces.ClientServiceInterface;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Transactional
public class ClientService implements ClientServiceInterface {
    @Override
    public List<String> getAllRegisteredOrganizations() {
        return List.of();
    }

    @Override
    public List<String> getAllNotRegisteredOrganizations() {
        return List.of();
    }

    @Override
    public Client registerClient(String phoneNumber, String email, String password, String name) {
        return null;
    }

    @Override
    public Boolean checkIfUserIsAuthorized(Integer id, String password) {
        return null;
    }

    @Override
    public Client getClientByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public ClientProfileInterface getClientProfileById(Integer clientId) {
        return null;
    }

    @Override
    public void setClientProfileById(Integer clientId, String phoneNumber, String email) {

    }

    @Override
    public List<ProductShortInfoInterface> getAllProductsShortInfo() {
        return List.of();
    }

    @Override
    public OrderInterface getCurrentOrderInfoByClientId(Integer clientId) {
        return null;
    }

    @Override
    public ProductInfoInterface getProductInfoById(Integer productId) {
        return null;
    }

    @Override
    public void addProductToOrder(Integer orderId, Integer productId) {

    }

    @Override
    public void removeProductFromOrder(Integer orderId, Integer productId) {

    }

    @Override
    public List<OrderInterface> getAllOrdersInfoByClientId(Integer clientId) {
        return List.of();
    }

    @Override
    public OrderInterface getOrderInfoByOrderId(Integer orderId) {
        return null;
    }

    @Override
    public List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer orderId) {
        return List.of();
    }

    @Override
    public void setProductCountInOrder(Integer orderId, Integer productId, Integer count) {

    }

    @Override
    public void acceptOrder(Integer orderId) {

    }

    @Override
    public void payForOrder(Integer orderId) {

    }

    @Override
    public void cancelOrder(Integer orderId, Timestamp doneAt) {

    }

    @Override
    public AdminContactsInterface getAdminContactsInChat(Integer orderId) {
        return null;
    }

    @Override
    public ArrayList<MessageInterface> getMessagesInChat(Integer orderId) {
        return null;
    }

    @Override
    public void postMessageInChat(Integer orderId, String content, Timestamp datetime) {

    }
}
