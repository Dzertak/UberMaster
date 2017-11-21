package entity.model;

import annotation.AttrType;
import entity.attr.BaseEntityAttr;

public class BaseEntityModel {

	@AttrType(BaseEntityAttr.NAME_ATTR)
	protected String name;

	@AttrType(BaseEntityAttr.DESCRIPTION)
	protected String description;

	@AttrType(BaseEntityAttr.OBJECT_ID)
	protected long object_id;
}
