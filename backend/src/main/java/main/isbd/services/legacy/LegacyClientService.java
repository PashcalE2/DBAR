package main.isbd.services.legacy;

import main.isbd.data.dto.order.MessageInterface;
import main.isbd.data.dto.order.OrderInterface;
import main.isbd.data.dto.product.ProductInOrderInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.product.ProductShortInfoInterface;
import main.isbd.data.dto.users.AdminContactsInterface;
import main.isbd.data.model.Client;
import main.isbd.data.dto.users.ClientProfileInterface;
import main.isbd.repositories.legacy.LegacyClientRepository;
import main.isbd.utils.CheckRightsInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@ApplicationScope
@Deprecated
public class LegacyClientService implements CheckRightsInterface {
    private final LegacyClientRepository legacyClientRepository;

    public LegacyClientService(LegacyClientRepository legacyClientRepository) {
        this.legacyClientRepository = legacyClientRepository;
    }

    public List<String> getAllRegisteredOrganizations() {
        return legacyClientRepository.getAllRegisteredOrganizations();
    }

    public List<String> getAllNotRegisteredOrganizations() {
        return legacyClientRepository.getAllNotRegisteredOrganizations();
    }

    public Client registerClient(String phone_number, String email, String password, String name) {
        return legacyClientRepository.registerClient(phone_number, email, password, name);
    }

    public Boolean checkIfUserIsAuthorized(Integer id, String password) {
        return legacyClientRepository.checkIfClientIsAuthorised(id, password);
    }

    public Client getClientByNameAndPassword(String name, String password) {
        return legacyClientRepository.getClientByNameAndPassword(name, password);
    }

    public ClientProfileInterface getClientProfileById(Integer client_id) {
        return legacyClientRepository.getClientProfileById(client_id);
    }

    public void setClientProfileById(Integer client_id, String phone_number, String email) {
        legacyClientRepository.setClientProfileById(client_id, phone_number, email);
    }

    public List<ProductShortInfoInterface> getAllProductsShortInfo() {
        return legacyClientRepository.getAllProductsShortInfo();
    }

    public OrderInterface getCurrentOrderInfoByClientId(Integer client_id) {
        return legacyClientRepository.getCurrentOrderInfoByClientId(client_id);
    }

    public ProductInfoInterface getProductInfoById(Integer product_id) {
        return legacyClientRepository.getProductInfoById(product_id);
    }

    public void addProductToOrder(Integer order_id, Integer product_id) {
        legacyClientRepository.addProductToOrder(order_id, product_id);
    }

    public void removeProductFromOrder(Integer order_id, Integer product_id) {
        legacyClientRepository.removeProductFromOrder(order_id, product_id);
    }

    public List<OrderInterface> getAllOrdersInfoByClientId(Integer client_id) {
        return legacyClientRepository.getAllOrdersInfoByClientId(client_id);
    }

    public OrderInterface getOrderInfoByOrderId(Integer order_id) {
        return legacyClientRepository.getOrderInfoByOrderId(order_id);
    }

    public List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer order_id) {
        return legacyClientRepository.getAllProductsInOrder(order_id);
    }

    public void setProductCountInOrder(Integer order_id, Integer product_id, Integer count) {
        legacyClientRepository.setProductCountInOrder(order_id, product_id, count);
    }

    public void acceptOrder(Integer order_id) {
        legacyClientRepository.acceptOrder(order_id);
    }

    public void payForOrder(Integer order_id) {
        legacyClientRepository.payForOrder(order_id);
    }

    public void cancelOrder(Integer order_id, Timestamp done_at) {
        legacyClientRepository.cancelOrder(order_id, done_at);
    }

    public AdminContactsInterface getAdminContactsInChat(Integer order_id) {
        return legacyClientRepository.getAdminContactsInChat(order_id);
    }

    public ArrayList<MessageInterface> getMessagesInChat(Integer order_id) {
        return new ArrayList<>(legacyClientRepository.getMessagesInChat(order_id));
    }

    public void postMessageInChat(Integer order_id, String content, Timestamp datetime) {
        legacyClientRepository.postMessageInChat(order_id, content, datetime);
    }
}
