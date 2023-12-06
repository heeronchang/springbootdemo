package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.dao.UserInfoMapper;
import com.example.springbootdemo.pojo.UserInfo;
import com.example.springbootdemo.pojo.entity.Org;
import com.example.springbootdemo.pojo.entity.User;
import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.service.OrgService;
import com.example.springbootdemo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;
    private final UserService userService;
    private final OrgService orgService;

    @Autowired
    public UserInfoServiceImpl(
            UserInfoMapper userInfoMapper,
            UserService jpaUserService,
            OrgService orgService
    ) {
        this.userInfoMapper = userInfoMapper;
        this.userService = jpaUserService;
        this.orgService = orgService;
    }

    // TODO just test transaction
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public UserInfo add(UserInfo userInfo) {
        // transaction
        // add org, add user
        Org org = new Org();
        org.setName(userInfo.getOrg());
        org.setDescription(userInfo.getOrgDescription());
        org = orgService.insert(org);

        User user = new User();
        // user.setId(1L); // 触发异常，回滚
        user.setName(userInfo.getName());
        user.setAccount("heeron");
        user.setPassword("123456");
        user.setOrgId(org.getId());
        user = userService.insert(user);

        userInfo.setOrgId(org.getId());
        userInfo.setId(user.getId());
        return userInfo;
    }

    @Override
    @Cacheable(value = "-userinfo", keyGenerator = "cacheKeyGenerator")
    public UserInfo findOne(Long id) {
        return userInfoMapper.findOne(id);
    }

    // @CachePut
    // @CacheEvict

    @Override
    @Cacheable(value = "test")
    public String test(int i, int j) {
        return "test";
    }
}
