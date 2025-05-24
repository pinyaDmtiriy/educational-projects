select r.role, u.username, p.first_name, e.email from user_role ur
join
    roles r on ur.role_id = r.id
join
    users u on ur.user_id = u.id
join
    emails e on e.user_id = u.id
left join
    profiles p on p.user_id = u.id
where u.id = ?;