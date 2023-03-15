INSERT INTO user (type_user, id_user, pw_user, name_user, phone_user, email_user, dpt_user, gender_user)
VALUES (2, "202001488", "1234", "김문기", "010-3778-9690", "mkmkqoa@gmail.com", "컴퓨터공학부", 0),
       (2, "202001498", "1234", "김진", "010-9374-9846", "k.j11n.30@gmail.com", "컴퓨터공학부", 1),
       (2, "202001541", "1234", "이용인", "010-7686-7103", "0517lyi@naver.com", "컴퓨터공학부", 1),
       (2, "202001549", "1234", "장희권", "010-7940-7706", "jang010505@naver.com", "컴퓨터공학부", 0),
       (1, "20202020", "1234", "백형부", "000111222", "hbbaek359@gmail.com", "컴퓨터공학부", 0);


INSERT INTO auditor (id_user, id_subject)
VALUES (1, 1), (1, 2),
       (2, 1), (2, 2),
       (3, 1), (3, 2),
       (4, 1), (4, 2),
       (5, 1), (5, 2);

-- delete from attendance;