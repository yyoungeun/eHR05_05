package kr.co.ehr.file.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import kr.co.ehr.cmn.StringUtil;
import kr.co.ehr.file.service.FileVO;

//@Controller
public class OldFileController {
	
	Logger LOG = LoggerFactory.getLogger(this.getClass()); //객체가 속하는 클래스의 정보를 알안는 메소드이다.
	
	private static final String UPLOAD_ROOT = "D:\\HR_FILE";
	private static final String VIEW_NAME = "file/file";
	
	@Resource(name="downloadView")
	private View download;
	
	@RequestMapping(value="file/download.do", method=RequestMethod.POST)
	public ModelAndView download(HttpServletRequest req, ModelAndView mView) {
		
		String orgFileNm = req.getParameter("orgFileNm");
		String saveFileNm = req.getParameter("saveFileNm");
		LOG.debug("===============================");
		LOG.debug("=@Controller orgFileNm="+orgFileNm);
		LOG.debug("=@Controller saveFileNm="+saveFileNm); 
		LOG.debug("===============================");	
//		File downloadFile = (File) model.get("downloadFile");
//		String orgFileNm = (String) model.get("orgFileNm");
		mView.setView(download);
		
		File downloadFile =new File(saveFileNm);
		mView.addObject("downloadFile", downloadFile);
		mView.addObject("orgFileNm", orgFileNm);
		
		return mView;
	}
	
	@RequestMapping(value="file/uploadfileview.do")
	public String uploadFileView() {
		LOG.debug("===================================");
		LOG.debug("=@Controller uploadFileView=");
		LOG.debug("===================================");
		
		return VIEW_NAME;
	}
	
	@RequestMapping(value="file/do_save.do", method= RequestMethod.POST)
	public ModelAndView do_save(MultipartHttpServletRequest mReg, ModelAndView model) throws IllegalStateException, IOException {
		
		LOG.debug("===================================");
		LOG.debug("=@Controller do_save=");
		LOG.debug("===================================");
		//Upload파일 정보: 원본, 저장, 사이즈, 확장자 List
		List<FileVO> fileList = new ArrayList<FileVO>();
		
		String workDiv = StringUtil.nvl(mReg.getParameter("work_div"));
		//----------------------------------------------------------------
		//--예외처리
		//----------------------------------------------------------------
//		if(workDiv.equals("")) {
//			throw new IllegalArgumentException("작업구분은 필수 값입니다.");
//		}
		
		if(workDiv.equals("")) {
			throw new ArithmeticException("0으로 나눌 수 없습니다.");
		}
		LOG.debug("=@Controller workDiv="+workDiv);
		
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
		
		//01.파일Read
		Iterator<String> files = mReg.getFileNames();
		while(files.hasNext()) {
			FileVO fileVO = new FileVO();
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
			fileVO.setOrgFileNm(orgFileNm);
			fileVO.setSaveFileNm(newFile);
			fileVO.setFileSize(fileSize);
			fileVO.setExt(ext);
			fileList.add(fileVO);
			mFile.transferTo(new File(newFile));
		}
		
		model.addObject("fileList", fileList);
		
		model.setViewName(VIEW_NAME);
		
		return model;
	}

}
