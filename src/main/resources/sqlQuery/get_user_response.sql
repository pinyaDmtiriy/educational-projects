select u.username, r.role, e.email, p.first_name, p.last_name
from user_role ur
join
    roles r on r.id = ur.role_id
join
    users u on u.id = ur.user_id
join
    emails e on e.user_id = u.id
left join
    profiles p on p.user_id = u.id
where u.id = ?;