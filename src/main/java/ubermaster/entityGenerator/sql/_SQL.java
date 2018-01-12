package ubermaster.entityGenerator.sql;

/**
 * @author Serpye
 * */
public interface _SQL
{
	String OBJECT_TYPE_ID_MASTER = "2";
	String OBJECT_TYPE_ID_POKE = "3";
	String OBJECT_TYPE_ID_ORDER = "5";

	String ATTR_ORDER_PARENT_ID = "-19";

	String ATTR_PHONE = "1";
	String ATTR_PASS = "2";
	String ATTR_USER_DESCR = "3";
	String ATTR_PICTURE = "4";
	String ATTR_LOCATION = "5";
	String ATTR_PROFESSION = "6";
	String ATTR_SKILLS = "7";
	String ATTR_EXPIERENCE = "8";
	String ATTR_PAYMENT = "9";
	String ATTR_SMOKE = "10";
	String ATTR_TOOLS = "11";
	String ATTR_START_TIME = "12";
	String ATTR_END_TIME = "13";
	String ATTR_MASTER_REF = "14";
	String ATTR_SMALL_DESCR = "15";
	String ATTR_BIG_DESCR = "16";
	String ATTR_START_DATE = "17";
	String ATTR_DUE_DATE = "18";
	String ATTR_STATUS = "19";
	String ATTR_BLOCKED = "20";
	String ATTR_ORDER_PROFESSION = "21";
	String ATTR_ORDER_MARK = "22";
	String ATTR_ORDER_COMMENT = "23";
	String ATTR_ORDER_MASTER_END_DATE = "24";

	String DATE = "01/01/18 ";

	String INSERT_ENTITY = "{call insertEntity(?)}";
	String DELETE_ENTITY = "delete from Objects where object_id = ?";
	String DELETE_ALL_ENTITIES = "delete from Objects";
}
