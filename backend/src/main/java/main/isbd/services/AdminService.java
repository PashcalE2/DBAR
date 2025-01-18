package main.isbd.services;

import lombok.AllArgsConstructor;
import main.isbd.data.dto.order.MessageInterface;
import main.isbd.data.dto.order.OrderInterface;
import main.isbd.data.dto.product.ProductInOrderInfoInterface;
import main.isbd.data.dto.product.ProductInfoInterface;
import main.isbd.data.dto.users.ClientContactsInterface;
import main.isbd.data.model.Admin;
import main.isbd.repositories.AdminRepository;
import main.isbd.services.interfaces.AdminServiceInterface;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Transactional
public class AdminService implements AdminServiceInterface {
    private final AdminRepository adminRepository;

    @Override
    public Boolean checkIfUserIsAuthorized(Integer adminId, String password) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        return admin.map(value -> value.getPassword().equals(password)).orElse(false);
    }

    @Override
    public Admin getAdminByIdAndPassword(Integer adminId, String password) {
        return adminRepository.findByIdAndPassword(adminId, password);
    }

    @Override
    public ProductInfoInterface getProductInfoById(Integer productId) {
        return null;
    }

    @Override
    public List<OrderInterface> getAllOrdersInfoByAdminId(Integer adminId) {
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
    public void askForOrderAssembling(Integer orderId) {

    }

    @Override
    public ClientContactsInterface getClientContactsInChat(Integer orderId) {
        return null;
    }

    @Override
    public List<MessageInterface> getMessagesInChat(Integer orderId) {
        return List.of();
    }

    @Override
    public void postMessageInChat(Integer orderId, String content, Timestamp datetime) {

    }
}
