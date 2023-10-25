DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

-- ----------------------------
-- 1、存儲每一個已配置的 jobDetail 的詳細訊息
-- ----------------------------
create table QRTZ_JOB_DETAILS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    job_name             varchar(200)    not null            comment '任務名稱',
    job_group            varchar(200)    not null            comment '任務組名',
    description          varchar(250)    null                comment '相關介紹',
    job_class_name       varchar(250)    not null            comment '執行任務類名稱',
    is_durable           varchar(1)      not null            comment '是否持久化',
    is_nonconcurrent     varchar(1)      not null            comment '是否並發',
    is_update_data       varchar(1)      not null            comment '是否更新數據',
    requests_recovery    varchar(1)      not null            comment '是否接受恢覆執行',
    job_data             blob            null                comment '存放持久化job對象',
    primary key (sched_name, job_name, job_group)
) engine=innodb comment = '任務詳細訊息表';

-- ----------------------------
-- 2、 存儲已配置的 Trigger 的訊息
-- ----------------------------
create table QRTZ_TRIGGERS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    trigger_name         varchar(200)    not null            comment '觸發器的名字',
    trigger_group        varchar(200)    not null            comment '觸發器所屬組的名字',
    job_name             varchar(200)    not null            comment 'qrtz_job_details表job_name的外鍵',
    job_group            varchar(200)    not null            comment 'qrtz_job_details表job_group的外鍵',
    description          varchar(250)    null                comment '相關介紹',
    next_fire_time       bigint(13)      null                comment '上一次觸發時間（毫秒）',
    prev_fire_time       bigint(13)      null                comment '下一次觸發時間（默認為-1表示不觸發）',
    priority             integer         null                comment '優先級',
    trigger_state        varchar(16)     not null            comment '觸發器狀態',
    trigger_type         varchar(8)      not null            comment '觸發器的類型',
    start_time           bigint(13)      not null            comment '開始時間',
    end_time             bigint(13)      null                comment '結束時間',
    calendar_name        varchar(200)    null                comment '日程表名稱',
    misfire_instr        smallint(2)     null                comment '補償執行的策略',
    job_data             blob            null                comment '存放持久化job對象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group) references QRTZ_JOB_DETAILS(sched_name, job_name, job_group)
) engine=innodb comment = '觸發器詳細訊息表';

-- ----------------------------
-- 3、 存儲簡單的 Trigger，包括重覆次數，間隔，以及已觸發的次數
-- ----------------------------
create table QRTZ_SIMPLE_TRIGGERS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外鍵',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外鍵',
    repeat_count         bigint(7)       not null            comment '重覆的次數統計',
    repeat_interval      bigint(12)      not null            comment '重覆的間隔時間',
    times_triggered      bigint(10)      not null            comment '已經觸發的次數',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = '簡單觸發器的訊息表';

-- ----------------------------
-- 4、 存儲 Cron Trigger，包括 Cron 表達式和時區訊息
-- ---------------------------- 
create table QRTZ_CRON_TRIGGERS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外鍵',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外鍵',
    cron_expression      varchar(200)    not null            comment 'cron表達式',
    time_zone_id         varchar(80)                         comment '時區',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Cron類型的觸發器表';

-- ----------------------------
-- 5、 Trigger 作為 Blob 類型存儲(用於 Quartz 用戶用 JDBC 創建他們自己定制的 Trigger 類型，JobStore 並不知道如何存儲實例的時候)
-- ---------------------------- 
create table QRTZ_BLOB_TRIGGERS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外鍵',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外鍵',
    blob_data            blob            null                comment '存放持久化Trigger對象',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Blob類型的觸發器表';

-- ----------------------------
-- 6、 以 Blob 類型存儲存放日歷訊息， quartz可配置一個日歷來指定一個時間範圍
-- ---------------------------- 
create table QRTZ_CALENDARS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    calendar_name        varchar(200)    not null            comment '日歷名稱',
    calendar             blob            not null            comment '存放持久化calendar對象',
    primary key (sched_name, calendar_name)
) engine=innodb comment = '日歷訊息表';

-- ----------------------------
-- 7、 存儲已暫停的 Trigger 組的訊息
-- ---------------------------- 
create table QRTZ_PAUSED_TRIGGER_GRPS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外鍵',
    primary key (sched_name, trigger_group)
) engine=innodb comment = '暫停的觸發器表';

-- ----------------------------
-- 8、 存儲與已觸發的 Trigger 相關的狀態訊息，以及相聯 Job 的執行訊息
-- ---------------------------- 
create table QRTZ_FIRED_TRIGGERS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    entry_id             varchar(95)     not null            comment '調度器實例id',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外鍵',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外鍵',
    instance_name        varchar(200)    not null            comment '調度器實例名',
    fired_time           bigint(13)      not null            comment '觸發的時間',
    sched_time           bigint(13)      not null            comment '定時器制定的時間',
    priority             integer         not null            comment '優先級',
    state                varchar(16)     not null            comment '狀態',
    job_name             varchar(200)    null                comment '任務名稱',
    job_group            varchar(200)    null                comment '任務組名',
    is_nonconcurrent     varchar(1)      null                comment '是否並發',
    requests_recovery    varchar(1)      null                comment '是否接受恢覆執行',
    primary key (sched_name, entry_id)
) engine=innodb comment = '已觸發的觸發器表';

-- ----------------------------
-- 9、 存儲少量的有關 Scheduler 的狀態訊息，假如是用於集群中，可以看到其他的 Scheduler 實例
-- ---------------------------- 
create table QRTZ_SCHEDULER_STATE (
    sched_name           varchar(120)    not null            comment '調度名稱',
    instance_name        varchar(200)    not null            comment '實例名稱',
    last_checkin_time    bigint(13)      not null            comment '上次檢查時間',
    checkin_interval     bigint(13)      not null            comment '檢查間隔時間',
    primary key (sched_name, instance_name)
) engine=innodb comment = '調度器狀態表';

-- ----------------------------
-- 10、 存儲程序的悲觀鎖的訊息(假如使用了悲觀鎖)
-- ---------------------------- 
create table QRTZ_LOCKS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    lock_name            varchar(40)     not null            comment '悲觀鎖名稱',
    primary key (sched_name, lock_name)
) engine=innodb comment = '存儲的悲觀鎖訊息表';

-- ----------------------------
-- 11、 Quartz集群實現同步機制的行鎖表
-- ---------------------------- 
create table QRTZ_SIMPROP_TRIGGERS (
    sched_name           varchar(120)    not null            comment '調度名稱',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggers表trigger_name的外鍵',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggers表trigger_group的外鍵',
    str_prop_1           varchar(512)    null                comment 'String類型的trigger的第一個參數',
    str_prop_2           varchar(512)    null                comment 'String類型的trigger的第二個參數',
    str_prop_3           varchar(512)    null                comment 'String類型的trigger的第三個參數',
    int_prop_1           int             null                comment 'int類型的trigger的第一個參數',
    int_prop_2           int             null                comment 'int類型的trigger的第二個參數',
    long_prop_1          bigint          null                comment 'long類型的trigger的第一個參數',
    long_prop_2          bigint          null                comment 'long類型的trigger的第二個參數',
    dec_prop_1           numeric(13,4)   null                comment 'decimal類型的trigger的第一個參數',
    dec_prop_2           numeric(13,4)   null                comment 'decimal類型的trigger的第二個參數',
    bool_prop_1          varchar(1)      null                comment 'Boolean類型的trigger的第一個參數',
    bool_prop_2          varchar(1)      null                comment 'Boolean類型的trigger的第二個參數',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = '同步機制的行鎖表';

commit;