package service.impl;

import entity.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.PublisherRepository;
import service.PublisherService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher updatePublisher(Long id, Publisher publisher) {
        Publisher existing = getPublisherById(id);
        existing.setName(publisher.getName());
        existing.setAddress(publisher.getAddress());
        existing.setContactInfo(publisher.getContactInfo());
        return publisherRepository.save(existing);
    }

    @Override
    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
