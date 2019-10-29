package kr.co.ehr.file.service;

import java.util.List;

import kr.co.ehr.cmn.DTO;

public interface FileService {
	
	/**파일 Num 자동증가*/
	public int num_max_plus_one(DTO dto);
	
	/**파일 개수*/
	public int get_file_count(DTO dto);
	
	/**삭제*/
	public int do_delete(DTO dto);
	
	/**등록*/
	public int do_save(DTO dto);
	
	/**목록 조회*/
	public List<?> get_retrieve(DTO dto);
}
