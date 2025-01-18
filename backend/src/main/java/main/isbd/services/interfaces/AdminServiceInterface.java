package main.isbd.services.interfaces;

import main.isbd.data.dto.order.MessageInterface;
import main.isbd.data.dto.order.OrderInterface;
import main.isbd.data.dto.product.ProductInOrderInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.users.ClientContactsInterface;
import main.isbd.data.model.Admin;

import java.sql.Timestamp;
import java.util.List;


// Вся необходимая логика для Консультанта
public interface AdminServiceInterface {
    Boolean checkIfUserIsAuthorized(Integer adminId, String password);

    Admin getAdminByIdAndPassword(Integer adminId, String password);

    ProductInfoInterface getProductInfoById(Integer productId);

    List<OrderInterface> getAllOrdersInfoByAdminId(Integer adminId);

    OrderInterface getOrderInfoByOrderId(Integer orderId);

    List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer orderId);

    void askForOrderAssembling(Integer orderId);

    ClientContactsInterface getClientContactsInChat(Integer orderId);

    List<MessageInterface> getMessagesInChat(Integer orderId);

    void postMessageInChat(Integer orderId, String content, Timestamp datetime);
}
