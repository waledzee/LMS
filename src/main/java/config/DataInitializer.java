package config;

import entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import repository.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final LanguageRepository languageRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeUsers();
        initializeAuthors();
        initializeCategories();
        initializePublishers();
        initializeLanguages();
        initializeBooks();
        initializeMembers();
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role admin = Role.builder().roleName("ADMIN").build();
            Role librarian = Role.builder().roleName("LIBRARIAN").build();
            Role staff = Role.builder().roleName("STAFF").build();
            
            roleRepository.saveAll(Arrays.asList(admin, librarian, staff));
        }
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow();
            Role librarianRole = roleRepository.findByRoleName("LIBRARIAN").orElseThrow();
            Role staffRole = roleRepository.findByRoleName("STAFF").orElseThrow();

            User admin = User.builder()
                    .username("admin")
                    .email("admin@lms.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .role(adminRole)
                    .build();

            User librarian = User.builder()
                    .username("librarian")
                    .email("librarian@lms.com")
                    .passwordHash(passwordEncoder.encode("librarian123"))
                    .role(librarianRole)
                    .build();

            User staff = User.builder()
                    .username("staff")
                    .email("staff@lms.com")
                    .passwordHash(passwordEncoder.encode("staff123"))
                    .role(staffRole)
                    .build();

            userRepository.saveAll(Arrays.asList(admin, librarian, staff));
        }
    }

    private void initializeAuthors() {
        if (authorRepository.count() == 0) {
            Author author1 = Author.builder()
                    .firstName("J.K.")
                    .lastName("Rowling")
                    .bio("British author, best known for the Harry Potter series")
                    .build();

            Author author2 = Author.builder()
                    .firstName("George")
                    .lastName("Orwell")
                    .bio("English novelist, essayist, journalist, and critic")
                    .build();

            Author author3 = Author.builder()
                    .firstName("Harper")
                    .lastName("Lee")
                    .bio("American novelist best known for To Kill a Mockingbird")
                    .build();

            authorRepository.saveAll(Arrays.asList(author1, author2, author3));
        }
    }

    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            Category fiction = Category.builder().name("Fiction").build();
            Category fantasy = Category.builder().name("Fantasy").build();
            Category dystopian = Category.builder().name("Dystopian").build();
            Category classic = Category.builder().name("Classic").build();

            categoryRepository.saveAll(Arrays.asList(fiction, fantasy, dystopian, classic));
        }
    }

    private void initializePublishers() {
        if (publisherRepository.count() == 0) {
            Publisher publisher1 = Publisher.builder()
                    .name("Bloomsbury Publishing")
                    .address("50 Bedford Square, London WC1B 3DP, UK")
                    .contactInfo("+44 20 7631 5600")
                    .build();

            Publisher publisher2 = Publisher.builder()
                    .name("Secker & Warburg")
                    .address("14 Carlisle Street, London W1D 3BN, UK")
                    .contactInfo("+44 20 7631 5600")
                    .build();

            Publisher publisher3 = Publisher.builder()
                    .name("J.B. Lippincott & Co.")
                    .address("Philadelphia, PA, USA")
                    .contactInfo("+1 215 238 4200")
                    .build();

            publisherRepository.saveAll(Arrays.asList(publisher1, publisher2, publisher3));
        }
    }

    private void initializeLanguages() {
        if (languageRepository.count() == 0) {
            Language english = Language.builder().name("English").build();
            Language spanish = Language.builder().name("Spanish").build();
            Language french = Language.builder().name("French").build();

            languageRepository.saveAll(Arrays.asList(english, spanish, french));
        }
    }

    private void initializeBooks() {
        if (bookRepository.count() == 0) {
            Author rowling = authorRepository.findByFirstNameAndLastName("J.K.", "Rowling").orElseThrow();
            Author orwell = authorRepository.findByFirstNameAndLastName("George", "Orwell").orElseThrow();
            Author lee = authorRepository.findByFirstNameAndLastName("Harper", "Lee").orElseThrow();

            Category fiction = categoryRepository.findByName("Fiction").orElseThrow();
            Category fantasy = categoryRepository.findByName("Fantasy").orElseThrow();
            Category dystopian = categoryRepository.findByName("Dystopian").orElseThrow();
            Category classic = categoryRepository.findByName("Classic").orElseThrow();

            Publisher bloomsbury = publisherRepository.findByName("Bloomsbury Publishing").orElseThrow();
            Publisher secker = publisherRepository.findByName("Secker & Warburg").orElseThrow();
            Publisher lippincott = publisherRepository.findByName("J.B. Lippincott & Co.").orElseThrow();

            Language english = languageRepository.findByName("English").orElseThrow();

            Book book1 = Book.builder()
                    .title("Harry Potter and the Philosopher's Stone")
                    .isbn("978-0747532699")
                    .edition("1st")
                    .publicationYear(1997)
                    .summary("The first book in the Harry Potter series")
                    .coverImage("harry_potter_1.jpg")
                    .publisher(bloomsbury)
                    .language(english)
                    .authors(new HashSet<>(Arrays.asList(rowling)))
                    .categories(new HashSet<>(Arrays.asList(fiction, fantasy)))
                    .build();

            Book book2 = Book.builder()
                    .title("1984")
                    .isbn("978-0451524935")
                    .edition("1st")
                    .publicationYear(1949)
                    .summary("A dystopian social science fiction novel")
                    .coverImage("1984.jpg")
                    .publisher(secker)
                    .language(english)
                    .authors(new HashSet<>(Arrays.asList(orwell)))
                    .categories(new HashSet<>(Arrays.asList(fiction, dystopian, classic)))
                    .build();

            Book book3 = Book.builder()
                    .title("To Kill a Mockingbird")
                    .isbn("978-0061120084")
                    .edition("1st")
                    .publicationYear(1960)
                    .summary("A novel about racial injustice and childhood innocence")
                    .coverImage("mockingbird.jpg")
                    .publisher(lippincott)
                    .language(english)
                    .authors(new HashSet<>(Arrays.asList(lee)))
                    .categories(new HashSet<>(Arrays.asList(fiction, classic)))
                    .build();

            bookRepository.saveAll(Arrays.asList(book1, book2, book3));
        }
    }

    private void initializeMembers() {
        if (memberRepository.count() == 0) {
            Member member1 = Member.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@email.com")
                    .phone("+1 555 123 4567")
                    .address("123 Main St, Anytown, USA")
                    .membershipDate(LocalDate.now().minusDays(30))
                    .build();

            Member member2 = Member.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .email("jane.smith@email.com")
                    .phone("+1 555 987 6543")
                    .address("456 Oak Ave, Somewhere, USA")
                    .membershipDate(LocalDate.now().minusDays(15))
                    .build();

            memberRepository.saveAll(Arrays.asList(member1, member2));
        }
    }
}
