package com.swqualityboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swqualityboard.TestConfig;
import com.swqualityboard.configuration.annotation.WithAuthUser;
import com.swqualityboard.configuration.security.SecurityConfig;
import com.swqualityboard.dto.memo.MemoDto;
import com.swqualityboard.dto.system.SystemDto;
import com.swqualityboard.dto.system.SystemQualityInput;
import com.swqualityboard.dto.system.SystemQualityOutput;
import com.swqualityboard.service.SystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.*;

import static com.swqualityboard.ApiDocumentUtils.getDocumentRequest;
import static com.swqualityboard.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@ExtendWith(RestDocumentationExtension.class) // JUnit 5 ????????? ?????? ????????? ?????????
@Import({TestConfig.class})
@WebMvcTest(controllers = SystemController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
class SystemControllerTest {
    @MockBean
    private SystemService systemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(sharedHttpSession())
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @WithAuthUser(email = "admin1@gmail.com", role = "ROLE_ADMIN")
    @DisplayName("????????? SW ???????????? ?????? ??????")
    @Test
    public void ?????????_SW_????????????_??????_??????() throws Exception {
        //given
        SystemDto systemDto = SystemDto.builder()
                .id("6184c7e317060cc9e4117dc0")
                .name("A")
                .build();
        MemoDto memoDto = MemoDto.builder()
                .id("6184c7e317060cc9e4117dc9")
                .content("??????????????????")
                .build();
        List<SystemQualityOutput> systemQualityOutputList = new ArrayList<>();
        SystemQualityOutput systemQualityOutput = SystemQualityOutput.builder()
                .id("6188fdc9948f96df1cff9ba1")
                .system(systemDto)
                .memo(memoDto)
                .critical(0)
                .high(0)
                .medium(3)
                .low(6)
                .complexity(4)
                .overlapping(4)
                .scale(2)
                .testCoverage(73)
                .numberRequest(42)
                .numberSuitableImplementation(22)
                .functionalCompatibility(52)
                .mtbf(440)
                .createdAt("2020-10-04")
                .build();
        systemQualityOutputList.add(systemQualityOutput);

        //when
        doReturn(systemQualityOutputList).when(systemService).selectSystemQuality(any(), any(SystemQualityInput.class));

        //then
        mockMvc.perform(get("/api/system-quality")
                        .queryParam("systems", "6184c7e317060cc9e4117dc0")
                        .queryParam("start", "2020-10-04")
                        .queryParam("end","2020-10-04")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer JWT ACCESS TOKEN"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andDo(
                        document(
                                "systemApi/select_system_quality/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("Authorization").description("Bearer JWT Token")),
                                requestParameters(
                                        parameterWithName("systems").description("????????? ?????? ?????????"),
                                        parameterWithName("start").description("?????? ?????????"),
                                        parameterWithName("end").description("?????? ?????????")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("?????? ?????? ??????"),
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER)
                                                .description("?????? ??????"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("?????? ?????????"),
                                        fieldWithPath("result").type(JsonFieldType.ARRAY)
                                                .description("????????? SW ???????????? ?????? ??????"),
                                        fieldWithPath("result.[].id").type(JsonFieldType.STRING)
                                                .description("????????? SW ???????????? ?????? ??????"),
                                        fieldWithPath("result.[].system").type(JsonFieldType.OBJECT)
                                                .description("????????? ??????"),
                                        fieldWithPath("result.[].system.id").type(JsonFieldType.STRING)
                                                .description("????????? ??????"),
                                        fieldWithPath("result.[].system.name").type(JsonFieldType.STRING)
                                                .description("????????? ??????"),
                                        fieldWithPath("result.[].memo").type(JsonFieldType.OBJECT).optional()
                                                .description("?????? ??????"),
                                        fieldWithPath("result.[].memo.id").type(JsonFieldType.STRING).optional()
                                                .description("?????? ??????"),
                                        fieldWithPath("result.[].memo.content").type(JsonFieldType.STRING).optional()
                                                .description("?????? ??????"),
                                        fieldWithPath("result.[].critical").type(JsonFieldType.NUMBER)
                                                .description("??????????????? critical"),
                                        fieldWithPath("result.[].high").type(JsonFieldType.NUMBER)
                                                .description("??????????????? high"),
                                        fieldWithPath("result.[].medium").type(JsonFieldType.NUMBER)
                                                .description("??????????????? medium"),
                                        fieldWithPath("result.[].low").type(JsonFieldType.NUMBER)
                                                .description("??????????????? low"),
                                        fieldWithPath("result.[].complexity").type(JsonFieldType.NUMBER)
                                                .description("?????????????????? ?????????"),
                                        fieldWithPath("result.[].overlapping").type(JsonFieldType.NUMBER)
                                                .description("?????????????????? ?????????"),
                                        fieldWithPath("result.[].scale").type(JsonFieldType.NUMBER)
                                                .description("?????????????????? ??????"),
                                        fieldWithPath("result.[].testCoverage").type(JsonFieldType.NUMBER)
                                                .description("????????? ????????????"),
                                        fieldWithPath("result.[].numberRequest").type(JsonFieldType.NUMBER)
                                                .description("?????? ?????? ??????"),
                                        fieldWithPath("result.[].numberSuitableImplementation").type(JsonFieldType.NUMBER)
                                                .description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.[].functionalCompatibility").type(JsonFieldType.NUMBER)
                                                .description("?????? ?????????"),
                                        fieldWithPath("result.[].mtbf").type(JsonFieldType.NUMBER)
                                                .description("????????? ?????????"),
                                        fieldWithPath("result.[].createdAt").type(JsonFieldType.STRING)
                                                .description("????????? SW ???????????? ?????????"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api ?????? ??????")
                                )
                        ));
    }

}
