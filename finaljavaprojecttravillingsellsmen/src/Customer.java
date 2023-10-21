public class Customer {

    private String cityName;
    private int customerId;
    private int orderId;
    private String customerName;
    private float cityLt;



    private float cityLn;

    public Customer(String cityName,int customerId, int orderId, String customerName, float cityLt, float cityLn) {
        this.cityName = cityName;
        this.customerId = customerId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.cityLt = cityLt;
        this.cityLn = cityLn;
    }

    public String getCityName() {
        return cityName;
    }
    public int getCustomerId() {
        return customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }
    public float getCityLt() {
        return cityLt;
    }

    public float getCityLn() {
        return cityLn;
    }
}
