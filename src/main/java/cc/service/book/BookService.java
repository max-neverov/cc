package cc.service.book;

import cc.common.model.Book;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface BookService {

    void create(Book book);
    List<Book> getAllBooksBelongToCategory(String category);

}
