import entity.model.Master;
import entity.model.Order;
import entity.model.Poke;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.facade.Facade;

import java.io.IOException;
import java.util.Date;
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
}
