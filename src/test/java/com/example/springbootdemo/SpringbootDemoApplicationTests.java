package com.example.springbootdemo;

import com.example.springbootdemo.pojo.UserInfo;
import com.example.springbootdemo.pojo.entity.Org;
import com.example.springbootdemo.service.OrgService;
import com.example.springbootdemo.service.RedisService;
import com.example.springbootdemo.service.UserInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootDemoApplicationTests {

	@Resource
	OrgService orgService;

	@Resource
	UserInfoService userInfoService;

	@Autowired
	RedisService redisService;

	@Test
	void contextLoads() {
	}

	@Test
	void addOrg() {
		Org org = new Org();
		org.setName("chang");
		org.setDescription("this is chang org.");

		Org res = orgService.insert(org);
		System.out.println(res);
	}

	@Test
	void userInfoFindOne() {
		UserInfo uInfo = userInfoService.findOne(2L);
		System.out.println(uInfo);
	}

	@Test
	void userInfoModify() {
		UserInfo uInfo = new UserInfo(1L, "hc666", 1L, "", "");
		UserInfo res = userInfoService.modify(uInfo);
		System.out.println(res);
	}

	@Test
	void test(){
		userInfoService.test(1, 2);

		long expire = redisService.getExpire("springboottest::SimpleKey [1, 2]");
		System.out.println(expire);
	}
}
