package kr.co.ehr.file.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.WorkDiv;
import kr.co.ehr.file.service.File;
import kr.co.ehr.user.service.Search;

@Repository
public class FileDaoImpl implements WorkDiv {
	Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private final String NAMESPACE = "kr.co.ehr.file";
	
	/**
	 * get_file_count
	 * @param dto
	 * @return int
	 */
	public int get_file_count(DTO dto) {
		String statement = NAMESPACE+".get_file_count";
		File inVO = (File) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + inVO);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int num = this.sqlSessionTemplate.selectOne(statement, inVO);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + num);
		LOG.debug("==================================");
		
		return num;
	}

	/**
	 * 원본파일명(like)으로 데이터 조회
	 * @param dto
	 * @return List<File>
	 */
	public List<?> get_fileIdOrgFileNmList(DTO dto){
		String statement = this.NAMESPACE +".get_fileIdOrgFileNmList";
		Search inVO = (Search) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " +inVO);
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		List<File> list = this.sqlSessionTemplate.selectList(statement, inVO);
		
		LOG.debug("==================================");
		LOG.debug("3. list: " + list);
		LOG.debug("==================================");
		
		return list;
	}
	
	
	/**
	 * num = max(num) + 1
	 * @param dto
	 * @return int 
	 */
	public int num_max_plus_one(DTO dto) {
		String statement = NAMESPACE+".num_max_plus_one";
		File inVO = (File) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " + inVO);
		LOG.debug("==================================");
		
		LOG.debug("==================================");
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		int num = this.sqlSessionTemplate.selectOne(statement, inVO);
		LOG.debug("==================================");
		LOG.debug("3. flag: " + num);
		LOG.debug("==================================");
		
		return num;
	}
	
	public int do_deleteFileId(DTO dto) {
		String statement = NAMESPACE+".do_delete_file_id";
		File inVO = (File) dto;
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
	public int do_update(DTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int do_delete(DTO dto) {
		String statement = NAMESPACE+".do_delete";
		File inVO = (File) dto;
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
		File inVO = (File) dto;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> get_retrieve(DTO dto) {
		String statement = this.NAMESPACE +".get_fileIdList";
		File inVO = (File) dto;
		LOG.debug("==================================");
		LOG.debug("1. param: " +inVO);
		LOG.debug("2. statement: " + statement);
		LOG.debug("==================================");
		
		List<File> list = this.sqlSessionTemplate.selectList(statement, inVO);
		
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
