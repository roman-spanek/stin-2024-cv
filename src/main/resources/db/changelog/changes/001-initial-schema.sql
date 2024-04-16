--changelog roman spanek: 001
CREATE TABLE payment (
    id INT PRIMARY KEY,
    type VARCHAR(50) not null,
    amount float null)
--rollback drop table payment;