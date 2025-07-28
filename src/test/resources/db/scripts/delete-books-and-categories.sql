DELETE FROM books_categories
WHERE book_id IN (1, 2, 3) AND category_id IN (1, 2);

DELETE FROM books
WHERE id IN (1, 2, 3);

DELETE FROM categories
WHERE id IN (1, 2);
ALTER TABLE books AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;
