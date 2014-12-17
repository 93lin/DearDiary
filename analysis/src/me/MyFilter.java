package me;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class MyFilter
 * 用户可以模仿这儿添加Filter, Filter的功能尽量要精简;
 * @author jhsr
 * @date 2014.12.2
 */
@WebFilter(filterName="step1", urlPatterns="*.py")
public class MyFilter implements Filter {

	private static final Logger log = Logger.getLogger(MyFilter.class);
    /**
     * Default constructor. 
     * 
     */
    public MyFilter() {
        // TODO Auto-generated constructor stub
    	
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("自定义Filter关闭...");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.info("自定义Filter初始化...");
	}

}
