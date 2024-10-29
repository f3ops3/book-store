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

@Sql(scripts = {"classpath:database/books/add-books-to-book-table.sql",
        "classpath:database/categories/add-category-to-categories-table.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:database/books-categories/delete-books-categories-relations.sql",
        "classpath:database/categories/delete-categories-from-category-table.sql",
        "classpath:database/books/delete-books-from-book-table.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    private static final Long validId = 1L;
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext context) {
        mockMvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

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
    @WithMockUser(username = "User", roles = "USER")
    @DisplayName("Get book by id")
    void getBook_validId_Success() throws Exception {
        //GIVEN
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
    @WithMockUser(username = "Admin", roles = "ADMIN")
    @DisplayName("Delete book by id")
    void deleteBook_validId_Success() throws Exception {
        //GIVEN
        //WHEN
        mockMvc.perform(delete("/books/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
        //THEN
    }

    @Test
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
        Assertions.assertEquals(actual.length, 4);
    }
}
