drop table ObjType CASCADE CONSTRAINTS;
drop table AttrType CASCADE CONSTRAINTS;
drop table Lists CASCADE CONSTRAINTS;
drop table Objects CASCADE CONSTRAINTS;
drop table Attributes CASCADE CONSTRAINTS;
drop table ObjReference CASCADE CONSTRAINTS;


create table ObjType
(
    object_type_id number(10) not null,
    parent_id number(10),
    Code varchar2(20),
    Description varchar2(1000 byte),
    constraint con_OT_obj_type_id primary key (object_type_id),
    constraint con_OT_parent_id foreign key (parent_id) references ObjType (object_type_id) on delete cascade
);

create table AttrType
(
    attr_id number(10) not null,
    object_type_id number(10) not null,
    object_type_id_ref number(10),
    Code varchar2(20),
    constraint con_AT_attr_id primary key (attr_id),
    constraint con_AT_obj_type_id foreign key (object_type_id) references ObjType (object_type_id),
    constraint con_AT_obj_type_ref foreign key (object_type_id_ref) references ObjType (object_type_id)
);

create table Lists
(
    attr_id number(10) not null,
    list_value_id number(10) not null,
    value varchar(4000),
    constraint con_L_attr_id foreign key (attr_id) references AttrType (attr_id) on delete cascade
);

create table Objects
(
    object_id number(20) not null,
    parent_id number(20),
    object_type_id number(10) not null,
    Name varchar2(2000 byte),
    Description varchar2(4000 byte),
    constraint con_O_object_id primary key (object_id),
    constraint con_O_parent_id foreign key (parent_id) references Objects (object_id) on delete cascade,
    constraint con_O_obj_type_id foreign key (object_type_id) references ObjType (object_type_id)
);

create table Attributes
(
    attr_id number(10) not null,
    object_id number(20) not null,
    value varchar2(4000 byte),
    date_value date,
    list_value_id number(10),
    constraint  con_ATR_attr_id foreign key (attr_id) references AttrType (attr_id) on delete cascade,
    constraint con_ATR_object_id foreign key (object_id) references Objects (object_id) on delete cascade
);

create table ObjReference
(
    attr_id number(10) not null,
    refer_id number(20) not null,
    object_id number(20) not null,
    constraint con_OR_primar primary key (attr_id, object_id, refer_id),
    constraint con_OR_attr_id foreign key (attr_id) references AttrType (attr_id) on delete cascade,
    constraint con_OR_object_id foreign key (object_id) references Objects (object_id) on delete cascade,
    constraint con_OR_ref_id foreign key (refer_id) references Objects (object_id) on delete cascade
);