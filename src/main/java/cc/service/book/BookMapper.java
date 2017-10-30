package cc.service.book;

import cc.common.model.Book;
import cc.persistence.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maxim Neverov
 */
@Component
public class BookMapper {

    public List<BookDto> mapToDto(Book book) {
        return book.getCategoryCodes().stream()
                .map(it -> new BookDto(null, book.getTitle(), it))
                .collect(Collectors.toList());
    }

    public List<Book> mapToDomain(List<BookDto> books) {
        List<Book> result = new ArrayList<>();
        Map<String, List<BookDto>> bookMap = books.stream()
                                             .collect(Collectors
                                             .groupingBy(BookDto::getTitle));

        for (Map.Entry<String, List<BookDto>> entry : bookMap.entrySet()) {
            List<String> codes = entry.getValue().stream()
                                 .map(BookDto::getCategoryCode)
                                 .collect(Collectors.toList());
            result.add(new Book(entry.getKey(), codes));
        }

        return result;
    }

}
