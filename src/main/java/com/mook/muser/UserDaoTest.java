package com.mook.muser;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/bean.xml")
//@ContextConfiguration("file:src/main/webapp/WEB/root-context.xml")
//@ContextConfiguration(value = "file:src/main/webapp/WEB/root-context.xml", locations = "/bean.xml")
public class UserDaoTest {
	@Autowired
	ApplicationContext context;

	UserDao dao = null;
	User user1 = null;
	User user2 = null;
	User user3 = null;
	
	@Before
	public void initSetup() {
		
		context = new GenericXmlApplicationContext("bean.xml");
		this.dao = context.getBean("userDao", UserDao.class); 

		dao = context.getBean("userDao", UserDao.class);
		this.user1 = new User("alldigital1", "최한묵", "alldigital32");
		this.user2 = new User("alldigital2", "김정국", "kimjung");
		this.user3 = new User("alldigital3", "이상록", "leedang");
		
	}
	
	@Test
	public void addNget() throws ClassNotFoundException, SQLException {
/*
		ConnectionMaker conInfo = new DConnectionMaker();
		User user = new User("alldigital", "최한묵", "alldigital32");
		UserDao dao = new DaoFactory().userDao();
		UserDao dao = new DaoFactory().userDao();
	    ApplicationContext context = new GenericXmlApplicationContext("xml 파일 이름");
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = (UserDao) context.getBean("userDao"); //or
*/

		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		assertEquals(dao.getCount(), 1);

		dao.add(user2);
		assertEquals(dao.getCount(), 2);

		dao.add(user3);
		assertEquals(dao.getCount(), 3);

		User rtnUser1 = dao.get(user1.getId());
		assertEquals(rtnUser1.getName(), user1.getName());
		assertEquals(rtnUser1.getPassword(), user1.getPassword());

		User rtnUser2 = dao.get(user2.getId());
		assertEquals(rtnUser2.getName(), user2.getName());
		assertEquals(rtnUser2.getPassword(), user2.getPassword());

		User rtnUser3 = dao.get(user3.getId());
		assertEquals(rtnUser3.getName(), user3.getName());
		assertEquals(rtnUser3.getPassword(), user3.getPassword());

	}
	
	
	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, Exception{
	
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.get("asdsadmsadas");
	}

	
}
