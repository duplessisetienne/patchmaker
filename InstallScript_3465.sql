--
--  PATCH.3465.467.ZAFMVNX.VX_DataSyncFixes
--
--
--  03-Dec-2019    L Harland      Initial Version
--

Clear Screen


define LOGFILE   = 'PATCH3465.log';
define PATCHNO   = 3465;


set define off;

Prompt ======================================================================================
Prompt |                     Installing PATCH.3465.467.ZAFMVNX.VX_DataSyncFixes       |
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
Values               ( 3465,
'InstallScript.sql',
'Compile VX_STATUS_PROFILE'
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

@procedure/test.sql;
@procedure/vx_dup.sql;
@procedure/vx_interconnect.sql;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Functions
Prompt Press ENTER to continue
Prompt ===============================================================================

@function/tets.sql;
@function/test2.sql;

----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Types
Prompt Press ENTER to continue
Prompt ===============================================================================

@type/test.sql;
@type/checks.sql;
@type/vx_type.sql;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Add/Alter Apex
Prompt Press ENTER to continue
Prompt ===============================================================================

@apex/f102_page_34.sql;
----------------------------------------------------------------------------------
set termout on;
Prompt
prompt
Prompt ===============================================================================
Prompt Wrapping Code
Prompt Press ENTER to continue
Prompt ===============================================================================

Begin
VX_WRAPPER.Wrap(i_name => VARCHAR_TABLE( 'test',  'vx_dup',  'vx_interconnect', 'tets','test2',
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
Prompt Patch Complete, see log file PATCH3465.log for details
Prompt Press ENTER To Exit

Spool Off;