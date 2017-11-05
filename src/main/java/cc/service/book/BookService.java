package cc.service.book;

import cc.common.model.Book;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Maxim Neverov
 */
public interface BookService {

    void create(Book book);
    List<Book> getBooksWithCategory(String category);
    Map<String, List<Book>> getBooksWithCategories(Collection<String> categoryCodes);
    List<Book> getBooks();

}
