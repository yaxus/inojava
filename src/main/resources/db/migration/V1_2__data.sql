INSERT INTO public.customer (id,"name",lastname,tel,email) VALUES
	 (2,'Вася','Федоров','322233','mail_vasya@karasya.ru'),
	 (3,'Коля',NULL,NULL,NULL),
	 (4,'Миша',NULL,NULL,NULL),
	 (5,'Коля',NULL,NULL,NULL),
	 (6,'Юля',NULL,NULL,NULL),
	 (7,'Света',NULL,NULL,NULL),
	 (8,'Женя',NULL,NULL,NULL),
	 (9,'Катя',NULL,NULL,NULL),
	 (10,'Таня',NULL,NULL,NULL),
	 (11,'Николь',NULL,NULL,NULL);
ALTER TABLE public.customer ALTER id restart 12;


INSERT INTO public.product (id,"name",descr,"cost",qty,cat) VALUES
	 (1,'Шпатель',NULL,150.0,20,'Стройматириалы'),
	 (2,'Пена монтажная',NULL,200.0,50,'Стройматириалы'),
	 (3,'Плита ГВЛВ',NULL,1100.0,500,'Стройматириалы'),
	 (4,'Песок 20 кг',NULL,230.0,200,'Стройматириалы'),
	 (5,'Очки защитные',NULL,130.0,150,'СИЗ'),
	 (6,'Рулетка',NULL,58.0,300,'Измерительные инструменты'),
	 (7,'Уголок',NULL,160.0,400,'Измерительные инструменты'),
	 (8,'Штангенциркуль',NULL,270.0,70,'Измерительные инструменты'),
	 (9,'Перчатки',NULL,32.0,1000,'СИЗ'),
	 (10,'Реноватор',NULL,7850.0,27,'Электроинструменты');
ALTER TABLE public.product ALTER id restart 11;

INSERT INTO public.order_status (id, "name") VALUES
	 (1, 'создан'),
	 (2, 'подтвержден'),
	 (3, 'оплачен'),
	 (4, 'отправлен'),
	 (5, 'получен'),
	 (6, 'возврат');
ALTER TABLE public.order_status ALTER id restart 7;

INSERT INTO public."order" (id,product_id,customer_id,ts,qty,order_status_id) VALUES
	 (2,1,2,'2025-10-04 19:44:19.465252',2,1),
	 (3,5,2,'2025-10-04 19:44:19.468078',3,1),
	 (4,9,11,'2025-10-04 19:44:19.469629',10,5),
	 (5,10,11,'2025-10-04 19:44:19.471434',1,5);
ALTER TABLE public."order" ALTER id restart 6;