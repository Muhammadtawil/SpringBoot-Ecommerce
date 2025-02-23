package shops.example.shops.payments.entity;

public class Payment {
    
}



// payment_id	INT	Primary Key, Auto Increment
// order_id	INT	Foreign Key to orders table
// payment_method	VARCHAR(50)	'credit card', 'paypal', 'bank transfer'
// payment_status	ENUM	Enum of 'pending', 'completed', 'failed'
// amount	DECIMAL(10,2)	Payment amount
// payment_date	TIMESTAMP	Timestamp of when payment was made
