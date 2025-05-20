select u.username, r.role from user_role u_r
join
users u on u_r.user_id = u.id
join
roles r on u_r.role_id = r.id
order by u.id
limit ? offset ?;