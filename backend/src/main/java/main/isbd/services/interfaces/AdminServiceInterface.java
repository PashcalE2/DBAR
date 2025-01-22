package main.isbd.services.interfaces;

import main.isbd.data.dto.users.ClientContacts;
import main.isbd.data.model.*;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.EntityNotFoundException;

import java.sql.Timestamp;
import java.util.List;


// Вся необходимая логика для Консультанта
public interface AdminServiceInterface {
    Boolean checkIfUserIsAuthorized(Integer adminId, String password) throws BadCredentialsException;

    Admin getAdminByIdAndPassword(Integer adminId, String password) throws EntityNotFoundException;

    ProductType getProductInfoById(Integer productId) throws EntityNotFoundException;

    List<Order> getAllOrdersByAdminId(Integer adminId) throws EntityNotFoundException;

    Order getOrderByOrderId(Integer orderId) throws EntityNotFoundException;

    List<ProductInOrder> getAllProductsInOrder(Integer orderId) throws EntityNotFoundException;

    void askForOrderAssembling(Integer orderId) throws BaseAppException;

    ClientContacts getClientContactsInChat(Integer orderId) throws EntityNotFoundException;

    List<Message> getMessagesInChat(Integer orderId);

    void postMessageInChat(Integer orderId, String content, Timestamp datetime) throws BaseAppException;
}
