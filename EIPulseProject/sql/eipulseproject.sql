-- (if not exist ) create database eipulse_project;
-- drop database  eipulse_project;
use eipulse_project;
-- ----------------------------
-- 1、部門表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment '部門id',
  parent_id         bigint(20)      default 0                  comment '父部門id',
  ancestors         varchar(50)     default ''                 comment '祖級列表',
  dept_name         varchar(30)     default ''                 comment '部門名稱',
  order_num         int(4)          default 0                  comment '顯示順序',
  leader            varchar(20)     default null               comment '負責人',
  phone             varchar(11)     default null               comment '聯絡電話',
  email             varchar(50)     default null               comment '信箱',
  status            char(1)         default '0'                comment '部門狀態（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '刪除標誌（0代表存在 2代表刪除）',
  create_by         varchar(64)     default ''                 comment '創建者',
  create_time 	    datetime                                   comment '創建時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '部門表';

-- ----------------------------
-- 初始化-部門表數據
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          'EIPulse開發',   0, '玉闓', '0923456789', 'yukai@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      '台灣總公司', 1, '玉闓', '0923456789', 'yukai@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      '美國分公司', 2, '諺雨', '0912345678', 'yanyu@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  '研發部門',   1, '城垣', '0978654321', 'chengyuan@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  '行銷部門',   2, '敘楷', '0967890123', 'xukai@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  '測試部門',   3, '湯圓', '0901234567', 'tangyuan@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  '財務部門',   4, '應君', '0943218765', 'yingjun@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  '人事部門',   5, '逸嘉', '0998765432', 'yijia@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  '行銷部門',   1, 'JoJo', '0987654321', 'jojo@gmail.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  '財務部門',   2, 'Dio', '0912345678', 'dio@gmail.com', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2、員工資訊表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '員工ID',
  dept_id           bigint(20)      default null               comment '部門ID',
  user_name         varchar(30)     not null                   comment '員工帳號',
  nick_name         varchar(30)     not null                   comment '員工暱稱',
  user_type         varchar(2)      default '00'               comment '員工類型（00系統員工）',
  email             varchar(50)     default ''                 comment '員工信箱',
  phonenumber       varchar(11)     default ''                 comment '手機號碼',
  sex               char(1)         default '0'                comment '員工性別（0男 1女 2未知）',
  avatar            varchar(100)    default ''                 comment '頭像位址',
  password          varchar(100)    default ''                 comment '密碼',
  status            char(1)         default '0'                comment '帳號狀態（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '刪除標誌（0代表存在 2代表刪除）',
  login_ip          varchar(50)     default ''                 comment '最後登入IP',
  login_date        datetime                                   comment '最後登入時間',
  create_by         varchar(64)     default ''                 comment '創建者',
  create_time       datetime                                   comment '創建時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(500)    default null               comment '備註',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '員工資訊表';

-- ----------------------------
-- 初始化-員工資訊表數據
-- ----------------------------
insert into sys_user values(1,103, 'admin', '城垣', '00', 'chengyuan@gmail.com', '0978654321', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '管理員');
insert into sys_user values(2,105, 'tangyuan',    '湯圓', '00', 'tangyuan@gmail.com',  '0901234567', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '測試員');
insert into sys_user values(3,102, 'yanyu',    '諺雨', '00', 'yanyu@gmail.com',  '0912345678', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '美國分部長');
insert into sys_user values(4,104, 'xukai',    '敘楷', '00', 'xukai@gmail.com',  '0967890123', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '行銷部長');
insert into sys_user values(5,106, 'yingjun',    '應君', '00', 'wangjun@gmail.com',  '0943218765', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '財務部長');
insert into sys_user values(6,107, 'yijia',    '逸嘉', '00', 'xiaokai@gmail.com',  '0998765432', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '人資部長');
insert into sys_user values(7,108, 'JoJo',    'JoJo', '00', 'wuyuan@gmail.com',  '0987654321', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '美國分部行銷部長');
insert into sys_user values(8,109, 'Dio',    'Dio', '00', 'liqiang@gmail.com',  '0912345678', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '美國分部財務部長');
insert into sys_user values(9, 104, 'mingxun',    '名薰', '00', 'wangmei@gmail.com',  '0923456782', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '行銷部員工');
insert into sys_user values(10,104,  'weishen',    '鮪申', '00', 'zhangli@gmail.com',  '0923456783', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '行銷部員工');
insert into sys_user values(11,104,  'chenhui',    '陳慧', '00', 'chenhui@gmail.com',  '0923456784', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '行銷部員工');
insert into sys_user values(12, 104, 'liuwei',    '劉偉', '00', 'liuwei@gmail.com',  '0923456785', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '行銷部員工');
insert into sys_user values(13, 104, 'huangkai',    '黃凱', '00', 'huangkai@gmail.com',  '0923456786', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '行銷部員工');
insert into sys_user values(14, 104, 'linlian',    '林蓮', '00', 'linlian@gmail.com',  '0923456787', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '行銷部員工');
insert into sys_user values(15,103, 'yangjian',    '楊健', '00', 'yangjian@gmail.com',  '0923456788', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '研發部員工');
insert into sys_user values(16,103, 'zhouming',    '周明', '00', 'zhouming@gmail.com',  '0923456789', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '研發部員工');
insert into sys_user values(17,106, 'wuyu',    '吳玉', '00', 'wuyu@gmail.com',  '0923456790', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '財務部員工');
insert into sys_user values(18,106, 'xujun',    '許俊', '00', 'xujun@gmail.com',  '0923456791', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '財務部員工');
insert into sys_user values(19,107, 'caili',    '蔡麗', '00', 'caili@gmail.com',  '0923456792', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '人資部員工');
insert into sys_user values(20,107, 'zhengjie',    '鄭杰', '00', 'zhengjie@gmail.com',  '0923456793', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, '人資部員工');




-- ----------------------------
-- 3、職位資訊表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '職位代號',
  post_code     varchar(64)     not null                   comment '職位編碼',
  post_name     varchar(50)     not null                   comment '職位名稱',
  post_sort     int(4)          not null                   comment '顯示順序',
  status        char(1)         not null                   comment '狀態（0正常 1停用）',
  create_by     varchar(64)     default ''                 comment '創建者',
  create_time   datetime                                   comment '創建時間',
  update_by     varchar(64)     default ''			       comment '更新者',
  update_time   datetime                                   comment '更新時間',
  remark        varchar(500)    default null               comment '備註',
  primary key (post_id)
) engine=innodb comment = '職位資訊表';

-- ----------------------------
-- 初始化-職位資訊表數據
-- ----------------------------
insert into sys_post values(1, 'ceo',  '執行長',    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   '主管',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   '人資',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', '普通員工',  4, '0', 'admin', sysdate(), '', null, '');





-- ----------------------------
-- 4、角色資訊表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '角色ID',
  role_name            varchar(30)     not null                   comment '角色名稱',
  role_key             varchar(100)    not null                   comment '角色權限字串',
  role_sort            int(4)          not null                   comment '顯示順序',
  data_scope           char(1)         default '1'                comment '數據範圍（1：全部數據權限 2：自定數據權限 3：本部門數據權限 4：本部門及以下數據權限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜單樹選擇項是否關聯顯示',
  dept_check_strictly  tinyint(1)      default 1                  comment '部門樹選擇項是否關聯顯示',
  status               char(1)         not null                   comment '角色狀態（0正常 1停用）',
  del_flag             char(1)         default '0'                comment '刪除標誌（0代表存在 2代表刪除）',
  create_by            varchar(64)     default ''                 comment '創建者',
  create_time          datetime                                   comment '創建時間',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新時間',
  remark               varchar(500)    default null               comment '備註',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色資訊表';

-- ----------------------------
-- 初始化-角色資訊表數據
-- ----------------------------
insert into sys_role values('1', '管理員',  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '管理員');
insert into sys_role values('2', '普通員工',    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '普通員工');


-- ----------------------------
-- 5、菜單權限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜單ID',
  menu_name         varchar(50)     not null                   comment '菜單名稱',
  parent_id         bigint(20)      default 0                  comment '父菜單ID',
  order_num         int(4)          default 0                  comment '顯示順序',
  path              varchar(200)    default ''                 comment '路由位址',
  component         varchar(255)    default null               comment '組件路徑',
  is_frame          int(1)          default 1                  comment '是否為外鏈（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否快取（0快取 1不快取）',
  menu_type         char(1)         default ''                 comment '菜單類型（M目錄 C菜單 F按鈕）',
  visible           char(1)         default 0                  comment '菜單狀態（0顯示 1隱藏）',
  status            char(1)         default 0                  comment '菜單狀態（0正常 1停用）',
  perms             varchar(100)    default null               comment '權限標識',
  icon              varchar(100)    default '#'                comment '菜單圖示',
  create_by         varchar(64)     default ''                 comment '創建者',
  create_time       datetime                                   comment '創建時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(500)    default ''                 comment '備註',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜單權限表';

-- ----------------------------
-- 初始化-菜單資訊表數據
-- ----------------------------
-- 一級菜單
insert into sys_menu values('1', '系統管理', '0', '1', 'system',           null,   1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, '系統管理目錄');
insert into sys_menu values('2', '系統監控', '0', '2', 'monitor',          null,   1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, '系統監控目錄');
insert into sys_menu values('3', '系統工具', '0', '3', 'tool',             null,   1, 0, 'M', '0', '0', '', 'tool',     'admin', sysdate(), '', null, '系統工具目錄');

-- 二級菜單
insert into sys_menu values('100',  '員工管理', '1',   '1', 'user',       'system/user/index',        1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, '員工管理菜單');
insert into sys_menu values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, '角色管理菜單');
insert into sys_menu values('102',  '菜單管理', '1',   '3', 'menu',       'system/menu/index',        1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, '菜單管理菜單');
insert into sys_menu values('103',  '部門管理', '1',   '4', 'dept',       'system/dept/index',        1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, '部門管理菜單');
insert into sys_menu values('104',  '職位管理', '1',   '5', 'post',       'system/post/index',        1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, '職位管理菜單');
insert into sys_menu values('105',  '字典管理', '1',   '6', 'dict',       'system/dict/index',        1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, '字典管理菜單');
insert into sys_menu values('106',  '參數設置', '1',   '7', 'config',     'system/config/index',      1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, '參數設置菜單');
insert into sys_menu values('107',  '通知公告', '1',   '8', 'notice',     'system/notice/index',      1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, '通知公告菜單');
insert into sys_menu values('108',  '日誌管理', '1',   '9', 'log',        '',                         1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, '日誌管理菜單');
insert into sys_menu values('109',  '在線員工', '2',   '1', 'online',     'monitor/online/index',     1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, '在線員工菜單');
insert into sys_menu values('110',  '定時任務', '2',   '2', 'job',        'monitor/job/index',        1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, '定時任務菜單');
insert into sys_menu values('111',  '數據監控', '2',   '3', 'druid',      'monitor/druid/index',      1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', sysdate(), '', null, '數據監控菜單');
insert into sys_menu values('112',  '服務監控', '2',   '4', 'server',     'monitor/server/index',     1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, '服務監控菜單');
insert into sys_menu values('113',  '快取監控', '2',   '5', 'cache',      'monitor/cache/index',      1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, '快取監控菜單');
insert into sys_menu values('114',  '表單構建', '3',   '1', 'build',      'tool/build/index',         1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'admin', sysdate(), '', null, '表單構建菜單');
insert into sys_menu values('115',  '程式碼生成', '3',   '2', 'gen',        'tool/gen/index',           1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', sysdate(), '', null, '程式碼生成菜單');
insert into sys_menu values('116',  '系統介面', '3',   '3', 'swagger',    'tool/swagger/index',       1, 0, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', sysdate(), '', null, '系統介面菜單');
-- 三級菜單
insert into sys_menu values('500',  '操作日誌', '108', '1', 'operlog',    'monitor/operlog/index',    1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, '操作日誌菜單');
insert into sys_menu values('501',  '登入日誌', '108', '2', 'logininfor', 'monitor/logininfor/index', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, '登入日誌菜單');
-- 員工管理按鈕
insert into sys_menu values('1001', '員工查詢', '100', '1',  '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', '員工新增', '100', '2',  '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', '員工修改', '100', '3',  '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', '員工刪除', '100', '4',  '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', '員工導出', '100', '5',  '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', '員工導入', '100', '6',  '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1007', '重設密碼', '100', '7',  '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- 角色管理按鈕
insert into sys_menu values('1008', '角色查詢', '101', '1',  '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', '角色新增', '101', '2',  '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', '角色修改', '101', '3',  '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', '角色刪除', '101', '4',  '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1012', '角色導出', '101', '5',  '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- 菜單管理按鈕
insert into sys_menu values('1013', '菜單查詢', '102', '1',  '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', '菜單新增', '102', '2',  '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', '菜單修改', '102', '3',  '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1016', '菜單刪除', '102', '4',  '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- 部門管理按鈕
insert into sys_menu values('1017', '部門查詢', '103', '1',  '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', '部門新增', '103', '2',  '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', '部門修改', '103', '3',  '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1020', '部門刪除', '103', '4',  '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- 職位管理按鈕
insert into sys_menu values('1021', '職位查詢', '104', '1',  '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', '職位新增', '104', '2',  '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', '職位修改', '104', '3',  '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', '職位刪除', '104', '4',  '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1025', '職位導出', '104', '5',  '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- 字典管理按鈕
insert into sys_menu values('1026', '字典查詢', '105', '1', '#', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', '字典新增', '105', '2', '#', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', '字典修改', '105', '3', '#', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', '字典刪除', '105', '4', '#', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1030', '字典導出', '105', '5', '#', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- 參數設置按鈕
insert into sys_menu values('1031', '參數查詢', '106', '1', '#', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', '參數新增', '106', '2', '#', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', '參數修改', '106', '3', '#', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', '參數刪除', '106', '4', '#', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1035', '參數導出', '106', '5', '#', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- 通知公告按鈕
insert into sys_menu values('1036', '公告查詢', '107', '1', '#', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', '公告新增', '107', '2', '#', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', '公告修改', '107', '3', '#', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1039', '公告刪除', '107', '4', '#', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- 操作日誌按鈕
insert into sys_menu values('1040', '操作查詢', '500', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', '操作刪除', '500', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1042', '日誌導出', '500', '4', '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- 登入日誌按鈕
insert into sys_menu values('1043', '登入查詢', '501', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', '登入刪除', '501', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', '日誌導出', '501', '3', '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
-- 在線員工按鈕
insert into sys_menu values('1046', '線上查詢', '109', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', '批次強退', '109', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', '單條強退', '109', '3', '#', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');
-- 定時任務按鈕
insert into sys_menu values('1049', '任務查詢', '110', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1050', '任務新增', '110', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', '任務修改', '110', '3', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', '任務刪除', '110', '4', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', '狀態修改', '110', '5', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', '任務導出', '110', '7', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');
-- 程式碼生成按鈕
insert into sys_menu values('1055', '生成查詢', '115', '1', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1056', '生成修改', '115', '2', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1057', '生成刪除', '115', '3', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1058', '導入程式碼', '115', '2', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1059', '預覽程式碼', '115', '4', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1060', '生成程式碼', '115', '5', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6、員工和角色關聯表  員工N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '員工ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '員工和角色關聯表';

-- ----------------------------
-- 初始化-員工和角色關聯表數據
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');
insert into sys_user_role values ('3', '2');
insert into sys_user_role values ('4', '2');
insert into sys_user_role values ('5', '2');
insert into sys_user_role values ('6', '2');
insert into sys_user_role values ('7', '2');
insert into sys_user_role values ('8', '2');
insert into sys_user_role values ('9', '2');
insert into sys_user_role values ('10', '2');
insert into sys_user_role values ('11', '2');
insert into sys_user_role values ('12', '2');
insert into sys_user_role values ('13', '2');
insert into sys_user_role values ('14', '2');
insert into sys_user_role values ('15', '2');
insert into sys_user_role values ('16', '2');
insert into sys_user_role values ('17', '2');
insert into sys_user_role values ('18', '2');
insert into sys_user_role values ('19', '2');
insert into sys_user_role values ('20', '2');

-- ----------------------------
-- 7、角色和菜單關聯表  角色1-N菜單
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜單ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜單關聯表';

-- ----------------------------
-- 初始化-角色和菜單關聯表數據
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');

-- ----------------------------
-- 8、角色和部門關聯表  角色1-N部門
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment '角色ID',
  dept_id   bigint(20) not null comment '部門ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '角色和部門關聯表';

-- ----------------------------
-- 初始化-角色和部門關聯表數據
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '102');

insert into sys_role_dept values ('2', '104');
insert into sys_role_dept values ('2', '105');
insert into sys_role_dept values ('2', '106');
insert into sys_role_dept values ('2', '107');
insert into sys_role_dept values ('2', '108');
insert into sys_role_dept values ('2', '109');

-- ----------------------------
-- 9、員工與職位關聯表  員工1-N職位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '員工ID',
  post_id   bigint(20) not null comment '職位ID',
  primary key (user_id, post_id)
) engine=innodb comment = '員工與職位關聯表';

-- ----------------------------
-- 初始化-員工與職位關聯表數據
-- ----------------------------
insert into sys_user_post values ('1', '2');
insert into sys_user_post values ('2', '2');
insert into sys_user_post values ('3', '2');
insert into sys_user_post values ('4', '2');
insert into sys_user_post values ('5', '2');
insert into sys_user_post values ('6', '3');
insert into sys_user_post values ('7', '2');
insert into sys_user_post values ('8', '2');

insert into sys_user_post values ('9', '4');
insert into sys_user_post values ('10', '4');
insert into sys_user_post values ('11', '4');
insert into sys_user_post values ('12', '4');
insert into sys_user_post values ('13', '4');
insert into sys_user_post values ('14', '4');
insert into sys_user_post values ('15', '4');
insert into sys_user_post values ('16', '4');
insert into sys_user_post values ('17', '4');
insert into sys_user_post values ('18', '4');
insert into sys_user_post values ('19', '3');
insert into sys_user_post values ('20', '3');
-- ----------------------------
-- 10、操作日誌記錄
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment '日誌主鍵',
  title             varchar(50)     default ''                 comment '模組標題',
  business_type     int(2)          default 0                  comment '業務類型（0其它 1新增 2修改 3刪除）',
  method            varchar(100)    default ''                 comment '方法名稱',
  request_method    varchar(10)     default ''                 comment '請求方式',
  operator_type     int(1)          default 0                  comment '操作類別（0其它 1後台員工 2手機端員工）',
  oper_name         varchar(50)     default ''                 comment '操作人員',
  dept_name         varchar(50)     default ''                 comment '部門名稱',
  oper_url          varchar(255)    default ''                 comment '請求URL',
  oper_ip           varchar(50)     default ''                 comment '主機位址',
  oper_location     varchar(255)    default ''                 comment '操作地點',
  oper_param        varchar(2000)   default ''                 comment '請求參數',
  json_result       varchar(2000)   default ''                 comment '返回參數',
  status            int(1)          default 0                  comment '操作狀態（0正常 1異常）',
  error_msg         varchar(2000)   default ''                 comment '錯誤消息',
  oper_time         datetime                                   comment '操作時間',
  primary key (oper_id)
) engine=innodb auto_increment=100 comment = '操作日誌記錄';


-- ----------------------------
-- 11、字典類型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '字典主鍵',
  dict_name        varchar(100)    default ''                 comment '字典名稱',
  dict_type        varchar(100)    default ''                 comment '字典類型',
  status           char(1)         default '0'                comment '狀態（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '創建者',
  create_time      datetime                                   comment '創建時間',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新時間',
  remark           varchar(500)    default null               comment '備註',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '字典類型表';

insert into sys_dict_type values(1,  '員工性別', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, '員工性別列表');
insert into sys_dict_type values(2,  '菜單狀態', 'sys_show_hide',       '0', 'admin', sysdate(), '', null, '菜單狀態列表');
insert into sys_dict_type values(3,  '系統開關', 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, '系統開關列表');
insert into sys_dict_type values(4,  '任務狀態', 'sys_job_status',      '0', 'admin', sysdate(), '', null, '任務狀態列表');
insert into sys_dict_type values(5,  '任務分組', 'sys_job_group',       '0', 'admin', sysdate(), '', null, '任務分組列表');
insert into sys_dict_type values(6,  '系統是否', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, '系統是否列表');
insert into sys_dict_type values(7,  '通知類型', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, '通知類型列表');
insert into sys_dict_type values(8,  '通知狀態', 'sys_notice_status',   '0', 'admin', sysdate(), '', null, '通知狀態列表');
insert into sys_dict_type values(9,  '操作類型', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, '操作類型列表');
insert into sys_dict_type values(10, '系統狀態', 'sys_common_status',   '0', 'admin', sysdate(), '', null, '登入狀態列表');


-- ----------------------------
-- 12、字典數據表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '字典編碼',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典標籤',
  dict_value       varchar(100)    default ''                 comment '字典鍵值',
  dict_type        varchar(100)    default ''                 comment '字典類型',
  css_class        varchar(100)    default null               comment '樣式屬性（其他樣式擴展）',
  list_class       varchar(100)    default null               comment '表格回顯樣式',
  is_default       char(1)         default 'N'                comment '是否默認（Y是 N否）',
  status           char(1)         default '0'                comment '狀態（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '創建者',
  create_time      datetime                                   comment '創建時間',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新時間',
  remark           varchar(500)    default null               comment '備註',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典數據表';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, '性別男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性別女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性別未知');
insert into sys_dict_data values(4,  1,  '顯示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '顯示菜單');
insert into sys_dict_data values(5,  2,  '隱藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '隱藏菜單');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常狀態');
insert into sys_dict_data values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用狀態');
insert into sys_dict_data values(8,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常狀態');
insert into sys_dict_data values(9,  2,  '暫停',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用狀態');
insert into sys_dict_data values(10, 1,  '默認',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, '預設分組');
insert into sys_dict_data values(11, 2,  '系統',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, '系統分組');
insert into sys_dict_data values(12, 1,  '是',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '系統預設是');
insert into sys_dict_data values(13, 2,  '否',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '系統默認否');
insert into sys_dict_data values(14, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '通知');
insert into sys_dict_data values(15, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, '公告');
insert into sys_dict_data values(16, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常狀態');
insert into sys_dict_data values(17, 2,  '關閉',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '關閉狀態');
insert into sys_dict_data values(18, 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '新增操作');
insert into sys_dict_data values(19, 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '修改操作');
insert into sys_dict_data values(20, 3,  '刪除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '刪除操作');
insert into sys_dict_data values(21, 4,  '授權',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '授權操作');
insert into sys_dict_data values(22, 5,  '導出',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '導出操作');
insert into sys_dict_data values(23, 6,  '導入',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '導入操作');
insert into sys_dict_data values(24, 7,  '強退',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '強退操作');
insert into sys_dict_data values(25, 8,  '生成程式碼', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '生成操作');
insert into sys_dict_data values(26, 9,  '清空數據', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '清空操作');
insert into sys_dict_data values(27, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '正常狀態');
insert into sys_dict_data values(28, 2,  '失敗',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用狀態');


-- ----------------------------
-- 13、參數配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment '參數主鍵',
  config_name       varchar(100)    default ''                 comment '參數名稱',
  config_key        varchar(100)    default ''                 comment '參數鍵名',
  config_value      varchar(500)    default ''                 comment '參數鍵值',
  config_type       char(1)         default 'N'                comment '系統內建（Y是 N否）',
  create_by         varchar(64)     default ''                 comment '創建者',
  create_time       datetime                                   comment '創建時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(500)    default null               comment '備註',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '參數配置表';

insert into sys_config values(1, '主框架頁-默認皮膚樣式名稱', 'sys.index.skinName',     'skin-blue',     'Y', 'admin', sysdate(), '', null, '藍色 skin-blue、綠色 skin-green、紫色 skin-purple、紅色 skin-red、黃色 skin-yellow' );
insert into sys_config values(2, '員工管理-帳號初始密碼',     'sys.user.initPassword',  '123456',        'Y', 'admin', sysdate(), '', null, '初始化密碼 123456' );
insert into sys_config values(3, '主框架頁-側邊欄主題',       'sys.index.sideTheme',    'theme-dark',    'Y', 'admin', sysdate(), '', null, '深色主題theme-dark，淺色主題theme-light' );


-- ----------------------------
-- 14、系統訪問記錄
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment '訪問ID',
  user_name      varchar(50)    default ''                comment '員工帳號',
  ipaddr         varchar(50)    default ''                comment '登入IP位址',
  login_location varchar(255)   default ''                comment '登入地點',
  browser        varchar(50)    default ''                comment '瀏覽器類型',
  os             varchar(50)    default ''                comment '操作系統',
  status         char(1)        default '0'               comment '登入狀態（0成功 1失敗）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '訪問時間',
  primary key (info_id)
) engine=innodb auto_increment=100 comment = '系統訪問記錄';


-- ----------------------------
-- 15、定時任務調度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment '任務ID',
  job_name            varchar(64)   default ''                 comment '任務名稱',
  job_group           varchar(64)   default 'DEFAULT'          comment '任務組名',
  invoke_target       varchar(500)  not null                   comment '調用目標字串',
  cron_expression     varchar(255)  default ''                 comment 'cron執行表達式',
  misfire_policy      varchar(20)   default '3'                comment '計劃執行錯誤策略（1立即執行 2執行一次 3放棄執行）',
  concurrent          char(1)       default '1'                comment '是否並發執行（0允許 1禁止）',
  status              char(1)       default '0'                comment '狀態（0正常 1暫停）',
  create_by           varchar(64)   default ''                 comment '創建者',
  create_time         datetime                                 comment '創建時間',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新時間',
  remark              varchar(500)  default ''                 comment '備註資訊',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '定時任務調度表';

insert into sys_job values(1, '系統默認（無參）', 'DEFAULT', 'eipulseTask.eipulseNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(2, '系統默認（有參）', 'DEFAULT', 'eipulseTask.eipulseParams(\'eipulse\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(3, '系統默認（多參）', 'DEFAULT', 'eipulseTask.eipulseMultipleParams(\'eipulse\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 16、定時任務調度日誌表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment '任務日誌ID',
  job_name            varchar(64)    not null                   comment '任務名稱',
  job_group           varchar(64)    not null                   comment '任務組名',
  invoke_target       varchar(500)   not null                   comment '調用目標字串',
  job_message         varchar(500)                              comment '日誌資訊',
  status              char(1)        default '0'                comment '執行狀態（0正常 1失敗）',
  exception_info      varchar(2000)  default ''                 comment '異常資訊',
  create_time         datetime                                  comment '創建時間',
  primary key (job_log_id)
) engine=innodb comment = '定時任務調度日誌表';


-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment '公告ID',
  notice_title      varchar(50)     not null                   comment '公告標題',
  notice_type       char(1)         not null                   comment '公告類型（1通知 2公告）',
  notice_content    longblob        default null               comment '公告內容',
  status            char(1)         default '0'                comment '公告狀態（0正常 1關閉）',
  create_by         varchar(64)     default ''                 comment '創建者',
  create_time       datetime                                   comment '創建時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(255)    default null               comment '備註',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '通知公告表';

-- ----------------------------
-- 初始化-公告資訊表數據
-- ----------------------------


-- ----------------------------
-- 18、程式碼生成業務表
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null auto_increment    comment '編號',
  table_name        varchar(200)    default ''                 comment '表名稱',
  table_comment     varchar(500)    default ''                 comment '表描述',
  class_name        varchar(100)    default ''                 comment '實體類名稱',
  tpl_category      varchar(200)    default 'crud'             comment '使用的模板（crud單表操作 tree樹表操作）',
  package_name      varchar(100)                               comment '生成包路徑',
  module_name       varchar(30)                                comment '生成模組名',
  business_name     varchar(30)                                comment '生成業務名',
  function_name     varchar(50)                                comment '生成功能名',
  function_author   varchar(50)                                comment '生成功能作者',
  gen_type          char(1)         default '0'                comment '生成程式碼方式（0zip壓縮包 1自訂路徑）',
  gen_path          varchar(200)    default '/'                comment '生成路徑（不填默認項目路徑）',
  options           varchar(1000)                              comment '其它生成選項',
  create_by         varchar(64)     default ''                 comment '創建者',
  create_time 	    datetime                                   comment '創建時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  remark            varchar(500)    default null               comment '備註',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = '程式碼生成業務表';


-- ----------------------------
-- 19、程式碼生成業務表欄位
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment '編號',
  table_id          varchar(64)                                comment '歸屬表編號',
  column_name       varchar(200)                               comment '列名稱',
  column_comment    varchar(500)                               comment '列描述',
  column_type       varchar(100)                               comment '列類型',
  java_type         varchar(500)                               comment 'JAVA類型',
  java_field        varchar(200)                               comment 'JAVA欄位名',
  is_pk             char(1)                                    comment '是否主鍵（1是）',
  is_increment      char(1)                                    comment '是否自增（1是）',
  is_required       char(1)                                    comment '是否必填（1是）',
  is_insert         char(1)                                    comment '是否為插入欄位（1是）',
  is_edit           char(1)                                    comment '是否編輯欄位（1是）',
  is_list           char(1)                                    comment '是否列表欄位（1是）',
  is_query          char(1)                                    comment '是否查詢欄位（1是）',
  query_type        varchar(200)    default 'EQ'               comment '查詢方式（等於、不等於、大於、小於、範圍）',
  html_type         varchar(200)                               comment '顯示類型（文本框、文本域、下拉框、複選框、單選框、日期控制項）',
  dict_type         varchar(200)    default ''                 comment '字典類型',
  sort              int                                        comment '排序',
  create_by         varchar(64)     default ''                 comment '創建者',
  create_time 	    datetime                                   comment '創建時間',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新時間',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = '程式碼生成業務表欄位';