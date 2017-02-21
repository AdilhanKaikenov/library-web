DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS gender;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS order_type;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS orders_books;

/////////////////////////////////////////////////
CREATE TABLE PUBLIC.user
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  login VARCHAR(15) NOT NULL,
  password VARCHAR(40) NOT NULL,
  email VARCHAR(40) NOT NULL,
  firstname VARCHAR(25) NOT NULL,
  surname VARCHAR(25) NOT NULL,
  patronymic VARCHAR(25) NOT NULL,
  gender INT NOT NULL,
  address VARCHAR(100) NOT NULL,
  mobile_phone VARCHAR(11) NOT NULL,
  role INT NOT NULL,
  status BOOLEAN DEFAULT TRUE
);
CREATE UNIQUE INDEX "USER_ID_uindex" ON PUBLIC.user (id);
CREATE UNIQUE INDEX "USER_LOGIN_uindex" ON PUBLIC.user (login);
CREATE UNIQUE INDEX "USER_EMAIL_uindex" ON PUBLIC.user (email);
CREATE UNIQUE INDEX "USER_MOBILE_PHONE_uindex" ON PUBLIC.user (mobile_phone);

CREATE TABLE PUBLIC.book
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  title VARCHAR(100) NOT NULL,
  cover VARCHAR(50) DEFAULT 'default_cover.jpg' NOT NULL,
  author INT NOT NULL,
  publish_year INT NOT NULL,
  genre INT NOT NULL,
  description VARCHAR(1600) NOT NULL,
  total_amount INT NOT NULL,
  deleted BOOLEAN DEFAULT FALSE
);
CREATE UNIQUE INDEX "BOOK_ID_uindex" ON PUBLIC.book (id);

CREATE TABLE PUBLIC.comment
(
  id iNT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  date DATETIME NOT NULL,
  text VARCHAR(250) NOT NULL
);
CREATE UNIQUE INDEX "COMMENT_id_uindex" ON PUBLIC.comment (id);

CREATE TABLE PUBLIC.orders_books
(
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  order_id INT NOT NULL,
  issued BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT orders_books_user_id_book_id_pk PRIMARY KEY (user_id, book_id)
);

CREATE TABLE PUBLIC.orders
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  user_id INT NOT NULL,
  order_type INT NOT NULL,
  order_date DATE NOT NULL,
  date_from DATE,
  date_to DATE,
  status BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX "register_order_id_uindex" ON PUBLIC.orders (id);

//////////////////////////////////////////////
////////////// REFERENCE TABLES //////////////
//////////////////////////////////////////////
CREATE TABLE PUBLIC.authors
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name VARCHAR(100) NOT NULL
);
CREATE UNIQUE INDEX "AUTHORS_id_uindex" ON PUBLIC.authors (id);
CREATE UNIQUE INDEX "AUTHORS_name_uindex" ON PUBLIC.authors (name);

CREATE TABLE PUBLIC.order_type
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  type VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX "ORDER_ID_uindex" ON PUBLIC.order_type (id);
CREATE UNIQUE INDEX "ORDER_TYPE_uindex" ON PUBLIC.order_type (type);

CREATE TABLE PUBLIC.genre
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  type VARCHAR(25) NOT NULL
);
CREATE UNIQUE INDEX "GENRE_ID_uindex" ON PUBLIC.genre (id);
CREATE UNIQUE INDEX "GENRE_TYPE_uindex" ON PUBLIC.genre (type);

CREATE TABLE PUBLIC.role
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  type VARCHAR(20) NOT NULL
);
CREATE UNIQUE INDEX "ROLE_id_uindex" ON PUBLIC.role (id);
CREATE UNIQUE INDEX "ROLE_TYPE_uindex" ON PUBLIC.role (type);

CREATE TABLE PUBLIC.gender
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  type VARCHAR(15) NOT NULL
);
CREATE UNIQUE INDEX "GENDER_ID_uindex" ON PUBLIC.gender (id);
CREATE UNIQUE INDEX "GENDER_TYPE_uindex" ON PUBLIC.gender (type);

//////////////////////////////////////////////
//////////////// DATA INSERT /////////////////
//////////////////////////////////////////////
INSERT INTO PUBLIC.gender(id, type) VALUES
  (0, 'MALE'),
  (1, 'FEMALE');

INSERT INTO PUBLIC.role(id, type) VALUES
  (0, 'LIBRARIAN'),
  (1, 'READER');

INSERT INTO PUBLIC.genre(id, type) VALUES
  (0, 'Documental literature'),
  (1, 'Detectives and thrillers'),
  (2, 'Computers and Internet'),
  (3, 'Poetry'),
  (4, 'Science and education');

INSERT INTO PUBLIC.Authors (id, name) VALUES 
  (1, 'Igor Prokopenko'),
  (2, 'Henri Troyat'),
  (3, 'Dick Francis'),
  (4, 'Natalia Andreeva'),
  (5, 'King Stephen'),
  (6, 'Michael Stelzner'),
  (7, 'Albitov Andrew'),
  (8, 'Yuri Polyakov'),
  (9, 'Arthur Wiggins'),
  (10,'Yakov Perelman');

INSERT INTO PUBLIC.book (id, title, cover, author, publish_year, genre, description, total_amount) VALUES
  (1, 'Aliens of national importance', 'cover1.jpg', 1, 2013, 0, 'This book is the first domestic, based on documents from military archives, about aliens, flying saucers, contacts with extraterrestrial civilizations and the parallel worlds of the famous documentary, author and host of the program "Military Secret" Igor Prokopenko. The author worked on the book for twenty years, it included a truly sensational materials: reports of military pilots of UFO attacks, reports of commanders of nuclear submarines of encounters with objects of extraterrestrial origin, reports chief spetsrazvedki Navy directive Navy Commander ... In this book, there is no single line of rumors and speculation. Each given fact, no matter how it may seem fantastic, documented or event participants. Do UFOs exist? Absolutely accurate answer, you will learn from this book ...', 10),
  (2, 'Peter the First', 'cover2.jpg', 2, 1980, 0, 'Myths about Peter the Great, brilliant reformer who saved the Russian state from imminent death and at the same time, "The Departed, disrupt Russia with its circular orbit to the comet zashvyrnut in space", almost the Antichrist, overthrew all moral principles and traditions continue haunt the minds and cause controversy today. Why does the human memory Ivan the Terrible - "the mad monster," and Peter the Great, in his cruelty surpassed it remains "immaculate genius"? Above all this reflects the famous French historian Henri Troyat and offers readers his version of the history of the life and reign of Peter.', 10),
  (3, 'Devil''s cocktail', 'cover3.jpg', 3, 1972, 1, 'From the moment the actor Link inherits the South African Republic, begin to pursue his mysterious accidents. Realizing that someone wants him dead, Link tries to get ahead of the mysterious killer', 10),
  (4, 'I''ll be you', 'cover4.jpg', 4, 2010, 1, 'This man knew a great secret. The mystery of death. He knew something that others avoided until the last moment, still believing that this is not the last gasp. And he knew. He did not care, live or die. He was able to defeat his fear of death. He saw the dying, and once had almost died himself. And get him to share this secret nobody could. No force and persuasion. This could only happen voluntarily. He will, of condescension to pathetic little people who do not want to take up the eternal to the last breath, and so afraid of the Elect. Those dedicated to the mystery of death, who could help them and their ... Release of this fear.', 10),
  (5, 'The Shawshank Redemption', 'default_cover.jpg', 5, 1999, 1, 'The book "The Shawshank Redemption" connects the horrors of prison life and the fantasy of escape. Shawshank - the name of the prison. Andy, vice president of solid bank, was sentenced to two life sentences for the murder of his wife and her lover. He is serving his sentence in Shawshank prison, where is all the circles of hell, showing remarkable courage, tenacity and composure. If you are not yet 30, and you get a life, then Valium to worse: the output of Redemption for you will not.', 10),
  (6, 'Content marketing. New methods of customer acquisition in the Internet age', 'cover6.jpg', 6, 2012, 2, 'Michael Stelzner - a recognized expert in the field of social media, the creator of one of the most popular marketing blogs in social networks SocialMediaExaminer.com (more than 150 000 subscribers). The book Stelzner tells about the different types of content that you can publish on your website, blog, a page on the social network and help you attract the attention of potential customers. The author explains how to plan the work for the preparation of the content, how to engage in its creation of renowned experts and how to use social media for its promotion. He also gives valuable advice on working with text, video reel, to organize events and draws attention to the details, which will increase the attractiveness of your content to users. This book - a useful guide for those who want to master the content-marketing tools and use it to win the favor of the Internet audience.', 10),
  (7, 'Facebook: how to find 100,000 friends to your business for free', 'cover7.jpg', 7, 2011, 2, 'Very few people understand how to use social media for business. Meanwhile, a powerful and practically free tool that allows you to gather around us hundreds of thousands of friends and have a month to a million contacts with actual and potential customers. It takes only a little time to create a company page on Facebook and "Vkontakte", discipline in support of communication with its visitors and the implementation of a number of recommendations, and that you will find in this edition. The author has already "stepped on a rake" for you and tell you about errors and successful solutions. This book will be most useful to marketers, PR-professionals, entrepreneurs. And also for those who want to understand how social networks help businesses.', 10),
  (8, 'One hundred days before the order', 'cover8.jpg', 8, 2005 , 3, 'In a new book by the famous writer Yuri Polyakov became the most famous tale of "One hundred days before the order" a modern army, still causes heated debates and divergent views. Also in this edition for the first time under one cover are collected other works of the author devoted to the military theme, written at different times and in different genres: prose, poetry, journalism, screenwriting. At the court of the reader is presented witty film story "Mom''s Story", have never been published. The collection will be of interest to those who have already passed the army school, and for those who are just getting ready to serve, this book can be a real textbook of a soldier''s life.', 10),
  (9, 'Five unsolved problems of science', 'cover9.jpg', 9, 2014, 4, 'American scientists Arthur Wiggins and Charles Wynn just detailed and humorous talk about the major problems of science, the solution of which scientists around the world are fighting. Astronomy. Why the universe is expanding, but the rate of expansion is increasing? Physics. Why do some particles have mass while others - not? Chemistry. What chemical reactions atoms are pushed to the formation of the first living beings? Biology. What is the total device and destination proteome? Geology. Is it possible to accurate long-term weather forecast? The author introduce the events that put these issues, discuss the existing theories, including string theory, the chaos, the human genome and protein folding, allow readers to take part in the reflections on the ideas proposed. The book tells the story of the biggest problems of astronomy, physics, chemistry, biology and geology, on which scientists are now working. The author consider the opening that led to these problems, are familiar with the work on their solution and discuss new theories, including string theory, the chaos, the human genome and protein folding. For a wide range of readers. Pictures Sidney Harris', 10),
  (10, 'Physics at every step', 'cover10.jpg', 10, 2009, 4, 'One of the best benefits of classical physics. Interesting stories, instructive experiments, interesting facts will teach the inquisitive reader to notice the simplest physical phenomena and understand their nature.', 10),
  (11, 'Selected poems', 'cover11.jpg', 4, 2015, 3, 'Selected Poems known Leningrad poet. Included love, military, philosophical poems, ballads, and allegories, some fragments of verse from the poet''s ironic fantasy.', 10),
  (12, 'Secrets of the universe', 'cover12.jpg', 9, 2008, 4, 'The book is based on the latest scientific data paints a picture of the universe in its development - from early concepts to mifopoeticheskih modern cosmological panorama. The author refers to the mysteries of the dispensation of the most difficult and pressing problems of astronomy, astrology, astrophysics, paranormal, space contacts. Text Only. In the book 140 illustrations.', 10);

INSERT INTO PUBLIC.order_type(id, type) VALUES
  (0, 'Subscription'),
  (1, 'Reading room');

INSERT INTO user (id, login, password, email, firstname, surname, patronymic, gender, address, mobile_phone, role)
VALUES
  (1, 'UserReader1', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_1@mail.ru', 'Zhdanov', 'Gelasius', 'Gelasevich', 0, 'Apt. 8, 1 Nurkena St., city of Karaganda, Kazakhstan', '87754715471', 1),
  (2, 'UserReader2', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_2@mail.ru', 'Arseny', 'Likhachev', 'Stepanovich', 0, 'Apt. 8, 1 Lenina St., city of Karaganda, Kazakhstan', '87754715472', 1),
  (3, 'UserReader3', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_3@mail.ru', 'Dorofeeva', 'Regina', 'Parfenevna', 1, 'Apt. 8, 1 Lenina St., city of Karaganda, Kazakhstan', '87754715473', 1),
  (4, 'UserReader4', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_4@mail.ru', 'Volkova', 'Larisa', 'Semyonovna', 1, 'Apt. 8, 1 Lenina St., city of Karaganda, Kazakhstan', '87754715474', 1),
  (5, 'UserReader5', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_5@mail.ru', 'Gorbachev', 'Antonin', 'Evgenyevich', 0, 'Apt. 8, 1 Lenina St., city of Karaganda, Kazakhstan', '87754715475', 1),
  (6, 'UserReader6', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_6@mail.ru', 'Abramova', 'Akulina', 'Vadimovna', 1, 'Apt. 8, 1 Lenina St., city of Karaganda, Kazakhstan', '87754715476', 1),
  (7, 'Anuar', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_7@mail.ru', 'Kadersizov', 'Anuar', 'Nurumovich', 0, '21\7 Abaya St., city of Karazhal, Kazakhstan', '87754715478', 1),
  (8, 'Maxim', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_8@mail.ru', 'Maximov', 'Maxim', 'Maximovich', 0, '21\3 Abaya St., city of Karazhal, Kazakhstan', '87754715479', 1),
  (9, 'Adilhan', 'fcea920f7412b5da7be0cf42b8c93759', 'adilhan_kai@mail.ru', 'Kaikenov', 'Adilhan', 'Dauletkhanovich', 0, '21\2 Abaya St., city of Karazhal, Kazakhstan', '87754715477', 0),
  (10, 'Karim', 'fcea920f7412b5da7be0cf42b8c93759', 'user_email_9@mail.ru', 'Dochshanov', 'Karim', 'Gabdyzhauatovich', 0, '63 Main St., city of Arkalyk, Kazakhstan', '87754715480', 0);
