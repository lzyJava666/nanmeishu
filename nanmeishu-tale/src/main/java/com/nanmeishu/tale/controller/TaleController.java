package com.nanmeishu.tale.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.tale.entity.Tale;
import com.nanmeishu.tale.feign.UserFeign;
import com.nanmeishu.tale.service.TaleService;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.web.TokenVerifyAnnotation;
import io.swagger.annotations.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "故事模块接口")
@RequestMapping("/tale")
@RestController
public class TaleController {

    @Autowired
    TaleService taleService;

    @Autowired
    UserFeign userFeign;
    @Value("${caiHongPiText}")
    String caiHongPiText;
    @Value("${caiHongPiImg}")
    String caiHongPiImg;

    @ApiOperation("/新增故事及其明细")
    @PostMapping("/saveTaleAndDetails")
    public ResponseResult saveTaleAndDetails(@RequestBody Tale tale, HttpServletRequest req) {
        saveTaleAndDetailsVerify(tale, req);
        taleService.saveTaleAndDetails(tale);
        return ResultUtil.success();
    }

    //新增故事及其明细---数据有效性验证
    private void saveTaleAndDetailsVerify(Tale tale, HttpServletRequest req) {
        DataUtil.verifyData(tale.getFrontDate(), "故事开始日期/frontDate");
        DataUtil.verifyData(tale.getEndDate(), "故事结束日期/endDate");
        if (tale.getCreateTime() == null) {
            tale.setCreateTime(LocalDateTime.now());
        }
        DataUtil.verifyData(tale.getType(), "故事类型/type");
        DataUtil.verifyData(tale.getTaleDetails().getTaleTitle(), "故事标题/taleTitle");
        if (tale.getTaleDetails().getContent() == null) {
            tale.getTaleDetails().setContent("");
        }
        if (tale.getType() == 1 && !tale.getFrontDate().toString().equals(tale.getEndDate().toString())) {
            throw new RuntimeException("日期的开始日期和结束日期都应该在同一天！");
        }
        if (tale.getUserId() == null) {
            String userId = JwtUtil.get(req.getHeader("accessToken"), "userId");
            ResponseResult userByToken = userFeign.get(userId);
            if (userByToken.getData() == null || userByToken.getData().equals("")) {
                throw new RuntimeException("所属用户必须是合法用户");
            } else {
                tale.setUserId(Long.parseLong(userId));
            }
        }
    }

    @ApiOperation("获取用户故事列表")
    @GetMapping("/listByUserId")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "tales:故事对象", response = Tale.class)
    })
    public ResponseResult listByUserId(@RequestParam("userId") String userId,
                                       @RequestParam("pageNum") String pageNum,
                                       @RequestParam("pageSize") String pageSize) {
        DataUtil.verifyData(userId, "用户id/userId");
        return ResultUtil.success(taleService.listByUserId(userId,pageNum,pageSize));
    }

    @ApiOperation("更新故事信息")
    @TokenVerifyAnnotation
    @PostMapping("/updateTaleAndDetails")
    public ResponseResult updateTaleAndDetails(@RequestBody Tale tale){
        updateTaleAndDetailsVerifyData(tale);
        taleService.updateTaleAndDetails(tale);
        return ResultUtil.success();
    }

    // 更新故事信息--数据有效性验证
    private void updateTaleAndDetailsVerifyData(Tale tale) {
        DataUtil.verifyData(tale.getTaleId(),"故事ID/taleId");
    }


    @TokenVerifyAnnotation
    @ApiOperation("获取用户故事列表通过token")
    @GetMapping("/listByToken")
    public ResponseResult listByToken(HttpServletRequest req,
                                      @RequestParam("pageNum") String pageNum,
                                      @RequestParam("pageSize") String pageSize) {
        String token = req.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        return listByUserId(userId,pageNum,pageSize);
    }

    @ApiOperation("获取彩虹屁")
    @ApiResponses({
            @ApiResponse(code = 200, message = "text:彩虹屁文字，img：彩虹屁url", response = Map.class)
    })
    @GetMapping("/getCaiHongPi")
    public ResponseResult getCaiHongPi() {
        Map<String, String> caihongpiMap = new HashMap<>();
        String caihongpiText = null;
        while (true) {
            caihongpiText = caiHongPiText();
            if (caihongpiText == null) {
                throw new RuntimeException("彩虹屁文字获取失败");
            }
            if (caihongpiText.length() > 20) {
                caihongpiText = caiHongPiText();
            } else {
                break;
            }
        }
        String caihongpiImg = caiHongPiImg();
        if (caihongpiImg == null) {
            throw new RuntimeException("彩虹屁图片获取失败");
        }
        caihongpiMap.put("text", caihongpiText);
        caihongpiMap.put("img", caihongpiImg);
        return ResultUtil.success(caihongpiMap);
    }

    /**
     * 获取彩虹屁图片Url
     */
    private String caiHongPiImg() {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet httpGetTest1 = new HttpGet(caiHongPiImg);
            response = httpclient.execute(httpGetTest1);
            // 4.获取响应实体
            HttpEntity entityTest = response.getEntity();
            String imgurl = JSON.parseObject(EntityUtils.toString(entityTest, "utf-8"), Map.class)
                    .get("imgurl").toString();
            return imgurl;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException("加载彩虹屁出错，请重新进入此页面");
            }
        }
        return null;
    }

    /**
     * 获取彩虹屁文字
     *
     * @return
     */
    private String caiHongPiText() {
        String str = "";
        String result = "";
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(caiHongPiText);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            inputStream = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((str = bufferedReader.readLine()) != null) {
                result += str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
