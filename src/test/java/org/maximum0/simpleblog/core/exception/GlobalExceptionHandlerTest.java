package org.maximum0.simpleblog.core.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.maximum0.simpleblog.core.support.AbstractContainerTest;
import org.maximum0.simpleblog.core.support.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
class GlobalExceptionHandlerTest extends AbstractContainerTest {

    @Autowired
    private MockMvc mockMvc;

    private void checkExceptionResponse(String url, String errorCodeParam, int expectedStatus, ErrorCode expectedErrorCode) throws Exception {
        mockMvc.perform(get(url)
                        .param("errorCode", errorCodeParam))
                .andExpect(status().is(expectedStatus))
                .andExpect(jsonPath("$.code").value(expectedErrorCode.getCode()))
                .andExpect(jsonPath("$.message").value(expectedErrorCode.getMessage()));
    }

    @Test
    @DisplayName("throwGenericExceptionTest 발생 시 예외 처리 응답이 반환됩니다.")
    public void throwGenericExceptionTest() throws Exception {
        checkExceptionResponse(
                "/api/exception/throw-generic-exception",
                "",
                500,
                ErrorCode.INTERNAL_SERVER_ERROR)
        ;
    }

    @Test
    @DisplayName("throwBaseExceptionTest 발생 시 예외 처리 응답이 반환됩니다. - NOT_FOUND")
    public void throwBaseExceptionNotFoundTest() throws Exception {
        checkExceptionResponse(
                "/api/exception/throw-base-exception",
                "NOT_FOUND",
                404,
                ErrorCode.NOT_FOUND
        );
    }

}