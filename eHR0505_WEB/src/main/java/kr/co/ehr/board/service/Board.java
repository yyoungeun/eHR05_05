package kr.co.ehr.board.service;

import kr.co.ehr.cmn.DTO;

public class Board extends DTO {
	
	private String boardId;		//	게시글_순번
	private String title;		//	제목
	private int readCnt;		//	조회수
	private String contents;	//	내용
	private String regId;		//	등록자ID
	private String regDt;		//	등록일
	
	public Board() {}

	public Board(String boardId, String title, int readCnt, String contents, String regId, String regDt) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.readCnt = readCnt;
		this.contents = contents;
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

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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
		return "Board [boardId=" + boardId + ", title=" + title + ", readCnt=" + readCnt + ", contents=" + contents
				+ ", regId=" + regId + ", regDt=" + regDt + ", toString()=" + super.toString() + "]";
	}
	
}
