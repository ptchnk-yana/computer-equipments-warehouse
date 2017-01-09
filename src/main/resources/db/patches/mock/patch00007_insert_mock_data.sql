DELETE FROM device;
DELETE FROM room;
DELETE FROM user;
DELETE FROM device_type;
DELETE FROM building;

INSERT INTO building (id, name, comment) VALUES (1, 'ИКС', 'ИКС - Институт Компьютерных Систем. Ранее известен как ФАВТ - факультет автоматики и вычислительной техники');
INSERT INTO building (id, name, comment) VALUES (2, 'Админ', 'Админ - Административное здание института');
INSERT INTO building (id, name, comment) VALUES (3, 'ГУК', 'ГУК - Главный Учебный Корпус где проходят занятия');

INSERT INTO device_type (id, title, image_url) VALUES (1, 'Компьютер в сборе', '/tmp/computer.png');
INSERT INTO device_type (id, title, image_url) VALUES (2, 'Дополнительный монитор', '/tmp/monitor.png');
INSERT INTO device_type (id, title, image_url) VALUES (3, 'Процессор из кластера', '/tmp/processor.png');
INSERT INTO device_type (id, title, image_url) VALUES (4, 'Принтер', '/tmp/printer.png');
INSERT INTO device_type (id, title, image_url) VALUES (5, 'Телефон', '/tmp/phone.png');
INSERT INTO device_type (id, title, image_url) VALUES (6, 'Проектор', '/tmp/projector.png');

INSERT INTO user (id, name, password, privilege) VALUES (1, 'admin', 'admin', 'ADMIN');
INSERT INTO user (id, name, password, privilege) VALUES (2, 'vera.viktorovna', '12345', 'OPERATOR');
INSERT INTO user (id, name, password, privilege) VALUES (3, 'oleg.nikolayevich', '12345', 'OPERATOR');
INSERT INTO user (id, name, password, privilege) VALUES (4, 'viktoriya.mickaylovna', '12345', 'OPERATOR');
INSERT INTO user (id, name, password, privilege) VALUES (5, 'alexandr.anatolievich', '12345', 'OPERATOR');
INSERT INTO user (id, name, password, privilege) VALUES (6, 'svetlana.leonidovna', '12345', 'OPERATOR');

INSERT INTO room (id, user_id, building_id, number, title) VALUES (1, 2, 1, 110, 'Отдел комплектования');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (2, 2, 1, 113, 'Отдел научной обработки документов');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (3, 2, 1, 112, 'Аудитория');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (4, 2, 1, 109, 'Научно методический отдел');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (5, 2, 1, 105, 'Аудитория');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (6, 4, 1, 107, 'Аудитория');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (7, 4, 1, 106, 'Сектор интернет технологий');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (8, 4, 1, 103, 'Директор');

INSERT INTO room (id, user_id, building_id, number, title) VALUES (9, 2, 2, 102, 'Лаборатория информационных технологий');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (10, 3, 2, 209, 'Базы данных');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (11, 3, 2, 210, 'Хранилище');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (12, 3, 2, 204, 'Аудитория амфитеатр');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (13, 3, 2, 205, 'Абонимент студентов');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (14, 3, 2, 202, 'Читальный зал');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (15, 4, 2, 203, 'Электронный Читальный зал');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (16, 5, 2, 303, 'Отдел студенческих работ');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (17, 6, 2, 401, 'Центр последипломного образования');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (18, 6, 2, 404, 'Отдел хранения файлов');

INSERT INTO room (id, user_id, building_id, number, title) VALUES (19, 2, 3, 101, 'Аудитория');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (20, 2, 3, 108, 'Аудитория');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (21, 3, 3, 116, 'Информационно библиографический отдел');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (22, 5, 3, 302, 'Социально-гуманитарный зал');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (23, 5, 3, 301, 'Иностранный зал');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (24, 6, 3, 402, 'Обслуживание студентов заочной формы обучения');
INSERT INTO room (id, user_id, building_id, number, title) VALUES (25, 6, 3, 403, 'Техническая лаборатория');

INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (1, 2, 1, -1367336112, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (2, 2, 3, -455078677, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (3, 2, 4, 1258528844, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (4, 2, 4, 2135555059, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (5, 3, 2, 1886133837, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (6, 3, 1, -2038361974, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (7, 3, 4, 416979075, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (8, 3, 5, -575621617, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (9, 4, 4, 431520513, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (10, 4, 3, -1746910162, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (11, 4, 3, -998845115, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (12, 4, 2, 536765672, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (13, 4, 5, 149940396, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (14, 5, 1, -703522357, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (15, 5, 3, 1788017275, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (16, 5, 1, 1399149342, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (17, 5, 1, -1918165650, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (18, 6, 3, 1287391269, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (19, 6, 4, -2079392050, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (20, 6, 3, 2041522487, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (21, 7, 5, -997843823, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (22, 7, 5, 649123009, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (23, 9, 1, -1365851953, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (24, 9, 1, -1123027747, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (25, 10, 5, 1943922316, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (26, 10, 4, 1479432136, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (27, 10, 1, -1210092151, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (28, 10, 5, 215382232, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (29, 10, 4, 1902561061, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (30, 11, 5, 1591141862, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (31, 11, 1, -104605250, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (32, 11, 4, 996207213, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (33, 11, 3, -1536013902, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (34, 12, 1, 1335629666, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (35, 12, 3, 444095875, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (36, 12, 4, 723976342, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (37, 12, 2, -94273830, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (38, 13, 1, 1525604468, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (39, 14, 1, -776666358, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (40, 14, 5, -64630384, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (41, 14, 4, -95719789, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (42, 14, 1, 1595644902, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (43, 14, 3, 1243720602, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (44, 15, 2, 202863291, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (45, 15, 4, -218498568, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (46, 15, 2, -2064688456, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (47, 15, 5, 1622282933, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (48, 17, 4, 1946317144, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (49, 17, 1, 1928213565, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (50, 18, 5, -646310528, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (51, 18, 5, -698692769, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (52, 19, 5, 1505713393, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (53, 20, 5, 1316197290, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (54, 20, 3, 1983663540, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (55, 20, 4, 652085155, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (56, 21, 5, -489040400, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (57, 21, 4, 1920433525, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (58, 21, 5, -284014, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (59, 21, 2, -611993439, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (60, 21, 4, -1462732552, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (61, 22, 4, -251008738, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (62, 22, 1, -1024199160, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (63, 22, 1, 334214931, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (64, 24, 5, -2087449126, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (65, 24, 1, 33140704, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (66, 24, 4, 1531317814, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (67, 24, 1, 1689046975, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (68, 25, 5, -1707802978, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (69, 25, 3, -1159346571, '');
INSERT INTO device (id ,room_id, device_type_id, hash, description) VALUES (70, 25, 2, -842270845, '');

CREATE TEMP TABLE t1 AS
SELECT 
    d.id AS id, concat(t.title, ' для ', r.number) AS s 
FROM 
    device d 
    INNER JOIN device_type t ON t.id = d.device_type_id 
    INNER JOIN room r ON r.id = d.room_id;

UPDATE device d SET (description) = (SELECT s FROM t1 WHERE t1.id = d.id);

DROP TABLE t1;
