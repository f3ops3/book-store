package springweb.courseproject.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static springweb.courseproject.util.TestConstants.CREATE_CATEGORY_REQUEST_DTO;
import static springweb.courseproject.util.TestConstants.CREATE_CATEGORY_RESPONSE_DTO;
import static springweb.courseproject.util.TestConstants.UPDATE_CATEGORY_REQUEST_DTO;

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
import springweb.courseproject.dto.category.CategoryResponseDto;

@Sql(scripts = {"classpath:/database/category/delete-category.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
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
    @DisplayName("Create a new category")
    void createCategory_validRequestDto_Success() throws Exception {
        //GIVEN
        String jsonRequest = objectMapper.writeValueAsString(CREATE_CATEGORY_REQUEST_DTO);
        //WHEN
        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        //THEN
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(CREATE_CATEGORY_RESPONSE_DTO, actual, "id");
    }

    @Test
    @Sql(scripts = {"classpath:/database/category/add-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @WithMockUser(username = "User", roles = "USER")
    @DisplayName("Get category by id")
    void getCategory_validId_Success() throws Exception {
        //GIVEN
        Long validId = 5L;
        //WHEN
        MvcResult result = mockMvc.perform(
                        get("/categories/{id}", validId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(CREATE_CATEGORY_RESPONSE_DTO, actual, "id");
    }

    @Test
    @Sql(scripts = {"classpath:/database/category/add-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @WithMockUser(username = "Admin", roles = "ADMIN")
    @DisplayName("Delete category by id")
    void deleteCategory_validId_Success() throws Exception {
        //GIVEN
        Long validId = 4L;
        //WHEN
        mockMvc.perform(
                        delete("/categories/{id}", validId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @Sql(scripts = {"classpath:/database/category/add-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @WithMockUser(username = "User", roles = "USER")
    @DisplayName("Get all categories")
    void getAllCategories_validSize_Success() throws Exception {
        //GIVEN
        //WHEN
        MvcResult result = mockMvc.perform(
                        get("/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        CategoryResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto[].class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.length, 1);
    }

    @Test
    @Sql(scripts = {"classpath:/database/category/add-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    @DisplayName("Update category by id")
    void updateCategory_validId_Success() throws Exception {
        //GIVEN
        Long validId = 2L;
        String jsonRequest = objectMapper.writeValueAsString(UPDATE_CATEGORY_REQUEST_DTO);
        //WHEN
        MvcResult result = mockMvc.perform(
                        put("/categories/{id}", validId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //THEN
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);
        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(CREATE_CATEGORY_RESPONSE_DTO, actual, "id");
    }
}
