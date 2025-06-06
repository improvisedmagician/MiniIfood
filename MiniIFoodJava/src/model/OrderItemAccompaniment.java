package model;

public class OrderItemAccompaniment {
    private int orderItemId;
    private int accompanimentId;

    public OrderItemAccompaniment() {}

    public OrderItemAccompaniment(int orderItemId, int accompanimentId) {
        this.orderItemId = orderItemId;
        this.accompanimentId = accompanimentId;
    }

    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getAccompanimentId() { return accompanimentId; }
    public void setAccompanimentId(int accompanimentId) { this.accompanimentId = accompanimentId; }
}
