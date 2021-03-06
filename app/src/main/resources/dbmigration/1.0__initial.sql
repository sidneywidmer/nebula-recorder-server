-- apply changes
create table users (
  id                            bigint generated by default as identity not null,
  email                         varchar(255) not null,
  password                      varchar(255) not null,
  when_created                  timestamptz not null,
  when_modified                 timestamptz not null,
  constraint pk_users primary key (id)
);

