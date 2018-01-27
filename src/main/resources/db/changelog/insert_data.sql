insert into category (code, title) values('cat1', 'title1');
insert into category (code, title) values('cat7', 'title7');

insert into category (code, title, super_category_code) values('cat2', 'title2', 'cat1');
insert into category (code, title, super_category_code) values('cat3', 'title3', 'cat2');
insert into category (code, title, super_category_code) values('cat4', 'title4', 'cat2');
insert into category (code, title, super_category_code) values('cat5', 'title5', 'cat2');
insert into category (code, title, super_category_code) values('cat6', 'title6', 'cat1');
insert into category (code, title, super_category_code) values('cat8', 'title8', 'cat7');
insert into category (code, title, super_category_code) values('cat9', 'title9', 'cat7');
insert into category (code, title, super_category_code) values('cat10', 'title10', 'cat8');
insert into category (code, title, super_category_code) values('cat11', 'title11', 'cat9');
insert into category (code, title, super_category_code) values('cat12', 'title12', 'cat9');


insert into book (title, category_code) values('book1', 'cat3');
insert into book (title, category_code) values('book1', 'cat4');
insert into book (title, category_code) values('book2', 'cat2');
insert into book (title, category_code) values('book2', 'cat6');
insert into book (title, category_code) values('book3', 'cat8');
insert into book (title, category_code) values('book3', 'cat11');
insert into book (title, category_code) values('book4', 'cat7');
insert into book (title, category_code) values('book5', 'cat10');
insert into book (title, category_code) values('book5', 'cat11');
insert into book (title, category_code) values('book6', 'cat11');
insert into book (title, category_code) values('book6', 'cat12');
insert into book (title, category_code) values('book7', 'cat1');
insert into book (title, category_code) values('book8', 'cat6');


insert into subscriber (email, category_code) values('email1', 'cat2');
insert into subscriber (email, category_code) values('email1', 'cat7');
insert into subscriber (email, category_code) values('email2', 'cat3');
insert into subscriber (email, category_code) values('email2', 'cat6');
insert into subscriber (email, category_code) values('email2', 'cat8');
insert into subscriber (email, category_code) values('email3', 'cat7');