package cc.persistence.book;

import cc.persistence.dto.BookDto;

/**
 * @author Maxim Neverov
 */
public interface BookRepository {

    void create(BookDto book);

}
