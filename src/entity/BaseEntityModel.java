package entity;

import annotation.AttrType;

public class BaseEntityModel
{
	@AttrType(BaseEntityAttr.NAME_ATTR)
	protected String name;

	@AttrType(BaseEntityAttr.DESCRIPTION)
	protected String description;

	@AttrType(BaseEntityAttr.OBJECT_ID)
	protected long object_id;
}
