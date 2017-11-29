import entity.model.Master;
import entity.model.Order;
import entity.model.Poke;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.facade.Facade;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EntityTEST
{
	private Facade facade;

	@Before
	public void start() throws SQLException, ClassNotFoundException
	{
		facade = new Facade("userPR", "PR");
	}

	@After
	public void end() throws IOException
	{
		facade.dispose();
	}

	@Test
	public void getDifferentEntitiesTEST()
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
	{
		final long ID = 10;

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

		facade.createEntity(poke, false);

		System.out.println(facade.getEntity(ID, Poke.class));
	}

	@Test
	public void insertMasterTEST() throws ParseException
	{
		Master master = new Master();
		master.setName("MasterName");
		master.setUserDescription("Master User Descr");
		master.setObject_id(10);
		master.setTools("Pipetka");
		master.setStart_time(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("01/02/2003 04:05"));
		master.setDescription("SomeDescr about MASTA");
		master.setLocation("MasterLand");
		master.setPassword("masterPASS, it's hard pass, right?");
		master.setPhoneNumber("0777");
		master.setPicture("PHOTO");
		master.setEnd_time(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("06/07/2008 09:10"));
		master.setExperience("11");
		master.setPayment(120000);
		master.setProfession("I'm master, what the stupid question?");
		master.setSkills("I can everything");
		master.setSmoke(false);

		facade.createEntity(master, false);
	}

	@Test
	public void insertMasterGetMasterTEST() throws ParseException
	{
		Master master = new Master();
		master.setName("MasterName");
		master.setUserDescription("Master User Descr");
		master.setObject_id(10);
		master.setTools("Pipetka");
		master.setStart_time(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("01/02/2003 04:05"));
		master.setDescription("SomeDescr about MASTA");
		master.setLocation("MasterLand");
		master.setPassword("masterPASS, it's hard pass, right?");
		master.setPhoneNumber("0777");
		master.setPicture("PHOTO");
		master.setEnd_time(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("06/07/2008 09:10"));
		master.setExperience("11");
		master.setPayment(120000);
		master.setProfession("I'm master, what the stupid question?");
		master.setSkills("I can everything");
		master.setSmoke(false);

		facade.createEntity(master, false);
		System.out.println(facade.getEntity(10, Master.class).toString());
	}
}
