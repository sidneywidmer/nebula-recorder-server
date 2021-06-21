-- apply changes
alter table recordings drop constraint if exists ck_recordings_recording_type;
alter table recordings alter column recording_type type varchar(3) using recording_type::varchar(3);
alter table recordings add constraint ck_recordings_recording_type check ( recording_type in ('GIF'));
