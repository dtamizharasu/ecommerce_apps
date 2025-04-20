package in.stack.eStore;

import in.stack.eStore.model.Order;
import in.stack.eStore.model.Products;
import in.stack.eStore.repository.OrderRepository;
import in.stack.eStore.service.OrderService;
import in.stack.eStore.utils.CommonQueries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOrderController {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    public void viewAllOrders(){
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        order.setProducts(p1);
        order.setOrderId(151);
        order.setOrderStatus("Active");
        Products p2 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        Order order2 = new Order();
        order2.setProducts(p2);
        order2.setOrderId(152);
        order2.setOrderStatus("Cancelled");
        orderList.add(order);
        orderList.add(order2);
        String query = CommonQueries.baseQuery.replace("tableName",CommonQueries.OrderTableName);
        Mockito.when(orderRepository.getAllOrdersDetails(query)).thenReturn(orderList);
        List<Order> result = orderService.getAllOrdersDetails(query);
        assertNotNull(result);
        assertEquals("Active",result.get(0).getOrderStatus(),"The Give Order ID Status Not Found");
        assertEquals(152,result.get(1).getOrderId(),"The Give Order Details Not Added");
    }

    @Test
    public void placeOrder(){
        Order order = new Order();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        order.setProducts(p1);
        order.setOrderId(151);
        order.setOrderStatus("Active");
        Mockito.when(orderRepository.placeOrder(order)).thenReturn(order);
        Order result = orderService.placeOrder(order);
        assertNotNull(result);
        assertEquals("Active",result.getOrderStatus(),"The Order Status Not Found");
    }

    @Test
    public void getOrderDetailsByID(){
        Order order = new Order();
        Products p1 = new Products(101,"LG TV","2021-06-09 04:42:00","2021-06-09 04:42:00",25000.0,"LG Products",40,"HomeAppliances");
        order.setProducts(p1);
        order.setOrderId(151);
        order.setOrderStatus("Active");
        Mockito.when(orderRepository.getOrderDetailsById(151)).thenReturn(order);
        Order result = orderService.getOrderDetailsById(151);
        assertNotNull(result);
        assertEquals(151,result.getOrderId(),"Given Order ID Not Valid");
    }

    @Test
    public void cancelOrder(){
        String message = "The Given Order Has Been Cancelled Successfully " + 151;
        Mockito.when(orderRepository.cancelOrder(151)).thenReturn(message);
        String result = orderService.cancelOrder(151);
        assertNotNull(result);
        assertEquals(message,result,"The Give Order ID Not Valid");
    }
}
