package cc.service.book;

import cc.common.model.Book;
import cc.persistence.book.BookRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        repository.save(mapper.mapToDto(book));
    }

    @Override
    public List<Book> getBooksWithCategory(String category) {
        List<Book> result = mapper.mapToDomain(repository.findByCategoryCode(category));
        result.sort(Comparator.comparing(Book::getTitle));
        return result;
    }

    @Override
    public Map<String, List<Book>> getBooksWithCategories(Collection<String> categoryCodes) {
        List<Book> books = mapper.mapToDomain(repository.findByCategoryCodeIn(categoryCodes));

        return getBooksByCategoryMap(books);
    }

    @Override
    public List<Book> getBooks() {
        List<Book> result = mapper.mapToDomain(repository.findAll());
        result.sort(Comparator.comparing(Book::getTitle));
        return result;
    }

    // Visible for testing
    Map<String, List<Book>> getBooksByCategoryMap(List<Book> books) {
        Map<String, List<Book>> result = new HashMap<>();
        for (Book book : books) {
            List<String> bookCategoryCodes = book.getCategoryCodes();
            for (String categoryCode : bookCategoryCodes) {
                List<Book> categoryBooks = result.get(categoryCode);
                if (categoryBooks == null) {
                    categoryBooks = new ArrayList<>();
                    categoryBooks.add(book);
                    result.put(categoryCode, categoryBooks);
                } else {
                    categoryBooks.add(book);
                }
            }
        }
        return result;
    }

}
