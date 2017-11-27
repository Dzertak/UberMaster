import entity.model.User;
import persistence.converter.Converter;
import persistence.converter.ConverterImpl;

public class UberMasterDev
{

	public static void main(String sqcString[]) throws IllegalAccessException
	{
		User user = new User();
		user.setLocation("Some Location");
		user.setPicture("Some Picture");
		user.setDescription("Some Descr");
		user.setPassword("Some pass");
		user.setPhoneNumber("0099");
		user.setName("I'ma Usa");
		user.setObject_id(0);

		Converter converter = new ConverterImpl();

		user = converter.convertToModel(converter.convertToEntity(user), User.class);
		System.out.println(user.toString());
	}
}
