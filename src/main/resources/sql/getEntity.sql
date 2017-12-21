create or replace procedure getEntity(innerID in number, returnCursor out sys_refCursor)
is
	ObjName Objects.name%type;
	ObjDescr Objects.description%type;
	ObjTypeID Objects.object_type_id%type;
begin
	begin
		select object_type_id, name, description
		into ObjTypeID, ObjName, ObjDescr
		from Objects
		where object_id = innerID;
	end;

	open returnCursor for
		with result as
			(
				select
						attr_id
				from
						AttrType attrT join
						(
							select
									object_type_id
							from ObjType
							start with object_type_id =
							(
								select
										object_type_id
								from
										Objects
								where object_id = innerId
							)
							connect by prior parent_id = object_type_id
						)
						allObjTypes on allObjTypes.object_type_id = attrT.object_type_id
			)
			select
				to_char(innerID),
				'-1'
			from dual
			union all
			select
				to_char(ObjTypeID),
				'-2'
			from dual
			union all
			select
				ObjName,
				'-3'
			from dual
			union all
			select
				ObjDescr,
				'-4'
			from dual
			union all
			select
				case when value is not null then value
				else case when date_value is not null then to_char(date_value, 'DD/MM/YY hh24:mi')
					else
						(
							select lst.value
							from Lists lst
							where attr.list_value_id = lst.list_value_id
						)
					end
				end,
				to_char(attr.attr_id)
			from Attributes attr, result
			where attr.attr_id in result.attr_id and attr.object_id = innerId
			union all
			select
				to_char(objRef.object_id),
				to_char(objRef.attr_id)
			from ObjReference objRef, result
			where objRef.attr_id in result.attr_id and objRef.object_id = innerId;
end;
/