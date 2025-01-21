package main.isbd.services;

import lombok.AllArgsConstructor;
import main.isbd.data.dto.users.ClientContacts;
import main.isbd.data.model.*;
import main.isbd.data.model.enums.SenderEnum;
import main.isbd.exception.EntityNotFoundException;
import main.isbd.exception.BadCredentialsException;
import main.isbd.repositories.*;
import main.isbd.services.interfaces.AdminServiceInterface;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Transactional
public class AdminService implements AdminServiceInterface {
    private final AdminRepository adminRepository;
    private final ProductTypeRepository productTypeRepository;
    private final OrderRepository orderRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final ClientRepository clientRepository;
    private final MessageRepository messageRepository;

    @Override
    public Boolean checkIfUserIsAuthorized(Integer adminId, String password) throws BadCredentialsException {
        Optional<Admin> admin = adminRepository.findById(adminId);
        return admin.map(value -> value.getPassword().equals(password))
                .orElseThrow(() -> new BadCredentialsException("Консультант не авторизован"));
    }

    @Override
    public Admin getAdminByIdAndPassword(Integer adminId, String password) throws EntityNotFoundException {
        return adminRepository.findByIdAndPassword(adminId, password)
                .orElseThrow(() -> new EntityNotFoundException("Консультант не найден"));
    }

    @Override
    public ProductType getProductInfoById(Integer productId) throws EntityNotFoundException {
        return productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Консультант не найден"));
    }

    @Override
    public List<Order> getAllOrdersByAdminId(Integer adminId) throws EntityNotFoundException {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Консультант не найден"));
        return orderRepository.findByAdminId_Id(adminId);
    }

    @Override
    public Order getOrderByOrderId(Integer orderId) throws EntityNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));
    }

    @Override
    public List<ProductInOrder> getAllProductsInOrder(Integer orderId) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));

        return productInOrderRepository.findByOrderId_Id(orderId);
    }

    @Override
    public void askForOrderAssembling(Integer orderId) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));

        // TODO собрать продукцию...
    }

    @Override
    public ClientContacts getClientContactsInChat(Integer orderId) throws EntityNotFoundException {
        Integer clientId = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден")).getClientId().getId();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));

        return new ClientContacts(client.getName(), client.getPhoneNumber(), client.getEmail());
    }

    @Override
    public List<Message> getMessagesInChat(Integer orderId) {
        return messageRepository.findByOrderId_Id(orderId);
    }

    @Override
    public void postMessageInChat(Integer orderId, String content, Timestamp datetime) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));

        Message message = new Message();
        message.setOrderId(order);
        message.setSender(SenderEnum.ADMIN);
        message.setText(content);
        message.setSentAt(Instant.now());

        messageRepository.saveAndFlush(message);
    }
}
