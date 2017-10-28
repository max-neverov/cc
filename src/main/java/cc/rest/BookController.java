package cc.rest;

import cc.common.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Controller
@RequestMapping("/books")
public class BookController {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void submitBook(@RequestBody Book book) {
        log.info("Got the book {}", book);
    }

}
