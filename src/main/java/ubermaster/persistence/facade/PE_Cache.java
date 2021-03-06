package ubermaster.persistence.facade;

import ubermaster.entity.model.PersistenceEntity;
import ubermaster.entity.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.*;

public class PE_Cache
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private final HashMap<Long, PersistenceEntity> CACHE = new HashMap<>();
	private final int LIFE_SPAN_MINUTE;
	private final int LIFE_SPAN_HOUR;
	private final int CLEANER_DELAY;

	private volatile boolean cleanerWorks = true;
	private Object mutex = new Object();
	private Object persMutex = new Object();

	private PE_Cleaner cleaner;
/*::|       CONSTRUCTOR     :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	PE_Cache(int hours, int minutes, int cleanerDelay)
	{
		LIFE_SPAN_HOUR = hours;
		LIFE_SPAN_MINUTE = minutes;
		CLEANER_DELAY = cleanerDelay;
	}
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	private class PE_Cleaner implements Callable<String>
	{
		private PersistenceEntity persistenceEntity;

		public void setLatestPE()
		{
		//==:	search latest PE
			PersistenceEntity persistenceEntity = null;
			for (long id : CACHE.keySet())
			{
				PersistenceEntity pe;
				synchronized (persMutex)
				{
					pe = CACHE.get(id);
				}

				if (persistenceEntity != null)
				{
					if (persistenceEntity.getLifeSpan().after(pe.getLifeSpan()))
						persistenceEntity = pe;
				}

				else
					persistenceEntity = pe;
			}

			this.persistenceEntity = persistenceEntity;
			if (this.persistenceEntity != null)
				System.out.println("Lastest is : " + this.persistenceEntity);
		}

		public PersistenceEntity getPersistenceEntity()
		{
			return persistenceEntity;
		}

		public String call()
		{
			setLatestPE();

			if (persistenceEntity == null)
				return null;

			do
			{
				try
				{
					synchronized (mutex)
					{
						Date date = new Date();
						System.out.println("Date : " + date.toString());
						System.out.println("PerDate : " + persistenceEntity.getLifeSpan().toString());
						System.out.println(persistenceEntity.getLifeSpan().before(new Date()));

						if (!persistenceEntity.getLifeSpan().after(date))
							break;

						if (!cleanerWorks)
							return "Stooped";
					}

					Thread.sleep(CLEANER_DELAY);
				}

				catch (InterruptedException exc)
				{
					exc.printStackTrace();
				}
			}
			while (true);

		//--:	delete and return info
			synchronized (persMutex)
			{
				CACHE.remove(persistenceEntity.getObject_id());
			}

			System.out.println("Cleared : " + persistenceEntity);

			return persistenceEntity.toString();
		}
	}
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
	public void autoCleanCache()
	{
		Thread thread = new Thread()
		{
			public void run()
			{
				ExecutorService exeService = Executors.newSingleThreadExecutor();
				cleaner = new PE_Cleaner();
				Future<String> futureCleaner;

				do
				{
					futureCleaner = exeService.submit(cleaner);

					try
					{
						while (!futureCleaner.isDone())
							Thread.sleep(1);
					}

					catch (InterruptedException exc)
					{
						exc.printStackTrace();
					}

				//==:	Add info to logger about delete entity
					try
					{
						if (futureCleaner.get() != null)
							System.out.println(futureCleaner.get());
					}

					catch (ExecutionException | InterruptedException exc)
					{
						exc.printStackTrace();
					}
				}
				while(cleanerWorks);
			}
		};

		thread.start();
	}

	public void put(PersistenceEntity persistenceEntity)
	{
		addTime(persistenceEntity);

		synchronized (persMutex)
		{
			CACHE.put(persistenceEntity.getObject_id(), persistenceEntity);
		}
	}

	public PersistenceEntity get(long id)
	{
		synchronized (persMutex)
		{
			addTime(CACHE.get(id));

			return CACHE.get(id);
		}
	}

	public boolean containsKey(long id)
	{
		return CACHE.containsKey(id);
	}

	public PersistenceEntity getUser(String phoneNumber, String password)
	{
	//--:   Checking for presenting entity in the CACHE
		final byte NOT_FOUND = 0;
		final byte PHONE_NUMBER_EQUALS = 1;
		final byte PASS_EQUALS = 2;
		final byte ALL_EQUALS = 3;
		for (long id : CACHE.keySet())
		{
			PersistenceEntity persistenceEntity;
			synchronized (persMutex)
			{
				persistenceEntity = CACHE.get(id);
			}

			HashMap<String, Object> attributes = (HashMap<String, Object>) persistenceEntity.getAttributes();
			byte condition = NOT_FOUND;
			for (String attrID : attributes.keySet())
			{
				if
				(
					attrID.equals(User.Model.PHONE_NUMBER)
						&&
					attributes.get(attrID).equals(phoneNumber)
				)
					condition |= PHONE_NUMBER_EQUALS;

				else if
				(
					attrID.equals(User.Model.PASSWORD)
						&&
					attributes.get(attrID).equals(password)
				)
					condition |= PASS_EQUALS;

				if (condition == ALL_EQUALS)
					return persistenceEntity;
			}
		}

		return null;
	}

	public PersistenceEntity getUser(String phoneNumber)
	{
		//==:   CACHE
		for (long id : CACHE.keySet())
		{
			PersistenceEntity persistenceEntity;
			synchronized (persMutex)
			{
				persistenceEntity = CACHE.get(id);
			}

			HashMap<String, Object> attributes = (HashMap<String, Object>) persistenceEntity.getAttributes();
			for (String attrID : attributes.keySet())
			{
				if
				(
					attrID.equals(User.Model.PHONE_NUMBER)
						&&
					attributes.get(attrID).equals(phoneNumber)
				)
					return persistenceEntity;
			}
		}

		return null;
	}

	public void stopCleaner()
	{
		cleanerWorks = false;
	}

	private boolean isThatDeletes(long id)
	{
		if
		(
			cleaner.getPersistenceEntity() != null
				&&
			cleaner.persistenceEntity.getObject_id() == id
		)
			return true;

		return false;
	}

	private void addTime(PersistenceEntity persistenceEntity)
	{
		synchronized (mutex)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, LIFE_SPAN_MINUTE);
			calendar.add(Calendar.HOUR, LIFE_SPAN_HOUR);
			persistenceEntity.setLifeSpan(calendar.getTime());

			if (isThatDeletes(persistenceEntity.getObject_id()))
				cleaner.setLatestPE();
		}
	}
}
