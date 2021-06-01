
    alter table event 
       drop 
       foreign key FK9t4wufytyojom81l5smhgrev;

    alter table event 
       drop 
       foreign key FK1ioci8tvlg7h4cbravpa5j2wt;

    alter table favorite_event 
       drop 
       foreign key FKgvm3wmrgu3h5biyjt3l8pscuc;

    alter table favorite_event 
       drop 
       foreign key FKkq2oxq25bx0phqg74enww5421;

    alter table going_to_event 
       drop 
       foreign key FKcp1u9q68irecaoypbbndr8bqe;

    alter table going_to_event 
       drop 
       foreign key FKbxitf6y468kxtqmpmqf14pqyr;

    alter table hashtag 
       drop 
       foreign key FK8b06ac6ouvueea1imi04lyou7;

    alter table interested_in_event 
       drop 
       foreign key FKpav49wi532oq8omc5jfo6fvn2;

    alter table interested_in_event 
       drop 
       foreign key FK7fpgboi4k5puoyd4xpg3btgxo;

    alter table preferred_event_category 
       drop 
       foreign key FK8x5ubchkvf6h2xyo8vib6tdf8;

    alter table user 
       drop 
       foreign key FKjjes1f6tjhqns02054ou51m00;

    alter table verificationtoken 
       drop 
       foreign key FKbgrxksl22d9tfbldwtsyv2pkv;

    drop table if exists event;

    drop table if exists favorite_event;

    drop table if exists going_to_event;

    drop table if exists hashtag;

    drop table if exists interested_in_event;

    drop table if exists position;

    drop table if exists preferred_event_category;

    drop table if exists refreshtoken;

    drop table if exists user;

    drop table if exists user_profile;

    drop table if exists verificationtoken;
