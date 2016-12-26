DROP DATABASE IF EXISTS api;

CREATE DATABASE api
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE api;

-- ----------------------------
--  Table structure for article
-- ----------------------------
DROP TABLE
IF EXISTS article;

CREATE TABLE article
(
  id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  title           VARCHAR(64)                           NOT NULL
  COMMENT '文章标题',
  tags            VARCHAR(512)                          NOT NULL
  COMMENT '标签',
  content         LONGTEXT                              NOT NULL
  COMMENT '文章内容',
  create_username VARCHAR(20)                           NOT NULL
  COMMENT '创建人',
  create_fullname VARCHAR(32)                           NOT NULL
  COMMENT '创建人姓名',
  is_deleted      TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time    TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time    TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '文章表';
CREATE UNIQUE INDEX id_UNIQUE
  ON article (id);
CREATE INDEX create_ix
  ON article (created_time);

-- ----------------------------
--  Table structure for dictionary
-- ----------------------------
DROP TABLE
IF EXISTS dictionary;

CREATE TABLE dictionary
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '代码',
  value        VARCHAR(128)                          NOT NULL
  COMMENT '值',
  type         VARCHAR(16)                           NOT NULL
  COMMENT '类型',
  sort         INT(11)                               NOT NULL                DEFAULT 0
  COMMENT '排序(从0开始)',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '字典表';
CREATE UNIQUE INDEX id_UNIQUE
  ON dictionary (id);
CREATE INDEX create_ix
  ON dictionary (created_time);
CREATE INDEX type_ix
  ON dictionary (type);
CREATE INDEX sort_ix
  ON dictionary (sort);
CREATE UNIQUE INDEX code_UNIQUE
  ON dictionary (code);

-- ----------------------------
--  Table structure for article_dictionary
-- ----------------------------
DROP TABLE
IF EXISTS article_dictionary;

CREATE TABLE article_dictionary
(
  article_id      VARCHAR(20) NOT NULL
  COMMENT '文章ID',
  dictionary_code VARCHAR(32) NOT NULL
  COMMENT '标签代码',
  PRIMARY KEY (article_id, dictionary_code)
)
  COMMENT '文章标签表';

-- ----------------------------
--  Table structure for attachment
-- ----------------------------
DROP TABLE
IF EXISTS attachment;

CREATE TABLE attachment
(
  id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  source_id       BIGINT(20)                            NOT NULL                DEFAULT 0
  COMMENT '来源ID',
  name            VARCHAR(256)                          NOT NULL                DEFAULT ''
  COMMENT '附件原名',
  path            VARCHAR(256)                          NOT NULL
  COMMENT '附件路径',
  type            VARCHAR(32)                           NOT NULL
  COMMENT '类型',
  create_username VARCHAR(20)                           NOT NULL                DEFAULT ''
  COMMENT '上传人',
  is_deleted      TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time    TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time    TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '附件表';
CREATE UNIQUE INDEX id_UNIQUE
  ON attachment (id);
CREATE INDEX create_ix
  ON attachment (created_time);