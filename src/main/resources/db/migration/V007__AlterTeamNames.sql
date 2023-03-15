ALTER TABLE IF EXISTS public.team_names DROP CONSTRAINT team_names_parent_team_id_fkey;
ALTER TABLE IF EXISTS public.team_names
  ADD CONSTRAINT team_names_parent_team_id_fkey FOREIGN KEY (parent_team_id)
    REFERENCES public.team_names (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE SET NULL;

DELETE FROM public.team_names;

INSERT INTO public.team_names (team_name) VALUES ('HM Courts & Tribunals Service');
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('Digital & Technology Services', 1);
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('Digital Delivery', 2);
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('Cross Cutting', 3);
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('Automation', 4);
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('Tactical & Small Systems Development', 5);
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('Future Hearings', 4);
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('FACT Bulk Scan', 4);
INSERT INTO public.team_names (team_name, parent_team_id) VALUES ('Video Hearing', 4);
