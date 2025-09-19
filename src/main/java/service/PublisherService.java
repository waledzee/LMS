package service;

import entity.Publisher;

import java.util.List;

public interface PublisherService {
    Publisher createPublisher(Publisher publisher);
    Publisher getPublisherById(Long id);
    List<Publisher> getAllPublishers();
    Publisher updatePublisher(Long id, Publisher publisher);
    void deletePublisher(Long id);
}

