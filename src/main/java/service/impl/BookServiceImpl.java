package service.impl;

import dto.BookDto;
import entity.*;
import lombok.RequiredArgsConstructor;
import mapper.BookMapper;
import org.springframework.stereotype.Service;
import repository.*;
import service.BookService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final LanguageRepository languageRepository;

    @Override
    public Book createBook(BookDto dto) {
        Book book = bookMapper.toEntity(dto);
        
        // Set publisher
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found"));
            book.setPublisher(publisher);
        }
        
        // Set language
        if (dto.getLanguageId() != null) {
            Language language = languageRepository.findById(dto.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found"));
            book.setLanguage(language);
        }
        
        // Set authors
        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(id -> authorRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Author not found with id: " + id)))
                    .collect(Collectors.toSet());
            book.setAuthors(authors);
        }
        
        // Set categories
        if (dto.getCategoryIds() != null && !dto.getCategoryIds().isEmpty()) {
            Set<Category> categories = dto.getCategoryIds().stream()
                    .map(id -> categoryRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Category not found with id: " + id)))
                    .collect(Collectors.toSet());
            book.setCategories(categories);
        }
        
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Long id, BookDto dto) {
        Book existing = getBookById(id);
        
        // Update basic fields
        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getIsbn() != null) existing.setIsbn(dto.getIsbn());
        if (dto.getEdition() != null) existing.setEdition(dto.getEdition());
        if (dto.getPublicationYear() != null) existing.setPublicationYear(dto.getPublicationYear());
        if (dto.getSummary() != null) existing.setSummary(dto.getSummary());
        if (dto.getCoverImage() != null) existing.setCoverImage(dto.getCoverImage());
        
        // Update publisher
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found"));
            existing.setPublisher(publisher);
        }
        
        // Update language
        if (dto.getLanguageId() != null) {
            Language language = languageRepository.findById(dto.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found"));
            existing.setLanguage(language);
        }
        
        // Update authors
        if (dto.getAuthorIds() != null) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(authorId -> authorRepository.findById(authorId)
                            .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId)))
                    .collect(Collectors.toSet());
            existing.setAuthors(authors);
        }
        
        // Update categories
        if (dto.getCategoryIds() != null) {
            Set<Category> categories = dto.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId)))
                    .collect(Collectors.toSet());
            existing.setCategories(categories);
        }
        
        return bookRepository.save(existing);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
