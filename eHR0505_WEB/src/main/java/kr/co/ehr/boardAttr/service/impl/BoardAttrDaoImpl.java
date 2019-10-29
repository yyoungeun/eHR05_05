package kr.co.ehr.boardAttr.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.boardAttr.service.BoardAttr;
import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.WorkDiv;
import kr.co.ehr.user.service.Search;


@Repository
public class BoardAttrDaoImpl implements WorkDiv {
	
	Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private final String NAMESPACE = "kr.co.ehr.boardAttr";
	
	/**read count증가 */
	public int do_updateReadCnt(DTO dto) {
		
		String statement = NAMESPACE+".do_updateReadCnt";
		BoardAttr inVO = (BoardAttr) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + inVO);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.update(statement, inVO);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public int do_update(DTO dto) {
		String statement = NAMESPACE+".do_update";
		BoardAttr inVO = (BoardAttr) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + inVO);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.update(statement, inVO);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public int do_delete(DTO dto) {
		String statement = NAMESPACE+".do_delete";
		BoardAttr inVO = (BoardAttr) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + inVO);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.delete(statement, inVO);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public int do_save(DTO dto) {
		String statement = this.NAMESPACE+".do_save";
		BoardAttr inVO = (BoardAttr) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + inVO);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int flag = this.sqlSessionTemplate.insert(statement, inVO);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + flag);
		LOG.debug("==================================");
		
		return flag;
	}

	@Override
	public DTO get_selectOne(DTO dto) {
		String statement = this.NAMESPACE +".get_selectOne";
		BoardAttr inVO = (BoardAttr) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + inVO);
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		BoardAttr outVO = this.sqlSessionTemplate.selectOne(statement, inVO);
		
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
		
		List<BoardAttr> list = this.sqlSessionTemplate.selectList(statement, search);
		
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
		
		List<BoardAttr> list = this.sqlSessionTemplate.selectList(statement, search);
		
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
