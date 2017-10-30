package cc.service.book;

import cc.common.model.Book;
import cc.persistence.dto.BookDto;

/**
 * @author Maxim Neverov
 */
public class BookMapper {

    public BookDto mapToDto(Book book) {
        BookDto result = new BookDto();
        result.setTitle(book.getTitle());
        result.setCategoryCodes(book.getCategoryCodes());

        return result;
    }

    public Book mapToDomain(BookDto bookDto) {
        Book result = new Book();
        result.setTitle(bookDto.getTitle());
        result.setCategoryCodes(bookDto.getCategoryCodes());

        return result;
    }

}
