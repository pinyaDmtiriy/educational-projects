with
update_user as(
update users
set username = ?, password = ?
where id = ?
returning id
),
delete_old_email as(
    delete from emails
    where user_id = (select id from update_user)
),
update_email as(
insert into emails(email, user_id)
select ?, id from update_user
)
select id from update_user;