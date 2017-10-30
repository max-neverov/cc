package cc.persistence.book;

import cc.persistence.dto.BookDto;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface BookRepository {

    void create(List<BookDto> books);
    List<BookDto> getAllBooksBelongToCategory(String category);

}
