package kr.co.ehr.boardAttr;

import org.springframework.test.web.servlet.request.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.boardAttr.service.BoardAttr;
import kr.co.ehr.boardAttr.service.impl.BoardAttrDaoImpl;
import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.impl.CodeDaoImpl;
import kr.co.ehr.user.service.Search;
import kr.co.ehr.user.service.User;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//@Test NAME_ASCENDING으로 수행.
public class DaoBoardAttrWebTest {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private WebApplicationContext context;
	
	List<BoardAttr> list;
	
	private MockMvc mockMvc;
	
	@Autowired
	BoardAttrDaoImpl boardAttrDaoImpl;
	
	
	@Before
	public void setUp() {
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		LOG.debug("setUp()");
		LOG.debug("^^^^^^^^^^^^^^^^^^");
		
		list = Arrays.asList(
				 new BoardAttr("1","j01_attr_134","j01_attr_내용",0,"20191007_134","admin","noData")
				,new BoardAttr("2","j02_attr_134","j02_attr_내용",0,"20191007_134","admin","noData")
				,new BoardAttr("3","j03_attr_134","j03_attr_내용",0,"20191007_134","admin","noData")
				,new BoardAttr("4","j04_attr_134","j04_attr_내용",0,"20191007_134","admin","noData")
				,new BoardAttr("5","j05_attr_134","j05_attr_내용",0,"20191007_134","admin","noData")
				);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void do_retrieve() throws Exception{
		
		LOG.debug("====================================");
		LOG.debug("=01. do_retrieve() 기존 데이터 찾고/삭제");
		LOG.debug("====================================");
		//기존Data찾기
		Search search=new Search();
		search.setSearchWord("_134");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		//Data삭제
		for(BoardAttr vo:getIdList) {
			do_delete(vo);
		}
		
		LOG.debug("====================================");
		LOG.debug("=02.do_retrieve() 단건 등록");
		LOG.debug("====================================");
		for(BoardAttr vo:list) {
			do_save(vo);
		}
		
		LOG.debug("====================================");
		LOG.debug("=03. do_retrieve() 목록조회 like_134");
		LOG.debug("====================================");
		
		search.setSearchDiv("10");
		search.setSearchWord("_134");
		search.setPageSize(10);
		search.setPageNum(1);
		
		List<BoardAttr> list = this.get_retrieve(search);
		
		for(BoardAttr vo :list) {
			LOG.debug(vo.toString());
		}
		
		assertThat(5, is(list.size()));
	}
	
	/**수정 
	 * @throws Exception */
	@Test
	public void update() throws Exception {
		LOG.debug("======================================");
		LOG.debug("=01. update() 기존 데이터 찾고/삭제=");
		LOG.debug("======================================");
		
		//기존Data찾기
		Search search=new Search();
		search.setSearchWord("_134");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		//Data삭제
		for(BoardAttr vo:getIdList) {
			do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. update() 단건등록=");
		LOG.debug("======================================");
		//for(BoardAttr vo:list) {
			this.do_save(list.get(0)); //한건 등록
		//}
		
		LOG.debug("======================================");
		LOG.debug("=03. update() 단건조회=");
		LOG.debug("======================================");
		getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search); //한 건 조회
		
		
		LOG.debug("======================================");
		LOG.debug("=04. update() Data수정/Update=");
		LOG.debug("======================================");
		
		BoardAttr boardAttr = getIdList.get(0);
		boardAttr.setTitle("U_"+boardAttr.getTitle());
		boardAttr.setContents("U_"+boardAttr.getContents());
		boardAttr.setFileId("U_"+boardAttr.getFileId());
		boardAttr.setRegId("U_"+boardAttr.getRegId());
		
		//Update
		this.do_update(boardAttr);
		
		LOG.debug("======================================");
		LOG.debug("=05. update() Data 조회/비교=");
		LOG.debug("======================================");
		BoardAttr vsData = this.get_selectOne(boardAttr);
		
		checkData(boardAttr, vsData);
		
	}
	
	/**수정*/
	public void do_update(BoardAttr inVO) throws Exception{
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_update.do")
				.param("boardId", inVO.getBoardId())
				.param("title", inVO.getTitle())
				.param("contents", inVO.getContents())
				.param("fileId", inVO.getFileId())
				.param("regId", inVO.getRegId());
		
		//url 호출 , 결과 return
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result=" + result);
		LOG.debug("=====================================");
	}
	
	@Test
	public void addAndGet() throws Exception {
		LOG.debug("=======================================");
		LOG.debug("=01. addAndGet() 기존 데이터 삭제=");
		LOG.debug("=======================================");
		Search search = new Search();
		search.setSearchWord("_134");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		for(BoardAttr vo: getIdList) {
			do_delete(vo);
		}
		
		LOG.debug("=======================================");
		LOG.debug("=02. addAndGet() 데이터 입력=");
		LOG.debug("=======================================");
		for(BoardAttr vo: list) {
			do_save(vo);
		}
		
		LOG.debug("=======================================");
		LOG.debug("=03. addAndGet() 단건조회=");
		LOG.debug("=======================================");
		getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		for(BoardAttr vo: getIdList) {
			BoardAttr vsVO = get_selectOne(vo);
			checkData(vsVO,vo);
		}
	}
	
	private void checkData(BoardAttr org, BoardAttr vs) {
		assertThat(org.getTitle(), is(vs.getTitle()));
		assertThat(org.getContents(), is(vs.getContents()));
		assertThat(org.getRegId(), is(vs.getRegId()));
		assertThat(org.getReadCnt(), is(vs.getReadCnt()));
	}
	
	private BoardAttr get_selectOne(BoardAttr vo) throws Exception {
		//uri, param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/do_selectOne.do")
				.param("boardId", vo.getBoardId());
		
		//url call 결과 return
		MvcResult result = mockMvc.perform(createMessage)
				                     .andExpect(status().isOk())
				                     .andReturn();
		
		ModelAndView modelAndView = result.getModelAndView();
		
		BoardAttr outVO = (BoardAttr) modelAndView.getModel().get("vo");
		
		
		LOG.debug("===============================");
		LOG.debug("=outVO="+outVO);
		LOG.debug("===============================");
		
		return outVO;
		
	}
	
	
	public void do_save(BoardAttr vo) throws Exception {
		//uri, param, post, get
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_save.do")
				.param("title", vo.getTitle()) //param 연결
				.param("contents", vo.getContents())
				.param("file_id", vo.getFileId())
				.param("reg_id", vo.getRegId());
		
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
	
	private void do_delete(BoardAttr board) throws Exception {
		//uri, param, post, get
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_delete.do")
				.param("boardId", board.getBoardId()); //param연결
		
		//url 호출 , 결과 return
		ResultActions resultActions = mockMvc.perform(createMessage);
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result=" + result);
		LOG.debug("=====================================");
	}
	
	/**엑셀 다운
	 * @throws Exception */
	@Test
	@Ignore
	public void excelDown() throws Exception {
		//uri, param, post, get
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/do_exceldown.do")
				.param("searchDiv", "10")
				.param("searchWord", "_134")
				.param("pageSize", "10")
				.param("pageNum", "1")
				.param("ext", "xls");
		
		//url 호출 , 결과 return
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(status().isOk());
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result=" + result);
		LOG.debug("=====================================");
	}
	
	/**목록조회
	 * @throws Exception */
		private List<BoardAttr> get_retrieve(Search inVO) throws Exception {
		//uri, param, post, get
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/board_attr/get_retrieve.do")
				.param("searchDiv", inVO.getSearchDiv())
				.param("searchWord", inVO.getSearchWord())
				.param("pageSize", String.valueOf(inVO.getPageSize()))
				.param("pageNum", String.valueOf(inVO.getPageNum()));
		
		//url 호출 , 결과 return
		MvcResult result = mockMvc.perform(createMessage)
				.andExpect(status().isOk())
				.andReturn();
		
		ModelAndView modelAndView = result.getModelAndView();
		List<BoardAttr> list =  (List<BoardAttr>) modelAndView.getModel().get("list");
		
//		String result = resultActions.andDo(print())
//				.andReturn()
//				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result=" + result);
		LOG.debug("=====================================");
		
		return list;
	}
	
	
	/**삭제
	 * @throws Exception */
	@Test
	@Ignore
	public void do_delete() throws Exception {
		//uri, param
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.post("/board_attr/do_delete.do")
				.param("boardId", "999");
		
		ResultActions resultActions = mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.msgId", is("1")));
		
		String result = resultActions.andDo(print())
				.andReturn()
				.getResponse().getContentAsString();
		
		LOG.debug("=====================================");
		LOG.debug("=result=" + result);
		LOG.debug("=====================================");
	}
	
	//junit의 테스트 메소드 작성방법
	//@Test public void 메소드이름(파라메터 사용금지){ }
	@Test
	public void getBean() {
		LOG.debug("======getBean()==============");
		LOG.debug("=context="+context);
		LOG.debug("=mockMvc="+mockMvc);
		LOG.debug("====================");
		assertThat(context,  is(notNullValue()));
		assertThat(mockMvc,  is(notNullValue()));
	}
	
	@After
	public void tearDown() {
	
	}
	
}


