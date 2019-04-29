package com.dkd.api;import com.dkd.XiaoyudiApplication;import com.dkd.common.utils.RedisService;import net.sf.json.JSONObject;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.http.HttpMethod;import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;import org.springframework.test.web.servlet.MockMvc;import org.springframework.test.web.servlet.MvcResult;import org.springframework.test.web.servlet.ResultActions;import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;import org.springframework.test.web.servlet.setup.MockMvcBuilders;import org.springframework.transaction.annotation.Transactional;import org.springframework.web.context.WebApplicationContext;@RunWith(SpringJUnit4ClassRunner.class)@SpringBootTest(classes = XiaoyudiApplication.class,properties = "/application.properties")@AutoConfigureMockMvc@Transactionalpublic class AppStatisticsControllerTest {    private final Logger logger = LoggerFactory.getLogger(AppStatisticsControllerTest.class);    private MockMvc mockMvc;    private String  token;    private String reqUrl = "/app/statistics";    private String loginReqUrl = "/app/user";    @Autowired    private RedisService redisService;    @Autowired    private WebApplicationContext webApplicationContext;    @Before    public void setUp() throws Exception {        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        String mobile="18564656666";        String tokenCache = "userToken"+mobile;        Object tokenObj = redisService.get(tokenCache);        if(tokenObj!=null) {            ResultActions resultActions = this.mockMvc.                    perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                            .param("phone", mobile)                            .param("password", "123456")                            .param("qcellcoreId", "1")                    );            MvcResult mvcResult = resultActions.andReturn();            logger.info("=====generalLogin mvcResult:" + mvcResult.getResponse().getStatus());            String result = mvcResult.getResponse().getContentAsString();            logger.info("=====generalLogin result :" + result);            JSONObject jsonObject = JSONObject.fromObject(result);            String tokenJson = jsonObject.optString("data");            String parents = JSONObject.fromObject(tokenJson).optString("parents");            token = JSONObject.fromObject(parents).optString("token");            logger.info("=====generalLogin result token:" + token);            redisService.set(tokenCache,token,24*60*60L);        }else {            token = String.valueOf(tokenObj);            System.err.println("已登录tokenCache:"+tokenCache+" :"+tokenObj);        }    }    @Test    public void getNounDayInfo() throws Exception{        String token;        String mobile="18564656666";        ResultActions resultActions1 = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                        .param("phone",mobile)                        .param("password","123456")                        .param("qcellcoreId","1")                );        MvcResult mvcResult1 = resultActions1.andReturn();        String result1 = mvcResult1.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result1);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST, reqUrl + "/noun/dayInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "1")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounDayInfo mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounDayInfo result:" + result);    }    @Test    public void getNounDayInfo1() throws Exception{        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST, reqUrl + "/noun/dayInfo")                                .param("token", "")                                .param("scene", "1")                                .param("module", "1")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounDayInfo mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounDayInfo result:" + result);    }    @Test    public void getNounDayInfo2() throws Exception{        String token;        String mobile="18564656666";        ResultActions resultActions1 = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                        .param("phone",mobile)                        .param("password","123456")                        .param("qcellcoreId","1")                );        MvcResult mvcResult1 = resultActions1.andReturn();        String result1 = mvcResult1.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result1);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/dayInfo")                                .param("token", token)                                .param("scene", "")                                .param("module", "")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounDayInfo1 mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounDayInfo1 result:" + result);    }    @Test    public void getNounDayInfo3() throws Exception{        //没有完善儿童信息1提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "18230113589")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/dayInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounDayInfo3 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounDayInfo3 resultTip:" + resultTip);    }    @Test    public void getNounDayInfo4() throws Exception{        //没有完善儿童信息2提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "13581949447")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/dayInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounDayInfo4 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounDayInfo4 resultTip:" + resultTip);    }    @Test    public void getNounDayInfo5() throws Exception{        //该模块还没有训练记录 提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "15848959799")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST, reqUrl + "/noun/dayInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "4")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounDayInfo5 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounDayInfo5 resultTip:" + resultTip);        //重新登录提示        ResultActions resultActionsLoginFailTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/dayInfo")                                .param("token","abcdefg")                                .param("scene","1")                                .param("module","4")                );        MvcResult mvcResultLoginFailTip = resultActionsLoginFailTip.andReturn();        System.out.println("=====getNounDayInfo5 mvcResultLoginFailTip:" + mvcResultLoginFailTip.getResponse().getStatus());        String resultLoginFailTip = mvcResultLoginFailTip.getResponse().getContentAsString();        System.out.println("=====getNounDayInfo5 resultLoginFailTip:" + resultLoginFailTip);    }    @Test    public void getNounWeekInfo() throws Exception{        String token;        String mobile="18564656666";        ResultActions resultActions1 = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                        .param("phone",mobile)                        .param("password","123456")                        .param("qcellcoreId","1")                );        MvcResult mvcResult1 = resultActions1.andReturn();        String result1 = mvcResult1.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result1);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/weekInfo")                                .param("token",token)                                .param("scene","1")                                .param("module","1")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounWeekInfo mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounWeekInfo result:" + result);    }    @Test    public void getNounWeekInfo1() throws Exception{        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/weekInfo")                                .param("token","")                                .param("scene","1")                                .param("module","1")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounWeekInfo1 mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounWeekInfo1 result:" + result);    }    @Test    public void getNounWeekInfo2() throws Exception{        String token;        String mobile="18564656666";        ResultActions resultActions1 = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                        .param("phone",mobile)                        .param("password","123456")                        .param("qcellcoreId","1")                );        MvcResult mvcResult1 = resultActions1.andReturn();        String result1 = mvcResult1.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result1);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/weekInfo")                                .param("token", token)                                .param("scene", "")                                .param("module", "")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounWeekInfo2 mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounWeekInfo2 result:" + result);    }    @Test    public void getNounWeekInfo3() throws Exception{        //没有完善儿童信息1提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "18230113589")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/weekInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounWeekInfo3 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounWeekInfo3 resultTip:" + resultTip);    }    @Test    public void getNounWeekInfo4() throws Exception{        //没有完善儿童信息2提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "13581949447")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/weekInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounWeekInfo4 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounWeekInfo4 resultTip:" + resultTip);    }    @Test    public void getNounWeekInfo5() throws Exception{        //该模块还没有训练记录 提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "15848959799")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST, reqUrl + "/noun/weekInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "4")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounWeekInfo5 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounWeekInfo5 resultTip:" + resultTip);        //重新登录提示        ResultActions resultActionsLoginFailTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/weekInfo")                                .param("token","abcdefg")                                .param("scene","1")                                .param("module","4")                );        MvcResult mvcResultLoginFailTip = resultActionsLoginFailTip.andReturn();        System.out.println("=====getNounWeekInfo5 mvcResultLoginFailTip:" + mvcResultLoginFailTip.getResponse().getStatus());        String resultLoginFailTip = mvcResultLoginFailTip.getResponse().getContentAsString();        System.out.println("=====getNounWeekInfo5 resultLoginFailTip:" + resultLoginFailTip);    }    @Test    public void getNounMonthInfo() throws Exception{        String token;        String mobile="18564656666";        ResultActions resultActions1 = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                        .param("phone",mobile)                        .param("password","123456")                        .param("qcellcoreId","1")                );        MvcResult mvcResult1 = resultActions1.andReturn();        String result1 = mvcResult1.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result1);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/monthInfo")                                .param("token",token)                                .param("scene","1")                                .param("module","1")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounMonthInfo mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounMonthInfo result:" + result);    }    @Test    public void getNounMonthInfo2() throws Exception{        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/monthInfo")                                .param("token","")                                .param("scene","1")                                .param("module","1")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getNounMonthInfo mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getNounMonthInfo result:" + result);        ResultActions resultActionsParamsIsNull = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/monthInfo")                                .param("token", "abc")                                .param("scene", "")                                .param("module", "")                );        MvcResult mvcResultParamsIsNull = resultActionsParamsIsNull.andReturn();        System.out.println("=====getNounMonthInfo2 mvcResultParamsIsNull:" + mvcResultParamsIsNull.getResponse().getStatus());        String resultParamsIsNull = mvcResultParamsIsNull.getResponse().getContentAsString();        System.out.println("=====getNounMonthInfo2 resultParamsIsNull:" + resultParamsIsNull);    }    @Test    public void getNounMonthInfo1() throws Exception {        //没有完善儿童信息1提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "18230113589")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/monthInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounMonthInfo1 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounMonthInfo1 resultTip:" + resultTip);    }    @Test    public void getNounMonthInfo3() throws Exception {        //没有完善儿童信息2提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "13581949447")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/monthInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounMonthInfo3 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounMonthInfo3 resultTip:" + resultTip);    }    @Test    public void getNounMonthInfo4() throws Exception {        //该模块还没有训练记录 提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "15848959799")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST, reqUrl + "/noun/monthInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "4")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====getNounMonthInfo4 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====getNounMonthInfo4 resultTip:" + resultTip);        //重新登录提示        ResultActions resultActionsLoginFailTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/monthInfo")                                .param("token","abcdefg")                                .param("scene","1")                                .param("module","4")                );        MvcResult mvcResultLoginFailTip = resultActionsLoginFailTip.andReturn();        System.out.println("=====getNounMonthInfo4 mvcResultLoginFailTip:" + mvcResultLoginFailTip.getResponse().getStatus());        String resultLoginFailTip = mvcResultLoginFailTip.getResponse().getContentAsString();        System.out.println("=====getNounMonthInfo4 resultLoginFailTip:" + resultLoginFailTip);    }    @Test    public void totalInfo1() throws Exception{        String token;        String mobile="18564656666";        ResultActions resultActions1 = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                        .param("phone",mobile)                        .param("password","123456")                        .param("qcellcoreId","1")                );        MvcResult mvcResult1 = resultActions1.andReturn();        String result1 = mvcResult1.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result1);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/totalInfo")                                .param("token",token)                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====totalInfo1 mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====totalInfo1 result:" + result);    }    @Test    public void totalInfo() throws Exception{        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/totalInfo")                                .param("token","")                );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====totalInfo mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====totalInfo result:" + result);    }    @Test    public void totalInfo2() throws Exception{        //没有完善儿童信息1提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "18230113589")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/totalInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====totalInfo2 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====totalInfo2 resultTip:" + resultTip);    }    @Test    public void totalInfo3() throws Exception{        //没有完善儿童信息2提示        //登录        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();        ResultActions resultActions = this.mockMvc.                perform(MockMvcRequestBuilders.post(loginReqUrl + "/generalLogin")                                .param("phone", "13581949447")                                .param("password", "123456")                                .param("qcellcoreId", "1")                );        MvcResult mvcResult = resultActions.andReturn();        String result = mvcResult.getResponse().getContentAsString();        JSONObject jsonObject = JSONObject.fromObject(result);        String  tokenJson =  jsonObject.optString("data");        String parents =  JSONObject.fromObject(tokenJson).optString("parents");        String token =  JSONObject.fromObject(parents).optString("token");        ResultActions resultActionsTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/totalInfo")                                .param("token", token)                                .param("scene", "1")                                .param("module", "2")                );        MvcResult mvcResultTip = resultActionsTip.andReturn();        System.out.println("=====totalInfo3 mvcResultTip:" + mvcResultTip.getResponse().getStatus());        String resultTip = mvcResultTip.getResponse().getContentAsString();        System.out.println("=====totalInfo3 resultTip:" + resultTip);    }    @Test    public void totalInfo4() throws Exception{        //重新登录提示        ResultActions resultActionsLoginFailTip = this.mockMvc.                perform(MockMvcRequestBuilders.request(HttpMethod.POST,reqUrl+"/noun/totalInfo")                                .param("token","abcdefg")                                .param("scene","1")                                .param("module","4")                );        MvcResult mvcResultLoginFailTip = resultActionsLoginFailTip.andReturn();        System.out.println("=====totalInfo4 mvcResultLoginFailTip:" + mvcResultLoginFailTip.getResponse().getStatus());        String resultLoginFailTip = mvcResultLoginFailTip.getResponse().getContentAsString();        System.out.println("=====totalInfo4 resultLoginFailTip:" + resultLoginFailTip);    }}