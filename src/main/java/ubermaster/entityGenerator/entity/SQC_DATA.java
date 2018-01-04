package ubermaster.entityGenerator.entity;

/**
 * @author Serpye
 * */
public interface SQC_DATA
{
	String SQC_NAME[] =
	{
		"Хаскил", "Александр", "Паскаль", "Вадим", "Виктор",
		"Давид", "Дионисий", "Лазарь", "Люциан", "Степан",
		"Ада", "Дайна", "Елизавета", "Ирина", "Лада",
		"Марта", "Мстислава", "Нинель", "Римма", "Юнона"
	};

	String SQC_SURNAME[] =
	{
		"Фандорин", "Трофимов", "Харитонов", "Поляков", "Абдулов",
		"Тихонов", "Гусев", "Карпов", "Марков", "Щукин", "Гаврилов",
		"Вишняков", "Суворов", "Юдин", "Кошелев", "Быков", "Власов",
		"Уваров", "Фролов", "Николаев", "Аксёнов", "Стрелков", "Фокин"
	};

	String SQC_LOCATION[] =
	{
		"Kievskyy", "Primorskyy", "Suvorovskyy", "Malinovskyy"
	};

	String SQC_PROFESSION[] =
	{
		"Locksmith", "Plumber", "Electrician", "Cleaner",
		"Computer foreman", "Handyman"
	};

	String SQC_ORDER_NAME[] =
	{
		"Installation a gas cooker.",
		"Repairing of rosette",

		"Installation of water and sewage pipes",
		"Water and sewer cleaning",

		"Replacement of electrical wiring",
		"Installation of two-rate counters",

		"Cleaning the yard from snow",
		"Daily cleaning",

		"Installing antivirus software",
		"Laptop repairing",

		"Garbage collection",
		"Unloading of trucks"
	};

	String SQC_STATUS[] =
	{
		"New", "In processing", "Completed"
	};

	int ATTR_POKE_SIZE = 8;
	int ATTR_MASTER_SIZE = 16;
	int ATTR_ORDER_SIZE = 10;
}
