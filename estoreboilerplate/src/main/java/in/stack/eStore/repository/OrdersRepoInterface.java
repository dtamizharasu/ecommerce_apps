package in.stack.eStore.repository;

import in.stack.eStore.model.Order;

import java.util.List;

public interface OrdersRepoInterface {

    List<Order> getAllOrdersDetails(String query);
    Order getOrderDetailsById(int productId);
    Order placeOrder(Order order);
    String cancelOrder(int orderId);
}
