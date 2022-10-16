create table users
(
    id                bigint       not null auto_increment,
    first_name        varchar(64) not null,
    last_name         varchar(64),
    username          varchar(128) not null,
    email             varchar(128) not null,
    password          varchar(32) not null,

    user_role_id       bigint not null,
    phone              varchar(32),
    date_of_birth      datetime,
    address_id         bigint,
    status             boolean default true,

    created_by        bigint not null,
    create_time       datetime  not null,
    updated_by        bigint,
    updated_time      datetime,

    INTERNAL_VERSION  bigint default 1,

    PRIMARY KEY (id)
) engine = InnoDB;

create table roles
(
    id                bigint       not null auto_increment,
    role_name         varchar(64)  not null,
    status            boolean default true,
    description       varchar(64) not null,

    created_by        bigint not null,
    create_time       datetime  not null,
    updated_by        bigint,
    updated_time      datetime,

    INTERNAL_VERSION  bigint default 1,

    PRIMARY KEY (id)
) engine = InnoDB;
