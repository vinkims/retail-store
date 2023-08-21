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
    (3, 'customer', 'retail store customer');
alter sequence IF EXISTS roles_id_seq restart with 4;

INSERT INTO contact_types("id", "name", "description", "regex_value")
VALUES
    (1, 'mobile_number', 'user mobile number', '^[0-9]{12}$'),
    (2, 'email', 'user email address' , '^(?<a>\\w*)(?<b>[\\.-]\\w*){0,3}@(?<c>\\w+)(?<d>\\.\\w{2,}){1,3}$');
alter sequence IF EXISTS contact_types_id_seq restart with 3;