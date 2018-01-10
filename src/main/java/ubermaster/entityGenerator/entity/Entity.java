package ubermaster.entityGenerator.entity;

/**
 * @author Serpye
 * */
public class Entity
{
/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private final Attribute SQC_PARAM[];
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private Entity(int size)
	{
		SQC_PARAM = new Attribute[size];
	}
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private static class Attribute
	{
		public final String ATTR_ID;
		public final String VALUE;

		public Attribute(String attr_id, String value)
		{
			ATTR_ID = attr_id;
			VALUE = value;
		}
	}

	public static class EntityController
	{
	/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
		private final Entity ENTITY;
		private int iterator = 0;
	/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
		public EntityController(int paramSize)
		{
			ENTITY = new Entity(paramSize);
		}
	/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
		public void addParam(String attr_id, String value)
		{
			if (iterator < ENTITY.SQC_PARAM.length)
			{
				ENTITY.SQC_PARAM[iterator] = new Attribute(attr_id, value);
				++iterator;
			}
		}

		public Entity getEntity()
		{
			return ENTITY;
		}
	}
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public Attribute[] getAllParam()
	{
		return SQC_PARAM;
	}

	public String[] getAllParamAsArray()
	{
		final String SQC_STRING_PARAM[] = new String[SQC_PARAM.length << 1];
		final int LEN_SQC_PARAM = SQC_PARAM.length;
		for
		(
			int iteraStr = 0, iteraParam = 0;
			iteraParam < LEN_SQC_PARAM;
			++iteraParam
		)
		{
			if (SQC_PARAM[iteraParam] == null)
				continue;

			SQC_STRING_PARAM[iteraStr] = SQC_PARAM[iteraParam].ATTR_ID;
			++iteraStr;
			SQC_STRING_PARAM[iteraStr] = SQC_PARAM[iteraParam].VALUE;
			++iteraStr;
		}

		return SQC_STRING_PARAM;
	}

	public String getParam(String attr_id)
	{
		final int LENGTH = SQC_PARAM.length;
		for (int i = 0; i < LENGTH; ++i)
			if (SQC_PARAM[i] != null && attr_id.equals(SQC_PARAM[i].ATTR_ID))
				return SQC_PARAM[i].VALUE;

		return null;
	}

	public String getEntityID()
	{
		if (SQC_PARAM[0] == null)
			return null;

		return SQC_PARAM[0].ATTR_ID;
	}

	public String toString()
	{
		final StringBuilder STRING_BUILDER = new StringBuilder();
		STRING_BUILDER.append("Entity :");

		final int LEN_SQC_PARAM = SQC_PARAM.length;
		for (int i = 0; i < LEN_SQC_PARAM; ++i)
			if (SQC_PARAM[i] != null)
				STRING_BUILDER.append
				(
					"\n\tAttr : " + SQC_PARAM[i].ATTR_ID
						+
					"\tValue : " + SQC_PARAM[i].VALUE
				);

		return STRING_BUILDER.toString();
	}
}
