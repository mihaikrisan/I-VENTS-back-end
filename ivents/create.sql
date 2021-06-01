
    create table event (
       event_id integer not null auto_increment,
        added_on_utc datetime(6) not null,
        address varchar(255) not null,
        description varchar(2048) not null,
        event_category varchar(45) not null,
        image_url varchar(255) not null,
        takes_place_on_utc datetime(6) not null,
        event_title varchar(45) not null,
        organizer_id integer not null,
        position_id integer not null,
        primary key (event_id)
    ) engine=InnoDB;

    create table favorite_event (
       user_id integer not null,
        event_id integer not null,
        primary key (user_id, event_id)
    ) engine=InnoDB;

    create table going_to_event (
       event_id integer not null,
        user_id integer not null,
        primary key (event_id, user_id)
    ) engine=InnoDB;

    create table hashtag (
       event_id integer not null,
        hashtag varchar(255) not null
    ) engine=InnoDB;

    create table interested_in_event (
       event_id integer not null,
        user_id integer not null,
        primary key (event_id, user_id)
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
       add constraint FK9t4wufytyojom81l5smhgrev 
       foreign key (organizer_id) 
       references user (user_id);

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
       add constraint FKcp1u9q68irecaoypbbndr8bqe 
       foreign key (user_id) 
       references user (user_id);

    alter table going_to_event 
       add constraint FKbxitf6y468kxtqmpmqf14pqyr 
       foreign key (event_id) 
       references event (event_id);

    alter table hashtag 
       add constraint FK8b06ac6ouvueea1imi04lyou7 
       foreign key (event_id) 
       references event (event_id);

    alter table interested_in_event 
       add constraint FKpav49wi532oq8omc5jfo6fvn2 
       foreign key (user_id) 
       references user (user_id);

    alter table interested_in_event 
       add constraint FK7fpgboi4k5puoyd4xpg3btgxo 
       foreign key (event_id) 
       references event (event_id);

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
INSERT INTO user_profile(age, email, first_name, last_name, phone_number) VALUES (1, 'a', 'a', 'a', '1'),        (2, 'b', 'b', 'b', '2'),        (3, 'c', 'c', 'c', '3');
INSERT INTO preferred_event_category(user_profile_id, event_category) VALUES (1, 'FASHION'),        (2, 'NIGHTLIFE'),        (2, 'FOOD_AND_DRINK'),        (3, 'SPORTS_AND_ACTIVE_LIFE');
INSERT INTO user (user_name, user_password, user_role, is_enabled, user_profile_id) VALUES ('user1', '$2a$10$DI.GFEUPKjG1uRgxDxTCjOVkyMlfFDbX4zrnDDROWEBjTaOpxeKj6', 'CLIENT', 1, 1),        ('user2', '$2a$10$IpOxHitET36mfCvF4AcP3OQXf9YWb0.WMJuCzxyqyYDT.fSd4GoaG', 'ORGANIZER', 1, 2),        ('user3', '$2a$10$xXvVsUxTNQBReMT3tfx5Nu5iijcuNFAx6Kxef6z8EBHC3frMGs8bO', 'ADMIN', 1, 3);
INSERT INTO position (latitude, longitude) VALUES (46.769892875017206, 23.580016953993848),        (46.76507204175534, 23.60396371607881),        (46.77518353683267, 23.573407990981153),        (46.75518353683267, 23.673407990981153);
INSERT INTO event(added_on_utc, address, description, event_category, image_url, takes_place_on_utc, event_title,                   organizer_id, position_id) VALUES ('2021-04-21 12:00:00', 'dddr fffyuf gygygy', 'sdtftftf ftyftftftyiftyf ffufyugyugyug ggygyugyugyu',         'FILM',         'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y29uY2VydHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80'            , '2021-06-22 12:00:00', 'Inception', 2, 1),        ( '2021-04-21 14:00:00', 'r32r23r ef efw efw', 'fwefuwehuf ehehwufhweuifweui efwefiowejfiweiofweio'        , 'FILM'        , 'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y29uY2VydHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80'        , '2021-06-22 12:00:00', 'Predestination', 2, 2),        ('2021-04-20 12:00:00', 'dqwd2323 3ef23f', 'weffu3ih eijfiwjeifj jejfowjkfojko fbfuiufh', 'MUSIC',         'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y29uY2VydHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80'            , '2021-06-29 12:00:00', 'Rock concert', 2, 3),        ( '2021-04-20 15:00:00', 'efwefwue fwenfweifweiojfioej'        , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tincidunt eget nullam non nisi est sit. In tellus integer feugiat scelerisque varius morbi enim nunc. Non pulvinar neque laoreet suspendisse interdum consectetur libero id faucibus. Ac orci phasellus egestas tellus. Sed enim ut sem viverra aliquet. Quam nulla porttitor massa id neque aliquam. Amet nisl suscipit adipiscing bibendum est. Amet mauris commodo quis imperdiet massa tincidunt nunc pulvinar. Morbi tristique senectus et netus et malesuada fames ac. Vitae ultricies leo integer malesuada. Vel fringilla est ullamcorper eget nulla facilisi etiam dignissim. Arcu dictum varius duis at consectetur. Augue mauris augue neque gravida in fermentum et sollicitudin ac. Urna neque viverra justo nec. Consequat interdum varius sit amet mattis vulputate enim nulla. Pretium lectus quam id leo in. Lacinia at quis risus sed. Imperdiet massa tincidunt nunc pulvinar sapien.
          Eu nisl nunc mi ipsum faucibus vitae. Lectus magna fringilla urna porttitor rhoncus dolor purus non enim. Vitae tempus quam pellentesque nec nam aliquam sem. Amet nulla facilisi morbi tempus iaculis. Est placerat in egestas erat. Duis at tellus at urna condimentum. Vitae semper quis lectus nulla at volutpat. Aliquam faucibus purus in massa tempor nec feugiat nisl pretium. Interdum consectetur libero id faucibus. Luctus accumsan tortor posuere ac ut consequat semper viverra. Feugiat in fermentum posuere urna nec. Pellentesque eu tincidunt tortor aliquam nulla. Tempor nec feugiat nisl pretium fusce id. Tortor consequat id porta nibh venenatis.'        , 'SPORTS_AND_ACTIVE_LIFE'        , 'https://ifbb.com/wp-content/uploads/2019/05/AA-6.jpg'        , '2021-06-10 23:00:00', 'Bodybuilding competition', 2, 4);
INSERT INTO hashtag(event_id, hashtag) VALUES (1, '#movienight'),        (1, '#mystery'),        (3, '#heavymetal'),        (3, '#rock');
INSERT INTO interested_in_event VALUES (3, 1),        (2, 3),        (4, 3);
INSERT INTO going_to_event VALUES (4, 1),        (4, 3),        (4, 2),        (2, 1);
