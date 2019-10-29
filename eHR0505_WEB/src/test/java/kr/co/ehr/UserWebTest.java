package kr.co.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import kr.co.ehr.user.service.Level;
import kr.co.ehr.user.service.User;
import kr.co.ehr.user.service.UserDao;

@WebAppConfiguration
//import com.ehr.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"
		   ,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
		}) //배열
public class UserWebTest {
	
	private Logger LOG = LoggerFactory.getLogger(UserWebTest.class);
	
	@Autowired
	WebApplicationContext context;
	
	List<User> users;
	
	private MockMvc mockMvc;
	
	@Autowired
	private UserDao dao;
	
	@Before
	public void setUp() {
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		LOG.debug("setUp()");
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		
		users = Arrays.asList(
				 new User("j01_134","송영은01","1234",Level.BASIC,49,0,"1150amy@naver.com","2019/08/23")
				,new User("j02_134","송영은02","1234",Level.BASIC,50,0,"1150amy@naver.com","2019/08/23") //BASIC -> SILVER
				,new User("j03_134","송영은03","1234",Level.SILVER,50,29,"1150amy@naver.com","2019/08/23")
				,new User("j04_134","송영은04","1234",Level.SILVER,50,30,"1150amy@naver.com","2019/08/23") //SILVER -> GOLD
				,new User("j05_134","송영은05","1234",Level.GOLD,99,99,"1150amy@naver.com","2019/08/23")
				);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		LOG.debug("==================================");
		LOG.debug("context=" + context);
		LOG.debug("mockMvc=" + mockMvc);
		LOG.debug("=dao="+ dao);
		LOG.debug("==================================");
	}
	
	//login/do_login.do
	@Test
	public void do_login() throws Exception {
		User user = users.get(0);
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/login/do_login.do")
				.param("u_id", user.getU_id())			
				.param("passwd", user.getPasswd());		
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/main/main.jsp"));
        
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");		
	}
	
	@Test
	@Ignore
	public void updateUser() throws Exception{
		
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		for(User user: users) {
			do_delete(user);
		}
			
		LOG.debug("======================================");
		LOG.debug("=02. 데이터 입력=");
		LOG.debug("======================================");
		do_insert(users.get(0));
		
		User user01 = users.get(0);
		user01.setName("송영은U");
		user01.setPasswd("1234U");
		user01.sethLevel(Level.GOLD);
		user01.setLogin(99);
		user01.setRecommend(999);
		user01.setEmail("1150aaa@naver.com");
		
		LOG.debug("======================================");
		LOG.debug("=03. 입력데이터 수정=");
		LOG.debug("=03.1 user01 수정=");
		LOG.debug("=03.1 user01 업데이트 수행=");
		LOG.debug("======================================");
		do_update(user01);
		
		LOG.debug("======================================");
		LOG.debug("=04. 수정데이터와 3번 비교=");
		LOG.debug("======================================");
		
		User vsUser = get_selectOne(user01);
		checkUser(vsUser, user01);
	}
	
	//CRUD(목록리스트 빼고)
	@Test
	@Ignore
	public void addAndGet() throws Exception {
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		for(User user:users) {
			do_delete(user);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 데이터 입력=");
		LOG.debug("======================================");
		for(User user:users) {
			do_insert(user);
		}
		
		assertThat(dao.count("_134"), is(5));
		LOG.debug("======================================");
		LOG.debug("=02. 단건조회=");
		LOG.debug("======================================");
		for(User user: users) {
			User vsUser = get_selectOne(user);
			assertThat(vsUser.getU_id(), is(user.getU_id()));
			assertThat(vsUser.getName(), is(user.getName()));
			assertThat(vsUser.getEmail(), is(user.getEmail()));
			checkUser(vsUser,user);
		}
	}
	
	public void do_update(User user) throws Exception {
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_update.do")
				.param("u_id", user.getU_id()) //param연결
				.param("name", user.getName())
				.param("passwd", user.getPasswd())
				.param("hLevel", user.gethLevel()+"")
				.param("login", user.getLogin()+"")
				.param("recommend", user.getRecommend()+"")
				.param("email", user.getEmail());
		//basic -> gold바로불가
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));
		String result = resultActions.andDo(print())
		.andReturn()
		.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
	}
	
	public void checkUser(User vsUser, User user) {
		assertThat(vsUser.getU_id(), is(user.getU_id()));
		assertThat(vsUser.getPasswd(), is(user.getPasswd()));
		assertThat(vsUser.getName(), is(user.getName()));
		assertThat(vsUser.getEmail(), is(user.getEmail()));
		
		assertThat(vsUser.getRecommend(), is(user.getRecommend()));
		assertThat(vsUser.getLogin(), is(user.getLogin()));
		assertThat(vsUser.gethLevel(), is(user.gethLevel()));
	}
	
	
	
	private User get_selectOne(User user) throws Exception {
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/get_select_one.do")
				.param("u_id", user.getU_id()); //param연결
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", is(user.getName())));

		
		String result = resultActions.andDo(print())
									.andReturn() //응답에 대한 결과 반환
									.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
		
		//Json to User
		Gson gson = new Gson();
		User outVO = gson.fromJson(result, User.class);
		
		return outVO;
		
	}
	
	public void do_insert(User user) throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_insert.do")
				.param("u_id", user.getU_id()) //param연결
				.param("name", user.getName())
				.param("passwd", user.getPasswd())
				.param("hLevel", user.gethLevel()+"")
				.param("login", user.getLogin()+"")
				.param("recommend", user.getRecommend()+"")
				.param("email", user.getEmail());
		
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));
		
		String result = resultActions.andDo(print())
		.andReturn()
		.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
	}
	
	//Data삭제
	/**
	 * Data삭제
	 * @param user
	 * @throws Exception 
	 */
	private void do_delete(User user) throws Exception {
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_delete.do")
				.param("u_id", user.getU_id()); //param연결
		
		ResultActions resultActions = mockMvc.perform(createMessage);
		
		String result = resultActions.andDo(print())
		.andReturn()
		.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
	}
	
	@Test
	@Ignore
	public void do_update() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_update.do")
				.param("u_id", "j01_134") //param연결
				.param("name", "송영은01_99")
				.param("passwd", "1234_99")
				.param("hLevel", Level.SILVER+"")
				.param("login", "4999")
				.param("recommend", "99")
				.param("email", "1150amy99@naver.com");
		//basic -> gold바로불가
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));
		String result = resultActions.andDo(print())
		.andReturn()
		.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
	}
	
	@Test
	@Ignore
	public void do_insert() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_insert.do")
				.param("u_id", "j01_134") //param연결
				.param("name", "송영은01")
				.param("passwd", "1234")
				.param("hLevel", Level.BASIC+"")
				.param("login", "49")
				.param("recommend", "0")
				.param("email", "1150amy@naver.com");
		
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));
		
		String result = resultActions.andDo(print())
									.andReturn()
									.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
	}
	
	@Test
	@Ignore
	public void do_delete() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_delete.do")
				.param("u_id", "j01_134"); //param연결
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		
		String result = resultActions.andDo(print())
									.andReturn()
									.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
	}
	
	@Test
	@Ignore
	public void get_selectOne() throws Exception {
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/get_select_one.do")
				.param("u_id", "j01_134"); //param연결
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", is("송영은01")));

		
		String result = resultActions.andDo(print())
									.andReturn() //응답에 대한 결과 반환
									.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
		
	}
	
	@Test
	@Ignore
	public void doUserView() throws Exception {
		//Request call: url, param
		MockHttpServletRequestBuilder createMessage= MockMvcRequestBuilders.get("/user/do_user_view.do");
		
		mockMvc.perform(createMessage)
		.andExpect(status().isOk()) //응답에 대한 테스트
		.andExpect(forwardedUrl("/user/user_mng.jsp")) //forwardedUrl: 이동 대상 경로를 검증한다. 패턴으로 검증할 때는 forwardedUrlPattern메서드를 사용.
		.andDo(print()); //요청과 응답에 대한 처리
		
	}
	
	@Test //1
	public void instacnceTesst() {
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		LOG.debug("=instacnceTesst()=");
		LOG.debug("^^^^^^^^^^^^^^^^^^");
	}
	
	@After
	public void tearDown() {
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		LOG.debug("tearDown()");
		LOG.debug("^^^^^^^^^^^^^^^^^^");
	}

}
