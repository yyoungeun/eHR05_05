package kr.co.ehr.cmn;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.ehr.code.service.Code;


public class StringUtil {
	
	private static Logger LOG = LoggerFactory.getLogger(StringUtil.class);
	
	//File Root 디렉토리
	public static final String UPLOAD_ROOT = "D:\\HR_FILE";
	
	
	/**
	 * D:\\HR_FILE\2019\09
	 */
	public static String dynamicDir() {
		// 01.동적으로 UPLOAD_ROOT 디렉토리 생성
		File fileRootDir = new File(UPLOAD_ROOT);
		if(fileRootDir.isDirectory() == false) {
			boolean flag = fileRootDir.mkdirs();
			LOG.debug("=flag=" + flag);
		}
		
		// 02.년월 디렉토리 생성:D:\\HR_FILE\2019\09
		String yyyy = StringUtil.cureDate("yyyy");
		LOG.debug("=yyyy=" + yyyy);
		String mm  =StringUtil.cureDate("MM");
		LOG.debug("=mm=" + mm);
		String dataPath = UPLOAD_ROOT + File.separator + yyyy + File.separator + mm;
		LOG.debug("=dataPath=" + dataPath);
		
		File fileYearMM = new File(dataPath);
		
		if(fileYearMM.isDirectory() == false) {
			boolean flag = fileYearMM.mkdirs();
			LOG.debug("=fileYearMM flag=" + flag);
		}
		
		return dataPath;
	}
	
	/**
	 * 파일 Rename
	 * @param f
	 * @return 파일 rename명 cloud.jpg -> cloud1~9999.jpg
	 */
	public static String fileRename(File f) {
		String retFileNm = "";
		//01. 파일 존재 Check
		if(!f.exists()) { //파일이 존재하지 않으면
			retFileNm = f.getAbsolutePath(); //절대 경로
			return retFileNm;
		}
		
		//02. 파일 있으면: rename
		//cloud + 확장자
		String name = f.getName(); //cloud.jpg
		String body = "";  //cloud
		String ext = null; //jpg
		int dot = name.lastIndexOf(".");
		if(dot != -1) {
			body = name.substring(0, dot);
			ext = name.substring(dot); //.jpg
		}
		
		//03. 파일 있을 경우 반복문 처리해서 저장
		int count = 0;
		while(f.exists() && count < 99999) {
			count++;
			retFileNm = body + count + ext;
			f = new File(f.getParent(),retFileNm);
		}
		
		return f.getAbsolutePath();
	}
	
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String retUUID = uuid.toString().replace("-", "");
		LOG.debug("===========================");
		LOG.debug("retUUID=\n" + retUUID);
		LOG.debug("retUUID length=\n" + retUUID.length());
		LOG.debug("===========================");
		return retUUID;
	}
	
	/**
	 * yyyy -> 2019
	 * MM -> 10
	 * @param format
	 * @return
	 */
	public static String cureDate(String format) {
		if(null == format || format.equals(""))format="yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		
		return formatter.format(new Date());
	}
    /**
     * 
     * @Method Name  : renderPaing
     * @작성일   : 2019. 7. 24.
     * @작성자   : SIST
     * @변경이력  : 최초작성
     * @Method 설명 :
     * @param maxNum:총글수
     * @param currPageNo: 현재페이지
     * @param rowPerPage:한페이지에 보여질 글수
     * @param bottomCount:바닥에 보여질 페이지수
     * @param url:호출url
     * @param scriptName:호출 javascript	
     * @return
     */
    public static String renderPaing(int maxNum,int currPageNo,int rowPerPage,int bottomCount
    		,String url, String scriptName){
    	/*  총글수 : 21 
    	 *  현재페이지			          1
			총글수			          0
			바닥에 보여질 페이지수			 10 
			한페이지에 보여질 글수			 10 
			호출url			
			호출 javascript
			<< < 1 2 3 4 5 6 7 8 9 10 > >>
			총글수 : 21,1
    	 */
    	int maxPageNo  = ((maxNum-1)/rowPerPage)+1;//총페이지
    	int startPaeNo = ((currPageNo-1)/bottomCount) * bottomCount+1;
    	int endPageNo  = ((currPageNo-1)/bottomCount+1)*bottomCount;
    	int nowBlockNo = ((currPageNo-1)/bottomCount)+1;
    	int maxBlockNo = ((maxNum-1)/bottomCount)+1;
    	
    	int inx  = 0;
    	StringBuilder html=new StringBuilder();
    	if(currPageNo>maxPageNo){
    		return "";
    	}
    	
    	//<table><tr><td></td></tr></table>
    	html.append("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"     >\n");
    	html.append("<tr> \n");
    	html.append("<td align=\"center\"> \n"); 
    	//paging-------------------
    	//<< &laquo;	왼쪽 꺾인 괄호
    	if(nowBlockNo>1 && nowBlockNo <=maxBlockNo){
    		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',1);\"  > ");
    		html.append("&laquo; ");
    		html.append("</a> \n");
    	}
    	
    	//<
    	if(startPaeNo>bottomCount){
    		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',"+(startPaeNo-1)+");\"  > ");
    		html.append("< ");
    		html.append("</a> \n");    		
    	}
    	
    	//1 2  .... 10
    	for(inx = startPaeNo;inx<=maxPageNo && inx<=endPageNo;inx++){
    		if( inx == currPageNo){//현재 page
    			html.append("<b>"+inx+"</b> &nbsp;&nbsp;");
    		}else{
        		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',"+inx+");\"  > ");
        		html.append(inx);
        		html.append("</a>&nbsp;&nbsp; \n");    	    			
    		}
    	}
    	
    	//>
    	if(maxPageNo>=inx){
    		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',"+((nowBlockNo*bottomCount)+1)+");\"  > ");
    		html.append("> ");
    		html.append("</a> \n");     		
    	}
    	
    	//>> &raquo;	오른쪽 꺾인 괄호
    	if(maxPageNo >=inx){
    		html.append("<a  href=\"javascript:"+scriptName+"('"+url+"',"+maxPageNo+");\"  > ");
    		html.append("&raquo; ");
    		html.append("</a> \n");    		
    	}
    	
    	//--paging-----------------
    	html.append("</td> \n");
    	html.append("</tr> \n");
    	html.append("</table>");
    	
    	
    	LOG.debug("===========================");
    	LOG.debug("html.toString()=\n"+html.toString());
    	LOG.debug("===========================");
    	return html.toString();
    }
/**
 * 
 * @Method Name  : makeSelectBox
 * @작성일   : 2019. 7. 22.
 * @작성자   : sist
 * @변경이력  : 최초작성
 * @Method 설명 :
 * @param list
 * @param selecetBoxNm: <select name="lvl" id="lvl">
 * @param selectedNm: <option selected>
 * @param allYN 전체 표시
 * @return : <select name="lvl" id="lvl">
 * 			 <option value="">전체</option>
 * 			 <option value="1" selected>일반사용자</option>
 * 			 <option value="9">관리자</option>
 * 			 </select>
 */

public static String makeSelectBox(List<Code> list, String selectBoxNm, String selectedNm, boolean allYN){
	
		StringBuilder sb = new StringBuilder();
		//<select name="lvl" id="lvl">
		sb.append("<select  class=\"form-control input-sm\" name='" + selectBoxNm + "' id='" + selectBoxNm + "' > \n");
		
		//전체
		if(allYN == true){
			sb.append("<option value=''>전체</option> \n");
			
		}
		//<option value="1" selected>일반사용자</option>
		for(Code dto :list){
			Code vo = dto;
			sb.append("\t<option value='"+vo.getCodeId()+"' ");
			if(selectedNm.equals(vo.getCodeId())){
				sb.append("selected='selected' ");
			}
			sb.append(">");
			sb.append(vo.getCodeNm());
			sb.append("</option> \n");
		}
		
		sb.append("</select> \n");
		LOG.debug("---------------------------------");
		LOG.debug(sb.toString());
		LOG.debug("---------------------------------");
		
	return sb.toString();
}

	public static String nvl(String val) {
		return nvl(val, "");
	}

	public static String nvl(String val, String rep) {
		if (val == null || ("").equals(val)) {
			val = rep;
		}
		return val;
	}

}
