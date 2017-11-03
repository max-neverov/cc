package cc.rest;

import cc.common.model.Book;
import cc.rest.validation.BookValidator;
import cc.service.book.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService service;
    private final BookValidator validator;

    @Inject
    public BookController(BookService service, BookValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void submitBook(@Valid @RequestBody Book book) {
        log.info("Got the book {}", book);
        service.create(book);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

}
