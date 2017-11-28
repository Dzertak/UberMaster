import entity.model.Master;
import entity.model.Order;
import entity.model.Poke;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.facade.Facade;

import java.io.IOException;
import java.sql.SQLException;

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
	public void getDifferentEntiteisTEST()
	{
		System.out.println(facade.getEntity(1, Poke.class));
		System.out.println();
		System.out.println(facade.getEntity(2, Poke.class));
		System.out.println();
		System.out.println(facade.getEntity(3, Master.class));
		System.out.println();
		System.out.println(facade.getEntity(4, Order.class));
	}
}
