package kr.co.ehr.board.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.board.service.BoardService;
import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.ExcelWriter;
import kr.co.ehr.user.service.Search;
import kr.co.ehr.user.service.User;

@Service
public class BoardServiceImpl implements BoardService {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private ExcelWriter excelWriter;

	@Autowired
	private BoardDaoImpl boardDaoImpl; // 인터페이스 통해 만들어야함

	@Override
	public int do_update(DTO dto) {
		
		return boardDaoImpl.do_update(dto);
	}

	@Override
	public int do_delete(DTO dto) {
		
		return boardDaoImpl.do_delete(dto);
	}

	@Override
	public int do_save(DTO dto) {
		
		return boardDaoImpl.do_save(dto);
	}

	@Override
	public DTO get_selectOne(DTO dto) {
		//조회 Count 증가
		Board outVO = (Board) boardDaoImpl.get_selectOne(dto);
		if(null != outVO) {
			boardDaoImpl.do_updateReadCnt(dto);
		}
		return outVO;
	}

	@Override
	public List<?> get_retrieve(DTO dto) {
		List<Board> list = (List<Board>) boardDaoImpl.get_retrieve(dto);
		LOG.debug("1==================================");
		LOG.debug("=s=list="+list);
		LOG.debug("1==================================");
		return list;
	}  

	@Override
	public String excelDown(Search vo, String ext) {
		List<String> headers = Arrays.asList("게시글_순번"
											,"제목"
											,"조회수"
											,"내용"
											,"등록자ID"
											,"등록일"
											);
		
		List<Board> list = (List<Board>) boardDaoImpl.get_retrieve(vo);
		String saveFileNm = "";
		
		if(ext.equalsIgnoreCase("csv")) {
			saveFileNm = excelWriter.csvWriterGeneralization(list, headers);
		}else if(ext.equalsIgnoreCase("xlsx")) {
			saveFileNm = excelWriter.xlsxWriterGeneralization(list, headers);
		}else if(ext.equalsIgnoreCase("xls")) {
			saveFileNm = excelWriter.xlsWriterGeneralization(list, headers);
		}
		return saveFileNm;
	}

}
