package com.feiek.cloud.service.ocr;

import com.feiek.cloud.dao.RoleDao;
import com.feiek.cloud.dao.UserDao;
import com.feiek.cloud.entity.Role;
import com.feiek.cloud.entity.User;
import com.feiek.cloud.service.ocr.huawei.OCRASKUtilHuawei;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OcrApplicationTests {

	@Autowired
	private  OCRASKUtilHuawei ocraskUtilHuawei;


	@Autowired
	private UserDao dao;

	@Autowired
	private RoleDao roleDao;

	@Rollback(false)
	@Transactional
	@Test
	public void contextLoads() {


//		String s = ocraskUtilHuawei.imageToWords("D:\\uploadFile\\20190416154957.png");
//		System.out.println(s);

//		List<User> all = dao.findAll();
//
//		System.out.println(all);
//		System.out.println(all.get(0).getRoles());



		Optional<User> userByName = dao.findOne((root, q, cb) ->
				cb.equal(root.get("name"), "王恒")
		);
		User user = userByName.get();
		System.out.println(userByName);


//		User user = new User();
//		user.setEmail("asa");
//		user.setMobile("123143124");
//		user.setName("asdas");
//		user.setPassword("asdasa");
//
//		Role role = new Role();
//		role.setCode("asdasd");
//		role.setName("asasa");
//		role.setStatu(12);
//
//
//		user.getRoles().add(role);
//
//		role.getUsers().add(user);
//
//		dao.save(user);
//		roleDao.save(role);
	}

}
