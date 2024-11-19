package org.example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

enum HoneyType{
    BAGREM, LIVADA, KESTEN
}

interface Sellable{
    double calcuateDiscount(double discountRate);
    String getDescription();
}

abstract class Item implements Sellable{
    private String barcode;
    private String name;
    private double price;

    public Item(String barcode, String name, double price){
        this.barcode = barcode;
        this.name = name;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double calcuateDiscount(double discountRate) {
        return discountRate/100 * this.price;
    }

    @Override
    public String getDescription() {
        return "Barcode: " + this.barcode + ", Name: " + this.name + ", Price: " + this.price;
    }
}

class Milk extends Item{
    private double fat;

    public Milk(String barcode, String name, double price, double fat) {
        super(barcode, name, price);
        this.fat = fat;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Fat: " + this.fat;
    }
}

class Honey extends Item{
    private HoneyType honeyType;

    public Honey(String barcode, String name, double price, HoneyType honeyType) {
        super(barcode, name, price);
        this.honeyType = honeyType;
    }

    public HoneyType getHoneyType() {
        return honeyType;
    }

    public void setHoneyType(HoneyType honeyType) {
        this.honeyType = honeyType;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Honey: " + this.honeyType;
    }
}

class Order<T extends Item & Sellable>{
    private String orderNo;
    private Date createAt;
    private HashMap<T, Integer> items;

    public Order(String orderNo, Date createAt, HashMap items) {
        this.orderNo = orderNo;
        this.createAt = createAt;
        this.items = new HashMap<>();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public HashMap<T, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<T, Integer> items) {
        this.items = items;
    }

    public void addItem(T item, int quantity){
        if (this.items.containsKey(item))
            this.items.put(item, this.items.get(item) + quantity);
        else
            this.items.put(item, quantity);
    }

    public double calculateTotalAmount(){
        double total = 0;
        for(Map.Entry<T, Integer> each: this.items.entrySet()){
            total += each.getKey().getPrice() * each.getValue();
        }
        return total;
    }
}

class Person{
    private String name;
    private int age;
    private List<Order<? extends Item>> orders;

    public Person(String name, int age, List<Order<? extends Item>> orders) {
        this.name = name;
        this.age = age;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order<? extends Item>> getOrders() {
        return orders;
    }

    public void setOrders(List<Order<? extends Item>> orders) {
        this.orders = orders;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addOrder(Order<? extends Item> order){
        this.orders.add(order);
    }
}


public class Main {
    public static void main(String[] args) {

        Milk milk1 = new Milk("12345", "Cow Milk", 1.50, 3.5);
        Milk milk2 = new Milk("67890", "Goat Milk", 2.00, 4.0);

        // Create Honey objects
        Honey honey1 = new Honey("11223", "Wildflower Honey", 5.00, HoneyType.LIVADA);
        Honey honey2 = new Honey("44556", "Manuka Honey", 10.00, HoneyType.BAGREM);

        // Create Orders
        Order<Milk> milkOrder = new Order<>("M001", new Date(), new HashMap<>());
        Order<Honey> honeyOrder = new Order<>("H001", new Date(), new HashMap<>());

        // Add items to Milk order
        milkOrder.addItem(milk1, 2); // 2 units of Cow Milk
        milkOrder.addItem(milk2, 1); // 1 unit of Goat Milk

        // Add items to Honey order
        honeyOrder.addItem(honey1, 3); // 3 units of Wildflower Honey
        honeyOrder.addItem(honey2, 2); // 2 units of Manuka Honey

        // Create a Person (customer)
        Person customer = new Person("John Doe", 30, new ArrayList<>());

        // Add orders to the customer
        customer.addOrder(milkOrder);
        customer.addOrder(honeyOrder);

        // Print customer details
        System.out.println("Customer: " + customer.getName());
        for (Order<? extends Item> order : customer.getOrders()) {
            System.out.println("Order Number: " + order.getOrderNo());
            for (Map.Entry<? extends Item, Integer> entry : order.getItems().entrySet()) {
                System.out.println("- " + entry.getKey().getDescription() + " x" + entry.getValue());
            }
            System.out.println("Total: " + order.calculateTotalAmount());
        }
    }
}