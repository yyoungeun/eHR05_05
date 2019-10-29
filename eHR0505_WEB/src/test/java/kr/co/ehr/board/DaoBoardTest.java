package kr.co.ehr.board;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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

import kr.co.ehr.board.service.Board;
import kr.co.ehr.board.service.impl.BoardDaoImpl;
import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.impl.CodeDaoImpl;
import kr.co.ehr.user.service.Search;

@WebAppConfiguration
//import com.ehr.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml" }) //배열
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Test NAME_ASCENDING으로 수행
public class DaoBoardTest {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private BoardDaoImpl boardDaoImpl;
	
	@Autowired
	private CodeDaoImpl codeDaoImpl;
	
	List<Board> list;
	
	@Before
	public void setUp() {
		LOG.debug("setup()");
		//public Board(String boardId, String title, int readCnt, String contents, String regId, String regDt)
		list = Arrays.asList(
				new Board("noBoatdId","J01제목_134",0,"J01내용_134","admin","no_date"),
				new Board("noBoatdId","J02제목_134",0,"J02내용_134","admin","no_date"),
				new Board("noBoatdId","J03제목_134",0,"J03내용_134","admin","no_date"),
				new Board("noBoatdId","J04제목_134",0,"J04내용_134","admin","no_date"),
				new Board("noBoatdId","J05제목_134",0,"J05내용_134","admin","no_date")
				);
		
	}
	
	@Test
	public void get_retrieve() {
		LOG.debug("get_retrieve");
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		
		Search search = new Search();
		search.setSearchWord("_134");
		List<Board> getList = (List<Board>) boardDaoImpl.get_boardIdList(search);
		for(Board vo: getList) {
			boardDaoImpl.do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 데이터 추가=");
		LOG.debug("======================================");
		
		for(Board vo:list) {
			int flag = boardDaoImpl.do_save(vo);
			assertThat(1, is(flag));
		}
		
		//=======================================
		//2. 등록 Date조회
		//=======================================
		search.setSearchDiv("10");
		search.setPageSize(10);
		search.setPageNum(1);
		List<Board> addlistData = (List<Board>) boardDaoImpl.get_retrieve(search);
		assertThat(5, is(addlistData.size()));
		
	}
	
	@Test
	@Ignore
	public void update() {
		LOG.debug("update()");
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		
		Search search = new Search();
		search.setSearchWord("_134");
		List<Board> getList = (List<Board>) boardDaoImpl.get_boardIdList(search); //조회
		for(Board vo: getList) {
			boardDaoImpl.do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 데이터 추가=");
		LOG.debug("======================================");
		
		for(Board vo:list) {
			int flag = boardDaoImpl.do_save(vo);
			assertThat(1, is(flag));
		}
		
		//=======================================
		//2.01 등록 Date조회
		//=======================================
		
		List<Board> addlistData = (List<Board>) boardDaoImpl.get_boardIdList(search);
		assertThat(5, is(addlistData.size()));
		
		LOG.debug("======================================");
		LOG.debug("=03. 데이터 수정=");
		LOG.debug("======================================");
		
		Board changeData = addlistData.get(0);
		changeData.setTitle(changeData.getTitle()+"_U");
		changeData.setContents(changeData.getContents()+"_U");
		changeData.setRegId(changeData.getRegId()+"_U");
		
		LOG.debug("======================================");
		LOG.debug("=03.01 데이터 수정데이터 등록=");
		LOG.debug("======================================");
		
		int flag = boardDaoImpl.do_update(changeData);
		assertThat(1, is(flag));
		
		LOG.debug("======================================");
		LOG.debug("=04 등록 DATA 조회=");
		LOG.debug("======================================");
		
		addlistData = (List<Board>) boardDaoImpl.get_boardIdList(search);
		
		LOG.debug("======================================");
		LOG.debug("=05 비교=");
		LOG.debug("======================================");
		this.checkData(changeData, addlistData.get(0));
	}
	
	@Test
	@Ignore
	public void addAndGet() {
		LOG.debug("addAndGet()");
		//=======================================
		//0. 기존 date삭제
		//=======================================
		Search search = new Search();
		search.setSearchWord("_134");
		List<Board> getList = (List<Board>) boardDaoImpl.get_boardIdList(search);
		for(Board vo: getList) {
			boardDaoImpl.do_delete(vo);
		}
		
		//=======================================
		//1. Date등록
		//=======================================
		for(Board vo:list) {
			int flag = boardDaoImpl.do_save(vo);
			assertThat(1, is(flag));
		}
		
		//=======================================
		//2. 등록 Date조회
		//=======================================
		
		List<Board> addlistData = (List<Board>) boardDaoImpl.get_boardIdList(search);
		assertThat(5, is(addlistData.size()));
		
		//=======================================
		//3. 비교:boardId는 자동증가로 비교할 수 없음(제목, 내용, 둥록자)
		//=======================================
		for(int i=0; i<list.size(); i++) {
			checkData(addlistData.get(i),list.get(i));
		}
		
	}
	
	private void checkData(Board org, Board vs) {
		LOG.debug("checkData()");
		assertThat(org.getTitle(), is(vs.getTitle()));
		assertThat(org.getContents(), is(vs.getContents()));
		assertThat(org.getRegId(), is(vs.getRegId()));
		
	}
	
	@Test
	public void getBean() {
		LOG.debug("getBean()");
		LOG.debug("==========================");
		LOG.debug("=context="+context);
		LOG.debug("boardDaoImpl=" + boardDaoImpl);
		LOG.debug("==========================");
		assertThat(context, is(notNullValue()));
		assertThat(boardDaoImpl, is(notNullValue()));
		
	}
	
	@After
	public void tearDown() {
		LOG.debug("tearDown()");
	}

}
