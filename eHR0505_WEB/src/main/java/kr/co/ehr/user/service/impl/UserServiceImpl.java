package kr.co.ehr.user.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.ExcelWriter;
import kr.co.ehr.user.service.Level;
import kr.co.ehr.user.service.Search;
import kr.co.ehr.user.service.User;
import kr.co.ehr.user.service.UserDao;
import kr.co.ehr.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private ExcelWriter excelWriter;

	@Autowired
	private MailSender mailSender;

	@Autowired
	private UserDaoImpl userDao; // 인터페이스 통해 만들어야함
	
	public static final int MIN_LOGINCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;

	// 최초 사용자 베이직 레벨
	public int do_save(DTO dto) {
		User user = (User) dto;
		if (null == user.gethLevel()) {
			user.sethLevel(Level.BASIC);
		}
		return userDao.do_save(user);
	}

	protected void upgradeLevel(User user) throws SQLException {
		//----------------------------------
		//-Transaction TestSource: 운영반영금지
		//----------------------------------
//		String id="j04_134";
//		if(user.getU_id().equals(id)) {
//			throw new RuntimeException(id+"트랜잭션 HR테스트");
//		}
		
		user.upgradeLevel(); // VO부분에 기능을 만듦
		userDao.do_update(user);

		sendUpgradeMail(user);// mail send

	}


	/**
	 * 등업 사용자에게 mail 전송.
	 * 
	 * @param user
	 */
	private void sendUpgradeMail(User user) {
		try {
			// POP 서버명 : pop.naver.com
			// SMTP 서버명 : smtp.naver.com
			// POP 포트 : 995, 보안연결(SSL) 필요
			// SMTP 포트 : 465, 보안 연결(SSL) 필요
			// 아이디 : jamesol
			// 비밀번호 : 네이버 로그인 비밀번호
			// 보내는 사람
			String host = "smtp.naver.com";
			final String userName = "1150amy";
			final String password = "@song7014";
			int port = 465;

			// 받는사람
			String recipient = user.getEmail();
			// 제목
			String title = user.getName() + "님 등업(https://cafe.naver.com/kndjang)";
			// 내용
			String contents = user.getU_id() + "님의 등급이\n" + user.gethLevel().name() + "로 업되었습니다.";
			// SMTP서버 설정
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", host);

			// 인증
			Session session = Session.getInstance(props, new Authenticator() {
				String uName = userName;
				String passwd = password;

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// TODO Auto-generated method stub
					return new PasswordAuthentication(uName, passwd);
				}
			});

			session.setDebug(true);

//			Message mimeMessage=new MimeMessage(session);
//			//보내는 사람
//			mimeMessage.setFrom(new InternetAddress("jamesol@naver.com"));
//			//받는사람
//			mimeMessage.setRecipient(Message.RecipientType.TO
//					                         , new InternetAddress(recipient));
//			//제목
//			mimeMessage.setSubject(title);
//			//내용
//			mimeMessage.setText(contents);
//			
//			//전송
//			Transport.send(mimeMessage);
			SimpleMailMessage mimeMessage = new SimpleMailMessage();
			// 보내는 사람
			mimeMessage.setFrom("1150amy@naver.com");
			// 받는사람
			mimeMessage.setTo(recipient);
			// 제목
			mimeMessage.setSubject(title);
			// 내용
			mimeMessage.setText(contents);
			// 전송
			mailSender.send(mimeMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.debug("==============================");
		LOG.debug("=mail send=");
		LOG.debug("==============================");
	}

	// level upgrade
	// 1. 전체 사용자를 조회
	// 2. 대상자를 선별
	// 2.1. BASIC사용자, 로그인cnt가 50이상이면 : BASIC -> SILVER
	// 2.2. SILVER사용자, 추천cnt가 300이상이면 : SILVER -> GOLD
	// 3. 대상자 업그레이드
	public void tx_upgradeLevels() throws SQLException {

		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user) == true) {
				upgradeLevel(user);

			}
		} // --for

	}

	// 업그레이드 대상여부 파악 : true
	private boolean canUpgradeLevel(User user) {
		Level currLevel = user.gethLevel();

		switch (currLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGINCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknown Level:" + currLevel);
		}

	}

	@Override
	public int do_update(DTO dto) {
		return userDao.do_update(dto);
	}

	@Override
	public List<User> get_retrieve(DTO vo) {
		return (List<User>) userDao.get_retrieve(vo);
	}

	@Override
	public int do_delete(DTO dto) {
		return userDao.do_delete(dto);
	}

	@Override
	public User get_selectOne(DTO dto) {
		return (User) userDao.get_selectOne(dto);
	}
	
	@Override
	public String excelDown(Search vo, String ext) {
		List<String> headers = Arrays.asList("아이디"
											,"이름"
											,"비번"
											,"레벨"
											,"로그인"
											,"추천"
											,"이메일"
											,"등록일"
											,"레벨값"
											);
		
		List<User> list = (List<User>) userDao.get_retrieve(vo);
		String saveFileNm = "";
		
		if(ext.equalsIgnoreCase("csv")) {
			saveFileNm = excelWriter.csvWriterGeneralization(list, headers);
		}else if(ext.equalsIgnoreCase("xlsx")) {
			saveFileNm = excelWriter.xlsxWriterGeneralization(list, headers);
		}
		return saveFileNm;
	}

	@Override
	public DTO idPassCheck(DTO dto) {
		kr.co.ehr.cmn.Message outMsg = new kr.co.ehr.cmn.Message();
		//--------------------------------------------
		//1. Id체크
		//--------------------------------------------
		int flag = userDao.id_check(dto);
		if(flag<1) {
			outMsg.setMsgId("10");
			outMsg.setMsgMsg("아이디를 확인하세요.");
			return outMsg;
		}
		
		//--------------------------------------------
		//2. 비번체크
		//--------------------------------------------
		flag = userDao.passwd_check(dto);
		if(flag<1) {
			outMsg.setMsgId("20");
			outMsg.setMsgMsg("비번을 확인하세요.");
			return outMsg;
		}
		
		if(flag == 1) {
			outMsg.setMsgId("30");
		}
		
		LOG.debug("=========================================");
		LOG.debug("=outMsg=" + outMsg);
		LOG.debug("=========================================");
		
		return outMsg;
	}
}
