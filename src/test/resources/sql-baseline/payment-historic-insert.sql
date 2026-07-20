select phe1_0.id,phe1_0.date,phe1_0.description,phe1_0.type from payment_historic phe1_0 where phe1_0.id=?
insert into payment_historic (date,description,type,id) values (?,?,?,?)
select phe1_0.id,phe1_0.date,phe1_0.description,phe1_0.type from payment_historic phe1_0 where phe1_0.id=?