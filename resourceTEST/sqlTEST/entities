-- create poke John Smith
insert into Objects ( object_id, parent_id, object_type_id, name, description) values (1, null, 3, 'John Smith', null);
insert all
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (1, 1, '380937999168', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (2, 1, 'easy_password', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (3, 1, 'Short description', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (4, 1, 'www.ref1.com', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (5, 1, null, null, 1)
select * from dual;

-- create poke  David Jones
insert into Objects ( object_id, parent_id, object_type_id, name, description) values (2, null, 3, 'David Jones', null);
insert all
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (1, 2, '380456785129', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (2, 2, 'easy_password2', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (3, 2, 'Short description 2', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (4, 2, 'www.ref2.com', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (5, 2, null, null, 2)
select * from dual;


--masterMichael Lee
insert into Objects ( object_id, parent_id, object_type_id, name, description) values (3, null, 2, 'Michael Lee', null);
insert all
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (1, 3, '380456111789', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (2, 3, 'easy_password3', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (3, 3, 'Short description 3', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (4, 3, 'www.ref3.com', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (5, 3, null, null, 3)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (6, 3, 'Сarpenter', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (7, 3, 'I am make good tables', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (8, 3, '10', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (9, 3, '200', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (10, 3, null, null, 6)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (11, 3, 'saw', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (12, 3, null, to_date('08:00', 'hh24:mi'), null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (13, 3, null, to_date('17:00', 'hh24:mi'), null)
select * from dual;

-- create  order(David Jones)
insert into Objects ( object_id, parent_id, object_type_id, name, description) values (4, 2, 5, 'fix the shelf', null);
insert all
    into ObjReference (attr_id, refer_id, object_id) values (14, 3, 4)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (15, 4, 'wooden shelf', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (16, 4, 'with flowers', null, null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (17, 4, null, to_date('14.11.17', 'DD.MM.YY'), null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (18, 4, null, to_date('15.11.17', 'DD.MM.YY'), null)
    into Attributes (attr_id, object_id, value, date_value, list_value_id) values (19, 4, null, null, 8)
select * from dual;