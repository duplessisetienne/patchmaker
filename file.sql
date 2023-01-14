--
--  test123
--
--
--  2019-12-20 E Du Plessis Initial Version
--

Clear Screen


define LOGFILE   = 'PATCH1234.log';
define PATCHNO   = 1234;


set define off;

Prompt ======================================================================================
Prompt |                     Installing test123       |
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
Values               ( 1234,
'InstallScript.sql',
'i am tetsing this'
);
Commit;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Procedures
Prompt Press ENTER to continue
Prompt ===============================================================================

@procedure/test;
@procedure/vx_dup;
@procedure/re;
@procedure/vx_system_api;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Wrapping Code
Prompt Press ENTER to continue
Prompt ===============================================================================

Begin
VX_WRAPPER.Wrap(i_name => VARCHAR_TABLE('vx_icap_api.sql'
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
Prompt Patch Complete, see log file PATCH3478.log for details
Prompt Press ENTER To Exit

Spool Off;