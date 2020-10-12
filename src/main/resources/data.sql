----------------------------------- user
INSERT INTO user (ID, NAME, PASSWORD)
SELECT * FROM (SELECT 1010101010101, 'arroba@punto.ceele', '$2a$10$PJCWxqKFs6A7JnfOfxQxk.TJDAZfomVE3UwJGKtrbHwbT8kaML2Ii') AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM user WHERE ID = 1010101010101
) LIMIT 1;

----------------------------------- table_restaurant
INSERT INTO table_restaurant (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NUMBER, SEATS)
SELECT * FROM (SELECT 1000, sysdate() as f1, FALSE, sysdate() as f2, 1, 4) AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM table_restaurant WHERE ID = 1000
) LIMIT 1;

INSERT INTO table_restaurant (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NUMBER, SEATS)
SELECT * FROM (SELECT 1001, sysdate() as f1, FALSE, sysdate() as f2, 2, 5) AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM table_restaurant WHERE ID = 1001
) LIMIT 1;

INSERT INTO table_restaurant (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NUMBER, SEATS)
SELECT * FROM (SELECT 1002, sysdate() as f1, FALSE, sysdate() as f2, 3, 2) AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM table_restaurant WHERE ID = 1002
) LIMIT 1;

----------------------------------- waiter
INSERT INTO waiter (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NAME, RUT)
SELECT * FROM (SELECT 1000, sysdate() as f1, FALSE, sysdate() as f2, 'Pablo', '24582193-k') AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM waiter WHERE ID = 1000
) LIMIT 1;

INSERT INTO waiter (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NAME, RUT)
SELECT * FROM (SELECT 1001, sysdate() as f1, FALSE, sysdate() as f2, 'Juan', '10616333-2') AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM waiter WHERE ID = 1001
) LIMIT 1;

INSERT INTO waiter (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NAME, RUT)
SELECT * FROM (SELECT 1002, sysdate() as f1, FALSE, sysdate() as f2, 'Pedro', '22444292-0') AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM waiter WHERE ID = 1002
) LIMIT 1;

----------------------------------- waiter
INSERT INTO dish (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NAME, PRICE)
SELECT * FROM (SELECT 1000, sysdate() as f1, FALSE, sysdate() as f2, 'POROTOS', 5500) AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM dish WHERE ID = 1000
) LIMIT 1;

INSERT INTO dish (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NAME, PRICE)
SELECT * FROM (SELECT 1001, sysdate() as f1, FALSE, sysdate() as f2, 'CHARQUICAN', 2300) AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM dish WHERE ID = 1001
) LIMIT 1;

INSERT INTO dish (ID, CREATED_DATE, IS_DELETED, LAST_UPDATED_DATE, NAME, PRICE)
SELECT * FROM (SELECT 1002, sysdate() as f1, FALSE, sysdate() as f2, 'PURE', 3600) AS tmp
WHERE NOT EXISTS (
    SELECT ID FROM dish WHERE ID = 1002
) LIMIT 1;
