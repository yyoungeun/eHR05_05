package kr.co.ehr.main.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	//View
	private final String VIEW_NM = "main/main";
	
	@RequestMapping(value="main/main.do", method=RequestMethod.GET)
	public String main() {
		LOG.debug("===================================");
		LOG.debug("VIEW_NM:" + VIEW_NM);
		LOG.debug("===================================");
		
		return VIEW_NM;
	}

}
