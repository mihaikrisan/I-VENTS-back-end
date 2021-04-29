
    create table event (
       event_id integer not null auto_increment,
        added_on_utc datetime(6) not null,
        address varchar(255) not null,
        description varchar(255) not null,
        event_category varchar(45) not null,
        image_url varchar(255) not null,
        max_number_of_persons integer,
        minimum_age integer,
        takes_place_on_utc datetime(6) not null,
        event_name varchar(45) not null,
        position_id integer not null,
        primary key (event_id)
    ) engine=InnoDB;

    create table favorite_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table going_to_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table hashtag (
       event_id integer not null,
        hashtag varchar(255) not null
    ) engine=InnoDB;

    create table interested_in_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table position (
       position_id integer not null auto_increment,
        latitude double precision not null,
        longitude double precision not null,
        primary key (position_id)
    ) engine=InnoDB;

    create table preferred_event_category (
       user_profile_id integer not null,
        event_category varchar(255) not null
    ) engine=InnoDB;

    create table refreshtoken (
       refreshtoken_id integer not null auto_increment,
        created_on_utc datetime(6) not null,
        token varchar(255) not null,
        primary key (refreshtoken_id)
    ) engine=InnoDB;

    create table user (
       user_id integer not null auto_increment,
        is_enabled bit not null,
        user_password varchar(255) not null,
        user_role varchar(20) not null,
        user_name varchar(20) not null,
        user_profile_id integer not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_profile (
       user_profile_id integer not null auto_increment,
        age integer not null,
        email varchar(45) not null,
        first_name varchar(45) not null,
        last_name varchar(45) not null,
        phone_number varchar(45) not null,
        primary key (user_profile_id)
    ) engine=InnoDB;

    create table verificationtoken (
       verificationtoken_id integer not null auto_increment,
        verificationtoken_expiry_date_utc datetime(6) not null,
        verificationtoken_uuid varchar(255) not null,
        user_id integer not null,
        primary key (verificationtoken_id)
    ) engine=InnoDB;

    alter table event 
       add constraint UK_jt2g7c3y96kwo4qd8lbf6hjkv unique (position_id);

    alter table user 
       add constraint UK_2ek1mbe9ojg3q7p83vusnrj15 unique (user_profile_id);

    alter table user 
       add constraint UK_lqjrcobrh9jc8wpcar64q1bfh unique (user_name);

    alter table user_profile 
       add constraint UK_tcks72p02h4dp13cbhxne17ad unique (email);

    alter table user_profile 
       add constraint UK_dd0g7xm8e4gtak3ka2h89clyh unique (phone_number);

    alter table event 
       add constraint FK1ioci8tvlg7h4cbravpa5j2wt 
       foreign key (position_id) 
       references position (position_id);

    alter table favorite_event 
       add constraint FKgvm3wmrgu3h5biyjt3l8pscuc 
       foreign key (event_id) 
       references event (event_id);

    alter table favorite_event 
       add constraint FKkq2oxq25bx0phqg74enww5421 
       foreign key (user_id) 
       references user (user_id);

    alter table going_to_event 
       add constraint FKbxitf6y468kxtqmpmqf14pqyr 
       foreign key (event_id) 
       references event (event_id);

    alter table going_to_event 
       add constraint FKcp1u9q68irecaoypbbndr8bqe 
       foreign key (user_id) 
       references user (user_id);

    alter table hashtag 
       add constraint FK8b06ac6ouvueea1imi04lyou7 
       foreign key (event_id) 
       references event (event_id);

    alter table interested_in_event 
       add constraint FK7fpgboi4k5puoyd4xpg3btgxo 
       foreign key (event_id) 
       references event (event_id);

    alter table interested_in_event 
       add constraint FKpav49wi532oq8omc5jfo6fvn2 
       foreign key (user_id) 
       references user (user_id);

    alter table preferred_event_category 
       add constraint FK8x5ubchkvf6h2xyo8vib6tdf8 
       foreign key (user_profile_id) 
       references user_profile (user_profile_id);

    alter table user 
       add constraint FKjjes1f6tjhqns02054ou51m00 
       foreign key (user_profile_id) 
       references user_profile (user_profile_id);

    alter table verificationtoken 
       add constraint FKbgrxksl22d9tfbldwtsyv2pkv 
       foreign key (user_id) 
       references user (user_id);

    create table event (
       event_id integer not null auto_increment,
        added_on_utc datetime(6) not null,
        address varchar(255) not null,
        description varchar(255) not null,
        event_category varchar(45) not null,
        image_url varchar(255) not null,
        max_number_of_persons integer,
        minimum_age integer,
        takes_place_on_utc datetime(6) not null,
        event_name varchar(45) not null,
        position_id integer not null,
        primary key (event_id)
    ) engine=InnoDB;

    create table favorite_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table going_to_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table hashtag (
       event_id integer not null,
        hashtag varchar(255) not null
    ) engine=InnoDB;

    create table interested_in_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table position (
       position_id integer not null auto_increment,
        latitude double precision not null,
        longitude double precision not null,
        primary key (position_id)
    ) engine=InnoDB;

    create table preferred_event_category (
       user_profile_id integer not null,
        event_category varchar(255) not null
    ) engine=InnoDB;

    create table refreshtoken (
       refreshtoken_id integer not null auto_increment,
        created_on_utc datetime(6) not null,
        token varchar(255) not null,
        primary key (refreshtoken_id)
    ) engine=InnoDB;

    create table user (
       user_id integer not null auto_increment,
        is_enabled bit not null,
        user_password varchar(255) not null,
        user_role varchar(20) not null,
        user_name varchar(20) not null,
        user_profile_id integer not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_profile (
       user_profile_id integer not null auto_increment,
        age integer not null,
        email varchar(45) not null,
        first_name varchar(45) not null,
        last_name varchar(45) not null,
        phone_number varchar(45) not null,
        primary key (user_profile_id)
    ) engine=InnoDB;

    create table verificationtoken (
       verificationtoken_id integer not null auto_increment,
        verificationtoken_expiry_date_utc datetime(6) not null,
        verificationtoken_uuid varchar(255) not null,
        user_id integer not null,
        primary key (verificationtoken_id)
    ) engine=InnoDB;

    alter table event 
       add constraint UK_jt2g7c3y96kwo4qd8lbf6hjkv unique (position_id);

    alter table user 
       add constraint UK_2ek1mbe9ojg3q7p83vusnrj15 unique (user_profile_id);

    alter table user 
       add constraint UK_lqjrcobrh9jc8wpcar64q1bfh unique (user_name);

    alter table user_profile 
       add constraint UK_tcks72p02h4dp13cbhxne17ad unique (email);

    alter table user_profile 
       add constraint UK_dd0g7xm8e4gtak3ka2h89clyh unique (phone_number);

    alter table event 
       add constraint FK1ioci8tvlg7h4cbravpa5j2wt 
       foreign key (position_id) 
       references position (position_id);

    alter table favorite_event 
       add constraint FKgvm3wmrgu3h5biyjt3l8pscuc 
       foreign key (event_id) 
       references event (event_id);

    alter table favorite_event 
       add constraint FKkq2oxq25bx0phqg74enww5421 
       foreign key (user_id) 
       references user (user_id);

    alter table going_to_event 
       add constraint FKbxitf6y468kxtqmpmqf14pqyr 
       foreign key (event_id) 
       references event (event_id);

    alter table going_to_event 
       add constraint FKcp1u9q68irecaoypbbndr8bqe 
       foreign key (user_id) 
       references user (user_id);

    alter table hashtag 
       add constraint FK8b06ac6ouvueea1imi04lyou7 
       foreign key (event_id) 
       references event (event_id);

    alter table interested_in_event 
       add constraint FK7fpgboi4k5puoyd4xpg3btgxo 
       foreign key (event_id) 
       references event (event_id);

    alter table interested_in_event 
       add constraint FKpav49wi532oq8omc5jfo6fvn2 
       foreign key (user_id) 
       references user (user_id);

    alter table preferred_event_category 
       add constraint FK8x5ubchkvf6h2xyo8vib6tdf8 
       foreign key (user_profile_id) 
       references user_profile (user_profile_id);

    alter table user 
       add constraint FKjjes1f6tjhqns02054ou51m00 
       foreign key (user_profile_id) 
       references user_profile (user_profile_id);

    alter table verificationtoken 
       add constraint FKbgrxksl22d9tfbldwtsyv2pkv 
       foreign key (user_id) 
       references user (user_id);

    create table event (
       event_id integer not null auto_increment,
        added_on_utc datetime(6) not null,
        address varchar(255) not null,
        description varchar(255) not null,
        event_category varchar(45) not null,
        image_url varchar(255) not null,
        max_number_of_persons integer,
        minimum_age integer,
        takes_place_on_utc datetime(6) not null,
        event_name varchar(45) not null,
        position_id integer not null,
        primary key (event_id)
    ) engine=InnoDB;

    create table favorite_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table going_to_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table hashtag (
       event_id integer not null,
        hashtag varchar(255) not null
    ) engine=InnoDB;

    create table interested_in_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table position (
       position_id integer not null auto_increment,
        latitude double precision not null,
        longitude double precision not null,
        primary key (position_id)
    ) engine=InnoDB;

    create table preferred_event_category (
       user_profile_id integer not null,
        event_category varchar(255) not null
    ) engine=InnoDB;

    create table refreshtoken (
       refreshtoken_id integer not null auto_increment,
        created_on_utc datetime(6) not null,
        token varchar(255) not null,
        primary key (refreshtoken_id)
    ) engine=InnoDB;

    create table user (
       user_id integer not null auto_increment,
        is_enabled bit not null,
        user_password varchar(255) not null,
        user_role varchar(20) not null,
        user_name varchar(20) not null,
        user_profile_id integer not null,
        primary key (user_id)
    ) engine=InnoDB;

    create table user_profile (
       user_profile_id integer not null auto_increment,
        age integer not null,
        email varchar(45) not null,
        first_name varchar(45) not null,
        last_name varchar(45) not null,
        phone_number varchar(45) not null,
        primary key (user_profile_id)
    ) engine=InnoDB;

    create table verificationtoken (
       verificationtoken_id integer not null auto_increment,
        verificationtoken_expiry_date_utc datetime(6) not null,
        verificationtoken_uuid varchar(255) not null,
        user_id integer not null,
        primary key (verificationtoken_id)
    ) engine=InnoDB;

    alter table event 
       add constraint UK_jt2g7c3y96kwo4qd8lbf6hjkv unique (position_id);

    alter table user 
       add constraint UK_2ek1mbe9ojg3q7p83vusnrj15 unique (user_profile_id);

    alter table user 
       add constraint UK_lqjrcobrh9jc8wpcar64q1bfh unique (user_name);

    alter table user_profile 
       add constraint UK_tcks72p02h4dp13cbhxne17ad unique (email);

    alter table user_profile 
       add constraint UK_dd0g7xm8e4gtak3ka2h89clyh unique (phone_number);

    alter table event 
       add constraint FK1ioci8tvlg7h4cbravpa5j2wt 
       foreign key (position_id) 
       references position (position_id);

    alter table favorite_event 
       add constraint FKgvm3wmrgu3h5biyjt3l8pscuc 
       foreign key (event_id) 
       references event (event_id);

    alter table favorite_event 
       add constraint FKkq2oxq25bx0phqg74enww5421 
       foreign key (user_id) 
       references user (user_id);

    alter table going_to_event 
       add constraint FKbxitf6y468kxtqmpmqf14pqyr 
       foreign key (event_id) 
       references event (event_id);

    alter table going_to_event 
       add constraint FKcp1u9q68irecaoypbbndr8bqe 
       foreign key (user_id) 
       references user (user_id);

    alter table hashtag 
       add constraint FK8b06ac6ouvueea1imi04lyou7 
       foreign key (event_id) 
       references event (event_id);

    alter table interested_in_event 
       add constraint FK7fpgboi4k5puoyd4xpg3btgxo 
       foreign key (event_id) 
       references event (event_id);

    alter table interested_in_event 
       add constraint FKpav49wi532oq8omc5jfo6fvn2 
       foreign key (user_id) 
       references user (user_id);

    alter table preferred_event_category 
       add constraint FK8x5ubchkvf6h2xyo8vib6tdf8 
       foreign key (user_profile_id) 
       references user_profile (user_profile_id);

    alter table user 
       add constraint FKjjes1f6tjhqns02054ou51m00 
       foreign key (user_profile_id) 
       references user_profile (user_profile_id);

    alter table verificationtoken 
       add constraint FKbgrxksl22d9tfbldwtsyv2pkv 
       foreign key (user_id) 
       references user (user_id);
