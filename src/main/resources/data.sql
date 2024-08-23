INSERT INTO tb_user (point, created_at, id, updated_at, name) VALUES (10000, '2024-08-23 16:32:51.619077', 1, '2024-08-23 16:32:51.619077', '울버린');
INSERT INTO tb_user (point, created_at, id, updated_at, name) VALUES (10000, '2024-08-23 16:32:51.629120', 2, '2024-08-23 16:32:51.629120', '푸우');
INSERT INTO tb_user (point, created_at, id, updated_at, name) VALUES (10000, '2024-08-23 16:32:51.629306', 3, '2024-08-23 16:32:51.629306', '마리오');
INSERT INTO tb_user (point, created_at, id, updated_at, name) VALUES (10000, '2024-08-23 16:32:51.629383', 4, '2024-08-23 16:32:51.629383', '기영이');
UPDATE tb_user_seq SET next_val = 5;

INSERT INTO tb_menu (price, created_at, id, updated_at, name) VALUES (1500, '2024-08-23 16:35:37.696371', 1, '2024-08-23 16:35:37.696371', '아이스 아메리카노');
INSERT INTO tb_menu (price, created_at, id, updated_at, name) VALUES (2500, '2024-08-23 16:35:37.704130', 2, '2024-08-23 16:35:37.704130', '더치 아메리카노');
INSERT INTO tb_menu (price, created_at, id, updated_at, name) VALUES (3000, '2024-08-23 16:35:37.704250', 3, '2024-08-23 16:35:37.704250', '아이스티');
INSERT INTO tb_menu (price, created_at, id, updated_at, name) VALUES (4500, '2024-08-23 16:35:37.704314', 4, '2024-08-23 16:35:37.704314', '카페라떼');
UPDATE tb_menu_seq SET next_val = 5;