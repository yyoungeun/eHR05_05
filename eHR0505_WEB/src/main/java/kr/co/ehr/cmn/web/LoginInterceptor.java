package kr.co.ehr.cmn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.ehr.user.service.User;

/**
 * 로그인이 안된경우: /login/login.jsp return
 * @author sist
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private final Logger LOG = LoggerFactory.getLogger(ViewNameInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {

			HttpSession httpSession = request.getSession();
			User user = (User) httpSession.getAttribute("user");
			LOG.debug("=======================================");
			LOG.debug("=user=" + user);
			LOG.debug("=======================================");
			
			if(null == user) {
				String context = request.getContextPath();
				response.sendRedirect(context+ "/login/login.jsp");
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	
	
}
