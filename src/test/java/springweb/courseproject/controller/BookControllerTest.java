package springweb.courseproject.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static springweb.courseproject.util.TestConstants.CREATE_BOOK_REQUEST_DTO;
import static springweb.courseproject.util.TestConstants.CREATE_BOOK_RESPONSE_DTO;
import static springweb.courseproject.util.TestConstants.SECOND_BOOK_ISBN;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import springweb.courseproject.dto.book.BookDto;

@Sql(scripts = {"classpath:/database/category/delete-category.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext context) {
        mockMvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Sql(scripts = {"classpath:/database/category/add-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(scripts = {"classpath:/database/delete-books-categories-relation.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @Sql(scripts = {"classpath:/database/book/delete-books.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    @DisplayName("Create a new book")
    void createBook_validRequestDto_Success() throws Exception {
        //Given
        String jsonRequest = objectMapper.writeValueAsString(CREATE_BOOK_REQUEST_DTO);

        //WHEN
        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        //THEN
        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(CREATE_BOOK_RESPONSE_DTO, actual, "id");
    }

    @Test
    @Sql(scripts = {"classpath:/database/book/add-book-1.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @WithMockUser(username = "User", roles = "USER")
    @DisplayName("Get book by id")
    void getBook_validId_Success() throws Exception {
        //GIVEN
        Long validId = 1L;
        BookDto expected = CREATE_BOOK_RESPONSE_DTO;
        expected.setIsbn(SECOND_BOOK_ISBN);
        //WHEN
        MvcResult result = mockMvc.perform(
                        get("/books/{id}", validId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual,
                "id", "categoryIds", "coverImage", "description");
    }

    @Test
    @Sql(scripts = {"classpath:/database/book/add-book-2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @WithMockUser(username = "Admin", roles = "ADMIN")
    @DisplayName("Delete book by id")
    void deleteBook_validId_Success() throws Exception {
        //GIVEN
        Long validId = 2L;
        //WHEN
        mockMvc.perform(delete("/books/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
        //THEN
    }

    @Test
    @Sql(scripts = {"classpath:/database/book/add-book-3.sql",
            "classpath:/database/book/add-book-4.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(scripts = {"classpath:/database/book/delete-books.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @WithMockUser(username = "User", roles = "USER")
    @DisplayName("Get all books")
    void getBooks_valid_Success() throws Exception {
        //GIVEN
        //WHEN
        MvcResult result = mockMvc.perform(
                        get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto[].class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.length, 2);
    }
}
