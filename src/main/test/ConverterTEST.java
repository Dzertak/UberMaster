import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ubermaster.persistence.converter.impl.ConverterImpl;

import java.text.ParseException;

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

	@Test
	public void convertStringToStringObject() throws ParseException
	{
		Object converted = ConverterImpl.convertStringToObject("StrinG", String.class);
		assertEquals("Must be", "StrinG", converted);
	}

	@Test
	public void convertStringToLong() throws ParseException
	{
		Object converted = ConverterImpl.convertStringToObject("12314545436577345", long.class);
		assertEquals("Must be", 12314545436577345L, converted);
	}

	@Test
	public void convertStringToBoolean() throws ParseException
	{
		Object converted = ConverterImpl.convertStringToObject("true", boolean.class);
		assertEquals("Must be", true, converted);
	}

	@Test
	public void convertNullToObject() throws ParseException
	{
		Object converted = ConverterImpl.convertStringToObject(null, boolean.class);
		assertEquals("Must be", null, converted);
	}
}
