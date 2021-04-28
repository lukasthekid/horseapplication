-- insert initial test data
-- the IDs are hardcoded to enable references between further test data
-- negative IDs are used to not interfere with user-entered data and allow clean deletion of test data

DELETE FROM horse where ID < 0;
DELETE FROM sport where ID < 0;

INSERT INTO sport (ID, NAME)
VALUES (-1, 'Polo')
       , (-2, 'Dressage')
       , (-3, 'Foxhunting')
      ;

INSERT INTO sport (ID, NAME, DESC) VALUES
(-4, 'Vaulting', 'acrobatic');



INSERT INTO horse (ID, NAME, DOB, SEX)
VALUES (-1, 'Ralf', '2008-11-11', 'Male'),
       (-2, 'RalfV', '2000-11-11', 'Male'),
       (-3, 'RalphM', '1999-01-12', 'Female'),
       (-5, 'Amadee', '2008-03-12', 'Male'),
       (-6, 'Greta', '2005-08-04', 'Female');

INSERT INTO horse (ID, NAME, DESC, DOB, SEX, FAV_SPORT, DAD_ID, MOM_ID)
VALUES (-4, 'Peter', 'sportlich', '2010-03-03', 'Male', -1, -2, -3),
       (-7, 'Anna', 'circus-pferd', '2015-01-09', 'Female', -4, -5, -6),
       (-8, 'Ron', 'baby-pferd', '2020-01-10', 'Male', null, -4, -7),
       (-9, 'Martin', null, '2001-03-09', 'Male', null, null, null),
       (-10, 'Elfi', null, '2011-08-12', 'Female', null, -2, -6);