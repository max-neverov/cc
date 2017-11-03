package cc.rest;

import cc.service.book.BookService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * @author Maxim Neverov
 */
@Profile("test")
@Configuration
public class ControllerConfiguration {

    private BookService bookService;

    public ControllerConfiguration() {
        bookService = Mockito.mock(BookService.class);
    }

    @Bean
    @Primary
    public BookService getBookService() {
        return bookService;
    }

}
