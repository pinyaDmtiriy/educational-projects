with
create_user as(
insert into
users(password, status, username)
values(?,?,?)
returning id
),
add_role as(
insert into
user_role(role_id, user_id)
select ?,id from create_user
),
create_profile as(
insert into
profiles(description, first_name, last_name, user_id)
select ?,?,?,id from create_user
),
create_email as(
insert into
emails(email, user_id)
select ?,id from create_user
)
select id from create_user;