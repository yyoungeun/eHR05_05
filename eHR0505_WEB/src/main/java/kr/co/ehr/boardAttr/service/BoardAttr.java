package kr.co.ehr.boardAttr.service;

import kr.co.ehr.cmn.DTO;

public class BoardAttr extends DTO {
	
		/**게시글_순번	*/
		private String boardId  ;
		/**제목	*/
		private String title	;
		/**내용	*/
		private String contents ;
		/**조회수	*/
		private int     readCnt ;
		/**파일ID*/
		private String fileId	;
		/**등록자ID*/
		private String regId	;
		/**등록일	*/
		private String regDt	;
	
	public BoardAttr() {}

	public BoardAttr(String boardId, String title, String contents, int readCnt, String fileId, String regId, String regDt) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.contents = contents;
		this.readCnt = readCnt;
		this.fileId = fileId;
		this.regId = regId;
		this.regDt = regDt;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	@Override
	public String toString() {
		return "BoardAttr [boardId=" + boardId + ", title=" + title + ", contents=" + contents + ", readCnt=" + readCnt
				+ ", fileId=" + fileId + ", regId=" + regId + ", regDt=" + regDt + ", toString()=" + super.toString()
				+ "]";
	}
	
}
