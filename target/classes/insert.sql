insert into product(id, name, price) VALUES
(1,'молоко',75),
(2,'хлеб',35),
(3,'сметана',45),
(4,'масло',70),
(5,'сыр',120);

insert into buyer (id, first_name, last_name) VALUES
(1,'Иван','Иванов'),
(2,'Петр','Петров'),
(3,'Анна','Кузнецова');

insert into purchase(id, product_id, buyer_id, date) VALUES
(1,1,1,'18-03-2021'),
(2,2,2,'18-03-2021'),
(3,3,3,'18-03-2021'),
(4,4,4,'18-03-2021'),
(5,5,5,'18-03-2021'),
(6,6,6,'18-03-2021'),
(7,7,7,'18-03-2021');
