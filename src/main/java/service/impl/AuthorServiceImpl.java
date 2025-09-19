package service.impl;

import entity.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.AuthorRepository;
import service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        Author existing = getAuthorById(id);
        existing.setFirstName(author.getFirstName());
        existing.setLastName(author.getLastName());
        existing.setBio(author.getBio());
        return authorRepository.save(existing);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
