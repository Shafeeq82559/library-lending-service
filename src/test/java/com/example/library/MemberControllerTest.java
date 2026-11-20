package com.example.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String VALID = """
            {"fullName": "Grace Stacks", "email": "grace@example.com", "phone": "+1 555 0101"}
            """;

    @Test
    void registersValidMember() throws Exception {
        mockMvc.perform(post("/api/members").contentType(MediaType.APPLICATION_JSON).content(VALID))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.standing").value("GOOD"));
    }

    @Test
    void rejectsInvalidEmail() throws Exception {
        String body = """
                {"fullName": "No Email", "email": "not-an-email"}
                """;
        mockMvc.perform(post("/api/members").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.email").exists());
    }

    @Test
    void rejectsDuplicateEmail() throws Exception {
        mockMvc.perform(post("/api/members").contentType(MediaType.APPLICATION_JSON).content(VALID))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/members").contentType(MediaType.APPLICATION_JSON).content(VALID))
                .andExpect(status().isConflict());
    }
}
