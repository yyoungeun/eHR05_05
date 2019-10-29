package kr.co.ehr.board;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.board.service.impl.BoardDaoImpl;
import kr.co.ehr.user.service.Level;
import kr.co.ehr.user.service.User;
import kr.co.ehr.user.service.UserDao;

@WebAppConfiguration
//import com.ehr.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"
		   ,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
		}) //배열
public class BoardWebTest {
	
	private Logger LOG = LoggerFactory.getLogger(BoardWebTest.class);
	
	@Autowired
	WebApplicationContext context;
	
	List<Board> list;
	
	private MockMvc mockMvc;
	
	@Autowired
	private BoardDaoImpl dao;
	
	@Before
	public void setUp() {
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		LOG.debug("setUp()");
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		
		list = Arrays.asList(
				new Board("noBoatdId","J01제목_134",0,"J01내용_134","admin","no_date"),
				new Board("noBoatdId","J02제목_134",0,"J02내용_134","admin","no_date"),
				new Board("noBoatdId","J03제목_134",0,"J03내용_134","admin","no_date"),
				new Board("noBoatdId","J04제목_134",0,"J04내용_134","admin","no_date"),
				new Board("noBoatdId","J05제목_134",0,"J05내용_134","admin","no_date")
				);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build(); //mockMvc생성자
		LOG.debug("==================================");
		LOG.debug("context=" + context);
		LOG.debug("mockMvc=" + mockMvc);
		LOG.debug("=dao="+ dao);
		LOG.debug("==================================");
	}
	
	@Test
	@Ignore
	public void excelDown() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.get("/board/do_excelDown.do")
				.param("pageSize", "10")
				.param("pageNum", "1") //param연결
				.param("searchDiv", "10")
				.param("searchWord", "_134")
				.param("ext", "xls");
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(status().isOk());
		
		String result = resultActions.andDo(print())
									.andReturn()
									.getResponse().getContentAsString();
	
		LOG.debug("===============================");
		LOG.debug("=result="+result);
		LOG.debug("===============================");
	}
	
	@Test
	public void get_retrieve() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.get("/board/get_retrieve.do")
				.param("pageSize", "10")
				.param("pageNum", "1") //param연결
				.param("searchDiv", "10")
				.param("searchWord", "_134");
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(status().isOk());
		
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
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/board/do_update.do")
				.param("boardId", "1148")
				.param("title", "J03제목_134_UUU") //param연결
				.param("contents", "J03내용_134_UUU")
				.param("regId", "admin7_UUU");
		
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
	public void do_save() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/board/do_save.do")
				.param("title", "J07제목_134") //param연결
				.param("contents", "J07내용_134")
				.param("regId", "admin7");
		
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
	
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void get_selectOne() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.get("/board/do_selectOne.do")
				.param("boardId", "1148"); //param연결
		
		ResultActions resultActions = mockMvc.perform(createMessage)
											 //.andExpect(status().is4xxClientError())
											 .andExpect(status().isOk());
		
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
	public void do_delete() throws Exception{
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/board/do_delete.do")
				.param("boardId", "1147"); //param연결
		
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
