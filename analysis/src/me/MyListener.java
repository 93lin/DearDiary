package me;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.fastmvc.filter.EncodingFilter;

public class MyListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(EncodingFilter.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		log.info("用户自定义容器环境初始化...");
		this.setupDataSource();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		log.info("用户自定义容器环境关闭...");
		this.shutdownDataSource();
	}
	/**
	 * 初始化系統數據連接池
	 */
	private void setupDataSource() {
		log.info("用户自定义開啓數據庫...");
	}

	private void setupMysqlDataSource() {
		//Dber.getThreadLocalDberMq();
		log.info("MySql数据库连接成功...");
	}
	/**
	 * 關閉數據連接池
	 */
	private void shutdownDataSource() {
		//Dber.removeThreadLocalDberMq();
		log.info("MySql数据库关闭成功...");
	}

}
