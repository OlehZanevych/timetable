INSERT INTO faculties (id, name, website, email, phone, address) VALUES
	(1, 'Біологічний', 'bioweb.lnu.edu.ua', 'biolog@lnu.edu.ua', '274-03-72, 239-41-53', 'вул. Михайла Грушевського, 4, м. Львів, 79005'),
	(2, 'Географічний', 'geography.lnu.edu.ua', 'geodekanat@gmail.com', '239-41-62, 272-26-44', 'вул. Дорошенка, 41, м. Львів, 79000'),
	(3, 'Геологічний', 'geology.lnu.edu.ua', 'decanat.geology@ukr.net', '261-60-56, 239-41-56', 'вул. Михайла Грушевського, 4, м. Львів, 79005'),
	(4, 'Економічний', 'econom.lnu.edu.ua', 'edean@lnu.edu.ua', '239-41-68', 'проспект Свободи, 18, м. Львів, 79008'),
	(5, 'Електроніки та комп''ютерних технологій', 'electronics.lnu.edu.ua', 'electronics.faculty@lnu.edu.ua', '261-14-91, 239-47-24, 239-41-82', 'вул. Драгоманова, 50, м. Львів, 79005'),
	(6, 'Журналістики', 'journ.lnu.edu.ua', 'journft@lnu.edu.ua', '239-47-51', 'вул. Генерала Чупринки, 49, м. Львів, 79044'),
	(7, 'Іноземних мов', 'lingua.lnu.edu.ua', 'lingua.faculty@lnu.edu.ua', '239-47-16', 'вул. Університетська 1/415, м. Львів, 79000'),
	(8, 'Історичний', 'clio.lnu.edu.ua', 'clio@lnu.edu.ua', '261-03-28', 'вул. Університетська, 1, м. Львів, 79000'),
	(9, 'Культури і мистецтв', 'kultart.lnu.edu.ua', 'fkultart@lnu.edu.ua', '239-41-97', 'вул. Валова,18, м. Львів, 79008'),
	(10, 'Механіко-математичний', 'www.mmf.lnu.edu.ua', 'dmmf@lnu.edu.ua', '260-00-09, 239-41-74, 239-47-43', 'вул. Університетська, 1 м. Львів, 79000'),
	(11, 'Міжнародних відносин', 'intrel.lnu.edu.ua', 'intrel.faculty@lnu.edu.ua', '255-43-17', 'вул. Січових Стрільців, 19, м. Львів, 79000'),
	(12, 'Педагогічної освіти', 'pedagogy.lnu.edu.ua', 'pedosv.fakultet@ukr.net', '239-42-30', 'вул. Туган-Барановського, 7, м. Львів, 79000'),
	(13, 'Прикладної математики та інформатики', 'ami.lnu.edu.ua', 'ami@lnu.edu.ua', '274-01-80, 239-41-86', 'вул. Університетська 1, м. Львів, 79000'),
	(14, 'Управління фінансами та бізнесу', 'financial.lnu.edu.ua', 'financial.faculty@lnu.edu.ua', '235-64-50, 235-86-54', 'вул. Коперника, 3, м. Львів, 79000'),
	(15, 'Фізичний', 'physics.lnu.edu.ua', 'fiz_dekanat@lnu.edu.ua', '272-70-64', 'вул. Кирила і Мефодія, 8, м. Львів, 79005'),
	(16, 'Філологічний', 'philology.lnu.edu.ua', 'filologylnu@gmail.com', '255-41-33, 239-41-58, 239-41-88', 'вул. Університетська, 1, кімната 232, м. Львів, 79000'),
	(17, 'Філософський', 'filos.lnu.edu.ua', 'dfilos@lnu.edu.ua', '239-45-79', 'вул. Університетська, 1, м. Львів, 79001'),
	(18, 'Хімічний', 'chem.lnu.edu.ua', 'chemdek@lnu.edu.ua', '260-03-91, 239-45-10', 'вул. Кирила і Мефодія, 6, м. Львів, 79005'),
	(19, 'Юридичний', 'law.lnu.edu.ua', 'law.faculty@lnu.edu.ua', '239-41-02', 'вул. Січових Стрільців, 14, м. Львів, 79000');
SELECT setval('faculties_id_seq', 19);

INSERT INTO departments (id, name, faculty_id, email, phone, info) VALUES
(1, 'Програмування', 13, 'programming.dep.ami@lnu.edu.ua', '(032) 239-41-78', 'Тестова інформація про кафедру'),
(2, 'Інформаційних систем', 13, 'is.dep.ami@lnu.edu.ua', '(032) 239-45-45', 'Тестова інформація про кафедру'),
(3, 'Дискретного аналізу та інтелектуальних систем', 13, 'kdais@lnu.edu.ua', '(032) 239-42-11', 'Тестова інформація про кафедру'),
(4, 'Обчислювальної математики', 13, 'cm.dep.ami@lnu.edu.ua', '(032) 239-43-91', 'Тестова інформація про кафедру'),
(5, 'Прикладної математики', 13, 'kpm@lnu.edu.ua', '(032) 239-41-78', 'Тестова інформація про кафедру'),
(6, 'Теорії оптимальних процесів', 13, 'ktop@lnu.edu.ua', '(032) 239-47-91', 'Тестова інформація про кафедру'),
(7, 'Математичного моделювання соціально-економічних процесів', 13, 'kafmmsep@lnu.edu.ua', '(032) 239-43-51', 'Тестова інформація про кафедру');
SELECT setval('faculties_id_seq', 7);

INSERT INTO scientific_degrees(id, name) VALUES
(1, 'Кандидат фізико-математичних наук'),
(2, 'Доктор фізико-математичних наук');
SELECT setval('scientific_degrees_id_seq', 2);

INSERT INTO users (id, username, password_hash, first_name, middle_name, last_name, email, phone, info) VALUES
(1, 'oleh', '$2a$10$UW.mzCX478ew48EVaLFRTuVi4AT6v3Qo5XgPm./vR.6/7OZs0DLEW', 'Oleh', 'Bohdanovych', 'Zanevych', 'Oleh.Zanevych@gmail.com', '+380671694179', 'Funny guy:)');

INSERT INTO lecturers (id, first_name, middle_name, last_name, email, phone, info, department_id, scientific_degree_id, academic_status) VALUES
(2, 'Yarema', 'Hryhorovych', 'Savula', 'savula@lnu.edu.ua', '(032) 239-41-86', '<p>Date and place of birth: 14.05.1946, Stryj, Lviv Region, Ukraine ;</p><p>Education (degrees, dates, universities)</p><p>Master, 1969, Ivan Franko National University of Lviv;<br> Kandidate of Sciences (physics and mathematics), 11. 07. 1973, Ivan Franko National University of Lviv;<br> Doctor of Sciences (physics and mathematics), 19.07.1987, Kazan University, Russia;<br> 1976 – scientific internship at the Lomonosov State University of Moscow;<br> 1977 – 1978 scientific internship at the Brno University of Technology;<br> 1998 -1999 scientific internship at the Vienna University of Technology;</p><p>Career/Employment (employers, positions and dates)</p><p>Assistant, Ivan Franko National University of Lviv, from 1972 to 1974;<br> Docent, Ivan Franko National University of Lviv, from 1974 to 1988;<br> Professor, Ivan Franko National University of Lviv, from 1988 to1989;<br> Head of Department of Applied Mathematics, Ivan Franko National University of Lviv, from 1989 to 2014;<br> Dean of Faculty of Applied Mathematics and Informatics, Ivan Franko National University of Lviv, from 1996 to 2015;<br> Head of Department of Applied Mathematics, Ivan Franko National University of Lviv, from 2016;</p><p>Fellowships, Membership of Professional Societies</p><p>Member of National Committee of Theoretical and Applied Mechanics;<br> Member of Ukrainian Academy of Science of High School;<br> 1995 – Team Leader of the Eurasia Grant “ Connection of the Lviv University to the Internet”;<br> 2001-2004 Team Leader of the Intas Grant No: 00751;<br> 2005 – NATO scholarship at the Miskolc University, Hungary;<br> 2009-2010 Team Leader of the TEMPUS Grant HES-00232-2008 “Educational Centers Network on Modern Technologies of Local Governing”,<br> 2009, 2010 Lecturer of IX and X International Economic Forum –Lviv Truskavets;<br> 2012, Team member of Grant “Education for Entrepreneurship and Innovation (E4E&amp;I)”, Stockholm University, Sweden.<br> 2012-2014, Team member of Tempus grant EARL: Eastern-European Qualification Framework in the Field of Informatics and Management.</p>', 5, 2, 'PROFESSOR');
SELECT setval('users_id_seq', 2);