insert all
	into ObjType (object_type_id, parent_id, Code, Description) values ( 1, null, 'User', null)
	into ObjType (object_type_id, parent_id, Code, Description) values ( 2, 1, 'Master', null)
	into ObjType (object_type_id, parent_id, Code, Description) values ( 3, 1, 'Poke', null)
	into ObjType (object_type_id, parent_id, Code, Description) values ( 4, 1, 'Admin', null)
	into ObjType (object_type_id, parent_id, Code, Description) values ( 5, null, 'Order', null)
select * from dual;

-- for users
insert all 
	 into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values(1, 1, null, 'Phone Number')
	 into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values(2, 1, null, 'Password')
	 into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values(3, 1, null, 'Description')
	 into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values(4, 1, null, 'Picture')
	 into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values(5, 1, null, 'Location')
	 into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values(20, 1, null, 'isUserBlocked')
 select * from dual;

-- for masters
insert all 
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (6, 2, null, 'Profession')
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (7, 2, null, 'Skills')
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (8, 2, null, 'Experience')
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (9, 2, null, 'Payment')
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (10, 2, null, 'Smoke')
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (11, 2, null, 'Tools')
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (12, 2, null, 'Start Time')
	into AttrType (attr_id,  object_type_id,  object_type_id_ref, Code) values (13, 2, null, 'End Time')
select * from dual;

--for orders
insert all 
	into AttrType  (attr_id,  object_type_id,  object_type_id_ref, Code) values (14, 5, 2, 'Master')
	into AttrType  (attr_id,  object_type_id,  object_type_id_ref, Code) values (15, 5, null, 'Small Description')
	into AttrType  (attr_id,  object_type_id,  object_type_id_ref, Code) values (16, 5, null, 'Big Description')
	into AttrType  (attr_id,  object_type_id,  object_type_id_ref, Code) values (17, 5, null, 'Start Date')
	into AttrType  (attr_id,  object_type_id,  object_type_id_ref, Code) values (18, 5, null, 'Due Date')
	into AttrType  (attr_id,  object_type_id,  object_type_id_ref, Code) values (19, 5, null, 'Status')
	into AttrType  (attr_id,  object_type_id,  object_type_id_ref, Code) values (21, 5, null, 'Master profession')
select * from dual;

 --  for users field locations
insert all
	into Lists (attr_id, list_value_id, value) values (5, 1, 'Kievskyy')
	into Lists (attr_id, list_value_id, value) values (5, 2, 'Primorskyy')
	into Lists (attr_id, list_value_id, value) values (5, 3, 'Suvorovskyy')
	into Lists (attr_id, list_value_id, value) values (5, 4, 'Malinovskyy')
select * from dual;

-- for masters 'Smoke'
insert all 
	into Lists (attr_id, list_value_id, value) values (10, 5, 'true')
	into Lists (attr_id, list_value_id, value) values (10, 6, 'false')
select * from dual;

-- for orders status
insert all 
	into Lists (attr_id, list_value_id, value) values (19, 7, 'New')
	into Lists (attr_id, list_value_id, value) values (19, 8, 'In processing')
	into Lists (attr_id, list_value_id, value) values (19, 9, 'Completed')
select * from dual;

-- for Pokes/Masters 'isBlocked'
insert all 
	into Lists (attr_id, list_value_id, value) values (20, 10, 'true')
	into Lists (attr_id, list_value_id, value) values (20, 11, 'false')
select * from dual;

-- for Masters 'Profession'
insert all 
	into Lists (attr_id, list_value_id, value) values (6, 12, 'Locksmith')
	into Lists (attr_id, list_value_id, value) values (6, 13, 'Plumber')
	into Lists (attr_id, list_value_id, value) values (6, 14, 'Electrician')
	into Lists (attr_id, list_value_id, value) values (6, 15, 'Cleaner')
	into Lists (attr_id, list_value_id, value) values (6, 16, 'Computer foreman')
	into Lists (attr_id, list_value_id, value) values (6, 17, 'Handyman')
select * from dual;

-- for Orders 'Profession'
insert all 
	into Lists (attr_id, list_value_id, value) values (21, 18, 'Locksmith')
	into Lists (attr_id, list_value_id, value) values (21, 19, 'Plumber')
	into Lists (attr_id, list_value_id, value) values (21, 20, 'Electrician')
	into Lists (attr_id, list_value_id, value) values (21, 21, 'Cleaner')
	into Lists (attr_id, list_value_id, value) values (21, 22, 'Computer foreman')
	into Lists (attr_id, list_value_id, value) values (21, 23, 'Handyman')
select * from dual;