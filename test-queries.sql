-- 5 запросов на чтение (в том числе JOIN с несколькими таблицами, агрегаты, сортировка, фильтрация).
-- Список всех заказов за последние 7 дней с именем покупателя и описанием товара.
select c."name" customer_name, p."name" product_name
     , o.qty, o.ts timest, os."name" order_status
  from "order" o
       join customer c on c.id = o.customer_id 
       join product p on p.id = o.product_id
       join order_status os on os.id = o.order_status_id
 where o.ts > current_date - interval '7 day'
;
-- Топ-3 самых популярных товара.
select o.product_id, p."name", sum(o.qty) cnt 
  from "order" o join product p on p.id = o.product_id
 group by o.product_id, p."name"
 order by cnt desc
 limit 3
;
-- Все заказы по клиенту со статусом "создан"
select c."name", p."name", count(1) cnt, sum(o.qty) qty 
  from customer c 
       join "order" o on o.customer_id = c.id
       join product p on p.id = o.product_id
       join order_status os on os.id = o.order_status_id
 where c.id = 2
   and os."name" = 'создан'
 group by c."name", p."name"
;
-- Товары которые не продаются
select p.*
  from product p
       left join "order" o on o.product_id = p.id
 where o.id is null
;
-- Количество товаров по категории
select p.cat, sum(p.qty), count(1)
  from product p 
 group by p.cat
;

-- 3 запроса на изменение (UPDATE).
-- Обновление количества на складе при покупке.
update product p 
   set qty = qty - 1
 where p.id = 2
;
-- Поднять цецу по категории где остаток > 0.
update product p 
   set cost = cost * 1.1
 where p.cat = 'СИЗ'
   and p.qty > 0
;
-- Обновить статус заказа.
update "order" o
   set order_status_id = (select os.id from order_status os where os.name = 'подтвержден')
 where o.id = 2
;


-- 2 запроса на удаление (DELETE).
-- Удаление клиентов без заказов.
delete 
  from customer c
 where not exists(select 1 from "order" o where o.customer_id = c.id)
;
-- Уделение всех товаров с остатком 0
delete 
  from product p
 where p.qty = 0
;

