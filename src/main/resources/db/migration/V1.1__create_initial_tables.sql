create table oauth_user
(
    id                  bigint       not null auto_increment,
    username            varchar(255) not null,
    password            varchar(255) not null,

    account_expired     boolean,
    account_locked      boolean,
    credentials_expired boolean,
    enabled             boolean,

    PRIMARY KEY (id)
) engine = InnoDB;

create table oauth_authority
(
    id                  bigint       not null auto_increment,
    name                varchar(255) not null,
    PRIMARY KEY (id)
) engine = InnoDB;

create table oauth_user_authorities
(
    user_id                  bigint       not null,
    authority_id             bigint       not null,
    KEY (user_id),
    KEY (authority_id)
) engine = InnoDB;

create table custom_users
(
    id                bigint       not null auto_increment,
    user_id           varchar(255) not null unique,
    first_name        varchar(64) not null,
    last_name         varchar(64),
    email             varchar(128) not null,
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
