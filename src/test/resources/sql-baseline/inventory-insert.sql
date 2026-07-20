select ie1_0.id,ie1_0.available_quantity,ie1_0.product_code,ie1_0.reserved_quantity from inventory ie1_0 where ie1_0.id=?
insert into inventory (available_quantity,product_code,reserved_quantity,id) values (?,?,?,?)
select ie1_0.id,ie1_0.available_quantity,ie1_0.product_code,ie1_0.reserved_quantity from inventory ie1_0 where ie1_0.id=?