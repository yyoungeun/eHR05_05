package kr.co.ehr.boardAttr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Array;
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

import kr.co.ehr.boardAttr.service.BoardAttr;
import kr.co.ehr.boardAttr.service.impl.BoardAttrDaoImpl;
import kr.co.ehr.user.service.Search;

@WebAppConfiguration
//import com.ehr.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml" }) //배열
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Test NAME_ASCENDING으로 수행
public class DaoBoardAttrTest {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private BoardAttrDaoImpl boardAttrDaoImpl;
	
	//Test Data
	List<BoardAttr> list;
	
	@Before
	public void setUp() {
		//BoardAttr(String boardId, String title, String contents, int readCnt, String fileId, String regId, String regDt)
		list = Arrays.asList(
					new BoardAttr("1","J01_ATTR_134제목","J01_ATTR_내용",0,"88","admin","noData")
					,new BoardAttr("2","J02_ATTR_134제목","J02_ATTR_내용",0,"88","admin","noData")
					,new BoardAttr("3","J03_ATTR_134제목","J03_ATTR_내용",0,"88","admin","noData")
					,new BoardAttr("4","J04_ATTR_134제목","J04_ATTR_내용",0,"88","admin","noData")
					,new BoardAttr("5","J05_ATTR_134제목","J05_ATTR_내용",0,"88","admin","noData")
				);
		LOG.debug("#####setUp()");
	}
	
	
	@Test
	public void get_retrieve() {
		//-------------------
		//기존Data삭제
		//-------------------
		Search search=new Search();
		search.setSearchWord("_134");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
				
		for(BoardAttr vo:getIdList) {
			boardAttrDaoImpl.do_delete(vo);
		}
		
		//-------------------
		//등록
		//-------------------
		for(BoardAttr vo:list) {
			int flag = boardAttrDaoImpl.do_save(vo);
			assertThat(1, is(1));
		}
		
		//-------------------
		//등록Data조회
		//-------------------
		search.setSearchDiv("10");
		search.setPageSize(3);
		search.setPageNum(1);
		
		List<BoardAttr> list = (List<BoardAttr>) boardAttrDaoImpl.get_retrieve(search);
		assertThat(3, is(list.size()));
		
	}

	@Test
	@Ignore
	public void do_update() {
		//------------------
		//기존Data삭제
		//------------------
		Search search = new Search();
		search.setSearchWord("_134");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		LOG.debug("==================================");
		LOG.debug("#####do_delete()");
		LOG.debug("==================================");
		for(BoardAttr vo: getIdList) {
			boardAttrDaoImpl.do_delete(vo);
		}
		//------------------
		//등록
		//------------------
		LOG.debug("==================================");
		LOG.debug("#####do_save()");
		LOG.debug("==================================");
		for(BoardAttr vo: list) {
			int flag = boardAttrDaoImpl.do_save(vo);
			assertThat(1, is(1));
		}
		
		//------------------
		//등록 데이터 조회
		//------------------
		getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		assertThat(5, is(getIdList.size()));
		
		//------------------
		//등록 데이터 수정
		//------------------
		BoardAttr boardAttr = getIdList.get(0);
		boardAttr.setTitle(boardAttr.getTitle()+"_U");
		boardAttr.setContents(boardAttr.getContents()+"_U");
		boardAttr.setFileId(boardAttr.getFileId()+"_U");
		boardAttr.setRegId(boardAttr.getRegId()+"_U");
		
		int flag = boardAttrDaoImpl.do_update(boardAttr);
		assertThat(1, is(1));
		
		//------------------
		//등록 데이터 조회
		//------------------
		getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		checkData(boardAttr, getIdList.get(0));
	}
	
	//1.삭제, 2.등록, 3.단건조회
	
	@Test
	@Ignore
	public void addAndGet() {
		//------------------------------
		//1-ID값 찾기: TITLE LIKE
		//------------------------------
		Search search = new Search();
		search.setSearchWord("_134");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		//------------------------------
		//2-삭제
		//------------------------------
		LOG.debug("==================================");
		LOG.debug("#####do_delete()");
		LOG.debug("==================================");
		for(BoardAttr vo: getIdList) {
			boardAttrDaoImpl.do_delete(vo);
		}
		
		//------------------------------
		//3-등록
		//------------------------------
		LOG.debug("==================================");
		LOG.debug("#####do_save()");
		LOG.debug("==================================");
		for(BoardAttr vo: list) {
			int flag = boardAttrDaoImpl.do_save(vo);
			assertThat(1, is(1));
		}
		
		//------------------------------
		//4-등록 Data조회
		//------------------------------
		getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		assertThat(5, is(getIdList.size()));
		
		//------------------------------
		//5-비교
		//------------------------------
		for(int i=0; i<list.size(); i++) {
			checkData(list.get(i), getIdList.get(i));
		}
		
	}
	
	private void checkData(BoardAttr org, BoardAttr vs) {
		assertThat(org.getTitle(), is(vs.getTitle()));
		assertThat(org.getContents(), is(vs.getContents()));
		assertThat(org.getRegId(), is(vs.getRegId()));
		assertThat(org.getReadCnt(), is(vs.getReadCnt()));
	}
	
	@Test
	@Ignore
	public void do_save() {
		//------------------------------
		//-ID값 찾기: TITLE LIKE
		//------------------------------
		Search search = new Search();
		search.setSearchWord("_134");
		List<BoardAttr> getIdList = (List<BoardAttr>) boardAttrDaoImpl.get_boardIdList(search);
		
		
		LOG.debug("==================================");
		LOG.debug("#####do_delete()");
		LOG.debug("==================================");
		for(BoardAttr vo: getIdList) {
			boardAttrDaoImpl.do_delete(vo);
		}
		
		LOG.debug("==================================");
		LOG.debug("#####do_save()");
		LOG.debug("==================================");
		for(BoardAttr vo: list) {
			int flag = boardAttrDaoImpl.do_save(vo);
			assertThat(1, is(1));
		}
	}
	
	
	@Test
	@Ignore
	public void do_delete() {
		LOG.debug("==================================");
		LOG.debug("#####do_delete()");
		LOG.debug("==================================");
		for(BoardAttr vo: list) {
			boardAttrDaoImpl.do_delete(vo);
		}
	}
	
	@Test
	public void getBean() {
		LOG.debug("==========================");
		LOG.debug("=context="+context);
		LOG.debug("=boardAttrDaoImpl="+boardAttrDaoImpl);
		LOG.debug("==========================");
		
		assertThat(context, is(notNullValue()));
		assertThat(boardAttrDaoImpl, is(notNullValue()));
	}
	
	@After
	public void tearDown() {
		LOG.debug("==================================");
		LOG.debug("#####tearDown()");
		LOG.debug("==================================");
		
	}

}
