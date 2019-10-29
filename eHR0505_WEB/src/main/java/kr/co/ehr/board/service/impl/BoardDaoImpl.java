package kr.co.ehr.board.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.WorkDiv;
import kr.co.ehr.user.service.Search;

/**
 * 게시판DAO
 * @author sist
 *
 */

@Repository
public class BoardDaoImpl implements WorkDiv {
	Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private final String NAMESPACE = "kr.co.ehr.board"; //상수
	
	/**read count증가 */
	public int do_updateReadCnt(DTO dto) {
		
		String statement = NAMESPACE+".do_updateReadCnt";
		Board board = (Board) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + board);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.update(statement, board);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public int do_update(DTO dto) {
		String statement = NAMESPACE+".do_update";
		Board board = (Board) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + board);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.update(statement, board);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public int do_delete(DTO dto) {
		String statement = NAMESPACE+".do_delete";
		Board board = (Board) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + board);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.delete(statement, board);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public int do_save(DTO dto) {
		String statement = this.NAMESPACE+".do_save";
		Board board = (Board) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + board);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.insert(statement, board);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public DTO get_selectOne(DTO dto) {
		String statement = this.NAMESPACE +".get_selectOne";
		Board board = (Board) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + board);
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		Board outVO = this.sqlSessionTemplate.selectOne(statement, board);
		
		LOG.debug("==================================");
		LOG.debug("3. flag: " + outVO);
		LOG.debug("==================================");
		
		return outVO;
	}
	
	/**Test위해 존재: Like*/
	public List<?> get_boardIdList(DTO dto){
		String statement = this.NAMESPACE +".get_boardIdList";
		Search search = (Search) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " +search);
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		List<Board> list = this.sqlSessionTemplate.selectList(statement, search);
		
		LOG.debug("==================================");
		LOG.debug("3. list: " + list);
		LOG.debug("==================================");
		
		return list;
	}

	@Override
	public List<?> get_retrieve(DTO dto) {
		String statement = this.NAMESPACE +".get_retrieve";
		Search search = (Search) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " +search);
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		List<Board> list = this.sqlSessionTemplate.selectList(statement, search);
		
		LOG.debug("==================================");
		LOG.debug("3. list: " + list);
		LOG.debug("==================================");
		
		return list;
	}

	@Override
	public List<?> get_excelDown(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
