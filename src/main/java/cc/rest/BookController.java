package cc.rest;

import cc.common.model.Book;
import cc.rest.validation.BookValidator;
import cc.service.book.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Slf4j
@RestController
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
        service.create(book);
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return service.getBooksWithCategory(category);
        } else {
            return service.getBooks();
        }
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

}
