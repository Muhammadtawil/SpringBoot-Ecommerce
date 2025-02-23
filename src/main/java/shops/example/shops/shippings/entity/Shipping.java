package shops.example.shops.shippings.entity;

public class Shipping {
    
}
// able: shipping
// Column	Type	Description
// shipping_id	INT	Primary Key, Auto Increment
// order_id	INT	Foreign Key to orders table
// shipping_method	VARCHAR(255)	'standard', 'express', etc.
// shipping_cost	DECIMAL(10,2)	Cost of shipping
// shipping_date	TIMESTAMP	Timestamp of when the order was shipped
// delivery_date	TIMESTAMP	Timestamp when the order was delivered
