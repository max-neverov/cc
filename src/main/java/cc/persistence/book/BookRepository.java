package cc.persistence.book;

import cc.persistence.dto.BookDto;

import java.util.Collection;
import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface BookRepository {

    void create(List<BookDto> books);
    List<BookDto> getBooksWithCategory(String category);
    List<BookDto> getBooksWithCategories(Collection<String> categories);
    List<BookDto> getBooks();

}
