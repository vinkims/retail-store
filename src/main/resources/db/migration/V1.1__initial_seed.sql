INSERT INTO statuses("id", "name", "description")
VALUES
    (1, 'active', 'resource enabled for all corresponding functionality'),
    (2, 'inactive', 'resource locked for some/all functionality'),
    (3, 'suspended', 'resource temporarily blocked for some/all operations'),
    (4, 'banned', 'resource permanently blocked from operation'),
    (5, 'pending', 'resource awaiting further action, temporarily blocked from some operations'),
    (6, 'complete', 'resource action successfully completed'),
    (7, 'unverified', 'resource requires verification that is yet to be done'),
    (8, 'failed', 'transaction unsuccessful'),
    (9, 'rejected', 'transaction rejected/cancelled'),
    (10, 'cancelled', 'transaction cancelled by payer');
alter sequence IF EXISTS statuses_id_seq restart with 11;

INSERT INTO roles("id", "name", "description")
VALUES
    (1, 'system-admin', 'Sytem administrator'),
    (2, 'admin', 'client adminstrator/manager'),
    (3, 'customer', 'retail store customer'),
    (4, 'salesperson', 'Store sales person');
alter sequence IF EXISTS roles_id_seq restart with 5;

INSERT INTO contact_types("id", "name", "description", "regex_value")
VALUES
    (1, 'mobile_number', 'user mobile number', '^[0-9]{12}$'),
    (2, 'email', 'user email address' , '^(?<a>\\w*)(?<b>[\\.-]\\w*){0,3}@(?<c>\\w+)(?<d>\\.\\w{2,}){1,3}$');
alter sequence IF EXISTS contact_types_id_seq restart with 3;

INSERT INTO client_types ("id", "name", "description")
VALUES
    (1, 'retailer', 'resellers of retail goods or services'),
    (2, 'wholesaler', 'sells goods or services to retailers'),
    (3, 'supplier', 'supplies goods or services to retailers or wholesalers');
alter sequence client_types_id_seq restart with 4;

INSERT INTO payment_channels ("id", "name", "description")
VALUES
    (1, 'mobile_money', 'Payments made via mobile money platforms'),
    (2, 'debit_card', 'Debit card payment'),
    (3, 'credit_card', 'Credit card payment'),
    (4, 'cash', 'Cash payment'),
    (5, 'digital_payment', 'Payments made via digital platforms');
alter sequence payment_channels_id_seq restart with 6;

INSERT INTO transaction_types ("id", "name", "description")
VALUES 
    (1, 'purchase', 'Adding stock into inventory'),
    (2, 'refund', 'Refunds for canceled orders or returns'),
    (3, 'salary', 'Employee salary payment'),
    (4, 'shipping', 'All transactions related to delivery'),
    (5, 'salary_advance', 'Advance on salary for employees'),
    (6, 'discount', 'Discounts given to customers'),
    (7, 'commission', 'Commission earned by employee(s)'),
    (8, 'deposit', 'Product or service is partially paid for'),
    (9, 'sales', 'Sale of goods or services'),
    (10, 'bonus', 'Bonus given to employees'),
    (11, 'cash_injection', 'Cash injected into the business from outside');
alter sequence transaction_types_id_seq restart with 12;

INSERT INTO expense_types ("id", "name", "description")
VALUES 
    (1, 'rent', 'Payment for renting or leasing space'),
    (2, 'utilities', 'Costs associated with utilities such as electricity'),
    (3, 'salary', 'Compensation paid to employees'),
    (4, 'office_supplies', 'Expenditure for items needed for daily operations'),
    (5, 'travel', 'Costs related to travel expenses'),
    (6, 'marketing', 'Expenditures on advertising and marketing'),
    (7, 'inventory', 'Expenses associated with purchasing, storing and managing stock'),
    (8, 'legal', 'Costs for legal services');
alter sequence expense_types_id_seq restart with 9;

INSERT INTO sale_types ("id", "name", "description")
VALUES
    (1, 'retail', 'Occur when customers purchase products'),
    (2, 'online', 'Sales conducted through online platforms'),
    (3, 'wholesale', 'Sales made to other businesses or retailers'),
    (4, 'seasonal', 'Sales events tied to specific seasons'),
    (5, 'clearance', 'Products are sold at reduced prices to clear out inventory'),
    (6, 'pre-order', 'Sales made before a product is available');
alter sequence sale_types_id_seq restart with 7;