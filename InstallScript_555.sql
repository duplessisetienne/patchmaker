--
--  test
--
--
--  1
--

Clear Screen


define LOGFILE   = 'PATCH555.log';
define PATCHNO   = 555;


set define off;

Prompt ======================================================================================
Prompt |                     Installing test       |
Prompt | Please ensure Patch notes document has been read, and all objects affected by this |
Prompt |                              deployment are backed up.                             |
Prompt | !!!!!!!!!!!!!!!!!!!!!!!Press ENTER to confirm and continue!!!!!!!!!!!!!!!!!!!!!!!! |
Prompt ======================================================================================
Pause

Select  TO_CHAR(Sysdate, 'DD-Mon-YYYY HH24:Mi:SS') As RUN_DATE
From    DUAL;

set feedback off;
set verify off;
set termout off;

Insert Into SVE_EFIX (SVE_EFIX_NUMBER, SVE_SCRIPT_NAME, SVE_DETAILS)
Values               ( 555,
'InstallScript.sql',
'test'
);
Commit;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Tables
Prompt Press ENTER to continue
Prompt ===============================================================================

@tables/test.sql;
@tables/test.sql;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Triggers
Prompt Press ENTER to continue
Prompt ===============================================================================

@triggers/test.sql;
@triggers/test.sql;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Views
Prompt Press ENTER to continue
Prompt ===============================================================================

@views/test.sql;
@views/test.sql;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Data
Prompt Press ENTER to continue
Prompt ===============================================================================

@sql/test.sql;
@sql/test.sql;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Procedures
Prompt Press ENTER to continue
Prompt ===============================================================================

@procedures/test.sql;
@procedures/test.sql;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Functions
Prompt Press ENTER to continue
Prompt ===============================================================================

@functions/test.sql;
@functions/test.sql;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Types
Prompt Press ENTER to continue
Prompt ===============================================================================

@types/test.sql;
@types/test.sql;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Apex
Prompt Press ENTER to continue
Prompt ===============================================================================

@apex/test.sql;
@apex/test.sql;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Wrapping Code
Prompt Press ENTER to continue
Prompt ===============================================================================

Begin
VX_WRAPPER.Wrap(i_name => VARCHAR_TABLE( 'test', 'test',
)
);

DBMS_OUTPUT.PUT_LINE('Code Wrapped successfully');
Exception
When Others
Then
DBMS_OUTPUT.PUT_LINE('Error Wrapping Code');
End;
/

----------------------------------------------------------------------------------
set termout on;
Prompt
Prompt ===============================================================================
Prompt Patch Complete, see log file PATCH555.log for details
Prompt Press ENTER To Exit

Spool Off;