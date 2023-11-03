create table wawa_entry(
entry_id    int primary key,
entry_title varchar(1000) not null,
entry_img   varchar(1000) null,
entry_url   varchar(1000) null
)engine innodb;