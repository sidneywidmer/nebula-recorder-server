-- apply changes
alter table users alter column activation_code type varchar(10) using activation_code::varchar(10);
