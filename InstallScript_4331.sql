--
--  Notifier oAuth
--
--
--  V01.00.00  15-Dec-2022  E Du Plessos    Initial Version
--

Clear Screen


set define off;
SPOOL 4331.log

Prompt ======================================================================================
Prompt |                     Installing Notifier oAuth       |
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
Values               ( 4331,
'InstallScript.sql',
'Add oAuth functioinality for Notifier'
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
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Triggers
Prompt Press ENTER to continue
Prompt ===============================================================================

@triggers/test.trg;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Views
Prompt Press ENTER to continue
Prompt ===============================================================================

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
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Procedures
Prompt Press ENTER to continue
Prompt ===============================================================================

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

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Types
Prompt Press ENTER to continue
Prompt ===============================================================================

@types/test.typ;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Apex
Prompt Press ENTER to continue
Prompt ===============================================================================

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
VX_WRAPPER.Wrap(i_name => VARCHAR_TABLE( 'test',
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
Prompt Patch Complete, see log file PATCH4331.log for details
Prompt Press ENTER To Exit

Spool Off;