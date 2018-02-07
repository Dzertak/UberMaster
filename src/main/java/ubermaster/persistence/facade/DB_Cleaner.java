package ubermaster.persistence.facade;

import ubermaster.persistence.manager.impl.ManagerImpl;

public class DB_Cleaner
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	protected boolean cleanerWorks = false;
	private final int CLEANER_DELAY;
/*::|       CONSTRUCTOR       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public DB_Cleaner(int delay)
	{
		CLEANER_DELAY = delay;
		cleanerWorks = true;
	}
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public void start()
	{
		if (!cleanerWorks)
			return;

		Thread thread = new Thread()
		{
			public void run()
			{
				do
				{
					ManagerImpl.cleanOrders();

					System.out.println("Order cleared");
					try
					{
						Thread.sleep(CLEANER_DELAY);
					}

					catch (InterruptedException exc)
					{
						exc.printStackTrace();
					}
				}
				while (cleanerWorks);
			}
		};

		thread.start();
	}

	public void stop()
	{
		cleanerWorks = false;
	}
}
