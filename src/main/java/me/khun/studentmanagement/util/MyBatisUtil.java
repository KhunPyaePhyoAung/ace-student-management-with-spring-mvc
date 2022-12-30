package me.khun.studentmanagement.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	
	private static SqlSessionFactory sessionFactory;

	public static SqlSessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader("me/khun/studentmanagement/model/repo/impl/mybatis-config.xml");
				sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sessionFactory;
	}
}
