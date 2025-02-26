package main.isbd.services;

import lombok.AllArgsConstructor;
import main.isbd.data.dto.users.*;
import main.isbd.data.model.*;
import main.isbd.data.model.enums.OrderStatusEnum;
import main.isbd.data.model.enums.ProductInOrderStatusEnum;
import main.isbd.data.model.enums.SenderEnum;
import main.isbd.exception.BaseAppException;
import main.isbd.exception.EntityNotFoundException;
import main.isbd.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final ProductTypeRepository productTypeRepository;
    private final OrderRepository orderRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final ClientRepository clientRepository;
    private final MessageRepository messageRepository;
    private final ProductRepository productRepository;
    private final ProductWarehouseRepository productWarehouseRepository;
    private final JwtTokenService jwtTokenService;
    private final AdminScheduleRepository adminScheduleRepository;
    private final SupportServiceRepository supportServiceRepository;
    private final AuthGateway authGateway;

    public AdminRegResponse registerAdmin(AdminRegister adminRegister, String accessToken) throws BaseAppException {
        JwtPairResponse jwtPairResponse = authGateway.registerActorAsAdmin(new ActorRegister(
                adminRegister.getPhoneNumber(),
                adminRegister.getEmail(),
                adminRegister.getLogin(),
                adminRegister.getPassword()
        ), accessToken);
        String adminToken = jwtPairResponse.getAccess();
        Admin savedAdmin;
        try {
            String login = jwtTokenService.extractLoginWithAvailabilityCheck(adminToken, "ROLE_ADMIN");
            AdminSchedule adminSchedule = new AdminSchedule();
            adminSchedule.setWorkingHours(adminRegister.getScheduleHours());
            adminSchedule.setDescription(adminRegister.getScheduleDescription());
            AdminSchedule savedAdminSchedule = adminScheduleRepository.save(adminSchedule);
            SupportService supportService = new SupportService();
            supportService.setAddress(adminRegister.getSupportAddress());
            supportService.setName(adminRegister.getSupportName());
            supportService.setEmail(adminRegister.getSupportEmail());
            supportService.setPhoneNumber(adminRegister.getSupportPhoneNumber());
            SupportService savedSupportService = supportServiceRepository.save(supportService);
            Admin admin = new Admin();
            admin.setLogin(login);
            admin.setFullName(adminRegister.getFullName());
            admin.setClientServiceId(savedAdminSchedule.getId());
            admin.setScheduleId(savedSupportService.getId());
            try {
                savedAdmin = adminRepository.save(admin);
            } catch (Exception e) {
                throw new BaseAppException(e.getMessage(), HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            authGateway.deleteActorByHisToken(adminToken);
            throw e;
        }
        return new AdminRegResponse(adminRegister, savedAdmin, jwtPairResponse);
    }

    public ProductType getProductInfoById(Integer productId) throws BaseAppException {
        return productTypeRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Консультант не найден"));
    }

    public List<Order> getAllOrdersByAdmin(String login) throws EntityNotFoundException {
        Admin admin = adminRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Консультант не найден"));
        return orderRepository.findAllByAdminId(admin);
    }

    public Order getOrderByOrderId(Integer orderId) throws BaseAppException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));
    }

    public List<ProductInOrder> getAllProductsInOrder(Integer orderId) throws BaseAppException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));
        return productInOrderRepository.findAllByOrderId(order);
    }

    public void askForOrderAssembling(Integer orderId) throws BaseAppException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));

        if (!order.getStatus().equals(OrderStatusEnum.IN_PROGRESS)) {
            throw new BaseAppException("Можно собирать только выполняющиеся в данный момент заказы", HttpStatus.FORBIDDEN);
        }

        getAllProductsInOrder(orderId).forEach(productInOrder -> {
            switch (productInOrder.getStatus()) {
                case AWAITS_PRODUCTION -> {
                    ProductId productId = new ProductId();
                    productId.setTypeId(productInOrder.getTypeId().getId());
                    productId.setWarehouseId(productWarehouseRepository.findAll().get(0).getId());

                    Product product = new Product();
                    product.setId(productId);
                    product.setTypeId(productInOrder.getTypeId());
                    product.setCount(productInOrder.getCount());

                    productRepository.save(product);
                }
                case AWAITS_ASSEMBLING -> {
                    Optional<Product> optionalProduct = productRepository.findAll().stream()
                            .filter(product -> product.getTypeId().getId()
                                    .equals(productInOrder.getTypeId().getId()) &&
                                    product.getCount() >= productInOrder.getCount()).findAny();

                    if (optionalProduct.isEmpty()) {
                        return;
                    }

                    Product readyProduct = optionalProduct.get();
                    readyProduct.setCount(readyProduct.getCount() - productInOrder.getCount());
                    productRepository.save(readyProduct);

                    productInOrder.setStatus(ProductInOrderStatusEnum.ASSEMBLED);
                    productInOrderRepository.save(productInOrder);
                }
            }
        });

        long productsRemaining = getAllProductsInOrder(orderId).stream()
                .filter(product -> !(product.getStatus().equals(ProductInOrderStatusEnum.ASSEMBLED)))
                .count();

        if (productsRemaining == 0L) {
            order.setStatus(OrderStatusEnum.DONE);
            orderRepository.save(order);
        }
    }

    public ClientContacts getClientContactsInChat(Integer orderId) throws EntityNotFoundException {
        Integer clientId = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден")).getClientId().getId();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));
        return new ClientContacts(client.getName());
    }

    public List<Message> getMessagesInChat(Integer orderId) {
        return messageRepository.findByOrderId_Id(orderId);
    }

    public void postMessageInChat(Integer orderId, String content) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ не найден"));
        Message message = new Message();
        message.setOrderId(order);
        message.setSender(SenderEnum.ADMIN);
        message.setText(content);
        message.setSentAt(Timestamp.from(Instant.now()));
        messageRepository.saveAndFlush(message);
    }
}
