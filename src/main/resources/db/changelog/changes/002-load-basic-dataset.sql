--changelog roman spanek: 002
INSERT INTO payment (ID,type,amount) VALUES (1,'CASH', 100);
INSERT INTO payment (ID,type,amount) VALUES (2,'CARD', 300);
--rollback delete from payment where id in (1,2);