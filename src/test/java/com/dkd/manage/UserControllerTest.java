package com.dkd.manage;import com.dkd.XiaoyudiApplication;import com.dkd.model.XydChild;import com.fasterxml.jackson.databind.ObjectMapper;import com.fasterxml.jackson.databind.ObjectWriter;import com.google.gson.Gson;import org.apache.http.entity.StringEntity;import org.json.JSONObject;import org.junit.After;import org.junit.Before;import org.junit.Test;import org.junit.runner.RunWith;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.http.MediaType;import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;import org.springframework.test.web.servlet.MockMvc;import org.springframework.test.web.servlet.MvcResult;import org.springframework.test.web.servlet.ResultActions;import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;import org.springframework.test.web.servlet.setup.MockMvcBuilders;import sun.text.resources.FormatData;import java.util.Date;@RunWith(SpringJUnit4ClassRunner.class)@SpringBootTest(classes = XiaoyudiApplication.class,properties = "/application.properties")@AutoConfigureMockMvcpublic class UserControllerTest {    private final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);    private MockMvc mockMvc;    @Autowired    private UserController userController;    private String reqUrl = "/manage/user";    @Before    public void setUp() throws Exception {        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();    }    @After    public void tearDown() throws Exception {    }    @Test    public void toParentsPage() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/toParentsPage"));        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====toParentsPage mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====toParentsPage result:" + result);    }    @Test    public void toChildPage() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/toChildPage"));        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====toChildPage mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====toChildPage result:" + result);    }    @Test    public void getParentsList() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/getParentsList.ajax")                        .contentType(MediaType.APPLICATION_JSON)                        .param("phone", "123456")                        .param("qcellcoreId", "123")                        .param("nickname", "aa")                        .param("start", "0")                        .param("length", "10")        );        MvcResult mvcResult = resultActions.andReturn();        System.out.println("=====getParentsList mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        System.out.println("=====getParentsList result:" + result);    }    @Test    public void parentsDelete() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/parentsDelete")                        .param("id", "80")        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====parentsDelete mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====parentsDelete result:" + result);        ResultActions resultActionsFail = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/parentsDelete")                        .param("id", "")        );        MvcResult mvcResultFail = resultActionsFail.andReturn();        logger.info("=====parentsDelete mvcResultFail:" + mvcResultFail.getResponse().getStatus());        String resultFail = mvcResultFail.getResponse().getContentAsString();        logger.info("=====parentsDelete resultFail:" + resultFail);    }    @Test    public void updatePassword()throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/updatePassword")                        .param("id", "80")        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====parentsDelete mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====parentsDelete result:" + result);        ResultActions resultActionsFail = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/updatePassword")                        .param("id", "")        );        MvcResult mvcResultFail = resultActionsFail.andReturn();        logger.info("=====parentsDelete mvcResultFail:" + mvcResultFail.getResponse().getStatus());        String resultFail = mvcResultFail.getResponse().getContentAsString();        logger.info("=====parentsDelete resultFail:" + resultFail);    }    @Test    public void toParentsAdd() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/toParentsAdd")                .param("phone","123").param("qcellcoreId","12").param("passwrod","123456")        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====toParentsAdd mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====toParentsAdd result:" + result);        ResultActions resultActionsParamsError = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/toParentsAdd")                        .param("phone", "").param("qcellcoreId","").param("passwrod","")        );        MvcResult mvcResultParamsError = resultActionsParamsError.andReturn();        logger.info("=====toParentsAdd mvcResultParamsError:" + mvcResultParamsError.getResponse().getStatus());        String resultParamsError = mvcResultParamsError.getResponse().getContentAsString();        logger.info("=====toParentsAdd resultParamsError:" + resultParamsError);        ResultActions resultActionsNotFindPhoneQCell = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/toParentsAdd")                        .param("phone","18510329001").param("qcellcoreId","99999").param("passwrod","123456")        );        MvcResult mvcResultNotFindPhoneQCell = resultActionsNotFindPhoneQCell.andReturn();        logger.info("=====toParentsAdd mvcResultNotFindPhoneQCell:" + mvcResultNotFindPhoneQCell.getResponse().getStatus());        String resultNotFindPhoneQCell = mvcResultNotFindPhoneQCell.getResponse().getContentAsString();        logger.info("=====toParentsAdd resultNotFindPhoneQCell:" + resultNotFindPhoneQCell);        ResultActions resultActionsRegistered = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/toParentsAdd")                        .param("phone","18614369666").param("qcellcoreId","99999").param("passwrod","123456")        );        MvcResult mvcResultRegistered = resultActionsRegistered.andReturn();        logger.info("=====toParentsAdd mvcResultRegistered:" + mvcResultRegistered.getResponse().getStatus());        String resultRegistered = mvcResultRegistered.getResponse().getContentAsString();        logger.info("=====toParentsAdd resultRegistered:" + resultRegistered);    }    @Test    public void parentsAdd() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/parentsAdd")                        .param("phone", "16601215921")                        .param("qcellcoreId", "1")                        .param("passwrod", "123456")        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====parentsAdd mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====parentsAdd result:" + result);        ResultActions resultActionsParamsError = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/parentsAdd")                        .param("phone", "").param("qcellcoreId", "").param("passwrod", "")        );        MvcResult mvcResultParamsError = resultActionsParamsError.andReturn();        logger.info("=====toParentsAdd mvcResultParamsError:" + mvcResultParamsError.getResponse().getStatus());        String resultParamsError = mvcResultParamsError.getResponse().getContentAsString();        logger.info("=====toParentsAdd resultParamsError:" + resultParamsError);        ResultActions resultActionsNotFindPhoneQCell = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/parentsAdd")                        .param("phone","18510329001").param("qcellcoreId","99999").param("passwrod","123456")        );        MvcResult mvcResultNotFindPhoneQCell = resultActionsNotFindPhoneQCell.andReturn();        logger.info("=====toParentsAdd mvcResultNotFindPhoneQCell:" + mvcResultNotFindPhoneQCell.getResponse().getStatus());        String resultNotFindPhoneQCell = mvcResultNotFindPhoneQCell.getResponse().getContentAsString();        logger.info("=====toParentsAdd resultNotFindPhoneQCell:" + resultNotFindPhoneQCell);        ResultActions resultActionsSuccess = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/parentsAdd")                        .param("phone", String.valueOf(new Date().getTime()).substring(11)).param("qcellcoreId","1").param("passwrod","123456")        );        MvcResult mvcResultSuccess = resultActionsSuccess.andReturn();        logger.info("=====toParentsAdd mvcResultSuccess:" + mvcResultSuccess.getResponse().getStatus());        String resultSuccess = mvcResultSuccess.getResponse().getContentAsString();        logger.info("=====toParentsAdd resultSuccess:" + resultSuccess);    }    @Test    public void getChildList() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/getChildList.ajax")                        .param("name", "嘻嘻")                        .param("countiy","1")                        .param("province","110")                        .param("city","111")                        .param("birthdate","2018")                        .param("sex","男")                        .param("phone","16601215921")                        .param("start","1")                        .param("length", "10")                        .contentType(MediaType.APPLICATION_JSON)        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====getChildList mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====getChildList result:" + result);    }    @Test    public void childDelete() throws Exception {        ResultActions resultActionsIdIsNull = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/childDelete")                        .param("id", "")        );        MvcResult mvcResultIdIsNull = resultActionsIdIsNull.andReturn();        logger.info("=====childDelete mvcResultIdIsNull:" + mvcResultIdIsNull.getResponse().getStatus());        String resultIdIsNull = mvcResultIdIsNull.getResponse().getContentAsString();        logger.info("=====childDelete resultIdIsNull:" + resultIdIsNull);        ResultActions resultActionsXydChildIsNull = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/childDelete")                        .param("id", "1")        );        MvcResult mvcResultXydChildIsNull = resultActionsXydChildIsNull.andReturn();        logger.info("=====childDelete mvcResultXydChildIsNull:" + mvcResultXydChildIsNull.getResponse().getStatus());        String resultXydChildIsNull = mvcResultXydChildIsNull.getResponse().getContentAsString();        logger.info("=====childDelete resultXydChildIsNull:" + resultXydChildIsNull);        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/childDelete")                        .param("id", "126")        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====childDelete mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====childDelete result:" + result);    }    @Test    public void toChildInfo() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/toChildInfo")                        .param("id", "1")        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====toChildInfo mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====toChildInfo result:" + result);    }    @Test    public void childUpdate() throws Exception {        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/childUpdate")                        .contentType(MediaType.APPLICATION_JSON)                        .param("countiy", "1")                        .param("province", "110")                        .param("city", "111")                        .param("id", "126")        );        MvcResult mvcResult = resultActions.andReturn();        logger.info("=====childUpdate mvcResult:" + mvcResult.getResponse().getStatus());        String result = mvcResult.getResponse().getContentAsString();        logger.info("=====childUpdate result:" + result);        ResultActions resultActionsUpdateFail = this.mockMvc.perform(MockMvcRequestBuilders.post(reqUrl + "/childUpdate")                        .contentType(MediaType.APPLICATION_JSON)                        .param("countiy", "999999")                        .param("province", "999999")                        .param("city", "999999")        );        MvcResult mvcResultUpdateFail = resultActionsUpdateFail.andReturn();        logger.info("=====childUpdate mvcResultUpdateFail:" + mvcResultUpdateFail.getResponse().getStatus());        String resultUpdateFail = mvcResultUpdateFail.getResponse().getContentAsString();        logger.info("=====childUpdate resultUpdateFail:" + resultUpdateFail);    }}