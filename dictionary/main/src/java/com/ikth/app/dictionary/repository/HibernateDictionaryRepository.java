package com.ikth.app.dictionary.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateDictionaryRepository implements IDictionaryRepository {
	
	private SessionFactory sessionFactory= null;
	
	private SimpleHibernateRunner<Void, List<DictionaryEntity>> funcBatchMerge= (session, parameter) -> {
		
		int batSize= Integer.valueOf((String) sessionFactory.getProperties().get("hibernate.jdbc.batch_size"));
		IntStream.range(0, parameter.size())
				 .forEach((idx) -> {
					 if(idx % batSize == 0 && idx > 0) {
						 
						 session.flush();
						 session.clear();
					 }
					 
					 DictionaryEntity dict= parameter.get(idx);
					 session.persist(dict);
				 });
				 
		
		return null;
	};
	
	public HibernateDictionaryRepository() {
		
		Configuration configuration= new Configuration();
		configuration.addAnnotatedClass(DictionaryEntity.class);
		sessionFactory= configuration.configure().buildSessionFactory();
		
	}
	
	@Override
	public List<DictionaryEntity> findAll() throws Exception {
		
		return this.<List<DictionaryEntity>, Void>runHibernate(
				(session, parameter) -> session.createQuery("from DictionaryEntity", DictionaryEntity.class).getResultList(), null);
	}
	
	@Override
	public void removeAll() throws Exception {
		
		this.<Integer, Void>runHibernate((session, parameter) -> session.createQuery("delete from DictionaryEntity").executeUpdate(), null);
	}

	@Override
	public void add(List<DictionaryEntity> dictionaries) throws Exception {

		this.<Void, List<DictionaryEntity>>runHibernate(funcBatchMerge, dictionaries);
	}
	
	private <R, T> R runHibernate(SimpleHibernateRunner<R, T> r, T parameter) throws Exception {
		
		Session session= sessionFactory.openSession();
		Transaction transaction= null;
		
		try {
			
			transaction= session.beginTransaction();
			R result= r.run(session, parameter);
			transaction.commit();
			return result;
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@FunctionalInterface
	interface SimpleHibernateRunner<R, T> {
		
		R run(Session session, T parameter);
	}
}
