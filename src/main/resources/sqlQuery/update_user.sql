with
update_user as(
update users
set username = ?, password = ?
where id = ?
returning id
),
update_email as(
update emails
set email = ?
where user_id = (select id from update_user)
),
update_profile as(
update profiles
set first_name = ?, last_name = ?, description = ?
where user_id = (select id from update_user)
)
select id from update_user;