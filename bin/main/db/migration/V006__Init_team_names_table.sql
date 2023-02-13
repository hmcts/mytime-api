CREATE TABLE IF NOT EXISTS public.team_names (
  id SERIAL PRIMARY KEY,
  team_name varchar(255),
  parent_team_id integer references public.team_names(id)
);

INSERT INTO public.team_names (id, team_name) VALUES (1, 'HM Courts & Tribunals Service');
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (2, 'Digital & Technology Services', 1);
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (3, 'Digital Delivery', 2);
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (4, 'Cross Cutting', 3);
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (5, 'Automation', 4);
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (6, 'Tactical & Small Systems Development', 5);
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (7, 'Future Hearings', 4);
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (8, 'FACT Bulk Scan', 4);
INSERT INTO public.team_names (id, team_name, parent_team_id) VALUES (9, 'Video Hearing', 4);

CREATE TABLE IF NOT EXISTS public.team_users (
  id SERIAL PRIMARY KEY,
  team_id integer references public.team_names(id),
  user_id integer references public.User(Id)
);