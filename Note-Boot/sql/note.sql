-- ----------------------------
-- 1、笔记表
-- ----------------------------
drop table if exists sys_note;
create table sys_note
(
    note_id       bigint(20) not null auto_increment comment '笔记ID',
    note_title    varchar(255) default '' comment '笔记标题',
    reminder_date datetime comment '提醒日期',
    note_content  text         default null comment '笔记内容',
    note_status   char(1)      default '0' comment '笔记状态',
    note_type     char(1)      default '0' comment '笔记类型',
    del_flag      char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    create_by     varchar(64)  default '' comment '创建者',
    create_time   datetime comment '创建时间',
    update_by     varchar(64)  default '' comment '更新者',
    update_time   datetime comment '更新时间',
    remark        varchar(255) default null comment '备注',
    primary key (note_id)
) engine=innodb auto_increment=100 comment = '笔记表';