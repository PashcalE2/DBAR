package main.isbd.services.interfaces;

import main.isbd.data.dto.users.AdminLogin;
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

    Admin loginAdmin(AdminLogin adminLogin) throws BaseAppException;

    ProductType getProductInfoById(Integer adminId, String password, Integer productId) throws BaseAppException;

    List<Order> getAllOrdersByAdminId(Integer adminId, String password) throws EntityNotFoundException;

    Order getOrderByOrderId(Integer adminId, String password, Integer orderId) throws BaseAppException;

    List<ProductInOrder> getAllProductsInOrder(Integer adminId, String password, Integer orderId) throws BaseAppException;

    void askForOrderAssembling(Integer adminId, String password, Integer orderId) throws BaseAppException;

    ClientContacts getClientContactsInChat(Integer adminId, String password, Integer orderId) throws EntityNotFoundException;

    List<Message> getMessagesInChat(Integer adminId, String password, Integer orderId);

    void postMessageInChat(Integer adminId, String password, Integer orderId, String content, Timestamp datetime) throws BaseAppException;
}
