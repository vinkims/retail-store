-- statuses
CREATE TABLE IF NOT EXISTS statuses (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- client_types
CREATE TABLE IF NOT EXISTS client_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- clients
CREATE TABLE IF NOT EXISTS clients (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "client_code" VARCHAR(50),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "client_type_id" SMALLINT REFERENCES client_types("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- users
CREATE TABLE IF NOT EXISTS users (
    "id" SERIAL PRIMARY KEY,
    "account_number" VARCHAR(40),
    "first_name" VARCHAR(50),
    "middle_name" VARCHAR(50),
    "last_name" VARCHAR(50),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "client_id" SMALLINT REFERENCES clients("id") ON DELETE SET NULL,
    "modified_on" TIMESTAMPTZ,
    "last_active_on" TIMESTAMPTZ,
    "password" VARCHAR,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- countries
CREATE TABLE IF NOT EXISTS countries (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50),
    "code" VARCHAR(3)
);

-- regions
CREATE TABLE IF NOT EXISTS regions (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50),
    "country_id" SMALLINT REFERENCES countries("id") ON DELETE CASCADE
);

-- addresses
CREATE TABLE IF NOT EXISTS addresses (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(50),
    "region_id" SMALLINT REFERENCES regions("id") ON DELETE SET NULL,
    "street" VARCHAR(50),
    "address_line_one" VARCHAR,
    "address_line_two" VARCHAR,
    "postal_code" VARCHAR,
    "city" VARCHAR
);

-- user_addresses
CREATE TABLE IF NOT EXISTS user_addresses (
    "user_id" INTEGER REFERENCES users("id") ON DELETE CASCADE,
    "address_id" INTEGER REFERENCES addresses("id") ON DELETE CASCADE,
    "is_default" boolean DEFAULT false,
    PRIMARY KEY("user_id", "address_id")
);

-- roles
CREATE TABLE IF NOT EXISTS roles (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- user_roles
CREATE TABLE IF NOT EXISTS user_roles (
    "user_id" INTEGER REFERENCES users("id") ON DELETE CASCADE,
    "role_id" SMALLINT REFERENCES roles("id") ON DELETE CASCADE,
    PRIMARY KEY("user_id", "role_id")
);

-- contact_types
CREATE TABLE IF NOT EXISTS contact_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR,
    "regex_value" VARCHAR
);

-- contacts
CREATE TABLE IF NOT EXISTS contacts (
    "user_id" INTEGER NOT NULL REFERENCES users("id") ON DELETE CASCADE,
    "contact_type_id" SMALLINT REFERENCES contact_types("id") ON DELETE SET NULL,
    "value" VARCHAR(200) PRIMARY KEY NOT NULL UNIQUE
);

-- blacklist_tokens
CREATE TABLE IF NOT EXISTS blacklist_tokens (
    "id" SERIAL PRIMARY KEY,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "token_hash" BIGINT NOT NULL UNIQUE
);

-- messaging_tokens
CREATE TABLE IF NOT EXISTS messaging_tokens (
    "id" SERIAL PRIMARY KEY,
    "device_name" VARCHAR(50),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "token" VARCHAR,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL
);

-- product_categories
CREATE TABLE IF NOT EXISTS product_categories (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "client_id" SMALLINT REFERENCES clients("id") ON DELETE CASCADE,
    "parent_category_id" SMALLINT REFERENCES product_categories("id") ON DELETE CASCADE,
    "name" VARCHAR(50),
    "description" VARCHAR
);

-- product_types
CREATE TABLE IF NOT EXISTS product_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "client_id" SMALLINT REFERENCES clients("id") ON DELETE CASCADE,
    "name" VARCHAR(50),
    "description" VARCHAR
);

-- product_brands
CREATE TABLE IF NOT EXISTS product_brands (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "client_id" SMALLINT REFERENCES clients("id") ON DELETE CASCADE,
    "name" VARCHAR(50),
    "description" VARCHAR
);

-- products
CREATE TABLE IF NOT EXISTS products (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "name" VARCHAR(80),
    "description" VARCHAR,
    "image" VARCHAR(200),
    "client_id" INTEGER REFERENCES clients("id") ON DELETE CASCADE,
    "product_category_id" SMALLINT REFERENCES product_categories("id") ON DELETE SET NULL,
    "product_type_id" SMALLINT REFERENCES product_types("id") ON DELETE SET NULL,
    "product_brand_id" SMALLINT REFERENCES product_brands("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- product_items
CREATE TABLE IF NOT EXISTS product_items (
    "id" SERIAL PRIMARY KEY,
    "product_id" INTEGER REFERENCES products("id") ON DELETE CASCADE,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "quantity" INTEGER DEFAULT 0,
    "price" NUMERIC(11,4),
    "image" VARCHAR(200),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- config_types
CREATE TABLE IF NOT EXISTS config_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "client_id" INTEGER REFERENCES clients("id") ON DELETE CASCADE,
    "name" VARCHAR(50),
    "description" VARCHAR
);

-- product_item_config_types
CREATE TABLE IF NOT EXISTS product_item_configs (
    "product_item_id" INTEGER REFERENCES product_items("id") ON DELETE CASCADE,
    "config_type_id" SMALLINT REFERENCES config_types("id") ON DELETE CASCADE,
    "value" VARCHAR NOT NULL,
    PRIMARY KEY("product_item_id", "config_type_id")
);

-- payment_channels
CREATE TABLE IF NOT EXISTS payment_channels (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50),
    "description" VARCHAR(50)
);

-- payment_methods
CREATE TABLE IF NOT EXISTS payment_methods (
    "id" SMALLSERIAL PRIMARY KEY,
    "user_id" INTEGER REFERENCES users("id") ON DELETE CASCADE,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "payment_channel_id" SMALLINT REFERENCES payment_channels("id") ON DELETE SET NULL,
    "provider" VARCHAR(50),
    "account_number" VARCHAR(50),
    "expiry_date" DATE,
    "is_default" boolean DEFAULT false
);

-- orders
CREATE TABLE IF NOT EXISTS orders (
    "id" SERIAL PRIMARY KEY,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "order_number" VARCHAR(50),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "shipping_address_id" INTEGER REFERENCES addresses("id") ON DELETE SET NULL,
    "payment_method_id" SMALLINT REFERENCES payment_methods("id") ON DELETE SET NULL,
    "order_total" NUMERIC(11,4),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- order_items
CREATE TABLE IF NOT EXISTS order_items (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "product_item_id" INTEGER REFERENCES product_items("id") ON DELETE SET NULL,
    "order_id" INTEGER REFERENCES orders("id") ON DELETE CASCADE,
    "quantity" INTEGER DEFAULT 0,
    "price" NUMERIC(11,4)
);

-- shopping_sessions
CREATE TABLE IF NOT EXISTS shopping_sessions (
    "id" SERIAL PRIMARY KEY,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "total" NUMERIC(11,4),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "status_id" SMALLINT REFERENCES statuses("id")
);

-- cart_items
CREATE TABLE IF NOT EXISTS cart_items (
    "id" SERIAL PRIMARY KEY,
    "session_id" INTEGER REFERENCES shopping_sessions("id") ON DELETE CASCADE,
    "product_item_id" INTEGER REFERENCES product_items("id") ON DELETE SET NULL,
    "quantity" INTEGER DEFAULT 0,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- transaction_types
CREATE TABLE IF NOT EXISTS transaction_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" VARCHAR
);

-- transactions
CREATE TABLE IF NOT EXISTS transactions (
    "id" BIGSERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "transaction_code" VARCHAR(50) UNIQUE,
    "amount" NUMERIC(11,4),
    "transaction_type_id" SMALLINT REFERENCES transaction_types("id") ON DELETE SET NULL,
    "payment_channel_id" SMALLINT REFERENCES payment_channels("id") ON DELETE SET NULL,
    "client_id" INTEGER REFERENCES clients("id") ON DELETE SET NULL,
    "reference" VARCHAR(50),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- user_transactions
CREATE TABLE IF NOT EXISTS user_transactions (
    "user_id" INTEGER REFERENCES users("id") ON DELETE CASCADE,
    "transaction_id" BIGINT REFERENCES transactions("id") ON DELETE CASCADE,
    PRIMARY KEY("user_id", "transaction_id")
);

-- expense_types
CREATE TABLE IF NOT EXISTS expense_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50),
    "description" VARCHAR
);

-- expenses
CREATE TABLE IF NOT EXISTS expenses (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "description" VARCHAR,
    "expense_type_id" SMALLINT REFERENCES expense_types("id") ON DELETE SET NULL,
    "client_id" INTEGER REFERENCES clients("id") ON DELETE CASCADE,
    "amount" NUMERIC(11,4),
    "status_id" SMALLINT REFERENCES statuses("id")
);

-- user_expenses
CREATE TABLE IF NOT EXISTS user_expenses (
    "user_id" INTEGER REFERENCES users("id") ON DELETE CASCADE,
    "expense_id" INTEGER REFERENCES expenses("id") ON DELETE CASCADE,
    PRIMARY KEY("user_id", "expense_id")
);

-- sale_types
CREATE TABLE IF NOT EXISTS sale_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(50),
    "description" VARCHAR
);

-- sales
CREATE TABLE IF NOT EXISTS sales (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "modified_on" TIMESTAMPTZ,
    "amount" NUMERIC(11,4),
    "customer_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "attendant_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "sale_type_id" SMALLINT REFERENCES sale_types("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id")
);

-- audit_events
CREATE TABLE IF NOT EXISTS audit_events (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "principal" VARCHAR,
    "event_type" VARCHAR,
    "event_data" JSON
);