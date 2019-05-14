package com.ikth.app.dictionary.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.core.runtime.Platform;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.osgi.framework.Bundle;

import com.ikth.app.dictionary.Activator;

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
		configuration.setProperty("hibernate.connection.url", getDerbyUrl());
		sessionFactory= configuration.configure().buildSessionFactory();
		
	}
	
	private String getDerbyUrl() {
		
//		Bundle bundle= Activator.getDefault().getBundle();
		Bundle bundle= Platform.getBundle(Activator.PLUGIN_ID);
		String repositoryPath= bundle.getDataFile("dictRepo").getAbsolutePath();
		
		return "jdbc:derby:" + repositoryPath + ";create=true";
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
