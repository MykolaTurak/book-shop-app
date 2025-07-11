DELETE FROM books_categories
WHERE book_id = 1 AND category_id = 1;

DELETE FROM books
WHERE id = 1;

DELETE FROM categories
WHERE id = 1;
ALTER TABLE books AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;