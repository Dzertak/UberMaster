package entity;

import entity.model.BaseEntityModel;

public class BaseEntity extends BaseEntityModel
{
	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public long getObjectID()
	{
		return object_id;
	}
}
