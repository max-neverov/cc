package cc.service.book;

import cc.common.model.Book;
import cc.persistence.book.BookRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    @Inject
    public BookServiceImpl(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(Book book) {
        repository.create(mapper.mapToDto(book));
    }

    @Override
    public List<Book> getAllBooksBelongToCategory(String category) {
        return mapper.mapToDomain(repository.getAllBooksBelongToCategory(category));
    }

}
