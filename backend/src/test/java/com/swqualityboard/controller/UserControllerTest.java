package com.swqualityboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swqualityboard.TestConfig;
import com.swqualityboard.configuration.annotation.WithAuthUser;
import com.swqualityboard.configuration.security.SecurityConfig;
import com.swqualityboard.dto.system.SystemDto;
import com.swqualityboard.dto.team.TeamSystemDto;
import com.swqualityboard.dto.user.select.UserInfoOutput;
import com.swqualityboard.entity.Authority;
import com.swqualityboard.service.UserService;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.swqualityboard.ApiDocumentUtils.getDocumentRequest;
import static com.swqualityboard.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@ExtendWith(RestDocumentationExtension.class) // JUnit 5 ????????? ?????? ????????? ?????????
@Import({TestConfig.class})
@WebMvcTest(controllers = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
class UserControllerTest {
    @MockBean
    private UserService userService;

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
    @DisplayName("?????? ?????? ??????")
    @Test
    public void ??????_??????_??????() throws Exception {
        //given
        Set<Authority> authorities = new HashSet<>();
        Authority authority = Authority.builder()
                .id("617efacc17060cc9e4117d75")
                .role("ROLE_ADMIN")
                .build();
        authorities.add(authority);
        List<TeamSystemDto> teamDtoList = new ArrayList<>();
        List<SystemDto> systemDtoList = new ArrayList<>();
        systemDtoList.add(new SystemDto("6184c7e317060cc9e4117dc0","A"));
        systemDtoList.add(new SystemDto("6184d76b17060cc9e4117de1","B"));
        systemDtoList.add(new SystemDto("6184d77317060cc9e4117de3","C"));
        teamDtoList.add(new TeamSystemDto("6184da9b17060cc9e4117e1d","?????? 1???", systemDtoList));
        UserInfoOutput userInfoOutput = UserInfoOutput.builder()
                .id("6184d96139ab91305d6fb8de")
                .email("admin1@gmail.com")
                .nickname("?????? 1??? ?????????")
                .authorities(authorities)
                .teams(teamDtoList)
                .build();

        //when
        doReturn(userInfoOutput).when(userService).getUserInfo(any());

        //then
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer JWT ACCESS TOKEN"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andDo(
                        document(
                                "userApi/select_user/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestHeaders(headerWithName("Authorization").description("Bearer JWT Token")),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("?????? ?????? ??????"),
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER)
                                                .description("?????? ??????"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("?????? ?????????"),
                                        fieldWithPath("result").type(JsonFieldType.OBJECT)
                                                .description("?????? ?????? ??????"),
                                        fieldWithPath("result.id").type(JsonFieldType.STRING)
                                                .description("?????? ??????"),
                                        fieldWithPath("result.email").type(JsonFieldType.STRING)
                                                .description("?????? ?????????"),
                                        fieldWithPath("result.nickname").type(JsonFieldType.STRING)
                                                .description("?????? ??????"),
                                        fieldWithPath("result.authorities").type(JsonFieldType.ARRAY)
                                                .description("?????? ?????? ?????????"),
                                        fieldWithPath("result.authorities.[].id").type(JsonFieldType.STRING)
                                                .description("?????? ?????? ??????"),
                                        fieldWithPath("result.authorities.[].role").type(JsonFieldType.STRING)
                                                .description("?????? ?????? ??????"),
                                        fieldWithPath("result.teams").type(JsonFieldType.ARRAY)
                                                .description("??? ?????? ??????"),
                                        fieldWithPath("result.teams.[].id").type(JsonFieldType.STRING)
                                                .description("??? ??????"),
                                        fieldWithPath("result.teams.[].name").type(JsonFieldType.STRING)
                                                .description("??? ??????"),
                                        fieldWithPath("result.teams.[].systems").type(JsonFieldType.ARRAY)
                                                .description("????????? ?????? ??????"),
                                        fieldWithPath("result.teams.[].systems.[].id").type(JsonFieldType.STRING)
                                                .description("????????? ??????"),
                                        fieldWithPath("result.teams.[].systems.[].name").type(JsonFieldType.STRING)
                                                .description("????????? ??????"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api ?????? ??????")
                                )
                        ));
    }

}
