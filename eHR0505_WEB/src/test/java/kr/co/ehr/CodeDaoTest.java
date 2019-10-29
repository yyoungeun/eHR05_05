package kr.co.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.impl.CodeDaoImpl;

@WebAppConfiguration
//import com.ehr.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml" }) //배열
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Test NAME_ASCENDING으로 수행
public class CodeDaoTest {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private CodeDaoImpl codeDaoImpl;
	
	private Code code;
	
	@Before
	public void setUp() {
		code = new Code();
		
	}
	@Test
	public void getBean() {
		LOG.debug("==========================");
		LOG.debug("=context="+context);
		LOG.debug("=codeDaoImpl="+codeDaoImpl);
		LOG.debug("==========================");
		assertThat(codeDaoImpl, is(notNullValue()));
		assertThat(codeDaoImpl, is(notNullValue()));
	}
	
	@Test
	public void get_retrieve() {
		code.setCodeId("PAGE_SIZE");
		List<Code> list =  (List<Code>) this.codeDaoImpl.get_retrieve(code);
		LOG.debug("==========================");
		for(Code vo:list) {
			LOG.debug(vo.toString());

		}
		LOG.debug("==========================");
	}
	
	@After
	public void tearDown() {
		
	}
	

}
