package com.biz.sec.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.sec.domain.UserVO;
import com.biz.sec.persistence.UserDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
	
	//private final로 선언하면
	//@Autowired 또는 @RequiredArgsConstructor를 이용해 bean에 설정된 id와 객체이름을 똑같이 설정하여 bean을 불러올 수 있다
	//@Autowired
	private final PasswordEncoder pwEncoder;
	private final UserDao userDao;
	
	/**
	 * @since 2020-04-09
	 * @author 나
	 * 
	 * @param username
	 * @param password
	 * @return
	 * 
	 * 회원가입 시 비밀번호를 암호화한 뒤 DB에 insert하기
	 * 
	 * @update 2020-04-10
	 * Map<String, String> 타입의 VO 데이터를
	 * UserVO 타입으로 변경
	 */
	public int insert(String username, String password) {
		
		// 회원가입 form에서 전달받은 password 값을 암호화 시키는 과정
		String encPW = pwEncoder.encode(password);
		log.debug("암호화된 비밀번호 : " + encPW);
		
		UserVO userVO = UserVO.builder()
							.username(username)
							.password(encPW)
							.build();
		
		int ret = userDao.insert(userVO);
		
		return ret;
	}

	public UserVO findByUserName(String username) {
		return userDao.findByUserName(username);
		
	}

}
