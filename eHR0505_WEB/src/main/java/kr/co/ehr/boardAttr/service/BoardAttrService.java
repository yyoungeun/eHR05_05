package kr.co.ehr.boardAttr.service;

import java.util.List;

import kr.co.ehr.cmn.DTO;
import kr.co.ehr.user.service.Search;

public interface BoardAttrService {

	/**엑셀 다운*/
	public String excelDown(Search vo, String ext);
	
	/**수정*/
	public int do_update(DTO dto);
	
	/**트랜잭션 처리: board_attr, file 테이블 양쪽 삭제 */
	public int tx_do_delete(DTO dto);
	
	/**삭제*/
	public int do_delete(DTO dto);
	
	/**저장*/
	public int do_save(DTO dto);
	
	/**단건조회: 조회Count증가+ */
	public DTO get_selectOne(DTO dto);
	
	/**목록조회*/
	public List<?> get_retrieve(DTO dto);
}
