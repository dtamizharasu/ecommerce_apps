package in.stack.eStore.service;

import in.stack.eStore.model.Order;

import java.util.List;

public interface OrderServiceInterface {

    List<Order> getAllOrdersDetails(String query);
    Order getOrderDetailsById(int productId);
    Order placeOrder(Order order);
    String cancelOrder(int orderId);
}
