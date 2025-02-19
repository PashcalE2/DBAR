package main.isbd.services;

import main.isbd.data.dto.order.ChatMessage;
import main.isbd.data.dto.order.OrderInfo;
import main.isbd.data.dto.product.ProductInOrderInfo;
import main.isbd.data.dto.product.ProductInfo;
import main.isbd.data.dto.product.ProductShortInfo;
import main.isbd.data.dto.users.AdminContacts;
import main.isbd.data.dto.users.ClientLogin;
import main.isbd.data.dto.users.ClientProfile;
import main.isbd.data.dto.users.ClientRegister;
import main.isbd.data.model.*;
import main.isbd.data.model.enums.OrderStatusEnum;
import main.isbd.data.model.enums.ProductInOrderStatusEnum;
import main.isbd.data.model.enums.SenderEnum;
import main.isbd.exception.BadCredentialsException;
import main.isbd.exception.BaseAppException;
import main.isbd.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ProductTypeRepository productTypeRepository;
    private final OrderRepository orderRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final AdminRepository adminRepository;
    private final MessageRepository messageRepository;

    public ClientService(ClientRepository clientRepository, ProductTypeRepository productTypeRepository, OrderRepository orderRepository, ProductInOrderRepository productInOrderRepository, AdminRepository adminRepository, MessageRepository messageRepository) {
        this.clientRepository = clientRepository;
        this.productTypeRepository = productTypeRepository;
        this.orderRepository = orderRepository;
        this.productInOrderRepository = productInOrderRepository;
        this.adminRepository = adminRepository;
        this.messageRepository = messageRepository;
    }

    public Client authorize(Integer client_id, String password) throws BadCredentialsException {
        Client client = clientRepository.findById(client_id).orElseThrow(
                () -> new BadCredentialsException("Client not found")
        );
        if (!client.getPassword().equals(password)) {
            throw new BadCredentialsException("Wrong password");
        }
        return client;
    }

    public Client loginClient(ClientLogin clientLogin) throws BaseAppException {
        if (clientLogin == null || !clientLogin.isValid()) {
            throw new BaseAppException("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }
        System.out.printf("Клиент (%s) пытается зайти\n", clientLogin.getName());
        return clientRepository.findByNameAndPassword(clientLogin.getName(), clientLogin.getPassword())
                .orElseThrow(() -> new BadCredentialsException("Неверный логин или пароль\n"));
    }

    public Client registerClient(ClientRegister clientRegister) throws BaseAppException {
        if (clientRegister == null || !clientRegister.isValid()) {
            throw new BaseAppException("Какой-то странный у вас реквест боди\n", HttpStatus.BAD_REQUEST);
        }
        Client client = new Client();
        client.setPhoneNumber(clientRegister.getPhoneNumber());
        client.setEmail(clientRegister.getEmail());
        client.setPassword(clientRegister.getPassword());
        client.setName(clientRegister.getName());
        Client savedClient;
        try {
            savedClient = clientRepository.save(client);
        } catch (Exception e) {
            throw new BaseAppException(e.getMessage(), HttpStatus.CONFLICT);
        }
        return savedClient;
    }

    public ClientProfile getClientProfile(Integer client_id, String password) throws BadCredentialsException {
        Client client = authorize(client_id, password);
        return new ClientProfile(client.getPhoneNumber(), client.getEmail());
    }

    public ClientProfile setClientProfile(Integer client_id, String password, ClientProfile clientProfile)
            throws BaseAppException {
        Client client = authorize(client_id, password);
        client.setPhoneNumber(clientProfile.getPhoneNumber());
        client.setEmail(clientProfile.getEmail());
        Client savedClient = clientRepository.save(client);
        return new ClientProfile(savedClient.getPhoneNumber(), savedClient.getEmail());
    }

    public List<ProductShortInfo> getProductsShortInfo() {
        return productTypeRepository.findAll().stream()
                .map(p -> new ProductShortInfo(p.getId(), p.getName(), p.getPrice()))
                .toList();
    }

    public ProductInfo getProductInfo(Integer product_id) throws BaseAppException {
        ProductType productType = productTypeRepository.findById(product_id)
                .orElseThrow( () -> new BaseAppException("No product with id: " +  product_id, HttpStatus.NOT_FOUND));
        return new ProductInfo(
                productType.getId(),
                productType.getName(),
                productType.getPrice(),
                productType.getDescription()
        );
    }

    public void addProductTypeToOrder(Integer orderId, Integer productId) throws BaseAppException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseAppException("No order with id: " + orderId, HttpStatus.NOT_FOUND));
        ProductType productType = productTypeRepository.findById(productId)
                .orElseThrow(() -> new BaseAppException("No productType with id: " +  productId, HttpStatus.NOT_FOUND));
        ProductInOrderId productInOrderId = new ProductInOrderId();
        productInOrderId.setOrderId(order.getId());
        productInOrderId.setTypeId(productType.getId());
        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setId(productInOrderId);
        productInOrder.setTypeId(productType);
        productInOrder.setOrderId(order);
        productInOrder.setCount(1);
        productInOrder.setStatus(ProductInOrderStatusEnum.AWAITS_PRODUCTION);
        try {
            productInOrderRepository.save(productInOrder);
        } catch (Exception e) {
            throw new BaseAppException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public void removeProductTypeFromOrder(Integer orderId, Integer productId) throws BaseAppException {
        ProductInOrderId productInOrderId = new ProductInOrderId();
        productInOrderId.setOrderId(orderId);
        productInOrderId.setTypeId(productId);
        ProductInOrder productInOrder = productInOrderRepository.findById(productInOrderId)
                .orElseThrow(() -> new BaseAppException(
                        "No product " + productId + " in order " + orderId,
                        HttpStatus.NOT_FOUND)
                );
        try {
            productInOrderRepository.delete(productInOrder);
        } catch (Exception e) {
            throw new BaseAppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<OrderInfo> getAllClientOrdersInfo(Integer clientId) throws BaseAppException {
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new BadCredentialsException("Client not found")
        );
        return orderRepository.findAllByClientId(client).stream().map(
                o -> new OrderInfo(o.getId(), o.getClientId().getId(), o.getAdminId().getId(),
                        o.getStatus().getValue(), o.getCreatedAt(), o.getCompletedAt())).toList();
    }

    public OrderInfo getOrderInfo(Integer orderId) throws BaseAppException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BaseAppException("No order with id: " + orderId, HttpStatus.NOT_FOUND));
        return new OrderInfo(order.getId(), order.getClientId().getId(), order.getAdminId().getId(),
                order.getStatus().getValue(), order.getCreatedAt(), order.getCompletedAt());
    }

    public OrderInfo getClientCurrentOrder(Integer clientId) throws BaseAppException {
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new BadCredentialsException("Client not found")
        );
        Optional<Order> optionalOrder = orderRepository.findOneByClientIdAndStatus(client, OrderStatusEnum.BEING_FORMED);
        Order order;
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            order = createNewEmptyOrder(client);
        }
        return new OrderInfo(order.getId(), order.getClientId().getId(), order.getAdminId().getId(),
                order.getStatus().getValue(), order.getCreatedAt(), order.getCompletedAt());
    }

    public Order createNewEmptyOrder(Client client) throws BaseAppException {
        long adminCount = adminRepository.count();
        Random random = new Random();
        long selectedAdminId = random.nextLong(adminCount) + 1;
        Admin admin = adminRepository.findById((int) selectedAdminId).orElseThrow(
                () -> new BaseAppException("Selected Admin does not exist" , HttpStatus.INTERNAL_SERVER_ERROR)
        );
        Order order = new Order();
        order.setClientId(client);
        order.setAdminId(admin);
        order.setStatus(OrderStatusEnum.BEING_FORMED);
        order.setCreatedAt(Timestamp.from(Instant.now()));
        order.setCompletedAt(null);
        return orderRepository.save(order);
    }

    public List<ProductInOrderInfo> findAllProductsInOrder(Integer orderId) throws BaseAppException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BaseAppException("No order with id: " + orderId, HttpStatus.NOT_FOUND)
        );
        return productInOrderRepository.findAllByOrderId(order).stream().map(
                p -> new ProductInOrderInfo(p.getTypeId().getId(), p.getStatus().getValue(), p.getCount())
        ).toList();
    }

    public void installProductCountInOrder(Integer productId, Integer orderId, Integer count) throws BaseAppException {
        ProductInOrderId productInOrderId = new ProductInOrderId();
        productInOrderId.setOrderId(orderId);
        productInOrderId.setTypeId(productId);
        ProductInOrder productInOrder = productInOrderRepository.findById(productInOrderId).orElseThrow(
                () -> new BaseAppException("No product " + productId + " in order " + orderId, HttpStatus.NOT_FOUND)
        );
        productInOrder.setCount(count);
        try {
            productInOrderRepository.save(productInOrder);
        } catch (Exception e) {
            throw new BaseAppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void changeOrderStatus(Integer orderId, OrderStatusEnum status) throws BaseAppException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BaseAppException("No order with id: " + orderId, HttpStatus.NOT_FOUND)
        );
        order.setStatus(status);
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            throw new BaseAppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AdminContacts findOrderAdminContacts(Integer orderId) throws BaseAppException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BaseAppException("No order with id: " + orderId, HttpStatus.NOT_FOUND)
        );
        Admin admin = order.getAdminId();
        return new AdminContacts(admin.getFullName(), admin.getPhoneNumber(), admin.getEmail());
    }

    public List<ChatMessage> findAllOrderMessages(Integer orderId) throws BaseAppException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BaseAppException("No order with id: " + orderId, HttpStatus.NOT_FOUND)
        );
        return messageRepository.findAllByOrderId(order).stream().map(m -> new ChatMessage(
                m.getId(), m.getSender().getValue(), m.getText(), m.getSentAt())).toList();
    }

    public void postMessageToOrderChat(Integer orderId, String content) throws BaseAppException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BaseAppException("No order with id: " + orderId, HttpStatus.NOT_FOUND)
        );
        Message message = new Message();
        message.setOrderId(order);
        message.setSender(SenderEnum.CLIENT);
        message.setText(content);
        message.setSentAt(Timestamp.from(Instant.now()));
        try {
            messageRepository.save(message);
        } catch (Exception e) {
            throw new BaseAppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
