create table if not exists ticker
(
    active boolean not null,
    id     bigint generated by default as identity
        primary key,
    locale varchar(255),
    market varchar(255),
    name   varchar(255),
    ticker varchar(255)
);