with
new_user as(
insert into users(password, status, username)
values(?,?,?)
returning id
),
add_role as(
insert into user_role(user_id, role_id)
select id,1 from new_user
),
add_email as(
insert into emails(email, user_id)
select ?, id from new_user
)
select id from new_user;