package in.stack.eStore.service;

import in.stack.eStore.model.Order;
import in.stack.eStore.repository.OrderRepository;

import java.util.List;

public class OrderService implements OrderServiceInterface{

    OrderRepository orderRepository = new OrderRepository();

    @Override
    public List<Order> getAllOrdersDetails(String query) {
        return orderRepository.getAllOrdersDetails(query);
    }

    @Override
    public Order getOrderDetailsById(int productId) {
        return orderRepository.getOrderDetailsById(productId);
    }

    @Override
    public Order placeOrder(Order order) {
        return orderRepository.placeOrder(order);
    }

    @Override
    public String cancelOrder(int orderId) {
        return orderRepository.cancelOrder(orderId);
    }
}
