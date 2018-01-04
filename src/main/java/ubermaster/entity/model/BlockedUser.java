package ubermaster.entity.model;

import ubermaster.annotation.Attribute;
import ubermaster.entity.attr.BlockedUserAttr;

import java.util.HashMap;

public class BlockedUser extends User
{
/**:|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	@Attribute(Model.IS_BLOCKED)
	protected boolean isBlocked;
/**:|       CONSTRUCTOR     :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/**:|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public interface Model extends BlockedUserAttr
	{						}
/**:|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public void setBlocked(boolean blocked)
	{
		isBlocked = blocked;
	}

	public boolean getBlocked()
	{
		return isBlocked;
	}

	@Override
	public HashMap getAllFields()
	{
		HashMap<String, Object> hashmap = super.getAllFields();

		hashmap.put(Model.IS_BLOCKED, isBlocked);

		return hashmap;
	}

	public String toString()
	{
		return super.toString() + "\nIs blocked : " + isBlocked;
	}
}
