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

INSERT INTO event(event_category, max_number_of_persons, minimum_age, event_name, position_id)
VALUES ('FILM', 30, 18, 'Inception', 1),
       ('FILM', 25, 18, 'Predestination', 2);

INSERT INTO event(event_category, event_name, position_id)
VALUES ('MUSIC', 'Rock concert', 3),
       ('SPORTS_AND_ACTIVE_LIFE', 'Bodybuilding competition', 4);

INSERT INTO hashtag(event_id, hashtag)
VALUES ('1', '#movienight'),
       ('1', '#mystery'),
       ('3', '#heavymetal');
