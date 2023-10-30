# EIPulseinSpringboot

#EEIT71 final project with spring boot
#彥宇9/21 將hibernate專案移至springboot3，進度完成entitys修改，部分controller及mail使用... 
#彥宇9/21 21:30 更改entitys設定關聯設定全部@JsonIgnor,contact併入employee,employeeService新增員工加入密碼自動生成及加密、查詢員工測試OK,LoginService忘記密碼method撰寫完畢未測試

-- 部門資料
create table dept
(
   dept_id     int auto_increment
       primary key,
   dept_name   varchar(255) not null,
   dept_office varchar(255) not null
);

--輸入部門資料
INSERT INTO dept (dept_name, dept_office) VALUES
('人事管理', 'A01'),
('公關部', 'B02'),
('行銷部', 'C03'),
('財務部', 'D04'),
('研發部', 'E05'),
('客服部', 'F06'),
('採購部', 'G07'),
('生產部', 'H08'),
('品質保證部', 'I09'),
('物流部', 'J10');

-- 權限表
create table permission
(
   permission_id        int auto_increment
       primary key,
   permission_name      varchar(50)  not null,
   permission_statement varchar(255) not null
);

-- 輸入權限
INSERT INTO permission (permission_name, permission_statement) VALUES
('計時人員', '允許讀取個人資料'),
('正職人員', '允許讀取個人資料'),
('部門主管', '允許簽核資料'),
('人事經理'', '允許管理員工資料'),
('系統測試員, '允許管理系統設定'),
('超級管理員', '允許管理系統設定');


-- 職位
create table title
(
   title_id   int auto_increment
       primary key,
   title_name varchar(50) not null,
   dept_id    int         not null,
   constraint title_dept_dept_id_fk
       foreign key (dept_id) references dept (dept_id)
);

-- 輸入職位
INSERT INTO title (title_name, dept_id) VALUES
('人事經理', 1),('人事系統測試助理', 1),('超級管理員', 1),
('公關經理', 2),('公關助理', 2),('公關專員', 2),
('行銷經理', 3),('行銷助理', 3),('行銷專員', 3),
('財務經理', 4),('財務助理', 4),('財務專員', 4),
('研發經理', 5),('研發助理', 5),('研發專員', 5),
('客服經理', 6),('客服助理', 6),('客服專員', 6),
('採購經理', 7),('採購助理', 7),('採購專員', 7),
('生產經理', 8),('生產助理', 8),('生產專員', 8),
('品保經理', 9),('品保助理', 9),('品保專員', 9),
('物流經理', 10),('物流助理', 10),('物流專員', 10);

-- 員工資料表
create table employee
(
   emp_id     int auto_increment
       primary key,
   emp_name   varchar(50)                        not null,
   birth      date                               not null,
   email      varchar(100)                       not null,
   id_number  varchar(50)                        not null,
   gender     varchar(10)                        not null,
   phone      varchar(20)                        not null,
   tel        varchar(50)                        null,
   photo_url  varchar(255)                       null,
   address    varchar(100)                       not null,
   title_id   int                                not null,
   hire_date  date                               null,
   leave_date date                               null,
   edit_date  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
   emp_state  varchar(50)                        not null,
   constraint employee_title_title_id_fk
       foreign key (title_id) references title (title_id)
);
-- 輸入員工資料 (離職特別標註10位)
INSERT INTO employee (emp_name, birth, email, id_number, gender, phone, tel, address, title_id, hire_date, leave_date, emp_state) VALUES
('王小明', '1990-01-01', 'wang@example.com', 'A123456789', '男', '0912345678', '02-12345678', '台北市', 1, '2022-01-01', null, '在職'),
('李小花', '1991-02-02', 'li@example.com', 'A987654321', '女', '0912345678', null, '台北市', 2, '2022-01-02', null, '在職'),
('張三', '1992-03-03', 'zhang@example.com', 'B123456789', '男', '0912345678', null, '新北市', 3, '2022-01-03', null, '在職'),
('李四', '1993-04-04', 'li4@example.com', 'B987654321', '女', '0912345678', '02-12345678', '新北市', 4, '2022-01-04', null, '在職'),
('王五', '1994-05-05', 'wang5@example.com', 'C123456789', '男', '0912345678', null, '桃園市', 5, '2022-01-05', null, '在職'),
('趙六', '1995-06-06', 'zhao@example.com', 'C987654321', '女', '0912345678', '03-12345678', '桃園市', 6, '2022-01-06', null, '在職'),
('孫七', '1996-07-07', 'sun@example.com', 'D123456789', '男', '0912345678', null, '台中市', 7, '2022-01-07', null, '在職'),
('周八', '1997-08-08', 'zhou@example.com', 'D987654321', '女', '0912345678', null, '台中市', 8, '2022-01-08', null, '在職'),
('吳九', '1998-09-09', 'wu@example.com', 'E123456789', '男', '0912345678', '04-12345678', '台南市', 9, '2022-01-09', null, '在職'),
('郭十', '1999-10-10', 'guo@example.com', 'E987654321', '女', '0912345678', null, '台南市', 10, '2022-01-10', null, '在職'),
('陳一', '2000-11-11', 'chen@example.com', 'F123456789', '男', '0912345678', null, '高雄市', 11, '2022-01-11', null, '在職'),
('林二', '2001-12-12', 'lin@example.com', 'F987654321', '女', '0912345678', '07-12345678', '高雄市', 12, '2022-01-12', null, '在職'),
('趙三', '2002-01-13', 'zhao3@example.com', 'G123456789', '男', '0912345678', null, '台北市', 13, '2022-01-13', null, '在職'),
('孫四', '2003-02-14', 'sun4@example.com', 'G987654321', '女', '0912345678', null, '台北市', 14, '2022-01-14', null, '在職'),
('周五', '2004-03-15', 'zhou5@example.com', 'H123456789', '男', '0912345678', '02-12345678', '新北市', 15, '2022-01-15', null, '在職'),
('吳六', '2005-04-16', 'wu6@example.com', 'H987654321', '女', '0912345678', null, '新北市', 16, '2022-01-16', null, '在職'),
('郭七', '2006-05-17', 'guo7@example.com', 'I123456789', '男', '0912345678', null, '桃園市', 17, '2022-01-17', null, '在職'),
('陳八', '2007-06-18', 'chen8@example.com', 'I987654321', '女', '0912345678', '03-12345678', '桃園市', 18, '2022-01-18', null, '在職'),
('林九', '2008-07-19', 'lin9@example.com', 'J123456789', '男', '0912345678', null, '台中市', 19, '2022-01-19', null, '在職'),
('張十', '2009-08-20', 'zhang10@example.com', 'J987654321', '女', '0912345678', null, '台中市', 20, '2022-01-20', null, '在職'),
('李十一', '1990-09-21', 'li11@example.com', 'K123456789', '男', '0912345678', null, '台北市', 1, '2022-01-21', '2022-09-21', '離職'),
('王十二', '1991-10-22', 'wang12@example.com', 'K987654321', '女', '0912345678', '02-12345678', '台北市', 2, '2022-01-22', '2022-09-22', '離職'),
('張十三', '1992-11-23', 'zhang13@example.com', 'L123456789', '男', '0912345678', null, '新北市', 3, '2022-01-23', '2022-09-23', '離職'),
('李十四', '1993-12-24', 'li14@example.com', 'L987654321', '女', '0912345678', null, '新北市', 4, '2022-01-24', '2022-09-24', '離職'),
('王十五', '1994-01-25', 'wang15@example.com', 'M123456789', '男', '0912345678', null, '桃園市', 5, '2022-01-25', '2022-09-25', '離職'),
('趙十六', '1995-02-26', 'zhao16@example.com', 'M987654321', '女', '0912345678', '03-12345678', '桃園市', 6, '2022-01-26', '2022-09-26', '離職'),
('孫十七', '1996-03-27', 'sun17@example.com', 'N123456789', '男', '0912345678', null, '台中市', 7, '2022-01-27', '2022-09-27', '離職'),
('周十八', '1997-04-28', 'zhou18@example.com', 'N987654321', '女', '0912345678', null, '台中市', 8, '2022-01-28', '2022-09-28', '離職'),
('吳十九', '1998-05-29', 'wu19@example.com', 'O123456789', '男', '0912345678', '04-12345678', '台南市', 9, '2022-01-29', '2022-09-29', '離職'),
('郭二十', '1999-06-30', 'guo20@example.com', 'O987654321', '女', '0912345678', null, '台南市', 10, '2022-01-30', '2022-09-30', '離職');





-- 緊急聯絡人
create table emergency
(
   emergency_id   int auto_increment
       primary key,
   emergency_name varchar(50) not null,
   phone          varchar(50) not null,
   relation       varchar(20) not null,
   emp_id         int         not null,
   constraint emergency_employee_emp_id_fk
       foreign key (emp_id) references employee (emp_id)
);

-- 輸入緊急連絡人
INSERT INTO emergency (emergency_name, phone, relation, emp_id) VALUES
('王大明', '0911111111', '父親', 1),
('李大花', '0922222222', '母親', 2),
('張四', '0933333333', '兄弟', 3),
('李五', '0944444444', '姐妹', 4),
('王六', '0955555555', '朋友', 5),
('趙七', '0966666666', '父親', 6),
('孫八', '0977777777', '母親', 7),
('周九', '0988888888', '兄弟', 8),
('吳十', '0999999999', '姐妹', 9),
('郭一一', '0910101010', '朋友', 10),
('陳一二', '0912121212', '父親', 11),
('林一三', '0913131313', '母親', 12),
('趙一四', '0914141414', '兄弟', 13),
('孫一五', '0915151515', '姐妹', 14),
('周一六', '0916161616', '朋友', 15),
('吳一七', '0917171717', '父親', 16),
('郭一八', '0918181818', '母親', 17),
('陳一九', '0919191919', '兄弟', 18),
('林二零', '0920202020', '姐妹', 19),
('趙二一', '0921212121', '朋友', 20),
('孫二二', '0922222222', '父親', 21),
('周二三', '0923232323', '母親', 21),
('吳二四', '0924242424', '兄弟', 23),
('郭二五', '0925252525', '姐妹', 24),
('陳二六', '0926262626', '朋友', 25),
('林二七', '0927272727', '父親', 26),
('趙二八', '0928282828', '母親', 27),
('孫二九', '0929292929', '兄弟', 28),
('周三零', '0930303030', '姐妹', 29),
('吳三一', '0931313131', '朋友', 30);


-- 權限中介表
create table permission_info
(
   id            int auto_increment
       primary key,
   emp_id        int not null,
   permission_id int not null,
   constraint permission_info_employee_emp_id_fk
       foreign key (emp_id) references employee (emp_id),
   constraint permission_info_permission_permission_id_fk
       foreign key (permission_id) references permission (permission_id)
);

-- 輸入中介表
INSERT INTO permission_info (emp_id, permission_id) VALUES
(1, 4),  -- 人事經理
(2, 5),  --系統測試員 
(3, 6),  -- 超級管理員
(4, 1),  -- 財務助理
(5, 1),  -- 研發專員
(6, 1),  -- 客服助理
(7, 1),  -- 採購專員
(8, 1),  -- 生產助理
(9, 1),  -- 品保專員
(10, 1), -- 物流助理
(11, 4), -- 人事經理
(12, 1), -- 公關助理
(13, 1), -- 行銷專員
(14, 1), -- 財務助理
(15, 1), -- 研發專員
(16, 1), -- 客服助理
(17, 1), -- 採購專員
(18, 1), -- 生產助理
(19, 1), -- 品保專員
(20, 1), -- 物流助理
(21, 4), -- 人事經理
(22, 1), -- 公關助理
(23, 1), -- 行銷專員
(24, 1), -- 財務助理
(25, 1), -- 研發專員
(26, 1), -- 客服助理
(27, 1), -- 採購專員
(28, 1), -- 生產助理
(29, 1), -- 品保專員
(30, 1); -- 物流助理





-- 權限紀錄表
create table permission_move
(
   id                     int auto_increment
       primary key,
   emp_id                 int                                not null,
   before_permission_name varchar(50)                        not null,
   after_permission_name  varchar(50)                        not null,
   reason                 varchar(255)                       not null,
   effect_date            date                                   null,
   approver               varchar(50)                        null,
   edit_date              datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
   constraint permission_move_employee_emp_id_fk
       foreign key (emp_id) references employee (emp_id)
);

-- 輸入權限紀錄表
INSERT INTO permission_move (emp_id, before_permission_name, after_permission_name, reason, effect_date, approver) VALUES
(1, '允許管理員工資料', '允許簽核資料', '升職為部門主管', '2022-10-01', '人事經理'),
(2, '允許讀取個人資料', '允許管理系統設定', '轉職為人事系統測試助理', '2022-09-15', '人事經理'),
(3, '允許讀取個人資料', '允許管理系統設定', '升職超級管理員', '2022-08-20', '行銷經理'),
(4, '允許讀取個人資料', '允許管理員工資料', '轉職為人事專員', '2022-07-10', '人事經理'),
(5, '允許讀取個人資料', '允許簽核資料', '轉職為研發經理', '2022-06-05', '研發經理'),
(6, '允許讀取個人資料', '允許簽核資料', '升職為部門主管', '2022-05-01', '客服經理'),
(7, '允許讀取個人資料', '允許管理員工資料', '轉職為人事專員', '2022-04-15', '人事經理'),
(8, '允許讀取個人資料', '允許讀取個人資料', '轉職為生產專員', '2022-03-20', '研發經理'),
(9, '允許讀取個人資料', '允許簽核資料', '升職為部門主管', '2022-02-10', '品保經理'),
(10, '允許讀取個人資料', '允許管理員工資料', '轉職為人事專員', '2022-01-05', '人事經理');


-- 離職申請
create table resign_record
(
   resign_id     int auto_increment
       primary key,
   emp_id        int                                not null,
   reason        varchar(255)                       not null,
   leave_date    date                               null,
   approver      varchar(50)                        null,
   edit_date     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
   quit          tinyint(1)                         null,
   transfer_form tinyint(1)                         null,
   constraint emp_id
       unique (emp_id),
   constraint resign_record_employee_emp_id_fk
       foreign key (emp_id) references employee (emp_id)
);

-- 輸入離職申請
INSERT INTO resign_record (emp_id, reason, leave_date, approver, quit, transfer_form) VALUES
(21, '個人原因', '2022-09-21', '人事經理', 1, 1),
(22, '轉職', '2022-09-22', '人事經理', 1, 1),
(23, '家庭因素', '2022-09-23', '人事經理', 1, 1),
(24, '轉職', '2022-09-24', '人事經理', 1, 1),
(25, '個人原因', '2022-09-25', '人事經理', 1, 1),
(26, '家庭因素', '2022-09-26', '人事經理', 1, 1),
(27, '轉職', '2022-09-27', '人事經理', 1, 1),
(28, '個人原因', '2022-09-28', '人事經理', 1, 1),
(29, '家庭因素', '2022-09-29', '人事經理', 1, 1),
(30, '轉職', '2022-09-30', '人事經理', 1, 1);

-- 職位異動表
create table title_move
(
   id               int auto_increment
       primary key,
   emp_id           int                                not null,
   before_dept_info varchar(255)                       not null,
   after_dept_info  varchar(255)                       not null,
   reason           varchar(50)                        not null,
   effect_date      date                               null,
   approver         varchar(20)                        null,
   edit_date        datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
   constraint title_move_employee_emp_id_fk
       foreign key (emp_id) references employee (emp_id)
);

-- 輸入職位異動表
INSERT INTO title_move (emp_id, before_dept_info, after_dept_info, reason, effect_date, approver) VALUES
(1, '人事專員', '人事部門主管', '升職為部門主管', '2022-10-01', '人事經理'),
(2, '技術支持', '人事系統測試助理', '轉職為人事系統測試助理', '2022-09-15', '人事經理'),
(3, '行銷專員', '行銷部門主管', '升職超級管理員', '2022-08-20', '行銷經理'),
(4, '客服專員', '人事專員', '轉職為人事專員', '2022-07-10', '人事經理'),
(5, '研發專員', '研發部門主管', '轉職為研發經理', '2022-06-05', '研發經理'),
(6, '客服專員', '客服部門主管', '升職為部門主管', '2022-05-01', '客服經理'),
(7, '採購專員', '人事專員', '轉職為人事專員', '2022-04-15', '人事經理'),
(8, '生產專員', '生產專員', '轉職為生產專員', '2022-03-20', '研發經理'),
(9, '品保專員', '品保部門主管', '升職為部門主管', '2022-02-10', '品保經理'),
(10, '物流專員', '人事專員', '轉職為人事專員', '2022-01-05', '人事經理');
