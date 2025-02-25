package main.isbd.services.legacy;

import main.isbd.data.dto.order.MessageInterface;
import main.isbd.data.dto.order.OrderInterface;
import main.isbd.data.dto.product.ProductInOrderInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.model.Admin;
import main.isbd.data.dto.users.ClientContactsInterface;
import main.isbd.repositories.legacy.LegacyAdminRepository;
import main.isbd.utils.CheckRightsInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.sql.Timestamp;
import java.util.List;

@Deprecated
@Service
@Transactional
@ApplicationScope
public class LegacyAdminService implements CheckRightsInterface {
    private final LegacyAdminRepository legacyAdminRepository;

    public LegacyAdminService(LegacyAdminRepository legacyAdminRepository) {
        this.legacyAdminRepository = legacyAdminRepository;
    }

    public Boolean checkIfUserIsAuthorized(Integer admin_id, String password) {
        return legacyAdminRepository.checkIfAdminIsAuthorized(admin_id, password);
    }

    public Admin getAdminByIdAndPassword(Integer admin_id, String password) {
        return legacyAdminRepository.getAdminByIdAndPassword(admin_id, password);
    }

    public ProductInfoInterface getProductInfoById(Integer product_id) {
        return legacyAdminRepository.getProductInfoById(product_id);
    }

    public List<OrderInterface> getAllOrdersInfoByAdminId(Integer admin_id) {
        return legacyAdminRepository.getAllOrdersInfoByAdminId(admin_id);
    }

    public OrderInterface getOrderInfoByOrderId(Integer order_id) {
        return legacyAdminRepository.getOrderInfoByOrderId(order_id);
    }

    public List<ProductInOrderInfoInterface> getAllProductsInOrder(Integer order_id) {
        return legacyAdminRepository.getAllProductsInOrder(order_id);
    }

    public void askForOrderAssembling(Integer order_id) {
        legacyAdminRepository.askForOrderAssembling(order_id);
    }

    public ClientContactsInterface getClientContactsInChat(Integer order_id) {
        return legacyAdminRepository.getClientContactsInChat(order_id);
    }

    public List<MessageInterface> getMessagesInChat(Integer order_id) {
        return legacyAdminRepository.getMessagesInChat(order_id);
    }

    public void postMessageInChat(Integer order_id, String content, Timestamp datetime) {
        legacyAdminRepository.postMessageInChat(order_id, content, datetime);
    }
}
