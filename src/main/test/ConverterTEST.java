import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ubermaster.persistence.converter.impl.ConverterImpl;
import static org.junit.Assert.*;
public class ConverterTEST
{
	@BeforeClass
	public static void beforeClassMethod()
	{

	}

	@AfterClass
	public static void afterClassMethod()
	{

	}

	@Test
	public void convertNullToString()
	{
		Object coverted = ConverterImpl.convertObjectToString(null);
		assertEquals("Must be", null, coverted);
	}

	@Test
	public void convertStringToString()
	{
		final String STRING = "String";
		Object coverted = ConverterImpl.convertObjectToString(STRING);
		assertEquals("Must be", STRING, coverted);
	}

	@Test
	public void convertLongToString()
	{
		Object coverted = ConverterImpl.convertObjectToString(15861464564564654L);
		assertEquals("Must be", "15861464564564654", coverted);
	}

	@Test
	public void convertBooleanToString()
	{
		Object coverted = ConverterImpl.convertObjectToString(true);
		assertEquals("Must be", "true", coverted);
	}
}
