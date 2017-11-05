package cc.rest;

import cc.Application;
import cc.common.model.Book;
import cc.rest.exception.DbError;
import cc.service.book.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Maxim Neverov
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BookControllerTest {

    private String PATH = "/books";
    private MockMvc mockMvc;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookService service;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        reset(service);
    }

    @Test
    public void return201WhenCreated() throws Exception {
        Book book = new Book("title", Arrays.asList("cat1", "cat2"));
        doNothing().when(service).create(book);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isCreated());
    }

    @Test
    public void return400WhenCannotInsertInDb() throws Exception {
        Book book = new Book("title", Arrays.asList("cat1", "cat2"));
        doThrow(DbError.class).when(service).create(book);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void return500WhenAnyDbException() throws Exception {
        Book book = new Book("title", Arrays.asList("cat1", "cat2"));
        doThrow(QueryTimeoutException.class).when(service).create(book);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void return500WhenAnyException() throws Exception {
        Book book = new Book("title", Arrays.asList("cat1", "cat2"));
        doThrow(Throwable.class).when(service).create(book);

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void return400WhenAnyBookFieldMissing() throws Exception {
        Book book = new Book();

        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isBadRequest());

        book.setTitle("");
        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isBadRequest());

        book.setTitle("title");
        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(book)))
                .andExpect(status().isBadRequest());
    }

}
