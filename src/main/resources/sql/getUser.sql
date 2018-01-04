/*
===================================================================================================================
				GET USER BY PHONE AND PASS
===================================================================================================================
*/
create or replace procedure getUser(telephone in varchar2, passwd in varchar2, returnCursor out sys_refCursor)
is
	objID Objects.object_id%type;
	begin
		objID := -9;

		begin
			select attrPN.object_id
			into objID
			from Attributes attrPN join Attributes attrPass on attrPN.object_id = attrPass.object_id
			where attrPN.value = telephone and attrPN.attr_id = 1 and attrPass.value = passwd and attrPass.attr_id = 2;
		end;

		if (objID > -1) then
			getEntity(objID, returnCursor);
		end if;
	end;
/
/*
===================================================================================================================
				GET USER BY PHONE
===================================================================================================================
*/
create or replace procedure getUserByPhone(telephone in varchar2, returnCursor out sys_refCursor)
is
	objID Objects.object_id%type;
	begin
		objID := -9;

		begin
			select object_id
			into objID
			from Attributes
			where value = telephone and attr_id = 1;
		end;

		if (objID > -1) then
			getEntity(objID, returnCursor);
		end if;
	end;
/