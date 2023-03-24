drop database diviction;
create database diviction;
create table diviction.member
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    email           VARCHAR(64)  NOT NULL UNIQUE ,
    password        VARCHAR(128) NOT NULL,
    name            VARCHAR(32)  NOT NULL,
    birth           VARCHAR(64)  NOT NULL,
    address         VARCHAR(256) NOT NULL,
    gender          VARCHAR(32)  NOT NULL,
    profile_img_url VARCHAR(256) NOT NULL,
    authority       VARCHAR(32)  NOT NULL
);
create table diviction.counselor
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    email           VARCHAR(64)  NOT NULL UNIQUE ,
    password        VARCHAR(128) NOT NULL,
    name            VARCHAR(32)  NOT NULL,
    birth           VARCHAR(64)  NOT NULL,
    address         VARCHAR(256) NOT NULL,
    gender          VARCHAR(32)  NOT NULL,
    profile_img_url VARCHAR(256) NOT NULL,
    confirm         BOOLEAN,
    introduce       VARCHAR(1024),
    representative_service VARCHAR(256),
    activity_area   VARCHAR(512),
    contact_hours   VARCHAR(256),
    contact         VARCHAR(1024),
    authority       VARCHAR(32)  NOT NULL
);
create table diviction.drug
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    drug_name VARCHAR(32) NOT NULL
);
create table diviction.drug_member
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT REFERENCES diviction.member (id),
    drug_id BIGINT REFERENCES diviction.drug (id)
);
create table diviction.matching
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id   BIGINT      NOT NULL,
    counselor_id BIGINT      NOT NULL,
    state        VARCHAR(32) NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES diviction.member (id),
    FOREIGN KEY (counselor_id) REFERENCES diviction.counselor (id)
);
create table diviction.consulting
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    matching_id  BIGINT        NOT NULL REFERENCES diviction.matching (id),
    content      VARCHAR(2048) NOT NULL,
    date         VARCHAR(32)   NOT NULL
);
create table diviction.checklist
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT        NOT NULL,
    foreign key (user_id) REFERENCES diviction.member (id),
    start_date VARCHAR(32)   NOT NULL,
    end_date   VARCHAR(32)   NOT NULL,
    content    VARCHAR(2048) NOT NULL,
    state      VARCHAR(32)   NOT NULL
);
create table diviction.refresh_token
(
    token_key   VARCHAR(64) PRIMARY KEY,
    token_value VARCHAR(512)
);
create table diviction.audit
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT      NOT NULL REFERENCES diviction.member (id),
    date    VARCHAR(32) NOT NULL,
    q1      INT         NOT NULL,
    score   INT         NOT NULL
);
create table diviction.dass
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT      NOT NULL REFERENCES diviction.member (id),
    date             VARCHAR(32) NOT NULL,
    melancholy_score INT         NOT NULL,
    unrest_score     INT         NOT NULL,
    stress_score     INT         NOT NULL
);
create table diviction.dast
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id   BIGINT       not null,
    foreign key (user_id) references member (id),
    date      VARCHAR(32)  not null,
    drug      VARCHAR(512) not null,
    frequency BIGINT       not null,
    injection BIGINT       not null,
    cure      BIGINT       not null,
    question  BIGINT       not null
);
create table diviction.memo(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    match_id BIGINT NOT NULL ,
    FOREIGN KEY(match_id) REFERENCES diviction.matching(id),
    title VARCHAR(128) NOT NULL ,
    content VARCHAR(2048) NOT NULL ,
    init_dt VARCHAR(64) NOT NULL ,
    modi_dt VARCHAR(64) NOT NULL
)
#create index member_email_index USING HASH ON diviction.member(email)
