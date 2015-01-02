
insert into Account values
  ( 12, 'jane' );
insert into Account values
  ( 34, 'harold' );
insert into Account values
  ( 55, 'maurice' );


-- populate Plan table with specified Plans
insert into Plan values
  ( 'Simple Started Plan', 20.99, 2, 'RecurringPlan' );
insert into Plan values
  ( 'SIM-Only Post-Paid Plan', 40.99, 0, 'RecurringPlan' );
insert into Plan values
  ( 'IPhone Plan', 50.99, 3, 'RecurringPlan' );
insert into Plan values
  ( 'Easy Pre-Paid Plan', 25.99, 2, 'PrePaidPlan' );
insert into Plan values
  ( 'Combo Plan', 30.99, 5, 'RecurringPlan' );


-- populate Phone table with specified Phone numbers
insert into Phone values
  (123456789, '(520) 111-1111', 'Good Phones', 'a_model' );
insert into Phone values
  (234567890, '(520) 121-2222', 'Phony Phones', 'b_model' );
insert into Phone values
  (345678901, '(520) 589-6532', 'Good Phones', 'c_model' );
insert into Phone values
  (456789012, '(640) 213-5689', 'Phony Phones', 'a_model' );
insert into Phone values
  (567890123, '(821) 546-7896', 'Good Phones', 'b_model' );

-- populate Bill table
insert into Bill values
  ( 12, to_date('12/05/2014', 'mm/dd/yyyy'), to_date('11/15/2014', 'mm/dd/yyyy'), to_date('12/10/2014', 'mm/dd/yyyy') );
insert into Bill values
  ( 34, to_date('1/05/2015', 'mm/dd/yyyy'), to_date('12/15/2014', 'mm/dd/yyyy'), to_date('1/10/2015', 'mm/dd/yyyy') );
insert into Bill values
  ( 55, to_date('12/05/2014', 'mm/dd/yyyy'), to_date('11/15/2014', 'mm/dd/yyyy'), to_date('12/10/2014', 'mm/dd/yyyy') );

-- populate Subscribes table
insert into Subscribe values
  ( 123456789, 12, 'Combo Plan' );
insert into Subscribe values
  ( 234567890, 34, 'Combo Plan' );
insert into Subscribe values
  ( 345678901, 34, 'IPhone Plan' );
insert into Subscribe values
  ( 456789012, 55, 'SIM-Only Post-Paid Plan' );
insert into Subscribe values
  ( 567890123, 55, 'Simple Started Plan' );
