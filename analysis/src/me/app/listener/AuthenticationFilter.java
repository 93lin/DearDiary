package me.app.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.Const;
import me.app.entity.User;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
	private static final Logger log = Logger.getLogger(AuthenticationFilter.class);
	private FilterConfig filterConfig = null;
	private static List<String> noFilter = new ArrayList<String>();
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    	noFilter.add("/view/default/index.jsp");
    	noFilter.add("/view/default/error404.jsp");
    	noFilter.add("/view/default/error500.jsp");
    	noFilter.add("/view/default/login.jsp");
    	
    	//添加一些不需要认证的页面或者路由
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("认证网关关闭中...");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		log.info("认证模块开始工作...");
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String username = String.valueOf(req.getParameter("username"));
        String password = String.valueOf(req.getParameter("password"));
        String url = req.getServletPath();
        boolean authenSuccess = true;
        /**
         * 判断系统有session,密码是否认证通过;
         */
        if(noFilter.contains(url)) {
        } else if(req.getSession().getAttribute("username")!=null) {
        } else if(req.getSession().getAttribute("username")==null && username !=null && password != null) {
        	authenSuccess = validate(username, password);
        	if(authenSuccess) {
        		User user = new User(username, password);
        		req.getSession(true).setAttribute("username", user);
        	}
        } else {
        	authenSuccess = false;
        }
        
        if(authenSuccess) {
        	chain.doFilter(req, res);
        } else {
/*	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<HTML>");
	        out.println("<HEAD>");
	        out.println("<TITLE>");
	        out.println("Incorrect Password");
	        out.println("</TITLE>");
	        out.println("</HEAD>");
	        out.println("<BODY>");
	        out.println("<H1>Incorrect Password</H1>");
	        out.println("Sorry, that password was incorrect.");
	        out.println("</BODY>");
	        out.println("</HTML>");
	        out.flush();*/
        	log.info("认证失败");
        	res.sendRedirect(Const.domain);
	    }    
	}

	private boolean validate(Object ... str) {
		if( (String.valueOf(str[0]).trim().equals("username") && (String.valueOf(str[1]).trim().equals("password")) )) {
			return true;
		}
		return false;
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = fConfig;
		log.info("认证网关初始化...");
	}

}
