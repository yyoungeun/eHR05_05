package kr.co.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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

import kr.co.ehr.cmn.UserExcelWriter;
import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.impl.CodeDaoImpl;
import kr.co.ehr.user.service.Level;
import kr.co.ehr.user.service.User;

@WebAppConfiguration
//import com.ehr.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml" }) //배열
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Test NAME_ASCENDING으로 수행
public class UserExcelTest {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private UserExcelWriter userExcelWriter;
	
	List<User> users;
	
	List<String> headers;
	
	@Before
	public void setUp() {
		headers = Arrays.asList("아이디"
				,"이름"
				,"비번"
				,"레벨"
				,"로그인"
				,"추천"
				,"이메일"
				,"등록일");
		users = Arrays.asList(
				 new User("j01_134","송영은01","1234",Level.BASIC,49,0,"1150amy@naver.com","2019/08/23")
				,new User("j02_134","송영은02","1234",Level.BASIC,50,0,"1150amy@naver.com","2019/08/23") //BASIC -> SILVER
				,new User("j03_134","송영은03","1234",Level.SILVER,50,29,"1150amy@naver.com","2019/08/23")
				,new User("j04_134","송영은04","1234",Level.SILVER,50,30,"1150amy@naver.com","2019/08/23") //SILVER -> GOLD
				,new User("j05_134","송영은05","1234",Level.GOLD,99,99,"1150amy@naver.com","2019/08/23")
				);
	}
	
	@Test
	@Ignore
	public void xlsxWriterGeneralization() {
		userExcelWriter.xlsxWriterGeneralization(users, headers);
	}	
	
	@Test
	@Ignore
	public void valueObjectRefl() {
		Object obj = users.get(0);
		Field[]  fileds = obj.getClass().getDeclaredFields();
		for(int i=0;i<fileds.length;i++) {
			Field field =fileds[i];
			
			field.setAccessible(true);
   
			try {  
				Object value = field.get(obj);
				LOG.debug(field.getName()+":"+value);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Test
	@Ignore
	public void xlsxWriterStyle() {
		userExcelWriter.xlsxWriterStyle(users);
	}
	
	@Test
	@Ignore
	public void csvWriter() { //.csv
		userExcelWriter.csvWriter(users);
	}
	
	@Test
	@Ignore
	public void xlsWriter() { //97-2003
		userExcelWriter.xlsWriter(users);
	}
	
	@Test
	@Ignore
	public void xlsxWriter() { //워크시트
		userExcelWriter.xlsxWriter(users);
	}
	
	@Test
	public void getBean() {
		LOG.debug("==========================");
		LOG.debug("=context="+context);
		LOG.debug("=userExcelWriter=" + userExcelWriter);
		LOG.debug("==========================");
		assertThat(context, is(notNullValue()));
		assertThat(userExcelWriter, is(notNullValue()));
		
	}
	
	@After
	public void tearDown() {
		
	}

}
