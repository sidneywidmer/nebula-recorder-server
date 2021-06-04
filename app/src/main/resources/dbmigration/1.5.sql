-- apply changes
alter table recordings alter column name type varchar(30) using name::varchar(30);
alter table recordings add column recording_type integer not null;
alter table recordings add constraint ck_recordings_recording_type check ( recording_type in (0,1,2));
alter table recordings add column tag varchar(30);
alter table recordings add column description varchar(50);

