DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS GENDER;
DROP TABLE IF EXISTS BOOK;

CREATE TABLE PUBLIC.USER
(
  ID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  LOGIN VARCHAR(15) NOT NULL,
  PASSWORD VARCHAR(20) NOT NULL,
  EMAIL VARCHAR(40) NOT NULL,
  FIRSTNAME VARCHAR(25) NOT NULL,
  SURNAME VARCHAR(25) NOT NULL,
  PATRONYMIC VARCHAR(25) NOT NULL,
  GENDER INT NOT NULL,
  ADDRESS VARCHAR(25) NOT NULL,
  MOBILE_PHONE VARCHAR(11) NOT NULL,
  ROLE INT NOT NULL,
  STATUS BOOLEAN
);
CREATE UNIQUE INDEX "USER_ID_uindex" ON PUBLIC.USER (ID);
CREATE UNIQUE INDEX "USER_LOGIN_uindex" ON PUBLIC.USER (LOGIN);
CREATE UNIQUE INDEX "USER_EMAIL_uindex" ON PUBLIC.USER (EMAIL);
CREATE UNIQUE INDEX "USER_MOBILE_PHONE_uindex" ON PUBLIC.USER (MOBILE_PHONE);

CREATE TABLE PUBLIC.ROLE
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  ROLE_TYPE VARCHAR(20) NOT NULL
);
CREATE UNIQUE INDEX "ROLE_id_uindex" ON PUBLIC.ROLE (id);
CREATE UNIQUE INDEX "ROLE_ROLE_TYPE_uindex" ON PUBLIC.ROLE (ROLE_TYPE);

CREATE TABLE PUBLIC.GENDER
(
  ID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  GENDER_TYPE VARCHAR(15) NOT NULL
);
CREATE UNIQUE INDEX "GENDER_ID_uindex" ON PUBLIC.GENDER (ID);
CREATE UNIQUE INDEX "GENDER_GENDER_TYPE_uindex" ON PUBLIC.GENDER (GENDER_TYPE);


INSERT INTO PUBLIC.GENDER(ID, GENDER_TYPE) VALUES
  (0, 'MALE'),
  (1, 'FEMALE');

INSERT INTO PUBLIC.ROLE(ID, ROLE_TYPE) VALUES
  (0, 'ADMIN'),
  (1, 'USER');

CREATE TABLE PUBLIC.Book
(
  id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  TITLE VARCHAR(100) NOT NULL,
  COVER VARCHAR(50) NOT NULL,
  AUTHORS VARCHAR(100) NOT NULL,
  PUBLISH_YEAR INT NOT NULL,
  GENRE INT NOT NULL,
  DESCRIPTION VARCHAR(1500) NOT NULL,
  TOTAL_AMOUNT INT NOT NULL,
  AVAILABLE_AMOUNT INT NOT NULL
);
CREATE UNIQUE INDEX "Book_id_uindex" ON PUBLIC.Book (id);

CREATE TABLE PUBLIC.GENRE
(
  ID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  GENRE_TYPE VARCHAR(25) NOT NULL
);
CREATE UNIQUE INDEX "GENRE_ID_uindex" ON PUBLIC.GENRE (ID);
CREATE UNIQUE INDEX "GENRE_GENRE_TYPE_uindex" ON PUBLIC.GENRE (GENRE_TYPE);

INSERT INTO PUBLIC.GENRE(ID, GENRE_TYPE) VALUES
  (0, 'Documental literature'),
  (1, 'Detectives and thrillers'),
  (2, 'Computers and Internet'),
  (3, 'Poetry'),
  (4, 'Science and education')
;

INSERT INTO PUBLIC.BOOK (TITLE, COVER, AUTHORS, PUBLISH_YEAR, GENRE, DESCRIPTION, TOTAL_AMOUNT, AVAILABLE_AMOUNT) VALUES
  ('Aliens of national importance', 'cover1.jpg', 'Igor Prokopenko', 2011, 0, 'This book is the first domestic, based on documents from military archives, about aliens, flying saucers, contacts with extraterrestrial civilizations and the parallel worlds of the famous documentary, author and host of the program "Military Secret" Igor Prokopenko. The author worked on the book for twenty years, it included a truly sensational materials: reports of military pilots of UFO attacks, reports of commanders of nuclear submarines of encounters with objects of extraterrestrial origin, reports chief spetsrazvedki Navy directive Navy Commander ... In this book, there is no single line of rumors and speculation. Each given fact, no matter how it may seem fantastic, documented or event participants. Do UFOs exist? Absolutely accurate answer, you will learn from this book ...', 10, 10),
  ('Peter the First', 'cover2.jpg', 'Henri Troyat', 1980, 0, 'Myths about Peter the Great, brilliant reformer who saved the Russian state from imminent death and at the same time, "The Departed, disrupt Russia with its circular orbit to the comet zashvyrnut in space", almost the Antichrist, overthrew all moral principles and traditions continue haunt the minds and cause controversy today. Why does the human memory Ivan the Terrible - "the mad monster," and Peter the Great, in his cruelty surpassed it remains "immaculate genius"? Above all this reflects the famous French historian Henri Troyat and offers readers his version of the history of the life and reign of Peter.', 10, 10),
  ('Devil''s cocktail', 'cover3.jpg', 'Dick Francis', 1972, 1, 'From the moment the actor Link inherits the South African Republic, begin to pursue his mysterious accidents. Realizing that someone wants him dead, Link tries to get ahead of the mysterious killer', 10, 10),
  ('I''ll be you', 'cover4.jpg', 'Natalia Andreeva', 2010, 1, 'This man knew a great secret. The mystery of death. He knew something that others avoided until the last moment, still believing that this is not the last gasp. And he knew. He did not care, live or die. He was able to defeat his fear of death. He saw the dying, and once had almost died himself. And get him to share this secret nobody could. No force and persuasion. This could only happen voluntarily. He will, of condescension to pathetic little people who do not want to take up the eternal to the last breath, and so afraid of the Elect. Those dedicated to the mystery of death, who could help them and their ... Release of this fear.', 10, 10),
  ('The Shawshank Redemption', 'default_cover.jpg', 'King Stephen', 1999, 1, 'The book "The Shawshank Redemption" connects the horrors of prison life and the fantasy of escape. Shawshank - the name of the prison. Andy, vice president of solid bank, was sentenced to two life sentences for the murder of his wife and her lover. He is serving his sentence in Shawshank prison, where is all the circles of hell, showing remarkable courage, tenacity and composure. If you are not yet 30, and you get a life, then Valium to worse: the output of Redemption for you will not.', 10, 10),
  ('Content marketing. New methods of customer acquisition in the Internet age', 'cover6.jpg', 'Michael Stelzner', 2011, 2, 'Michael Stelzner - a recognized expert in the field of social media, the creator of one of the most popular marketing blogs in social networks SocialMediaExaminer.com (more than 150 000 subscribers). The book Stelzner tells about the different types of content that you can publish on your website, blog, a page on the social network and help you attract the attention of potential customers. The author explains how to plan the work for the preparation of the content, how to engage in its creation of renowned experts and how to use social media for its promotion. He also gives valuable advice on working with text, video reel, to organize events and draws attention to the details, which will increase the attractiveness of your content to users. This book - a useful guide for those who want to master the content-marketing tools and use it to win the favor of the Internet audience.', 10, 10),
  ('Facebook: how to find 100,000 friends to your business for free', 'cover7.jpg', 'Albitov Andrew', 2011, 2, 'Very few people understand how to use social media for business. Meanwhile, a powerful and practically free tool that allows you to gather around us hundreds of thousands of friends and have a month to a million contacts with actual and potential customers. It takes only a little time to create a company page on Facebook and "Vkontakte", discipline in support of communication with its visitors and the implementation of a number of recommendations, and that you will find in this edition. The author has already "stepped on a rake" for you and tell you about errors and successful solutions. This book will be most useful to marketers, PR-professionals, entrepreneurs. And also for those who want to understand how social networks help businesses.', 10, 10),
  ('One hundred days before the order', 'cover8.jpg', 'Yuri Polyakov', 2005 , 3, 'In a new book by the famous writer Yuri Polyakov became the most famous tale of "One hundred days before the order" a modern army, still causes heated debates and divergent views. Also in this edition for the first time under one cover are collected other works of the author devoted to the military theme, written at different times and in different genres: prose, poetry, journalism, screenwriting. At the court of the reader is presented witty film story "Mom''s Story", have never been published. The collection will be of interest to those who have already passed the army school, and for those who are just getting ready to serve, this book can be a real textbook of a soldier''s life.', 10, 10),
  ('Five unsolved problems of science', 'cover9.jpg', 'Arthur Wiggins, Charles Wynne', 2014, 4, 'American scientists Arthur Wiggins and Charles Wynn just detailed and humorous talk about the major problems of science, the solution of which scientists around the world are fighting. Astronomy. Why the universe is expanding, but the rate of expansion is increasing? Physics. Why do some particles have mass while others - not? Chemistry. What chemical reactions atoms are pushed to the formation of the first living beings? Biology. What is the total device and destination proteome? Geology. Is it possible to accurate long-term weather forecast? The authors introduce the events that put these issues, discuss the existing theories, including string theory, the chaos, the human genome and protein folding, allow readers to take part in the reflections on the ideas proposed. The book tells the story of the biggest problems of astronomy, physics, chemistry, biology and geology, on which scientists are now working. The authors consider the opening that led to these problems, are familiar with the work on their solution and discuss new theories, including string theory, the chaos, the human genome and protein folding. For a wide range of readers. Pictures Sidney Harris', 10, 10),
  ('Physics at every step', 'cover10.jpg', 'Yakov Perelman', 2010, 4, 'One of the best benefits of classical physics. Interesting stories, instructive experiments, interesting facts will teach the inquisitive reader to notice the simplest physical phenomena and understand their nature.', 10, 10)
;