create table employee
(
    emp_id    int auto_increment
        primary key,
    id_number varchar(20) not null,
    emp_name  varchar(20) not null,
    gender    varchar(10) null,
    birth     varchar(20) not null,
    photo     longblob    not null,
    email     varchar(30) not null,
    phone     varchar(30) not null,
    tel       varchar(30) null,
    address   varchar(30) not null
);

create table attendance
(
    attendance_id    int auto_increment
        primary key,
    date             date                 not null,
    emp_id           int                  not null,
    total_hours      decimal(4, 2)        not null,
    is_late          tinyint(1) default 0 not null,
    is_early_leave   tinyint(1) default 0 not null,
    is_hours_not_met tinyint(1) default 0 not null,
    is_over_time     tinyint(1) default 0 not null,
    constraint attendance_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id)
);

create table login
(
    emp_id   int          not null
        primary key,
    password varchar(255) not null,
    constraint login_ibfk_1
        foreign key (emp_id) references employee (emp_id)
);

create table office_regions
(
    regions_id   int auto_increment
        primary key,
    latitude     double      not null,
    longitude    double      not null,
    regions_name varchar(20) not null
);

create table clock_time
(
    clock_time_id int auto_increment
        primary key,
    emp_id        int         not null,
    time          datetime    not null,
    type          varchar(10) not null,
    regions_id    int         null,
    constraint clock_time_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id),
    constraint clock_time_office_regions_regions_id_fk
        foreign key (regions_id) references office_regions (regions_id)
);


INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1000, 'A123456789', 'anddie', '男', '1997-09-04', 0x3132333435, 'd0981843347@Gmail.com', '0912345678', '02-12345678', '台北市中山區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1001, 'B987654321', '陳美麗', '女', '1985-05-15', 0x3534333231, 'lisi@email.com', '0987654321', null, '新北市板橋區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1002, 'C456789012', '李大宇', '男', '1992-11-30', 0x3938373635, 'wangwu@email.com', '0912345678', '02-23456789', '台北市信義區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1003, 'D789012345', '林靜心', '女', '1988-07-20', 0x3637383930, 'liuqi@email.com', '0912345678', '03-12345678', '桃園市中壢區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1004, 'E234567890', '張文彬', '男', '1995-03-05', 0x3233343536, 'chenba@email.com', '0987654321', '03-23456789', '新竹市東區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1005, 'F567890123', '黃美玲', '女', '1997-12-18', 0x3738393031, 'qianjiu@email.com', '0912345678', '04-12345678', '台中市西屯區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1006, 'G890123456', '劉小華', '男', '1987-09-02', 0x3334353637, 'sunti@email.com', '0987654321', '04-23456789', '台中市北區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1007, 'H123456789', '陳怡君', '女', '1994-06-10', 0x3839303132, 'zhouyiyi@email.com', '0912345678', '05-12345678', '台南市東區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1008, 'I345678901', '吳宏偉', '男', '1998-04-03', 0x3435363738, 'wushi@email.com', '0987654321', '05-23456789', '台南市南區');
INSERT INTO eipulse.employee (emp_id, id_number, emp_name, gender, birth, photo, email, phone, tel, address) VALUES (1009, 'J678901234', '許雅琪', '女', '1991-08-05', 0x3031323334, 'zhaoliu@email.com', '0987654321', '02-34567890', '台北市大安區');


INSERT INTO eipulse.office_regions (regions_id, latitude, longitude, regions_name) VALUES (1, 22.99944417097877, 120.21594456761729, '唯農分公司');
INSERT INTO eipulse.office_regions (regions_id, latitude, longitude, regions_name) VALUES (3, 22.993460934548654, 120.22674896027664, '路易莎東安店');
INSERT INTO eipulse.office_regions (regions_id, latitude, longitude, regions_name) VALUES (4, 23.02741940760638, 120.23923950479475, '台南新總圖');
INSERT INTO eipulse.office_regions (regions_id, latitude, longitude, regions_name) VALUES (5, 23.008144102678692, 120.21331863796566, '台南公園總圖');


INSERT INTO eipulse.login (emp_id, password) VALUES (1000, '$2a$10$AltyrKhiw2jBy0N/BVt1muESgZzzw8E5LKlej7sutl5j7K4UNFFEy');
INSERT INTO eipulse.login (emp_id, password) VALUES (1001, '$2a$10$jJ36mdWOKK12bvzKPfUqzuPgFWcz20O85I4xdWBEWVe2L8NDfcG6K');
INSERT INTO eipulse.login (emp_id, password) VALUES (1002, '100');
INSERT INTO eipulse.login (emp_id, password) VALUES (1003, '100');
INSERT INTO eipulse.login (emp_id, password) VALUES (1004, '100');
INSERT INTO eipulse.login (emp_id, password) VALUES (1005, '100');
INSERT INTO eipulse.login (emp_id, password) VALUES (1006, '100');
INSERT INTO eipulse.login (emp_id, password) VALUES (1007, '100');
INSERT INTO eipulse.login (emp_id, password) VALUES (1008, '100');
INSERT INTO eipulse.login (emp_id, password) VALUES (1009, '100');

