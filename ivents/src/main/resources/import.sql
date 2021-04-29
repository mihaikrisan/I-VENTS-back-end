INSERT INTO user_profile(age, email, first_name, last_name, phone_number)
VALUES (1, 'a', 'a', 'a', '1'),
       (2, 'b', 'b', 'b', '2'),
       (3, 'c', 'c', 'c', '3');

INSERT INTO preferred_event_category(user_profile_id, event_category)
VALUES (1, 'FASHION'),
       (2, 'NIGHTLIFE'),
       (2, 'FOOD_AND_DRINK'),
       (3, 'SPORTS_AND_ACTIVE_LIFE');

INSERT INTO user (user_name, user_password, user_role, is_enabled, user_profile_id)
VALUES ('user1', '$2a$10$DI.GFEUPKjG1uRgxDxTCjOVkyMlfFDbX4zrnDDROWEBjTaOpxeKj6', 'CLIENT', 1, 1),
       ('user2', '$2a$10$IpOxHitET36mfCvF4AcP3OQXf9YWb0.WMJuCzxyqyYDT.fSd4GoaG', 'ORGANIZER', 1, 2),
       ('user3', '$2a$10$xXvVsUxTNQBReMT3tfx5Nu5iijcuNFAx6Kxef6z8EBHC3frMGs8bO', 'ADMIN', 1, 3);

INSERT INTO position (latitude, longitude)
VALUES (46.769892875017206, 23.580016953993848),
       (46.76507204175534, 23.60396371607881),
       (46.77518353683267, 23.573407990981153),
       (46.75518353683267, 23.673407990981153);

INSERT INTO event(added_on_utc, address, description, event_category, image_url, max_number_of_persons, minimum_age,
                  takes_place_on_utc, event_name, position_id)
VALUES ('2021-04-21 12:00:00', 'dddr fffyuf gygygy', 'sdtftftf ftyftftftyiftyf ffufyugyugyug ggygyugyugyu',
        'FILM',
        'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y29uY2VydHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80'
           , 30, 18, '2021-04-22 12:00:00', 'Inception', 1),
       ( '2021-04-21 14:00:00', 'r32r23r ef efw efw', 'fwefuwehuf ehehwufhweuifweui efwefiowejfiweiofweio'
       , 'FILM'
       , 'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y29uY2VydHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80'
       , 25, 18, '2021-05-29 12:00:00', 'Predestination', 2);

INSERT INTO event(added_on_utc, address, description, event_category, image_url, takes_place_on_utc, event_name,
                  position_id)
VALUES ('2021-04-20 12:00:00', 'dqwd2323 3ef23f', 'weffu3ih eijfiwjeifj jejfowjkfojko fbfuiufh', 'MUSIC',
        'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y29uY2VydHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80'
           , '2021-05-29 12:00:00', 'Rock concert', 3),
       ( '2021-04-20 15:00:00', 'efwefwue fwenfweifweiojfioej'
       , 'fwef23f23 3f3f3 f34f ekfmwemfwemofkweofkowe ccccriccctyftyofgyuggyugpypuiyugyugtftyoftyoftyftyfctyfctyftyftyftyftftyoftyftytytyvtytyctyctyctyoctyctyctyoctyo'
       , 'SPORTS_AND_ACTIVE_LIFE'
       , 'https://ifbb.com/wp-content/uploads/2019/05/AA-6.jpg'
       , '2021-05-29 12:00:00', 'Bodybuilding competition', 4);

INSERT INTO hashtag(event_id, hashtag)
VALUES (1, '#movienight'),
       (1, '#mystery'),
       (3, '#heavymetal');

INSERT INTO interested_in_event
VALUES (1, 1);

INSERT INTO going_to_event
VALUES (1, 1);

INSERT INTO favorite_event
VALUES (1, 4);