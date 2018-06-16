package cc.persistence.book;

import cc.persistence.dto.BookDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface BookRepository extends JpaRepository<BookDto, Integer> {

    List<BookDto> findByCategoryCode(String category);

    List<BookDto> findByCategoryCodeIn(Collection<String> categories);

}
