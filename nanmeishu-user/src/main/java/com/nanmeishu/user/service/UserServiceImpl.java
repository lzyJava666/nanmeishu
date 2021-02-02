package com.nanmeishu.user.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanmeishu.user.constant.AllConstant;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.mapper.UserMapper;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.HttpClientUtil;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Value("${ProgressBars}")
    private String progressBars;

    @Override
    public void register(User user) {
        //判断用户是否合法
        List<User> userVerify = userMapper.selectList(new QueryWrapper<User>().eq("username", user.getUsername()).or().eq("phone", user.getPhone()));
        if (userVerify != null && userVerify.size() > 0) {
            throw new RuntimeException("用户名/手机号已被使用，请重试");
        }
        //注册
        int i = userMapper.insert(user);
        if (i <= 0) {
            throw new RuntimeException("注册失败！请检查数据合理性！");
        }
    }

    @Override
    public void updateUser(User user) {
        //判断修改的信息是否合法
        //判断手机号是否重复
        if (user.getPhone() != null) {
            User ifUserByPhone = userMapper.selectOne(new QueryWrapper<User>().eq("phone", user.getPhone()));
            if (ifUserByPhone != null) {
                if (user.getUserId() != ifUserByPhone.getUserId())
                    throw new RuntimeException("修改的手机号码已存在！");
            }
        }
        //判断身份证是否重复
        if (user.getIdentityCard() != null) {
            User ifUserByIdentityCard = userMapper.selectOne(new QueryWrapper<User>().eq("identity_card", user.getIdentityCard()));
            if (ifUserByIdentityCard != null) {
                if (user.getUserId() != ifUserByIdentityCard.getUserId())
                    throw new RuntimeException("修改的身份证号已存在！");
            }
        }
        //修改
        int u = userMapper.updateById(user);
        if (u <= 0) {
            throw new RuntimeException("修改出错");
        }
    }

    @Override
    public void updatePas(String userId, Map<String, Object> reqMap) {
        //验证用户信息
        //旧密码
        String formerPas = reqMap.get("formerPas").toString();
        //新密码
        String newPas = reqMap.get("newPas").toString();
        //手机号
        String phone = reqMap.get("phone").toString();
        User userVerify = userMapper.selectOne(new QueryWrapper<User>()
                .eq("password", DataUtil.md5Encrypt(formerPas, AllConstant.getPasMd5()))
                .eq("user_id", userId));
        if (userVerify != null && userVerify.getPhone().equals(phone)) {
            //验证成功
            User user = new User();
            user.setUserId(Long.parseLong(userId));
            user.setPassword(DataUtil.md5Encrypt(newPas, AllConstant.getPasMd5()));
            int u = userMapper.updateById(user);
            if (u <= 0) {
                throw new RuntimeException("修改出错");
            }
        } else {
            throw new RuntimeException("验证用户信息出错");
        }
    }

    @Override
    public Map<String, Object> getUserProgressBars(String userId) {
        Map<String, Object> resMap = new HashMap<>();
        User user = userMapper.selectById(userId);
        LocalDate createTime = user.getDateOfBirth();
        LocalDateTime currentLocal = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
        if(createTime==null){
            throw new RuntimeException("请完善出生日期");
        }
        //人生过去的几年
        int gobyYear = currentLocal.toLocalDate().getYear() - createTime.getYear();
        resMap.put("gobyYear", gobyYear);
        //吃过几顿饭
        resMap.put("meal", gobyYear * 366 * 3);
        long until = createTime.until(currentLocal.toLocalDate(), ChronoUnit.DAYS);
        //度过几个夜晚
        resMap.put("day", until);
        //经过几个周末
        resMap.put("weekend", Integer.parseInt(String.valueOf(until / 7)));
        //今天已过去几小时
        resMap.put("currentHour", Integer.parseInt(String.valueOf(currentLocal.getMinute() > 31 ? currentLocal.getHour() + 1 : currentLocal.getHour())));
        //本周已经过去几天
        //本周一
        LocalDate with = currentLocal.toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        resMap.put("currentWeek",  currentLocal.getDayOfWeek().getValue() );
        //本月过去几天
        resMap.put("currentMonth",  currentLocal.getDayOfMonth());
        //今天已过去几天
        resMap.put("currentYear",  currentLocal.getDayOfYear());
        //获取今日份鸡汤
        String[] sentence = getSentence();
        resMap.put("sentence", sentence);
            System.out.println(currentLocal+"===============================================");
        return resMap;
    }

    //获取今日份鸡汤
    private String[] getSentence() {
        String[] sentence = new String[2];
        String get = HttpClientUtil.Get(progressBars);
        //Map map = JSON.parseObject(JSON.parseObject(get, Map.class).get("data").toString(), Map.class);
        while (true) {
            System.out.println(get);
            if (get.length() > 25) {
                get = HttpClientUtil.Get(progressBars);
                //map = JSON.parseObject(JSON.parseObject(get, Map.class).get("data").toString(), Map.class);
            }else {
                break;
            }
        }
        sentence[0] = get;
        sentence[1] = "每日一言";
        return sentence;
    }
}
