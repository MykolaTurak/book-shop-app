INSERT INTO categories(id, name) VALUES (1, 'Fantasy');
INSERT INTO categories(id, name) VALUES (2, 'Science fiction');

INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES
  (1, 'The Java Chronicles', 'Elena Novak', '978-1-56619-909-4', 39.99, 'A deep dive into modern Java development.', 'java_chronicles.jpg'),
  (2, 'Spring Boot in Action', 'John Doe', '978-0-12345-678-9', 29.99, 'Practical guide for building microservices.', 'spring_boot.jpg'),
  (3, 'Docker for Dummies', 'Lisa Smith', '978-0-99999-999-9', 19.59, 'An easy introduction to containerization.', 'docker_for_dummies.jpg');

INSERT INTO books_categories(book_id, category_id) VALUES
  (1, 1),
  (2, 1),
  (3, 2);
