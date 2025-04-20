package in.stack.eStore.utils;

public class CommonQueries {

    public static String productTableName = "products";
    public static String baseQuery = "select * from tableName";
    public static String selectQuery = "select * from tableName where productId=?";
    public static String updateRecordsInProductsQuery = "update tableName set productId = ? ,productName = ?,updatedDate = ?,price = ?,descriptions = ?,quantity = ?,itemName = ? where productId = ?";
    public static String insertProductIntoProductQuery = "insert into products values(?,?,?,?,?,?,?,?)";
    public static String cartsTableName = "carts";
    public static String insertProductIntoCartsQuery = "insert into carts values(?,?,?,?,?,?,?,?)";
    public static String deleteRecordFrmTable = "delete from tableName where productId = ?";
    public static String updateRecordsInCartsQuery = "update tableName set productId = ? ,productName = ?,productUpdatedDateInCart = ?,price = ?,descriptions = ?,quantity = ?,itemName = ? where productId = ?";
    public static String OrderTableName = "orders";
    public static String insertProductIntoOrdersQuery = "insert into orders values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static String orderCancelQuery = "update orders set orderStatus = ?, orderCancelledDate = ? where orderId = ?";
    public static String selectOrder = "select * from tableName where orderId=?";
}
