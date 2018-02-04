package ubermaster.entityGenerator.entity;

import ubermaster.entityGenerator.sql.SQL_Manager;
import ubermaster.entityGenerator.sql._SQL;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Serpye
 * */
public class EntityGenerator implements SQC_DATA, _SQL
{
/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private static volatile long object_id = 0;
	private static final Object MUTEX = new Object();
	private final Random RANDOM;

	/**
	 * if it param equal 1 generator will gene only male
	 * else if param equal 0 — only female
	 * */
	private final float MALE_PERCENT;

	private final Entity SQC_POKES[];
	private final Entity SQC_ORDERS[];
	private final Entity SQC_MASTER[];
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public EntityGenerator
	(
		Random random,
		float malePercent, int masterCount,
		int pokeCount, int orderCount
	)
	{
		if (malePercent > 1)
			MALE_PERCENT = 1;

		else if (malePercent < 0)
			MALE_PERCENT = 0;

		else
			MALE_PERCENT = malePercent;

		SQC_MASTER = new Entity[masterCount];
		SQC_POKES = new Entity[pokeCount];
		SQC_ORDERS = new Entity[orderCount];

		if (random == null)
			RANDOM = new Random();

		else
			RANDOM = random;
	}
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public void init()
	{
		generate();

		System.out.println("===============================================");
		Entity[] pokes = getPokes();
		for (Entity poke : pokes)
			System.out.println(poke + "\n");

		System.out.println("===============================================");

		Entity[] masters = getMasters();
		for (Entity master : masters)
			System.out.println(master + "\n");

		System.out.println("===============================================");


		Entity[] orders = getOrders();
		for (Entity order : orders)
			System.out.println(order + "\n");


		SQL_Manager.deleteAllEntities();
		SQL_Manager.insertEntity(pokes);
		SQL_Manager.insertEntity(masters);
		SQL_Manager.insertEntity(orders);
	}

	private void generate()
	{
		final int LEN_SQC_POKE = SQC_POKES.length;
		for (int i = 0; i < LEN_SQC_POKE; ++i)
			synchronized (MUTEX)
			{
				SQC_POKES[i] = generatePoke();
			}

		final int LEN_SQC_MASTER = SQC_MASTER.length;
		for (int i = 0; i < LEN_SQC_MASTER; ++i)
			synchronized (MUTEX)
			{
				SQC_MASTER[i] = generateMaster();
			}

		final int LEN_SQC_ORDER = SQC_ORDERS.length;
		for (int i = 0; i < LEN_SQC_ORDER; ++i)
			synchronized (MUTEX)
			{
				SQC_ORDERS[i] = generateOrder();
			}


	}

	private Entity[] getPokes()
	{
		return SQC_POKES;
	}

	private Entity[] getOrders()
	{
		return SQC_ORDERS;
	}

	private Entity[] getMasters()
	{
		return SQC_MASTER;
	}

	private void generateUser(Entity.EntityController controller, String ObjTypeID)
	{
		//==:	Gene init
		boolean isMale;
		if (MALE_PERCENT <= 0.00000001)
			isMale = false;

		else if (MALE_PERCENT >= 0.9999999999)
			isMale = true;

		else
		{
			float percent = RANDOM.nextFloat();

			if (MALE_PERCENT > percent)
				isMale = true;

			else
				isMale = false;
		}

		final int NAME_RECORD_COUNT = SQC_DATA.SQC_NAME.length >> 1;
		final int ADDITIONAL;

		if (isMale)
			ADDITIONAL = 0;

		else
			ADDITIONAL = 10;

		//--:	IDs
		final String ID = Long.toString(object_id);
		controller.addParam(ID, ObjTypeID);
		++object_id;
		//--:	NAME
		final String NAME = SQC_DATA.SQC_NAME[RANDOM.nextInt(NAME_RECORD_COUNT) + ADDITIONAL];
		final String SURNAME = SQC_DATA.SQC_SURNAME[RANDOM.nextInt(SQC_DATA.SQC_SURNAME.length)]
				+ (!isMale ? "а" : "");

		//--:	DESCR
		final String DESCR = "DESCR : " + ID;

		controller.addParam(NAME + " " + SURNAME, DESCR);

		//--:	TELEPHONE
		char telephone[] = new char[12];
		for (int i = 0; i < 12; ++i)
			telephone[i] = (char)(RANDOM.nextInt(10) + '0');

		controller.addParam(ATTR_PHONE, new String(telephone));

		//--:	PASS
		final byte LEN_MIX_PASS = 10;
		final byte LEN_MAX_PASS = 25;
		final char SQC_PASS_CHAR[] = new String
				(
						"1234567890QWERTYUIOPASDFGHJKLZX" +
								"CVBNMqwertyuiopasdfghjklzxcvbnm"
				).toCharArray();
		final short LEN_SQC_PASS_CHAR = (short)SQC_PASS_CHAR.length;
		final char PASS[] = new char
				[
				RANDOM.nextInt(LEN_MAX_PASS - LEN_MIX_PASS)
						+
						LEN_MIX_PASS
				];
		final int LEN_PASS = PASS.length;
		for (int i = 0; i < LEN_PASS; ++i)
			PASS[i] = SQC_PASS_CHAR[RANDOM.nextInt(LEN_SQC_PASS_CHAR)];

		controller.addParam(ATTR_PASS, new String(PASS));

		//--:	USER DESCR
		final String USER_DESCR = "USER DESCR : " + ID;
		controller.addParam(ATTR_USER_DESCR, USER_DESCR);

		//--:	LOCATION
		final int LOCATION = RANDOM.nextInt(SQC_LOCATION.length);
		controller.addParam(ATTR_LOCATION, SQC_LOCATION[LOCATION]);

		//--:	PICTURE
		final String PICTURE = "www.PIcTuRe" + ID + ".com";
		controller.addParam(ATTR_PICTURE, PICTURE);

		//--:	IS BLOCKED
		final boolean isBlocked = RANDOM.nextBoolean();
		controller.addParam(ATTR_BLOCKED, Boolean.toString(isBlocked));
	}

	private Entity generatePoke()
	{
		Entity.EntityController controller = new Entity.EntityController(ATTR_POKE_SIZE);
		generateUser(controller, OBJECT_TYPE_ID_POKE);

		return controller.getEntity();
	}

	private Entity generateMaster()
	{
		Entity.EntityController controller = new Entity.EntityController(ATTR_MASTER_SIZE);
		generateUser(controller, OBJECT_TYPE_ID_MASTER);

		//--:	PROFESSION
		final String PROFESSION = SQC_PROFESSION[RANDOM.nextInt(SQC_PROFESSION.length)];
		controller.addParam(ATTR_PROFESSION, PROFESSION);

		//--:	SKILLS
		final String SKILLS = "Some skills";
		controller.addParam(ATTR_SKILLS, SKILLS);

		//--:	EXPERIENCE
		final int MAX_EXP = 30;
		final int MIN_EXP = 1;
		final int EXPIRENCE = RANDOM.nextInt(MAX_EXP - MIN_EXP) + MIN_EXP;
		controller.addParam(ATTR_EXPIERENCE, Integer.toString(EXPIRENCE));

		//--:	PAYMENT
		final int MAX_PAYMENT = 100000;
		final int MIN_PAYMENT = 1000;
		final int PAYMENT =
				RANDOM.nextInt(MAX_PAYMENT - MIN_PAYMENT) + MIN_PAYMENT;

		controller.addParam(ATTR_PAYMENT, Integer.toString(PAYMENT));

		//--:	SMOKE
		final boolean SMOKE = RANDOM.nextBoolean();
		controller.addParam(ATTR_SMOKE, Boolean.toString(SMOKE));

		//--:	Tools
		final String TOOLS = "Some tools";
		controller.addParam(ATTR_TOOLS, TOOLS);

		//--:	START TIME
		final byte MIN_START_HOUR = 6;
		final byte MAX_START_HOUR = 13;
		final int START_HOUR = RANDOM.nextInt
				(
					MAX_START_HOUR
						-
					MIN_START_HOUR
				) + MIN_START_HOUR;
		final String START_TIME =
				Integer.toString(START_HOUR)
					+
				(RANDOM.nextBoolean() ? "00" : "30");
		controller.addParam(ATTR_START_TIME, START_TIME);

		//--:	END TIME
		final byte MIN_END_HOUR = 15;
		final byte MAX_END_HOUR = 20;
		final int END_HOUR = RANDOM.nextInt
				(
					MAX_END_HOUR
						-
					MIN_END_HOUR
				) + MIN_END_HOUR;
		final String END_TIME =
				Integer.toString(END_HOUR)
					+
				(RANDOM.nextBoolean() ? "00" : "30");
		controller.addParam(ATTR_END_TIME, END_TIME);
		return controller.getEntity();
	}

	private Entity generateOrder()
	{
		Entity.EntityController controller = new Entity.EntityController(ATTR_ORDER_SIZE);

	//--:	IDs
		final String ID = Long.toString(object_id);
		controller.addParam(ID, OBJECT_TYPE_ID_ORDER);
		++object_id;
	//--:	NAME
		final int INX_NAME = RANDOM.nextInt(SQC_ORDER_NAME.length);
		final String NAME = SQC_ORDER_NAME[INX_NAME];

	//--:	DESCR
		final String DESCR = "DESCR : " + ID;
		controller.addParam(NAME, DESCR);

	//--:	ORDER DESCR ...
		controller.addParam(ATTR_SMALL_DESCR, NAME);
		controller.addParam(ATTR_BIG_DESCR, NAME);

	//--:	START DATE
		final int MIN_DUE_DAYS = 1;
		final int MAX_DUE_DAYS = 366;
		final Date DATE = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DATE);
		calendar.add
				(
					Calendar.DATE,
					RANDOM.nextInt(MAX_DUE_DAYS) - (MAX_DUE_DAYS >> 1)
				);

		controller.addParam
				(
					ATTR_START_DATE,
					new SimpleDateFormat("dd/MM/yyyy HH:mm").format(calendar.getTime())
				);
	//--:	DUE DATE
		calendar.add
				(
					Calendar.DATE,
					RANDOM.nextInt
					(
						MAX_DUE_DAYS
							-
						MIN_DUE_DAYS
					) + MIN_DUE_DAYS
				);
		final Date DUE_DATE = calendar.getTime();
		controller.addParam
				(
					ATTR_DUE_DATE,
					new SimpleDateFormat("dd/MM/yyyy HH:mm").format(DUE_DATE)
				);

	//--: 	POKE (parent_id)
		final Entity POKE = SQC_POKES[RANDOM.nextInt(SQC_POKES.length)];
		final String POKE_ID = POKE.getEntityID();
		controller.addParam(ATTR_ORDER_PARENT_ID, POKE_ID);

	//--:	STATUS
		byte status = (byte)RANDOM.nextInt(3);

	//--:	MASTER REF
		final String MASTER_PROFESSION = SQC_PROFESSION[INX_NAME >> 1];
		controller.addParam(ATTR_ORDER_PROFESSION, MASTER_PROFESSION);
		if (status != 0)
		{
			LinkedList<String> LST_MASTER_REF = new LinkedList<>();
			final int LEN_SQC_MASTER = SQC_MASTER.length;
			for (int i = 0; i < LEN_SQC_MASTER; ++i)
				if (SQC_MASTER[i].getParam(ATTR_PROFESSION).equals(MASTER_PROFESSION))
					LST_MASTER_REF.add(SQC_MASTER[i].getEntityID());

			if (LST_MASTER_REF.size() > 0)
			{
				final String MASTER_REF = LST_MASTER_REF.get
						(
								RANDOM.nextInt(LST_MASTER_REF.size())
						);
				controller.addParam(ATTR_MASTER_REF, MASTER_REF);

				if (status == 2)
				{
					controller.addParam
						(
							ATTR_ORDER_MARK,
							Integer.toString(RANDOM.nextInt(5) + 1)
						);
					controller.addParam(ATTR_ORDER_COMMENT, "SOME COMMENT " + ID);
					controller.addParam(ATTR_ORDER_MASTER_END_DATE, new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
				}
			}

			else
				status = 0;
		}

		controller.addParam(ATTR_STATUS, SQC_STATUS[status]);

		return controller.getEntity();
	}
}
