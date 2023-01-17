package de.neuefische.springsecuritybasics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll_whenNotLoggedIn_return401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isUnauthorized()
                );
    }

    @Test
    @WithMockUser
    void getAll_whenLoggedIn_returnAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                "[]",
                                true
                        )
                );
    }

    @Test
    void create_whenNotLoggedIn_return401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isUnauthorized()
                );
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void create_whenLoggedInAsAdmin_addNewBook() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "id": "",
                        "title": "A Song of Ice and Fire",
                        "author": "George R. R. Martin"
                    }
                """)
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().json("""
                {
                        "id": "",
                        "title": "A Song of Ice and Fire",
                        "author": "George R. R. Martin"
                    }
            """,
                        true
                )
        );
    }
}
