with
delete_user_role_users as(
delete from user_role where user_id = ?
returning user_id
),
delete_profile as(
delete from profiles
where user_id = (select user_id from delete_user_role_users)
),
delete_emails as(
delete from emails
where user_id = (select user_id from delete_user_role_users)
),
delete_users as(
delete from users where id = (select user_id from delete_user_role_users)
)
select user_id from delete_user_role_users;