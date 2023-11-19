-- 初建請選取語句創建資料庫 drop 亦同
-- create database  eipulsedemo;
-- drop database eipulsedemo;
 	
-- drop表用 自取
-- drop  table if exists 
create table if not exists chats
(
    chats_id   int auto_increment
        primary key,
    Created_at datetime(6)  null,
    Emp_id     varchar(255) null,
    File       varchar(255) null,
    Message    varchar(255) null,
    Room_id    int          null,
    user_ip    varchar(255) null
);

create table if not exists dept
(
    dept_id     int auto_increment
        primary key,
    dept_name   varchar(255) not null,
    dept_office varchar(255) not null
);

create table if not exists form_status
(
    status_id   int auto_increment
        primary key,
    Description varchar(255) null
);

create table if not exists form_type
(
    Type_id   int auto_increment
        primary key,
    Type_name varchar(255) null
);

create table if not exists holiday
(
    holiday_id   int          not null
        primary key,
    holiday_date datetime(6)  null,
    holiday_name varchar(255) null
);

create table if not exists leave_type
(
    id     int auto_increment
        primary key,
    days   int           null,
    type   varchar(255)  null,
    status int default 1 null,
    remark varchar(255)  null
);

create table if not exists message_entity
(
    message_id int auto_increment
        primary key,
    chat       varchar(255) null,
    Created_at datetime(6)  null,
    file       varchar(255) null,
    receiver   int          null,
    sender     int          null,
    user1      int          null,
    user2      int          null
);

create table if not exists office_regions
(
    regions_id   int auto_increment
        primary key,
    latitude     double       null,
    longitude    double       null,
    regions_name varchar(255) null
);

create table if not exists overtime_type
(
    id   int auto_increment
        primary key,
    type varchar(255) null
);

create table if not exists permission
(
    permission_id        int auto_increment
        primary key,
    permission_name      varchar(50)  not null,
    permission_statement varchar(255) not null
);

create table if not exists product_type
(
    type_id   int auto_increment
        primary key,
    type_name varchar(30) not null
);

create table if not exists sub_type
(
    sub_type_id     int auto_increment
        primary key,
    product_type_id int         not null,
    sub_name        varchar(20) not null,
    constraint sub_type_product_type_type_id_fk
        foreign key (product_type_id) references product_type (type_id)
);

create table if not exists product
(
    product_id     int auto_increment
        primary key,
    product_name   varchar(30)                        not null,
    sub_type_id    int                                not null,
    description    varchar(50)                        null,
    price          int                                not null,
    stock_quantity int                                not null,
    image_url      varchar(255)                       null,
    created_at     datetime default CURRENT_TIMESTAMP null,
    updated_at     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    status         varchar(20)                        not null,
    constraint product_sub_type_sub_type_id_fk
        foreign key (sub_type_id) references sub_type (sub_type_id)
);

create table if not exists subject_type
(
    subject_id     int auto_increment
        primary key,
    subject_name   varchar(30) not null,
    calculate_type varchar(10) not null,
    frequency      varchar(10) not null,
    amount_default int         null,
    status         varchar(10) not null
)auto_increment=1000;

create table if not exists title
(
    title_id   int auto_increment
        primary key,
    title_name varchar(50) not null,
    dept_id    int         not null,
    constraint title_dept_dept_id_fk
        foreign key (dept_id) references dept (dept_id)
);

create table if not exists employee
(
    emp_id      int auto_increment
        primary key,
    emp_name    varchar(50)          not null,
    birth       date                 not null,
    password    varchar(255)         not null,
    email       varchar(100)         not null,
    id_number   varchar(50)          not null,
    gender      varchar(10)          not null,
    phone       varchar(20)          not null,
    tel         varchar(50)          null,
    photo_url   varchar(1000)         null,
    address     varchar(255)         null,
    title_id    int                  not null,
    hire_date   date                 null,
    leave_date  date                 null,
    edit_date   date default (now()) null,
    emp_state   varchar(50)          not null,
    emp_line_id varchar(255)         null,
    constraint employee_pk
        unique (emp_line_id),
    constraint employee_title_title_id_fk
        foreign key (title_id) references title (title_id)
)auto_increment=1000;

create table if not exists attendance
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

create table if not exists cart
(
    cart_id    int auto_increment
        primary key,
    emp_id     int                                not null,
    created_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint shopping_cart_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id)
);

create table if not exists cart_item
(
    cart_item_id int auto_increment
        primary key,
    cart_id      int                                not null,
    product_id   int                                not null,
    quantity     int                                not null,
    created_at   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    updated_at   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint cart_item_product_product_id_fk
        foreign key (product_id) references product (product_id),
    constraint cart_item_shopping_cart_cart_id_fk
        foreign key (cart_id) references cart (cart_id)
);

create table if not exists clock_time
(
    clock_time_id int auto_increment
        primary key,
    emp_id        int          not null,
    time          datetime(6)  null,
    type          varchar(255) null,
    regions_id    int          null,
    constraint clock_time_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id),
    constraint clock_time_office_regions_regions_id_fk
        foreign key (regions_id) references office_regions (regions_id)
);

create table if not exists emergency
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

create table if not exists emp_salary_info
(
    emp_id                       int           not null
        primary key,
    basic_salary                 int           not null,
    labor_insurance_grade        int           not null,
    labor_volunteer_pension_rate decimal(5, 2) not null,
    health_insurance_grade       int           not null,
    family_dependants_num        int           not null,
    welfare_benefits_deduction   varchar(5)    not null,
    constraint emp_salary_info_ibfk_1
        foreign key (emp_id) references employee (emp_id)
);

create table if not exists event
(
    event_id    int auto_increment
        primary key,
    title       varchar(255)  not null,
    start       datetime      not null,
    end         datetime      not null,
    description varchar(1000) null,
    user_id     int           not null,
    constraint event_ibfk_1
        foreign key (user_id) references employee (emp_id)
);

create index user_id
    on event (user_id);

create table if not exists form_record
(
    form_id          int auto_increment
        primary key,
    emp_id           int         null,
    type_id          int         null,
    End_date         datetime(6) null,
    Start_date       datetime(6) null,
    status_id        int         null,
    Termination_date datetime(6) null,
    constraint form_record_ibfk_1
        foreign key (type_id) references form_type (Type_id),
    constraint form_record_ibfk_2
        foreign key (emp_id) references employee (emp_id),
    constraint form_record_ibfk_3
        foreign key (status_id) references form_status (status_id)
);

create table if not exists apply_leave
(
    form_id    int          not null
        primary key,
    type       int          not null,
    reason     varchar(255) null,
    days       int          not null,
    hours      int          not null,
    start_time datetime(6)  null,
    file       varchar(255) null,
    constraint apply_leave_ibfk_1
        foreign key (form_id) references form_record (form_id),
    constraint apply_leave_ibfk_2
        foreign key (type) references leave_type (id)
);

create index type
    on apply_leave (type);

create table if not exists apply_overtime
(
    form_id    int          not null
        primary key,
    type       int          not null,
    reason     varchar(255) null,
    date       date         not null,
    start_time time(6)      null,
    end_time   time(6)      null,
    file       varchar(255) null,
    constraint apply_overtime_ibfk_1
        foreign key (form_id) references form_record (form_id),
    constraint apply_overtime_ibfk_2
        foreign key (type) references overtime_type (id)
);

create index type
    on apply_overtime (type);

create table if not exists apply_resignation
(
    file          varchar(255) null,
    leave_date    date         null,
    quit          bit          null,
    reason        varchar(255) not null,
    transfer_form bit          null,
    form_id       int          not null
        primary key,
    constraint FKgsu7ecqqp4lk59t57f7npw6sn
        foreign key (form_id) references form_record (form_id)
);

create table if not exists form_eventlog
(
    event_id   int auto_increment
        primary key,
    form_id    int         null,
    End_date   datetime(6) null,
    Start_date datetime(6) null,
    head_count int         null,
    sequence   int         null,
    status_id  int         null,
    constraint form_eventlog_ibfk_1
        foreign key (status_id) references form_status (status_id),
    constraint form_eventlog_ibfk_2
        foreign key (form_id) references form_record (form_id)
);

create table if not exists form_audit_eventlog
(
    event_id    int auto_increment
        primary key,
    eventLog_id int          null,
    auditor     int          null,
    message     varchar(255) null,
    End_date    datetime(6)  null,
    status_id   int          null,
    constraint form_audit_eventlog_ibfk_1
        foreign key (status_id) references form_status (status_id),
    constraint form_audit_eventlog_ibfk_2
        foreign key (auditor) references employee (emp_id),
    constraint form_audit_eventlog_ibfk_3
        foreign key (eventLog_id) references form_eventlog (event_id)
);

create index auditor
    on form_audit_eventlog (auditor);

create index eventLog_id
    on form_audit_eventlog (eventLog_id);

create index status_id
    on form_audit_eventlog (status_id);

create index form_id
    on form_eventlog (form_id);

create index status_id
    on form_eventlog (status_id);

create index emp_id
    on form_record (emp_id);

create index status_id
    on form_record (status_id);

create index type_id
    on form_record (type_id);

create table if not exists news
(
    news_id    int auto_increment
        primary key,
    title      varchar(255)                         not null,
    content    text                                 not null,
    file       longblob                             null,
    post_time  datetime   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    publisher  int                                  not null,
    is_visible tinyint(1) default 1                 not null,
    constraint news_ibfk_1
        foreign key (publisher) references employee (emp_id)
);

create index publisher
    on news (publisher);

create table if not exists `order`
(
    order_id    int auto_increment
        primary key,
    emp_id      int                                not null,
    total_price int                                null,
    status      varchar(10)                        null,
    created_at  datetime default CURRENT_TIMESTAMP null,
    updated_at  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint order_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id)
);

create table if not exists order_item
(
    order_item_id int auto_increment
        primary key,
    order_id      int not null,
    product_id    int not null,
    quantity      int not null,
    total_price   int not null,
    constraint order_item_order_order_id_fk
        foreign key (order_id) references `order` (order_id),
    constraint order_item_product_product_id_fk
        foreign key (product_id) references product (product_id)
);

create table if not exists permission_info
(
    emp_id        int not null
        primary key,
    permission_id int not null,
    constraint permission_info_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id),
    constraint permission_info_permission_permission_id_fk
        foreign key (permission_id) references permission (permission_id)
);

create table if not exists permission_move
(
    id                     int auto_increment
        primary key,
    emp_id                 int                                not null,
    before_permission_name varchar(50)                        not null,
    after_permission_name  varchar(50)                        not null,
    reason                 varchar(255)                       not null,
    effect_date            date                               null,
    approver               varchar(50)                        null,
    edit_date              datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint permission_move_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id)
);

create table if not exists resign_record
(
    resign_id     int auto_increment
        primary key,
    emp_id        int          not null,
    reason        varchar(255) not null,
    leave_date    date         null,
    approver      varchar(50)  null,
    edit_date     date         null,
    quit          tinyint(1)   null,
    transfer_form tinyint(1)   null,
    constraint emp_id
        unique (emp_id),
    constraint resign_record_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id)
);

create table if not exists salary_history
(
    id              int auto_increment
        primary key,
    emp_id          int                                not null,
    original_salary int                                null,
    adjust_salary   int                                null,
    remark          varchar(50)                        null,
    adjusted_date   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint SalaryHistory_employee_emp_id_fk
        foreign key (emp_id) references employee (emp_id)
);

create table if not exists salary_month_record
(
    id           int auto_increment
        primary key,
    sl_year      int                                not null,
    sl_month     int                                not null,
    emp_id       int                                not null,
    add_sum      int                                not null,
    dedu_sum     int                                not null,
    net_salary   int                                not null,
    created_date datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint salary_month_record_ibfk_1
        foreign key (emp_id) references employee (emp_id)
);

create table if not exists salary_detail
(
    id           int auto_increment
        primary key,
    emp_id       int                                not null,
    subject_id   int                                not null,
    amount       int                                not null,
    created_date datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    record_id    int                                not null,
    constraint salary_detail_ibfk_1
        foreign key (emp_id) references employee (emp_id),
    constraint salary_detail_ibfk_2
        foreign key (subject_id) references subject_type (subject_id),
    constraint salary_detail_ibfk_3
        foreign key (record_id) references salary_month_record (id)
);

create table if not exists title_move
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


insert into dept (dept_name, dept_office)
values  ( '人資部', 'A01'),
        ('工程部', 'A02'),
        ('研發部', 'A03');

insert into title ( title_name, dept_id)
values  ( '人事經理', 1),
        ( '人事專員', 1),
        ( '工程部經理 ', 2),
        ( '製程工程師', 2),
        ( '設備工程師', 2),
        ( '研發部經理', 3),
        ( '研發工程師', 3),
        ( '研發助理', 3);

insert into form_status ( Description)
values  ( '審核中'),
        ( '已批准'),
        ( '已拒絕'),
        ( '待處理'),
        ( '已關閉'),
        ( '已撤回');

insert into form_type ( Type_name)
values  ( '請假'),
        ( '加班'),
        ( '離職'),
        ( '出差');

insert into leave_type ( days, type, status, remark)
values  ( 3, '半年特休', 1, '半年3天'),
        ( 4, '一年特休', 1, '滿一年多四天'),
        ( 3, '半薪病假', 1, '超過3天都為事假'),
        ( 12, '生理假', 2, ''),
        ( 30, '事假', 1, ''),
        ( 7, '婚假', 1, ''),
        ( 7, '喪假', 1, ''),
        ( 30, '產假', 2, '');

insert into office_regions ( latitude, longitude, regions_name)
values
    ( 22.993460934548654, 120.22674896027664, '路易莎東安店'),
    ( 22.99494490872267, 120.21791221990024, '路易莎勝利店'),
    ( 22.99944417097877, 120.21594456761729, '成大唯農分公司'),
    ( 23.02426988555787, 120.22328562940174, '南臺科大L棟');

insert into overtime_type ( type)
values  ( '平日'),
        ( '休息日'),
        ( '國定假日或特別休假'),
        ( '例假');

insert product_type ( type_name)
values  ( '票券');

insert into sub_type ( product_type_id, sub_name)
values  ( 1, '電影票'),
        ( 1, '餐券'),
        ( 1, '商品卡'),
        ( 1, '旅遊券');

insert into product ( product_name, sub_type_id, description, price, stock_quantity, image_url,status)
values  ( '西提牛排套餐券', 2, '適用於西提', 710, 16, 'https://i1.momoshop.com.tw/1692455303/goodsimg/0011/276/551/11276551_R.jpg', '上架'),
        ( '7-11 500元禮品卡', 3, '適用全台7-11', 450, 20, 'https://img.sopower.com.tw/image/20200722/20200722132116_95880.jpg',  '上架'),
        ( '威秀影城電影票', 1, '適用威秀影城', 270, 20, 'https://shoplineimg.com/5cc3db30527c4b0001a30cf0/5d99afc7916861001a6fec00/800x.webp?source_format=jpg', '下架'),
        ( '漢來海港平日晚餐券', 2, '適用於南部漢來海港', 1020, 20, 'https://cs-a.ecimg.tw/items/DBCRNFA900GI4W7/000001_1689756008.jpg', '上架'),
        ( '全家 500元禮品卡', 3, '適用全台全家', 450, 3, 'https://mall.iopenmall.tw/website/uploads_product/website_12985/P1298504213225_3_37090503.jpeg?hash=99663','上架'),
        ( '秀泰影城電影票', 1, '適用秀泰影城', 270, 10, 'https://shoplineimg.com/5a14d0ee72fdc03c82001454/613acabf9065d2002916d325/800x.webp?source_format=jpg', '上架'),
        ( '7-11 1000元禮品卡', 3, '適用全台7-11', 900, 7, 'https://www.cocorolife.tw/images/product/thumb/270030/20221230070753.jpg','上架'),
        ( '國賓電影票', 1, '適用國賓影城', 270, 10, 'https://media.ticketxpress.com.tw/Images/a08a5db9-2b51-42b6-a956-90acc8232dad.jpg','上架'),
        ( '饗食天堂餐券', 2, '適用饗食天堂', 1050, 10, 'https://picdn.gomaji.com/products/o/476/335476/335476_1_48.jpg','上架');

insert into permission ( permission_name, permission_statement)
values  ( '正式員工', '允許讀取個人資料'),
        ( '實習生', '允許讀取個人資料'),
        ( '部門主管', '允許簽核資料'),
        ( '人事專員', '允許管理員工資料'),
        ( '系統管理員', '允許管理系統設定'),
        ( '經理', '允許管理系統設定');

insert into employee (emp_id, emp_name, birth, password, email, id_number, gender, phone, tel, photo_url, address, title_id, hire_date, leave_date, edit_date, emp_state, emp_line_id)
values  (1000, '嚴經理', '1990-01-01', '100', 'gbu06m4d93@gmail.com', 'A123456789', '男', '0912345678', '02-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1000/DALL%C2%B7E%202023-11-16%2009.57.00%20-%20A%20professional%20headshot%20of%20a%20male%20manager.%20He%20is%20in%20his%20mid-30s%2C%20with%20short%2C%20neatly%20styled%20hair%20and%20a%20confident%2C%20approachable%20expression.%20He%27s%20wearing.png', '台北市圓山區', 1, '2019-01-01', null, '2023-11-05', '在職', null),
        (1001, '蕭經理', '1991-02-02', '100', 'li@gmail.com', 'A987654321', '女', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1002/%E5%93%A1%E5%B7%A51.png', '台北市', 3, '2019-01-02', null, '2023-10-30', '在職', null),
        (1002, '凃經理', '1992-03-03', '100', 'zhang@gmail.com', 'B123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1003/%E5%93%A1%E5%B7%A52-1.jpg', '新北市', 1, '2019-01-03', null, '2023-10-30', '在職', null),
        (1003, '李四', '1993-04-04', '100', 'li4@gmail.com', 'B987654321', '女', '0912345678', '02-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1004/%E5%93%A1%E5%B7%A51-1.jpg', '新北市', 2, '2022-01-04', null, '2023-10-30', '在職', null),
        (1004, '王五', '1994-05-05', '100', 'wang5@gmail.com', 'C123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1005/%E5%93%A1%E5%B7%A52-3.jpg', '桃園市', 5, '2022-01-05', null, '2023-10-30', '在職', null),
        (1005, '趙六', '1995-06-06', '100', 'zhao@gmail.com', 'C987654321', '女', '0912345678', '03-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1005/%E5%A5%B3-2.jpg', '桃園市', 7, '2022-01-06', null, '2023-10-30', '在職', null),
        (1006, '孫七', '1996-07-07', '100', 'sun@gmail.com', 'D123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1006/%E5%8D%97-3.png', '台中市', 7, '2022-01-07', null, '2023-10-30', '在職', null),
        (1007, '周八', '1997-08-08', '100', 'zhou@gmail.com', 'D987654321', '女', '0912345678', '04-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1007/%E5%A5%B3.jpeg', '台中市', 8, '2022-01-08', null, '2023-10-30', '在職', null),
        (1008, '吳九', '1998-09-09', '100', 'wu@gmail.com', 'E123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1008/%E5%8D%97-1.jpg', '台南市', 8, '2022-01-09', null, '2023-10-30', '在職', null),
        (1009, '郭十', '1999-10-10', '100', 'guo@gmail.com', 'E987654321', '女', '0912345678', '05-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1009/%E5%A5%B3.jpeg', '台南市', 8, '2022-01-10', null, '2023-10-30', '在職', null),
        (1010, '吳十九', '1998-11-29', '100', 'wu19@gmail.com', 'E123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1010/%E5%8D%97-5.png', '台南市', 4, '2022-01-29', null, '2023-10-30', '在職', null),
        (1011, '王十一', '1990-09-21', '100', 'wang11@gmail.com', 'K123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1011/%E5%8D%97-5.png', '台北市', 5, '2022-01-21', null, '2023-10-30', '在職', null),
        (1012, '陳彥宇', '1991-11-22', '100', 'd0981843347@gmail.com', 'L123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1012/IMG_9530.jpg', '台北市', 2, '2022-01-22', null, '2023-11-05', '在職', null),
        (1013, '吳承員', '1992-11-23', '100', 'zhang13@gmail.com', 'L123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1013/%E5%8D%97-6.png', '新北市', 2, '2022-01-23', null, '2023-10-30', '在職', null),
        (1014, '汪映君', '1993-11-24', '100', 'ygkhoph@gmail.com', 'B123456789', '女', '0912345678', '02-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1014/%E5%A5%B3-8.png', '新北市', 2, '2022-01-24', null, '2023-11-04', '在職', null),
        (1015, '凃懿珈', '1994-11-25', '100', 'hellojia0111@gmail.com', 'C123456789', '女', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1015/%E5%A5%B3-6.png', '桃園市', 2, '2022-01-25', null, '2023-11-04', '在職', null),
        (1016, '蕭旭凱', '1995-11-26', '100', 'Kyle2542736@gmail.com', 'C987654321', '男', '0912345678', '03-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1016/%E5%8D%97-6.png', '桃園市', 2, '2022-01-26', null, '2023-11-04', '在職', null),
        (1017, '嚴裕凱', '1996-11-27', '100', 'u06m4d93@gmail.com', 'D123456789', '男', '0912345678', null, 'https://eipulseimages.blob.core.windows.net/images/emp/1017/%E5%8D%97-6.png', '台中市', 2, '2022-01-27', null, '2023-11-05', '在職', null),
        (1018, '測試用員工18', '1997-11-28', '100', 'zhou18@gmail.com', 'D987654321', '女', '0912345678', '04-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1018/%E5%A5%B3-4.jpg', '台中市', 2, '2022-01-28', null, '2023-10-31', '離職', null),
        (1019, '郭二十', '1999-11-30', '100', 'guo20@gmail.com', 'O987654321', '女', '0912345678', '05-12345678', 'https://eipulseimages.blob.core.windows.net/images/emp/1019/%E5%A5%B3-7.png', '台南市', 5, '2022-01-30', null, '2023-10-30', '離職', null);

       insert into permission_info ( emp_id, permission_id)
values  ( 1000, 6),
        ( 1001, 6),
        ( 1002, 6),
        ( 1003, 1),
        ( 1004, 1),
        ( 1005, 1),
        ( 1006, 1),
        ( 1007, 1),
        ( 1008, 1),
        ( 1009, 1),
        ( 1010, 1),
        ( 1011, 1),
        ( 1012, 1),
        ( 1013, 1),
        ( 1014, 1),
        ( 1015, 1),
        ( 1016, 1),
        ( 1017, 1),
        ( 1018, 1),
        ( 1019, 1);

insert into permission_move ( emp_id, before_permission_name, after_permission_name, reason, effect_date, approver)
values  (1000, '允許管理員工資料', '允許管理系統設定', '升職為部門主管', '2022-10-01', '系統管理員'),
        ( 1001, '允許讀取個人資料', '允許管理系統設定', '升職為部門主管', '2022-09-15', '系統管理員'),
        ( 1002, '允許讀取個人資料', '允許管理系統設定', '升職為部門主管', '2022-08-20', '系統管理員');



insert into clock_time ( emp_id, time, type, regions_id)
values  ( 1011, '2023-11-06 07:44:43.372000', '上班', 3),
        ( 1011, '2023-11-06 17:00:46.064000', '下班', 3),
        ( 1012, '2023-11-06 07:59:59.633000', '上班', 3),
        ( 1012, '2023-11-06 17:05:01.581000', '下班', 3),
        ( 1013, '2023-11-06 08:45:07.764000', '上班', 3),
        ( 1013, '2023-11-06 17:45:11.229000', '下班', 3),
        ( 1014, '2023-11-06 07:45:20.127000', '上班', 3),
        ( 1014, '2023-11-06 16:45:22.127000', '下班', 3),
        ( 1015, '2023-11-06 07:59:28.042000', '上班', 3),
        ( 1015, '2023-11-06 18:45:31.278000', '下班', 3),
        ( 1016, '2023-11-06 08:45:39.659000', '上班', 3),
        ( 1016, '2023-11-06 18:45:41.643000', '下班', 3),
        ( 1017, '2023-11-06 07:58:50.157000', '上班', 3),
        ( 1017, '2023-11-06 17:03:52.591000', '下班', 3),
        ( 1018, '2023-11-06 10:47:28.859000', '上班', 3),
        ( 1019, '2023-11-06 07:47:42.010000', '上班', 3),
        ( 1019, '2023-11-06 17:03:44.025000', '下班', 3);

insert into clock_time ( emp_id, time, type, regions_id)
values  ( 1011, '2023-11-08 07:44:43.372000', '上班', 3),
        ( 1011, '2023-11-08 17:00:46.064000', '下班', 3),
        ( 1012, '2023-11-08 07:59:59.633000', '上班', 3),
        ( 1012, '2023-11-08 17:05:01.581000', '下班', 3),
        ( 1013, '2023-11-08 08:45:07.764000', '上班', 3),
        ( 1013, '2023-11-08 17:45:11.229000', '下班', 3),
        ( 1014, '2023-11-08 07:45:20.127000', '上班', 3),
        ( 1014, '2023-11-08 16:45:22.127000', '下班', 3),
        ( 1015, '2023-11-08 07:59:28.042000', '上班', 3),
        ( 1015, '2023-11-08 18:45:31.278000', '下班', 3),
        ( 1016, '2023-11-08 08:45:39.659000', '上班', 3),
        ( 1016, '2023-11-08 18:45:41.643000', '下班', 3),
        ( 1017, '2023-11-08 07:58:50.157000', '上班', 3),
        ( 1017, '2023-11-08 17:03:52.591000', '下班', 3),
        ( 1018, '2023-11-08 10:47:28.859000', '上班', 3),
        ( 1019, '2023-11-08 07:47:42.010000', '上班', 3),
        ( 1019, '2023-11-08 17:03:44.025000', '下班', 3);

insert into attendance ( date, emp_id, total_hours, is_late, is_early_leave, is_hours_not_met, is_over_time)
values  ( '2023-11-06', 1011, 9.27, 0, 0, 0, 0),
        ( '2023-11-06', 1012, 9.08, 0, 0, 0, 0),
        ( '2023-11-06', 1013, 9.00, 1, 0, 0, 0),
        ( '2023-11-06', 1014, 9.00, 0, 1, 0, 0),
        ( '2023-11-06', 1015, 10.77, 0, 0, 0, 1),
        ( '2023-11-06', 1016, 10.00, 1, 0, 0, 1),
        ( '2023-11-06', 1017, 9.08, 0, 0, 0, 0),
        ( '2023-11-06', 1018, 0.00, 1, 1, 1, 0),
        ( '2023-11-06', 1019, 9.27, 0, 0, 0, 0);


insert into attendance ( date, emp_id, total_hours, is_late, is_early_leave, is_hours_not_met, is_over_time)
values  ( '2023-11-08', 1011, 9.27, 0, 0, 0, 0),
        ( '2023-11-08', 1012, 9.08, 0, 0, 0, 0),
        ( '2023-11-08', 1013, 9.00, 1, 0, 0, 0),
        ( '2023-11-08', 1014, 9.00, 0, 1, 0, 0),
        ( '2023-11-08', 1015, 10.77, 0, 0, 0, 1),
        ( '2023-11-08', 1016, 10.00, 1, 0, 0, 1),
        ( '2023-11-08', 1017, 9.08, 0, 0, 0, 0),
        ( '2023-11-08', 1018, 0.00, 1, 1, 1, 0),
        ( '2023-11-08', 1019, 9.27, 0, 0, 0, 0);
        

-- 插入公佈欄測試資料
INSERT INTO `news` (`title`, `content`, `file`, `publisher`, `is_visible`)
VALUES 
  ('考勤標準異動', '即日起上班時間改成8:30到下午5:30', NULL, 1000, true),
  ('恭賀！美國分公司陳彥宇部長榮升副總', '陳彥宇部長表現優異，經由董事會開會通過，於12/1起職位晉升為美國分公司副總', NULL, 1001, true),
  ('11/18員工旅遊', '早上八點成大榕園集合出發', NULL, 1002, true),
  ('疫苗施打提醒', '近期流感傳染嚴重，本司關心同仁身體健康，敬請各位同仁撥空去施打疫苗', NULL, 1000, true),
  ('請不要在聊天室亂傳訊息', '近期接獲同仁反映，有人在聊天室上傳不雅照片，請各位同仁注意，違反者扣一日薪資', NULL, 1001, true),
  ('12/25聖誕節餐會', '12/25晚間7點於漢來海港舉辦聖誕節餐會，敬請各位同仁準時出席', NULL, 1000, true),
  ('人事異動通知', '內容8', NULL, 1001, true),
  ('調薪通知', '公司業績不錯，全體員工調薪5%', NULL, 1002, true),
  ('員工健檢通知', '請所有員工在2024/1/20前完成個人健康檢查', NULL, 1000, false),
  ('員工體重注意', '本公司員工BMI平均超過標準，請各單位注意', NULL, 1001, true),
  ('團體康樂消息', '11/17下午舉辦全司羽球比賽，優勝部門放假一天', NULL, 1002, true),
  ('下午茶投票', '請至Line投出想吃的下午茶，逾時不候', NULL, 1000, false),
  ('特約廠商更新', '新增瓦城為我司特約廠商，持員工證享有9折優惠', NULL, 1001, true),
  ('員工路跑開放報名', '即日起至12/10開放路跑報名，前三名員工享有特休假一天', NULL, 1002, true);
	
-- 插入行事曆測試資料
INSERT INTO `event` (`title`, `start`, `end`, `description`, `user_id`)
VALUES 
  ('出差高雄', '2023-11-19 08:00:00', '2023-11-21 10:00:00', '拜訪林先生說明產品資料', 1000),
  ('開會', '2023-11-16 09:30:00', '2023-11-16 12:00:00', '跟老闆開會', 1001),
  ('開會', '2023-11-17 14:00:00', '2023-11-17 16:30:00', '部門會議', 1002),
  ('開會', '2023-11-18 10:00:00', '2023-11-18 11:30:00', '部門會議', 1012),
  ('拜訪客戶', '2023-11-19 13:30:00', '2023-11-19 15:00:00', '拜訪資展國際張老師', 1000),
  ('出差', '2023-11-20 11:00:00', '2023-11-21 12:30:00', '去台北參加國際資訊展', 1012),
  ('開會', '2023-11-21 14:30:00', '2023-11-21 17:00:00', '跟老闆開會', 1002),
  ('開會', '2023-11-22 08:30:00', '2023-11-22 10:30:00', '跟經理開會', 1017),
  ('特休', '2023-11-23 12:00:00', '2023-11-23 14:30:00', '中午外出', 1001),
  ('休假', '2023-11-24 09:00:00', '2023-11-25 11:00:00', '休息', 1000),
  ('開會', '2023-11-25 15:00:00', '2023-11-25 17:30:00', '跟老闆開會', 1000),
  ('休假', '2023-11-26 10:30:00', '2023-11-27 12:30:00', '休息一下', 1003),
  ('開會', '2023-11-27 13:00:00', '2023-11-27 15:30:00', '部門會議', 1003),
  ('拜訪客戶', '2023-11-28 08:00:00', '2023-11-28 09:30:00', '拿測試產品給王先生', 1003),
  ('出差台中', '2023-11-29 11:30:00', '2023-11-30 17:00:00', '去台中參加產品發布會', 1003);
 
 insert into emergency (emergency_name,phone,relation,emp_id)
value ( '顏城敘', '0954124563', '父子',1000),
( '蕭辰祐', '0942324563', '父子',1001),
( '涂敏惠', '0951124563', '母女',1002),
('李敘', '0954199563', '父子',1003),
( '王仁偉', '0958744563', '父子',1004),
( '趙五郎', '0954124215', '父子',1005),
( '孫協志', '0954124747', '父子',1006),
( '周潤發', '0954124456', '父子',1007),
( '吳程樺', '0954124123', '父子',1008),
( '郭富城', '0954124777', '父子',1009),
( '吳品仁', '0954124969', '父子',1010),
( '王曉華', '0954124666', '母子',1011),
( '陳振元', '0954124500', '父子',1012),
( '吳憲訓', '0954124565', '父子',1013),
( '汪東城', '0954124563', '父子',1014),
( '涂欣慧', '0954124563', '母女',1015),
( '蕭頂發', '0954124563', '父子',1016),
( '嚴晨旭', '0954124563', '父子',1017),
( '測試用小白', '0954124563', '父子',1018),
( '郭白', '0954124563', '父子',1019);

insert into permission_move (emp_id,before_permission_name,after_permission_name,reason,effect_date,approver,edit_date)
value 
( 1001, '正式員工', '部門主管', '升職','2023-07-01','嚴經理', '2023-06-30'),
( 1000, '正式員工', '部門主管', '升職','2023-05-01','嚴經理', '2023-04-30'),
( 1002, '正式員工', '經理', '升職','2023-07-01','嚴經理', '2023-06-30');

insert into title_move (emp_id,before_dept_info,after_dept_info,reason,effect_date,approver,edit_date)
value ( 1001, '研發工程師', '設備工程師', '轉調部門','2023-05-01','嚴經理', '2023-04-15'),
( 1001, '設備工程師', '工程部經理', '升職','2023-07-01','嚴經理', '2023-06-15'),
( 1000, '研發助理', '研發工程師', '轉調部門','2023-05-01','嚴經理', '2023-04-15'),
( 1000, '研發工程師', '研發部經理', '升職','2023-08-01','嚴經理', '2023-07-15'),
( 1003, '研發工程師', '製程工程師', '轉調部門','2023-05-21','嚴經理', '2023-05-07');

insert into resign_record (emp_id,reason,leave_date,approver,edit_date,quit,transfer_form)
value ( 1018, '個人規劃', '2023-11-30','嚴經理','2023-11-15',1,1),
( 1019, '上班距離太遠', '2023-11-30','嚴經理','2023-11-15',1,1);


create view alldept_pepole as
select d.dept_id,d.dept_name,t.title_name,e.emp_id,e.emp_name,e.email
from employee e join title t on e.title_id = t.title_id
join dept d on t.dept_id = d.dept_id
order by t.title_name;

create view employees_name as
select e.emp_id,e.emp_name
from employee as e;
 
INSERT INTO emp_salary_info (emp_id, basic_salary, labor_insurance_grade, labor_volunteer_pension_rate, health_insurance_grade, family_dependants_num, welfare_benefits_deduction) 
VALUES 
(1000, 75000, 45800, 0.03, 76500, 2, '1'),
(1001, 76000, 45800, 0.06, 76500, 2, '1'),
(1002, 59000, 45800, 0.04, 60800, 0, '1'),
(1003, 29000, 30300, 0.00, 30300, 0, '0'),
(1004, 42000, 42000, 0.00, 42000, 0, '1'),
(1005, 26400, 26400, 0.00, 26400, 0, '1'),
(1006, 27000, 27600, 0.00, 27600, 0, '1'),
(1007, 30000, 30300, 0.06, 30300, 1, '1'),
(1008, 58000, 45800, 0.00, 60800, 0, '1'),
(1009, 28000, 28800, 0.00, 28800, 0, '1'),
(1010, 35000, 36300, 0.03, 36300, 0, '1'),
(1011, 65000, 45800, 0.06, 69800, 3, '1'),
(1012, 33600, 34800, 0.00, 34800, 0, '0'),
(1013, 35000, 36300, 0.00, 36300, 0, '1'),
(1014, 34000, 34800, 0.01, 34800, 1, '1'),
(1015, 65000, 45800, 0.06, 69800, 0, '1'),
(1016, 33600, 34800, 0.06, 34800, 1, '0'),
(1017, 35000, 36300, 0.00, 36300, 0, '1'),
(1018, 34000, 34800, 0.01, 34800, 1, '1'),
(1019, 34000, 34800, 0.01, 34800, 1, '1');


INSERT INTO salary_history (emp_id, original_salary, adjust_salary, remark, adjusted_date)
VALUES 
(1018, 26400, 30000, '約聘轉正職調薪', '2022-06-01 15:00:53'),
(1018, 30000, 32000, '年度調薪', '2023-01-01 15:01:09'),
(1018, 32000, 34000, '績效優良', '2023-06-01 15:01:42'),
(1003, 25000, 27000, '績效優良', '2022-06-01 15:00:53'),
(1003, 27000, 29000, '年度調薪', '2023-01-01 15:01:09');

INSERT INTO subject_type (subject_name, calculate_type, frequency, amount_default, status) 
VALUES 
('基本薪資', 'P', '1', null, 'true'),
('伙食津貼', 'P', '1', 2400, 'true'),
('加班費', 'P', '1', null, 'true'),
('年終獎金', 'P', '0', null, 'true'),
('勞保費', 'M', '1', null, 'true'),
('健保費', 'M', '1', null, 'true'),
('勞退自提退休金', 'M', '1', null, 'true'),
('福利金', 'M', '1', null, 'true'),
('扣薪假', 'M', '1', null, 'true'),
('業績獎金', 'P', '1', null, 'true');


INSERT INTO  salary_month_record (id, sl_year, sl_month, emp_id, add_sum, dedu_sum, net_salary, created_date) values
(1, 2023, 8, 1000, 75000, 6408, 68592, '2023-11-15 14:55:05'),
(2, 2023, 8, 1001, 76000, 7787, 68213, '2023-11-15 14:55:05'),
(3, 2023, 8, 1002, 59000, 4169, 54831, '2023-11-15 14:55:05'),
(4, 2023, 8, 1003, 29000, 1197, 27803, '2023-11-15 14:55:05'),
 (5, 2023, 8, 1004, 42000, 1869, 40131, '2023-11-15 14:55:05'),
(6, 2023, 8, 1005, 26400, 1175, 25225, '2023-11-15 14:55:05'),
(7, 2023, 8, 1006, 27000, 1225, 25775, '2023-11-15 14:55:05'),
(8, 2023, 8, 1007, 30000, 3635, 26365, '2023-11-15 14:55:06'),
 (9, 2023, 8, 1008, 58000, 2332, 55668, '2023-11-15 14:55:06'),
(10, 2023, 8, 1009, 28000, 1278, 26722, '2023-11-15 14:55:06'),
(11, 2023, 8, 1010, 35000, 2698, 32302, '2023-11-15 14:55:06'),
 (12, 2023, 8, 1011, 65000, 8502, 56498, '2023-11-15 14:55:06'),
(13, 2023, 8, 1012, 33600, 1375, 32225, '2023-11-15 14:55:06'),
(14, 2023, 8, 1013, 35000, 1609, 33391, '2023-11-15 14:55:06'),
(15, 2023, 8, 1014, 34000, 2432, 31568, '2023-11-15 14:55:06'),
(16, 2023, 8, 1015, 65000, 5255, 59745, '2023-11-15 14:55:06'),
(17, 2023, 8, 1016, 33600, 4002, 29598, '2023-11-15 14:55:06'),
(18, 2023, 8, 1017, 35000, 1609, 33391, '2023-11-15 14:55:06'),
(19, 2023, 8, 1018, 34000, 2432, 31568, '2023-11-15 14:55:06'),
(20, 2023, 8, 1019, 34000, 2432, 31568, '2023-11-15 14:55:06'),
(21, 2023, 9, 1000, 75000, 6408, 68592, '2023-11-15 14:55:31'),
(22, 2023, 9, 1001, 76000, 7787, 68213, '2023-11-15 14:55:31'),
(23, 2023, 9, 1002, 59000, 4169, 54831, '2023-11-15 14:55:31'),
(24, 2023, 9, 1003, 29000, 1197, 27803, '2023-11-15 14:55:31'),
(25, 2023, 9, 1004, 42000, 1869, 40131, '2023-11-15 14:55:31'),
(26, 2023, 9, 1005, 26400, 1175, 25225, '2023-11-15 14:55:31'),
 (27, 2023, 9, 1006, 27000, 1225, 25775, '2023-11-15 14:55:31'),
(28, 2023, 9, 1007, 30000, 3635, 26365, '2023-11-15 14:55:31'),
(29, 2023, 9, 1008, 58000, 2332, 55668, '2023-11-15 14:55:31'),
(30, 2023, 9, 1009, 28000, 1278, 26722, '2023-11-15 14:55:31'),
(31, 2023, 9, 1010, 35000, 2698, 32302, '2023-11-15 14:55:31'),
(32, 2023, 9, 1011, 65000, 8502, 56498, '2023-11-15 14:55:31'),
(33, 2023, 9, 1012, 33600, 1375, 32225, '2023-11-15 14:55:31'),
(34, 2023, 9, 1013, 35000, 1609, 33391, '2023-11-15 14:55:31'),
(35, 2023, 9, 1014, 34000, 2432, 31568, '2023-11-15 14:55:31'),
(36, 2023, 9, 1015, 65000, 5255, 59745, '2023-11-15 14:55:31'),
(37, 2023, 9, 1016, 33600, 4002, 29598, '2023-11-15 14:55:31'),
(38, 2023, 9, 1017, 35000, 1609, 33391, '2023-11-15 14:55:31'),
(39, 2023, 9, 1018, 34000, 2432, 31568, '2023-11-15 14:55:31'),
(40, 2023, 9, 1019, 34000, 2432, 31568, '2023-11-15 14:55:31'),
(41, 2023, 10, 1000, 75000, 6408, 68592, '2023-11-15 14:55:53'),
(42, 2023, 10, 1001, 76000, 7787, 68213, '2023-11-15 14:55:53'),
(43, 2023, 10, 1002, 59000, 4169, 54831, '2023-11-15 14:55:53'),
(44, 2023, 10, 1003, 29000, 1197, 27803, '2023-11-15 14:55:53'),
(45, 2023, 10, 1004, 42000, 1869, 40131, '2023-11-15 14:55:53'),
(46, 2023, 10, 1005, 26400, 1175, 25225, '2023-11-15 14:55:53'),
 (47, 2023, 10, 1006, 27000, 1225, 25775, '2023-11-15 14:55:53'),
(48, 2023, 10, 1007, 30000, 3635, 26365, '2023-11-15 14:55:53'),
(49, 2023, 10, 1008, 58000, 2332, 55668, '2023-11-15 14:55:53'),
(50, 2023, 10, 1009, 28000, 1278, 26722, '2023-11-15 14:55:53'),
(51, 2023, 10, 1010, 35000, 2698, 32302, '2023-11-15 14:55:53'),
(52, 2023, 10, 1011, 65000, 8502, 56498, '2023-11-15 14:55:53'),
 (53, 2023, 10, 1012, 33600, 1375, 32225, '2023-11-15 14:55:53'),
(54, 2023, 10, 1013, 35000, 1609, 33391, '2023-11-15 14:55:53'),
(55, 2023, 10, 1014, 34000, 2432, 31568, '2023-11-15 14:55:53'),
(56, 2023, 10, 1015, 65000, 5255, 59745, '2023-11-15 14:55:53'),
(57, 2023, 10, 1016, 33600, 4002, 29598, '2023-11-15 14:55:53'),
(58, 2023, 10, 1017, 35000, 1609, 33391, '2023-11-15 14:55:53'),
(59, 2023, 10, 1018, 34000, 2432, 31568, '2023-11-15 14:55:53'),
(60, 2023, 10, 1019, 34000, 2432, 31568, '2023-11-15 14:55:53');

INSERT INTO salary_detail (id, emp_id, subject_id, amount, created_date, record_id) 
VALUES 
(1, 1000, 1000, 75000, '2023-11-15 14:55:05', 1),
(2, 1000, 1004, 1099, '2023-11-15 14:55:05', 1),
(3, 1000, 1006, 1374, '2023-11-15 14:55:05', 1),
(4, 1000, 1007, 375, '2023-11-15 14:55:05', 1),
(5, 1000, 1005, 3560, '2023-11-15 14:55:05', 1),
(6, 1001, 1000, 76000, '2023-11-15 14:55:05', 2),
(7, 1001, 1004, 1099, '2023-11-15 14:55:05', 2),
(8, 1001, 1006, 2748, '2023-11-15 14:55:05', 2),
(9, 1001, 1007, 380, '2023-11-15 14:55:05', 2),
(10, 1001, 1005, 3560, '2023-11-15 14:55:05', 2),
(11, 1002, 1000, 59000, '2023-11-15 14:55:05', 3),
(12, 1002, 1004, 1099, '2023-11-15 14:55:05', 3),
(13, 1002, 1006, 1832, '2023-11-15 14:55:05', 3),
(14, 1002, 1007, 295, '2023-11-15 14:55:05', 3),
(15, 1002, 1005, 943, '2023-11-15 14:55:05', 3),
(16, 1003, 1000, 29000, '2023-11-15 14:55:05', 4),
(17, 1003, 1004, 727, '2023-11-15 14:55:05', 4),
(18, 1003, 1006, 0, '2023-11-15 14:55:05', 4),
(19, 1003, 1007, 0, '2023-11-15 14:55:05', 4),
(20, 1003, 1005, 470, '2023-11-15 14:55:05', 4),
(21, 1004, 1000, 42000, '2023-11-15 14:55:05', 5),
(22, 1004, 1004, 1008, '2023-11-15 14:55:05', 5),
(23, 1004, 1006, 0, '2023-11-15 14:55:05', 5),
(24, 1004, 1007, 210, '2023-11-15 14:55:05', 5),
(25, 1004, 1005, 651, '2023-11-15 14:55:05', 5),
(26, 1005, 1000, 26400, '2023-11-15 14:55:05', 6),
(27, 1005, 1004, 634, '2023-11-15 14:55:05', 6),
(28, 1005, 1006, 0, '2023-11-15 14:55:05', 6),
(29, 1005, 1007, 132, '2023-11-15 14:55:05', 6),
(30, 1005, 1005, 409, '2023-11-15 14:55:05', 6),
(31, 1006, 1000, 27000, '2023-11-15 14:55:05', 7),
(32, 1006, 1004, 662, '2023-11-15 14:55:05', 7),
(33, 1006, 1006, 0, '2023-11-15 14:55:05', 7),
(34, 1006, 1007, 135, '2023-11-15 14:55:06', 7),
(35, 1006, 1005, 428, '2023-11-15 14:55:06', 7),
(36, 1007, 1000, 30000, '2023-11-15 14:55:06', 8),
(37, 1007, 1004, 727, '2023-11-15 14:55:06', 8),
(38, 1007, 1006, 1818, '2023-11-15 14:55:06', 8),
(39, 1007, 1007, 150, '2023-11-15 14:55:06', 8),
(40, 1007, 1005, 940, '2023-11-15 14:55:06', 8),
(41, 1008, 1000, 58000, '2023-11-15 14:55:06', 9),
(42, 1008, 1004, 1099, '2023-11-15 14:55:06', 9),
(43, 1008, 1006, 0, '2023-11-15 14:55:06', 9),
(44, 1008, 1007, 290, '2023-11-15 14:55:06', 9),
(45, 1008, 1005, 943, '2023-11-15 14:55:06', 9),
(46, 1009, 1000, 28000, '2023-11-15 14:55:06', 10),
(47, 1009, 1004, 691, '2023-11-15 14:55:06', 10),
(48, 1009, 1006, 0, '2023-11-15 14:55:06', 10),
(49, 1009, 1007, 140, '2023-11-15 14:55:06', 10),
(50, 1009, 1005, 447, '2023-11-15 14:55:06', 10),
(51, 1010, 1000, 35000, '2023-11-15 14:55:06', 11),
(52, 1010, 1004, 871, '2023-11-15 14:55:06', 11),
(53, 1010, 1006, 1089, '2023-11-15 14:55:06', 11),
(54, 1010, 1007, 175, '2023-11-15 14:55:06', 11),
(55, 1010, 1005, 563, '2023-11-15 14:55:06', 11),
(56, 1011, 1000, 65000, '2023-11-15 14:55:06', 12),
(57, 1011, 1004, 1099, '2023-11-15 14:55:06', 12),
(58, 1011, 1006, 2748, '2023-11-15 14:55:06', 12),
(59, 1011, 1007, 325, '2023-11-15 14:55:06', 12),
(60, 1011, 1005, 4330, '2023-11-15 14:55:06', 12),
(61, 1012, 1000, 33600, '2023-11-15 14:55:06', 13),
(62, 1012, 1004, 835, '2023-11-15 14:55:06', 13),
(63, 1012, 1006, 0, '2023-11-15 14:55:06', 13),
(64, 1012, 1007, 0, '2023-11-15 14:55:06', 13),
(65, 1012, 1005, 540, '2023-11-15 14:55:06', 13),
(66, 1013, 1000, 35000, '2023-11-15 14:55:06', 14),
(67, 1013, 1004, 871, '2023-11-15 14:55:06', 14),
(68, 1013, 1006, 0, '2023-11-15 14:55:06', 14),
(69, 1013, 1007, 175, '2023-11-15 14:55:06', 14),
(70, 1013, 1005, 563, '2023-11-15 14:55:06', 14),
(71, 1014, 1000, 34000, '2023-11-15 14:55:06', 15),
(72, 1014, 1004, 835, '2023-11-15 14:55:06', 15),
(73, 1014, 1006, 348, '2023-11-15 14:55:06', 15),
(74, 1014, 1007, 170, '2023-11-15 14:55:06', 15),
(75, 1014, 1005, 1079, '2023-11-15 14:55:06', 15),
(76, 1015, 1000, 65000, '2023-11-15 14:55:06', 16),
(77, 1015, 1004, 1099, '2023-11-15 14:55:06', 16),
(78, 1015, 1006, 2748, '2023-11-15 14:55:06', 16),
(79, 1015, 1007, 325, '2023-11-15 14:55:06', 16),
(80, 1015, 1005, 1083, '2023-11-15 14:55:06', 16),
(81, 1016, 1000, 33600, '2023-11-15 14:55:06', 17),
(82, 1016, 1004, 835, '2023-11-15 14:55:06', 17),
(83, 1016, 1006, 2088, '2023-11-15 14:55:06', 17),
(84, 1016, 1007, 0, '2023-11-15 14:55:06', 17),
(85, 1016, 1005, 1079, '2023-11-15 14:55:06', 17),
(86, 1017, 1000, 35000, '2023-11-15 14:55:06', 18),
(87, 1017, 1004, 871, '2023-11-15 14:55:06', 18),
(88, 1017, 1006, 0, '2023-11-15 14:55:06', 18),
(89, 1017, 1007, 175, '2023-11-15 14:55:06', 18),
(90, 1017, 1005, 563, '2023-11-15 14:55:06', 18),
(91, 1018, 1000, 34000, '2023-11-15 14:55:06', 19),
(92, 1018, 1004, 835, '2023-11-15 14:55:06', 19),
(93, 1018, 1006, 348, '2023-11-15 14:55:06', 19),
(94, 1018, 1007, 170, '2023-11-15 14:55:06', 19),
(95, 1018, 1005, 1079, '2023-11-15 14:55:06', 19),
(96, 1019, 1000, 34000, '2023-11-15 14:55:06', 20),
(97, 1019, 1004, 835, '2023-11-15 14:55:06', 20),
(98, 1019, 1006, 348, '2023-11-15 14:55:06', 20),
(99, 1019, 1007, 170, '2023-11-15 14:55:06', 20),
(100, 1019, 1005, 1079, '2023-11-15 14:55:06', 20),
(101, 1000, 1000, 75000, '2023-11-15 14:55:31', 21),
(102, 1000, 1004, 1099, '2023-11-15 14:55:31', 21),
(103, 1000, 1006, 1374, '2023-11-15 14:55:31', 21),
(104, 1000, 1007, 375, '2023-11-15 14:55:31', 21),
(105, 1000, 1005, 3560, '2023-11-15 14:55:31', 21),
(106, 1001, 1000, 76000, '2023-11-15 14:55:31', 22),
(107, 1001, 1004, 1099, '2023-11-15 14:55:31', 22),
(108, 1001, 1006, 2748, '2023-11-15 14:55:31', 22),
(109, 1001, 1007, 380, '2023-11-15 14:55:31', 22),
(110, 1001, 1005, 3560, '2023-11-15 14:55:31', 22),
(111, 1002, 1000, 59000, '2023-11-15 14:55:31', 23),
(112, 1002, 1004, 1099, '2023-11-15 14:55:31', 23),
(113, 1002, 1006, 1832, '2023-11-15 14:55:31', 23),
(114, 1002, 1007, 295, '2023-11-15 14:55:31', 23),
(115, 1002, 1005, 943, '2023-11-15 14:55:31', 23),
(116, 1003, 1000, 29000, '2023-11-15 14:55:31', 24),
(117, 1003, 1004, 727, '2023-11-15 14:55:31', 24),
(118, 1003, 1006, 0, '2023-11-15 14:55:31', 24),
(119, 1003, 1007, 0, '2023-11-15 14:55:31', 24),
(120, 1003, 1005, 470, '2023-11-15 14:55:31', 24),
(121, 1004, 1000, 42000, '2023-11-15 14:55:31', 25),
(122, 1004, 1004, 1008, '2023-11-15 14:55:31', 25),
(123, 1004, 1006, 0, '2023-11-15 14:55:31', 25),
(124, 1004, 1007, 210, '2023-11-15 14:55:31', 25),
(125, 1004, 1005, 651, '2023-11-15 14:55:31', 25),
(126, 1005, 1000, 26400, '2023-11-15 14:55:31', 26),
(127, 1005, 1004, 634, '2023-11-15 14:55:31', 26),
(128, 1005, 1006, 0, '2023-11-15 14:55:31', 26),
(129, 1005, 1007, 132, '2023-11-15 14:55:31', 26),
(130, 1005, 1005, 409, '2023-11-15 14:55:31', 26),
(131, 1006, 1000, 27000, '2023-11-15 14:55:31', 27),
(132, 1006, 1004, 662, '2023-11-15 14:55:31', 27),
(133, 1006, 1006, 0, '2023-11-15 14:55:31', 27),
(134, 1006, 1007, 135, '2023-11-15 14:55:31', 27),
(135, 1006, 1005, 428, '2023-11-15 14:55:31', 27),
(136, 1007, 1000, 30000, '2023-11-15 14:55:31', 28),
(137, 1007, 1004, 727, '2023-11-15 14:55:31', 28),
(138, 1007, 1006, 1818, '2023-11-15 14:55:31', 28),
(139, 1007, 1007, 150, '2023-11-15 14:55:31', 28),
(140, 1007, 1005, 940, '2023-11-15 14:55:31', 28),
(141, 1008, 1000, 58000, '2023-11-15 14:55:31', 29),
(142, 1008, 1004, 1099, '2023-11-15 14:55:31', 29),
(143, 1008, 1006, 0, '2023-11-15 14:55:31', 29),
(144, 1008, 1007, 290, '2023-11-15 14:55:31', 29),
(145, 1008, 1005, 943, '2023-11-15 14:55:31', 29),
(146, 1009, 1000, 28000, '2023-11-15 14:55:31', 30),
(147, 1009, 1004, 691, '2023-11-15 14:55:31', 30),
(148, 1009, 1006, 0, '2023-11-15 14:55:31', 30),
(149, 1009, 1007, 140, '2023-11-15 14:55:31', 30),
(150, 1009, 1005, 447, '2023-11-15 14:55:31', 30),
(151, 1010, 1000, 35000, '2023-11-15 14:55:31', 31),
(152, 1010, 1004, 871, '2023-11-15 14:55:31', 31),
(153, 1010, 1006, 1089, '2023-11-15 14:55:31', 31),
(154, 1010, 1007, 175, '2023-11-15 14:55:31', 31),
(155, 1010, 1005, 563, '2023-11-15 14:55:31', 31),
(156, 1011, 1000, 65000, '2023-11-15 14:55:31', 32),
(157, 1011, 1004, 1099, '2023-11-15 14:55:31', 32),
(158, 1011, 1006, 2748, '2023-11-15 14:55:31', 32),
(159, 1011, 1007, 325, '2023-11-15 14:55:31', 32),
(160, 1011, 1005, 4330, '2023-11-15 14:55:31', 32),
(161, 1012, 1000, 33600, '2023-11-15 14:55:31', 33),
(162, 1012, 1004, 835, '2023-11-15 14:55:31', 33),
(163, 1012, 1006, 0, '2023-11-15 14:55:31', 33),
(164, 1012, 1007, 0, '2023-11-15 14:55:31', 33),
(165, 1012, 1005, 540, '2023-11-15 14:55:31', 33),
(166, 1013, 1000, 35000, '2023-11-15 14:55:31', 34),
(167, 1013, 1004, 871, '2023-11-15 14:55:31', 34),
(168, 1013, 1006, 0, '2023-11-15 14:55:31', 34),
(169, 1013, 1007, 175, '2023-11-15 14:55:31', 34),
(170, 1013, 1005, 563, '2023-11-15 14:55:31', 34),
(171, 1014, 1000, 34000, '2023-11-15 14:55:31', 35),
(172, 1014, 1004, 835, '2023-11-15 14:55:31', 35),
(173, 1014, 1006, 348, '2023-11-15 14:55:31', 35),
(174, 1014, 1007, 170, '2023-11-15 14:55:31', 35),
(175, 1014, 1005, 1079, '2023-11-15 14:55:31', 35),
(176, 1015, 1000, 65000, '2023-11-15 14:55:31', 36),
(177, 1015, 1004, 1099, '2023-11-15 14:55:31', 36),
(178, 1015, 1006, 2748, '2023-11-15 14:55:31', 36),
(179, 1015, 1007, 325, '2023-11-15 14:55:31', 36),
(180, 1015, 1005, 1083, '2023-11-15 14:55:31', 36),
(181, 1016, 1000, 33600, '2023-11-15 14:55:31', 37),
(182, 1016, 1004, 835, '2023-11-15 14:55:31', 37),
(183, 1016, 1006, 2088, '2023-11-15 14:55:31', 37),
(184, 1016, 1007, 0, '2023-11-15 14:55:31', 37),
(185, 1016, 1005, 1079, '2023-11-15 14:55:31', 37),
(186, 1017, 1000, 35000, '2023-11-15 14:55:31', 38),
(187, 1017, 1004, 871, '2023-11-15 14:55:31', 38),
(188, 1017, 1006, 0, '2023-11-15 14:55:31', 38),
(189, 1017, 1007, 175, '2023-11-15 14:55:31', 38),
(190, 1017, 1005, 563, '2023-11-15 14:55:31', 38),
(191, 1018, 1000, 34000, '2023-11-15 14:55:31', 39),
(192, 1018, 1004, 835, '2023-11-15 14:55:31', 39),
(193, 1018, 1006, 348, '2023-11-15 14:55:31', 39),
(194, 1018, 1007, 170, '2023-11-15 14:55:31', 39),
(195, 1018, 1005, 1079, '2023-11-15 14:55:31', 39),
(196, 1019, 1000, 34000, '2023-11-15 14:55:31', 40),
(197, 1019, 1004, 835, '2023-11-15 14:55:31', 40),
(198, 1019, 1006, 348, '2023-11-15 14:55:31', 40),
(199, 1019, 1007, 170, '2023-11-15 14:55:31', 40),
(200, 1019, 1005, 1079, '2023-11-15 14:55:31', 40),
(201, 1000, 1000, 75000, '2023-11-15 14:55:53', 41),
(202, 1000, 1004, 1099, '2023-11-15 14:55:53', 41),
(203, 1000, 1006, 1374, '2023-11-15 14:55:53', 41),
(204, 1000, 1007, 375, '2023-11-15 14:55:53', 41),
(205, 1000, 1005, 3560, '2023-11-15 14:55:53', 41),
(206, 1001, 1000, 76000, '2023-11-15 14:55:53', 42),
(207, 1001, 1004, 1099, '2023-11-15 14:55:53', 42),
(208, 1001, 1006, 2748, '2023-11-15 14:55:53', 42),
(209, 1001, 1007, 380, '2023-11-15 14:55:53', 42),
(210, 1001, 1005, 3560, '2023-11-15 14:55:53', 42),
(211, 1002, 1000, 59000, '2023-11-15 14:55:53', 43),
(212, 1002, 1004, 1099, '2023-11-15 14:55:53', 43),
(213, 1002, 1006, 1832, '2023-11-15 14:55:53', 43),
(214, 1002, 1007, 295, '2023-11-15 14:55:53', 43),
(215, 1002, 1005, 943, '2023-11-15 14:55:53', 43),
(216, 1003, 1000, 29000, '2023-11-15 14:55:53', 44),
(217, 1003, 1004, 727, '2023-11-15 14:55:53', 44),
(218, 1003, 1006, 0, '2023-11-15 14:55:53', 44),
(219, 1003, 1007, 0, '2023-11-15 14:55:53', 44),
(220, 1003, 1005, 470, '2023-11-15 14:55:53', 44),
(221, 1004, 1000, 42000, '2023-11-15 14:55:53', 45),
(222, 1004, 1004, 1008, '2023-11-15 14:55:53', 45),
(223, 1004, 1006, 0, '2023-11-15 14:55:53', 45),
(224, 1004, 1007, 210, '2023-11-15 14:55:53', 45),
(225, 1004, 1005, 651, '2023-11-15 14:55:53', 45),
(226, 1005, 1000, 26400, '2023-11-15 14:55:53', 46),
(227, 1005, 1004, 634, '2023-11-15 14:55:53', 46),
(228, 1005, 1006, 0, '2023-11-15 14:55:53', 46),
(229, 1005, 1007, 132, '2023-11-15 14:55:53', 46),
(230, 1005, 1005, 409, '2023-11-15 14:55:53', 46),
(231, 1006, 1000, 27000, '2023-11-15 14:55:53', 47),
(232, 1006, 1004, 662, '2023-11-15 14:55:53', 47),
(233, 1006, 1006, 0, '2023-11-15 14:55:53', 47),
(234, 1006, 1007, 135, '2023-11-15 14:55:53', 47),
(235, 1006, 1005, 428, '2023-11-15 14:55:53', 47),
(236, 1007, 1000, 30000, '2023-11-15 14:55:53', 48),
(237, 1007, 1004, 727, '2023-11-15 14:55:53', 48),
(238, 1007, 1006, 1818, '2023-11-15 14:55:53', 48),
(239, 1007, 1007, 150, '2023-11-15 14:55:53', 48),
(240, 1007, 1005, 940, '2023-11-15 14:55:53', 48),
(241, 1008, 1000, 58000, '2023-11-15 14:55:53', 49),
(242, 1008, 1004, 1099, '2023-11-15 14:55:53', 49),
(243, 1008, 1006, 0, '2023-11-15 14:55:53', 49),
(244, 1008, 1007, 290, '2023-11-15 14:55:53', 49),
(245, 1008, 1005, 943, '2023-11-15 14:55:53', 49),
(246, 1009, 1000, 28000, '2023-11-15 14:55:53', 50),
(247, 1009, 1004, 691, '2023-11-15 14:55:53', 50),
(248, 1009, 1006, 0, '2023-11-15 14:55:53', 50),
(249, 1009, 1007, 140, '2023-11-15 14:55:53', 50),
(250, 1009, 1005, 447, '2023-11-15 14:55:53', 50),
(251, 1010, 1000, 35000, '2023-11-15 14:55:53', 51),
(252, 1010, 1004, 871, '2023-11-15 14:55:53', 51),
(253, 1010, 1006, 1089, '2023-11-15 14:55:53', 51),
(254, 1010, 1007, 175, '2023-11-15 14:55:53', 51),
(255, 1010, 1005, 563, '2023-11-15 14:55:53', 51),
(256, 1011, 1000, 65000, '2023-11-15 14:55:53', 52),
(257, 1011, 1004, 1099, '2023-11-15 14:55:53', 52),
(258, 1011, 1006, 2748, '2023-11-15 14:55:53', 52),
(259, 1011, 1007, 325, '2023-11-15 14:55:53', 52),
(260, 1011, 1005, 4330, '2023-11-15 14:55:53', 52),
(261, 1012, 1000, 33600, '2023-11-15 14:55:53', 53),
(262, 1012, 1004, 835, '2023-11-15 14:55:53', 53),
(263, 1012, 1006, 0, '2023-11-15 14:55:53', 53),
(264, 1012, 1007, 0, '2023-11-15 14:55:53', 53),
(265, 1012, 1005, 540, '2023-11-15 14:55:53', 53),
(266, 1013, 1000, 35000, '2023-11-15 14:55:53', 54),
(267, 1013, 1004, 871, '2023-11-15 14:55:53', 54),
(268, 1013, 1006, 0, '2023-11-15 14:55:53', 54),
(269, 1013, 1007, 175, '2023-11-15 14:55:53', 54),
(270, 1013, 1005, 563, '2023-11-15 14:55:53', 54),
(271, 1014, 1000, 34000, '2023-11-15 14:55:53', 55),
(272, 1014, 1004, 835, '2023-11-15 14:55:53', 55),
(273, 1014, 1006, 348, '2023-11-15 14:55:53', 55),
(274, 1014, 1007, 170, '2023-11-15 14:55:53', 55),
(275, 1014, 1005, 1079, '2023-11-15 14:55:53', 55),
(276, 1015, 1000, 65000, '2023-11-15 14:55:53', 56),
(277, 1015, 1004, 1099, '2023-11-15 14:55:53', 56),
(278, 1015, 1006, 2748, '2023-11-15 14:55:53', 56),
(279, 1015, 1007, 325, '2023-11-15 14:55:53', 56),
(280, 1015, 1005, 1083, '2023-11-15 14:55:53', 56),
(281, 1016, 1000, 33600, '2023-11-15 14:55:53', 57),
(282, 1016, 1004, 835, '2023-11-15 14:55:53', 57),
(283, 1016, 1006, 2088, '2023-11-15 14:55:53', 57),
(284, 1016, 1007, 0, '2023-11-15 14:55:53', 57),
(285, 1016, 1005, 1079, '2023-11-15 14:55:53', 57),
(286, 1017, 1000, 35000, '2023-11-15 14:55:53', 58),
(287, 1017, 1004, 871, '2023-11-15 14:55:53', 58),
(288, 1017, 1006, 0, '2023-11-15 14:55:53', 58),
(289, 1017, 1007, 175, '2023-11-15 14:55:53', 58),
(290, 1017, 1005, 563, '2023-11-15 14:55:53', 58),
(291, 1018, 1000, 34000, '2023-11-15 14:55:53', 59),
(292, 1018, 1004, 835, '2023-11-15 14:55:53', 59),
(293, 1018, 1006, 348, '2023-11-15 14:55:53', 59),
(294, 1018, 1007, 170, '2023-11-15 14:55:53', 59),
(295, 1018, 1005, 1079, '2023-11-15 14:55:53', 59),
(296, 1019, 1000, 34000, '2023-11-15 14:55:53', 60),
(297, 1019, 1004, 835, '2023-11-15 14:55:53', 60),
(298, 1019, 1006, 348, '2023-11-15 14:55:53', 60),
(299, 1019, 1007, 170, '2023-11-15 14:55:53', 60),
(300, 1019, 1005, 1079, '2023-11-15 14:55:53', 60);

INSERT INTO form_record (form_id, emp_id, type_id, End_date, Start_date, status_id, Termination_date) 
VALUES (1, 1003, 1, '2023-11-04 17:00:00.000000', '2023-11-04 09:00:00.000000', 2, '2023-11-05 00:00:00.000000'),
(2, 1010, 2, '2023-11-05 16:30:00.000000', '2023-11-05 08:30:00.000000', 2, '2023-11-06 00:00:00.000000'),
(3, 1013, 1, '2023-11-06 18:15:00.000000', '2023-11-06 09:30:00.000000', 1, '2023-11-07 00:00:00.000000'),
(4, 1003, 2, '2023-11-12 23:45:33.025691', '2023-11-05 23:45:33.025691', 5, '2023-11-12 23:45:33.025691'),
(5, 1004, 2, '2023-11-06 09:33:30.808666', '2023-11-05 23:46:30.723497', 2, '2023-11-12 23:46:30.723497'),
(6, 1003, 2, '2023-11-07 13:23:00.986559', '2023-11-05 23:47:13.913414', 2, '2023-11-12 23:47:13.913414'),
(7, 1005, 2, '2023-11-12 23:48:01.752925', '2023-11-05 23:48:01.752925', 5, '2023-11-12 23:48:01.752925'),
(8, 1009, 2, '2023-11-13 00:12:23.054101', '2023-11-06 00:12:23.054101', 5, '2023-11-13 00:12:23.054101'),
(9, 1004, 3, '2023-11-13 00:14:51.603847', '2023-11-06 00:14:51.603847', 5, '2023-11-13 00:14:51.603847'),
(10, 1016, 1, '2023-11-13 10:06:30.167594', '2023-11-06 10:06:30.167594', 5, '2023-11-13 10:06:30.167594'),
(11, 1018, 3, '2023-11-15 23:06:30.167594', '2023-11-14 10:06:30.167594', 5, '2023-11-21 10:06:30.167594'),
(12, 1019, 3, '2023-11-15 23:06:30.167594', '2023-11-14 10:06:30.167594', 5, '2023-11-21 10:06:30.167594');

INSERT INTO form_eventlog (event_id, form_id, End_date, Start_date, head_count, sequence, status_id) 
VALUES (1, 1, '2023-11-04 19:00:00.000000', '2023-11-04 17:00:00.000000', 0, 0, 2),
(2, 2, '2023-11-05 20:30:00.000000', '2023-11-05 16:30:00.000000', 0, 0, 3),
(3, 3, '2023-11-06 21:15:00.000000', '2023-11-06 18:15:00.000000', 0, 0, 2),
(4, 4, '2023-11-12 23:45:33.025691', '2023-11-05 23:45:33.157401', 0, 0, 5),
(5, 5, '2023-11-06 09:33:30.808666', '2023-11-05 23:46:30.808666', 0, 0, 2),
(6, 6, '2023-11-07 13:23:00.986559', '2023-11-05 23:47:13.986559', 0, 0, 2),
(7, 7, '2023-11-12 23:48:01.752925', '2023-11-05 23:48:01.838567', 0, 0, 5),
(8, 8, '2023-11-13 00:12:23.054101', '2023-11-06 00:12:23.154224', 0, 0, 5),
(9, 9, '2023-11-13 00:14:51.603847', '2023-11-06 00:14:51.692523', 0, 0, 5),
(10, 10, '2023-11-13 10:06:30.167594', '2023-11-06 10:06:30.241287', 0, 0, 5),
(11, 11, '2023-11-15 23:06:30.167594', '2023-11-14 10:06:30.167594', 0, 0, 2),
(12, 12, '2023-11-15 23:06:30.167594', '2023-11-14 10:06:30.167594', 0, 0, 2);

INSERT INTO form_audit_eventlog (event_id, eventLog_id, auditor, message, End_date, status_id) 
VALUES (1, 1, 1000, '審核通過', '2023-11-04 19:00:00.000000', 2),
(2, 2, 1000, '審核未通過', '2023-11-05 20:30:00.000000', 3),
(3, 3, 1000, '審核通過', '2023-11-06 21:15:00.000000', 2),
(4, 4, 1000, null, '2023-11-12 23:45:33.025691', 5),
(5, 5, 1000, '辛苦了', '2023-11-06 09:33:30.808666', 2),
(6, 6, 1000, '辛苦了', '2023-11-07 13:23:00.986559', 2),
(7, 7, 1013, null, '2023-11-12 23:48:01.752925', 5),
(8, 8, 1012, null, '2023-11-13 00:12:23.054101', 5),
(9, 9, 1014, null, '2023-11-13 00:14:51.603847', 5),
(10, 10, 1016, null, '2023-11-13 10:06:30.167594', 5),
(11, 11, 1017, null, '2023-11-15 23:06:30.167594', 2),
(12, 12, 1017, null, '2023-11-15 23:06:30.167594', 2);

INSERT INTO apply_overtime (form_id, type, reason, date, start_time, end_time, file) 
 VALUES (1, 1, '專案緊急', '2023-11-04', '17:00:00', '19:00:00', null),
   (2, 2, '客戶需求', '2023-11-05', '17:00:00', '20:00:00', null),
   (3, 1, '加班補償', '2023-11-06', '18:00:00', '21:00:00', null),
   (4, 2, '凱神,我code還沒打完', '2023-11-01', '17:00:00', '23:00:00', ''),
   (5, 1, 'code還沒打完，真的好累', '2023-11-02', '17:00:00', '23:00:00', ''),
   (6, 1, '解決不完的BUG', '2023-11-03', '17:00:00', '23:00:00', ''),
   (7, 2, '週六了，BUG還沒處理完，真的好累', '2023-11-04', '17:00:00', '23:00:00', ''),
   (8, 2, '看起來又是加班的一天:)', '2023-11-05', '17:00:00', '23:00:00', '');

INSERT INTO apply_leave (form_id, type, reason, days, hours, start_time, file) 
VALUES (1, 2, '特休想放假', 1, 0, '2023-11-04 09:00:00.000000', null),
 (2, 2, '家裡有事情', 2, 0, '2023-11-05 08:00:00.000000', null),
 (3, 2, '家裡有事情', 3, 0, '2023-11-06 09:00:00.000000', null),
 (10, 2, '特休想放假', 0, 8, '2023-11-06 08:00:00.000000', '');
 
 INSERT INTO apply_resignation (file, leave_date, quit, reason, transfer_form, form_id) 
VALUES ('', '2023-11-16', true, '真的好累', true, 9),
('', '2023-11-30', true, '真的好累', true, 11),
('', '2023-11-30', true, '真的好累', true, 12);