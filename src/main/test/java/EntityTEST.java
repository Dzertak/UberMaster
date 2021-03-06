package java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import ubermaster.entity.model.Master;
import ubermaster.entity.model.Order;
import ubermaster.entity.model.Poke;
import ubermaster.entity.model.User;
import ubermaster.persistence.facade.Facade;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class EntityTEST
{
	private Facade facade;

	@Before
	public void before() throws SQLException, ClassNotFoundException,
			IllegalAccessException, InstantiationException
	{
		//OracleConnector.create();
		facade = new Facade();
	}

	@After
	public void after() throws SQLException
	{
		//OracleConnector.close();
	}

	@Test
	public void getDifferentEntitiesTEST()
			throws SQLException, ParserConfigurationException, SAXException, IOException
	{
		System.out.println(facade.getEntity(1, Poke.class));
		System.out.println();
		System.out.println(facade.getEntity(2, Poke.class));
		System.out.println();
		System.out.println(facade.getEntity(3, Master.class));
		System.out.println();
		System.out.println(facade.getEntity(4, Order.class));
	}

	@Test
	public void insertEntityTEST()
			throws SQLException, ParserConfigurationException, SAXException, IOException
	{
		final long ID = 11;

		Poke poke = new Poke();
		poke.setLocation("Gracksland");
		poke.setObject_id(ID);
		poke.setName("Just Gogo");
		poke.setPicture("Picture of Gogo");
		poke.setPhoneNumber("0123456789");
		poke.setPassword("gogoPASS");
		poke.setUserDescription("I'ma gogo, and I'm from Gracksland");

		System.out.println(poke.toString());
		System.out.println();

		facade.createEntity(poke);

		System.out.println(facade.getEntity(ID, Poke.class));
	}

	@Test
	public void insertMasterGetMasterTEST()
			throws SQLException, ParserConfigurationException, SAXException, IOException, ParseException
	{
		Master master = new Master();
		master.setName("MasterName");
		master.setUserDescription("Master User Descr");
		master.setObject_id(10);
		master.setTools("Pipetka");
		master.setStart_time((short)830);
		master.setDescription("SomeDescr about MASTA");
		master.setLocation("MasterLand");
		master.setPassword("masterPASS, it's hard pass, right?");
		master.setPhoneNumber("0777");
		master.setPicture("PHOTO");
		master.setEnd_time((short)1700);
		master.setExperience("11");
		master.setPayment(120000);
		master.setProfession("I'm master, what the stupid question?");
		master.setSkills("I can everything");
		master.setSmoke(false);

		facade.createEntity(master);
		System.out.println(facade.getEntity(10, Master.class).toString());
	}

	@Test
	public void deleteEntityTEST()
			throws SQLException, ParserConfigurationException, SAXException, IOException
	{
		facade.deleteEntity(10);
		facade.deleteEntity(11);
	}

	@Test
	public void getEntityByTP()
			throws SQLException, ParserConfigurationException, SAXException, IOException
	{
		User user = facade.getUser("380456111789", "easy_password3");
		System.out.println(user);
	}
}


/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
