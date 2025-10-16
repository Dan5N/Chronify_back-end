-- 日程管理系统数据库创建脚本
-- 适用于 MySQL 8.0+

-- 创建数据库
CREATE DATABASE IF NOT EXISTS chronify
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE chronify;

-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '加密后的密码',
    nickname VARCHAR(100) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    gender VARCHAR(10) COMMENT '性别',
    school VARCHAR(100) COMMENT '学校',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at TIMESTAMP NULL COMMENT '删除时间（软删除）',

    INDEX idx_username (username),
    INDEX idx_deleted (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 日程表
CREATE TABLE schedules (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    event VARCHAR(200) NOT NULL COMMENT '事件名称',
    date DATE NOT NULL COMMENT '日期',
    time TIME NOT NULL COMMENT '时间',
    location VARCHAR(200) COMMENT '地点',
    people VARCHAR(500) COMMENT '参与人',
    priority ENUM('important-urgent', 'important-not-urgent', 'not-important-urgent', 'not-important-not-urgent') NOT NULL COMMENT '优先级',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at TIMESTAMP NULL COMMENT '删除时间（软删除）',

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, date),
    INDEX idx_date (date),
    INDEX idx_deleted (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日程表';

-- 课程表
CREATE TABLE courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    name VARCHAR(200) NOT NULL COMMENT '课程名称',
    day_of_week TINYINT NOT NULL COMMENT '星期几 (1-7，1代表周一)',
    time VARCHAR(20) NOT NULL COMMENT '时间段',
    location VARCHAR(200) NOT NULL COMMENT '地点',
    teacher VARCHAR(100) NOT NULL COMMENT '教师',
    weeks JSON NOT NULL COMMENT '周次数组',
    notes TEXT COMMENT '备注',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at TIMESTAMP NULL COMMENT '删除时间（软删除）',

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_day (user_id, day_of_week),
    INDEX idx_deleted (deleted_at),

    -- 添加星期约束
    CONSTRAINT chk_day_of_week CHECK (day_of_week BETWEEN 1 AND 7)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- 笔记表
CREATE TABLE notes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    title VARCHAR(200) NOT NULL COMMENT '笔记标题',
    content LONGTEXT NOT NULL COMMENT '笔记内容',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at TIMESTAMP NULL COMMENT '删除时间（软删除）',

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_created (user_id, created_at),
    INDEX idx_deleted (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='笔记表';

-- 提醒表
CREATE TABLE reminders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    event VARCHAR(200) NOT NULL COMMENT '事件名称',
    reminder_time DATETIME NOT NULL COMMENT '提醒时间',
    schedule_id BIGINT COMMENT '关联的日程ID',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at TIMESTAMP NULL COMMENT '删除时间（软删除）',

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE SET NULL,
    INDEX idx_user_time (user_id, reminder_time),
    INDEX idx_active (is_active, reminder_time),
    INDEX idx_deleted (deleted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提醒表';

-- 插入测试数据
INSERT INTO users (username, password, nickname, gender, school) VALUES
('testuser', '$2a$10$example_hash', '测试用户', '男', '测试大学'),
('student1', '$2a$10$example_hash', '学生一', '女', '示例大学');

INSERT INTO schedules (user_id, event, date, time, location, people, priority) VALUES
(1, '团队会议', '2024-01-15', '14:00:00', '会议室A', '张三、李四', 'important-urgent'),
(1, '学习计划', '2024-01-16', '10:00:00', '图书馆', null, 'important-not-urgent');

INSERT INTO courses (user_id, name, day_of_week, time, location, teacher, weeks, notes) VALUES
(1, '高等数学', 1, '08:00-09:40', '教学楼A101', '张教授', '[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]', '期中考试在第8周'),
(1, '英语', 2, '10:00-11:40', '教学楼B201', '李老师', '[2,4,6,8]', '');

INSERT INTO notes (user_id, title, content) VALUES
(1, '项目会议记录', '讨论了项目进度和下一步计划...'),
(1, '学习笔记', '今天学习了新的编程概念...');

INSERT INTO reminders (user_id, event, reminder_time, schedule_id, is_active) VALUES
(1, '团队会议', '2024-01-15 13:50:00', 1, TRUE),
(1, '学习提醒', '2024-01-16 09:50:00', 2, TRUE);

-- 创建视图：用户完整信息
CREATE VIEW user_profile_view AS
SELECT
    id,
    username,
    nickname,
    avatar,
    gender,
    school,
    created_at,
    updated_at
FROM users
WHERE deleted_at IS NULL;

-- 创建视图：日程详情（含提醒）
CREATE VIEW schedule_detail_view AS
SELECT
    s.id,
    s.user_id,
    s.event,
    s.date,
    s.time,
    s.location,
    s.people,
    s.priority,
    s.created_at,
    s.updated_at,
    r.id as reminder_id,
    r.reminder_time,
    r.is_active
FROM schedules s
LEFT JOIN reminders r ON s.id = r.schedule_id AND r.deleted_at IS NULL
WHERE s.deleted_at IS NULL;