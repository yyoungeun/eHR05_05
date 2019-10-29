package kr.co.ehr.file.web;

import static kr.co.ehr.cmn.StringUtil.UPLOAD_ROOT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import kr.co.ehr.cmn.Message;
import kr.co.ehr.cmn.StringUtil;
import kr.co.ehr.file.service.FileService;
import kr.co.ehr.file.service.FileVO;

@Controller
public class FileController {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileService fileService;
	
	//팝업 화면
	private final String VIEW_LIST_NM = "file/file_list";
	private final String VIEW_MNG_NM = "file/file_mng";
	
	@RequestMapping(value="file/do_retrieve.do", method= RequestMethod.POST, produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public String get_fileList(kr.co.ehr.file.service.File inVO) {
		LOG.debug("===============================");
		LOG.debug("=inVO==========================");
		LOG.debug("===============================");
		
		List<kr.co.ehr.file.service.File> fileList = (List<kr.co.ehr.file.service.File>) fileService.get_retrieve(inVO);
		LOG.debug("===============================");
		LOG.debug("=fileList======================");
		LOG.debug("===============================");
		
		Gson gson = new Gson();
		String json = gson.toJson(fileList);
		LOG.debug("===============================");
		LOG.debug("=json==========================");
		LOG.debug("===============================");
		
		return json;
	}
	
	@RequestMapping(value="file/do_save.do", method= RequestMethod.POST, produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public String do_save(MultipartHttpServletRequest mReg) throws IllegalStateException, IOException {
		
		LOG.debug("===================================");
		LOG.debug("=@Controller do_save=");
		LOG.debug("===================================");
		//Upload파일 정보: 원본, 저장, 사이즈, 확장자 List
		List<kr.co.ehr.file.service.File> fileList = new ArrayList<kr.co.ehr.file.service.File>();
		
		String workDiv = StringUtil.nvl(mReg.getParameter("work_div"));
		String fileId = StringUtil.nvl(mReg.getParameter("attrFileId"));
		
		
		LOG.debug("=Controller workDiv" + workDiv);
		LOG.debug("=Controller fileId" + fileId);
		
		//----------------------------------------------------------------
		//--예외처리
		//----------------------------------------------------------------
//		if(workDiv.equals("")) {
//			throw new IllegalArgumentException("작업구분은 필수 값입니다.");
//		}
		
		
		//01. 동적으로 UPLOAD_ROOT 디렉토리 생성
		File fileRootDir = new File(UPLOAD_ROOT);
		if(fileRootDir.isDirectory() == false) {
			boolean flag = fileRootDir.mkdirs();
			LOG.debug("=@Controller flag="+flag);
		}
		
		//02.년월 디렉토리 생성
		String yyyy = StringUtil.cureDate("yyyy");
		LOG.debug("=@Controller yyyy="+ yyyy);
		
		String mm = StringUtil.cureDate("MM");
		LOG.debug("=@Controller MM="+ mm);
		
		String dataPath = UPLOAD_ROOT+File.separator+yyyy+File.separator+mm;
		LOG.debug("=@Controller dataPath="+ dataPath);
		
		File fileYearMM = new File(dataPath);
		
		if(fileYearMM.isDirectory()==false) {
			boolean flag = fileYearMM.mkdirs();
			LOG.debug("=@Controller fileYearMM flag ="+ flag);
		}
		
		int flag = 0;
		Message message=new Message();
		//01.파일Read
		Iterator<String> files = mReg.getFileNames();
		while(files.hasNext()) {
			kr.co.ehr.file.service.File fileVO = new kr.co.ehr.file.service.File();
			String orgFileNm = ""; //원본파일명
			String saveFileNm = ""; //저장파일명
			long fileSize = 0L; //파일 사이즈
			String ext = ""; //확장자
			
			String uploadFileNm = files.next(); //file01
			MultipartFile mFile = mReg.getFile(uploadFileNm);
			orgFileNm = mFile.getOriginalFilename();
			//file선택이 안되면 continue
			if(null == orgFileNm || orgFileNm.equals("")) continue;
			
			LOG.debug("=@Controller uploadFileNm ="+ uploadFileNm);
			LOG.debug("=@Controller orgFileNm ="+ orgFileNm);
			fileSize = mFile.getSize(); //file size byte
			
			if(orgFileNm.indexOf(".")>-1) {
				ext = orgFileNm.substring(orgFileNm.indexOf(".")+1);
			}
			LOG.debug("=@Controller fileSize ="+ fileSize);
			LOG.debug("=@Controller ext ="+ ext);
			File orgFileCheck = new File(dataPath,orgFileNm);
			
			String newFile = orgFileCheck.getAbsolutePath();
			if(orgFileCheck.exists() == true) {
				newFile = StringUtil.fileRename(orgFileCheck);
			}
			
			//-----------------------------------------------------------
			//-FileId 존재 유무로 Key생성 유무 판단
			//-----------------------------------------------------------
			
			//FileId 없는 경우
			if(fileId.equals("0") || fileId.length() != 40 ) {
				String yyyymmdd = StringUtil.cureDate("yyyyMMdd");
				String fileIdKey = yyyymmdd + StringUtil.getUUID();
				LOG.debug("yyyymmdd: " + yyyymmdd);
				LOG.debug("fileIdKey: " + fileIdKey);
				fileVO.setFileId(fileIdKey);
				fileVO.setNum(1);
				fileId = fileIdKey;
				//fileId가 있는 경우.
			}else {
				fileVO.setFileId(fileId);
				//max num
				int maxNum = this.fileService.num_max_plus_one(fileVO);
				LOG.debug("maxNum: " + maxNum);
				fileVO.setNum(maxNum);
			}
			
			
			fileVO.setOrgFileNm(orgFileNm);
			fileVO.setSaveFileNm(newFile);
			fileVO.setfSize(fileSize);
			fileVO.setExt(ext);
			fileList.add(fileVO);
			mFile.transferTo(new File(newFile));
			
			flag = fileService.do_save(fileVO);
			LOG.debug("flag: " + flag);
			
		}
		//등록성공
		if(flag>0) {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg(fileId);
		//등록실패	
		}else {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("등록실패.");	
		}
		Gson gson=new Gson();
		
		String gsonStr = gson.toJson(message);
		LOG.debug("gsonStr:"+gsonStr);
		
		return gsonStr;
		
	}

}
