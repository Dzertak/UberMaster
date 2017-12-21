create or replace type array as table of varchar(4000);
/

create or replace procedure insertEntity(innerArray in array)
as
	obID Objects.object_id%type;
	obTyID Objects.object_type_id%type;
	name Objects.name%type;
	descr Objects.description%type;
	
	attrID Attributes.attr_id%type;
	attrValue Attributes.value%type;
	
	lstMaxID Lists.list_value_id%type;
	lstID Lists.list_value_id%type;
	
	i binary_integer;
begin	
	i := innerArray.first();
	obID := to_number(innerArray(i));
	i := innerArray.next(i);
	obTyID := to_number(innerArray(i));
	i := innerArray.next(i);
	name := innerArray(i);
	i := innerArray.next(i);
	descr := innerArray(i);
	
	--INSERT OBJECTS
	begin
		insert into Objects ( object_id, parent_id, object_type_id, name, description) 
				values (obID, null, obTyID, name, descr);
	end;

	-- defining MAX ID of Lists
	begin
		select max(list_value_id) + 1
		into lstMaxID
		from Lists;
	end;
		
	--INSERT ATTRIBUTES
	i := innerArray.next(i);
	while i is not null loop
		attrID := to_number(innerArray(i));
		i := innerArray.next(i);
		attrValue := to_char(innerArray(i));
		i := innerArray.next(i);
		
		-- defining of presenting of value or not
		if (attrID in (5, 10, 19)) then
			begin
				select list_value_id 
				into lstID
				from Lists lst join AttrType attrT on lst.attr_id = attrT.attr_id
				where value = attrValue;
				EXCEPTION WHEN NO_DATA_FOUND then
					lstID := null;
			end;
		end if;
		
		-- if lst location  
		if (attrID in (5, 10, 19)) then		
			-- if such value is not presenting
			if (lstID is null) then				
				insert into Lists (attr_id, list_value_id, value) 
						values (attrID, lstMaxID, attrValue);
						
				insert into Attributes (attr_id, object_id, value, date_value, list_value_id) 
					values (attrID, obID, null, null, lstMaxID);
				
				lstMaxID := lstMaxID + 1;
			else
				insert into Attributes (attr_id, object_id, value, date_value, list_value_id) 
					values (attrID, obID, null, null, lstID);
			end if; 
		else
			if (attrID in (12, 13, 17, 18)) then
				insert into Attributes (attr_id, object_id, value, date_value, list_value_id) 
				values (attrID, obID, null, to_date(attrValue, 'DD/MM/YYYY hh24:mi'), null);		
			else
				insert into Attributes (attr_id, object_id, value, date_value, list_value_id) 
				values (attrID, obID, attrValue, null, null);		
			end if;
		end if;
	end loop;
end;
/