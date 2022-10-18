package above.azure

/**
 * Templates For Project Testcases Statistics
 * @author akudin
 */

public class AzureDevOpsStatsTemplates {

	static userDups = """
</table>
<table style="width: 100%; border-color: #FFFFFF;" border="0" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td colspan="2"><strong>%u_name</strong></td>
</tr>
%dups
</tbody>
</table>
"""

	static userRow = """
<tr>
<td style="width: 33%;">%name</td>
<td style="width: 33%;text-align: center;">%created</td>
<td style="width: 33%;text-align: center;">%assigned</td>
</tr>
"""

	static noStepsRow = """
<tr>
<td>%name</td>
<td style="text-align: center;">%count</td>
<td>%tcs</td>
</tr>
"""

	static body = """
<table style="width: 100%; border-color: #FFFFFF;" border="0" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td><span style="color: #008000; font-family: Arial; font-size: 20pt;">%project</span><br /><span style="font-family: Arial;">TFS Testcases Report on %date</span></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
</tbody>
</table>
<table style="width: 100%; border-color: #eeffee;" border="1" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td colspan="12">Testcases</td>
</tr>
<tr>
<td style="width: 8%;"><strong>Total</strong></td>
<td style="width: 8%;"><strong>100% Duplicated Titles</strong></td>
<td style="width: 8%;"><strong>Outcome Changed After 6/28/2020</strong></td>
<td style="width: 8%;"><strong>No Any Updates Since 1/1/${(new Date().format('yyyy').toInteger()-1).toString()}</strong></td>
<td style="width: 8%;"><strong>Were Updated After 1/1/${(new Date().format('yyyy').toInteger()-1).toString()}</strong></td>
<td style="width: 8%;"><strong>No Steps</strong></td>
<td style="width: 8%;"><strong>No Steps And Not Linked</strong></td>
<td style="width: 8%;"><strong>Not Linked</strong></td>
<td style="width: 8%;"><strong>Closed</strong></td>
<td style="width: 8%;"><strong>Deleted</strong></td>
<td style="width: 8%;"><strong>Regression Candidates</strong></td>
<td style="width: 8%;"><strong>Automated</strong></td>
</tr>
<tr>
<td style="width: 8%;text-align: center;">%total</td>
<td style="width: 8%;text-align: center;">%100dup</td>
<td style="width: 8%;text-align: center;">%ran</td>
<td style="width: 8%;text-align: center;">%no_updates</td>
<td style="width: 8%;text-align: center;">%updated</td>
<td style="width: 8%;text-align: center;">%no_steps</td>
<td style="width: 8%;text-align: center;">%no_st_not_linked</td>
<td style="width: 8%;text-align: center;">%not_linked</td>
<td style="width: 8%;text-align: center;">%closed</td>
<td style="width: 8%;text-align: center;">%deleted</td>
<td style="width: 8%;text-align: center;">%regression</td>
<td style="width: 8%;text-align: center;">%automated</td>
</tr>
</tbody>
</table>

<table style="width: 100%; border-color: #FFFFFF;" border="0" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td><img src=\"%years1\" width="100%" /></td>
</tr>
<tr>
<td><img src=\"%years2\" width="100%" /></td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
</tbody>
</table>

<table style="width: 100%; border-color: #eeffee;" border="1" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td colspan="3">All Testcases By Users</td>
</tr>
<tr>
<td style="width: 33%;"><strong>Name</strong></td>
<td style="width: 33%;"><strong>Created By</strong></td>
<td style="width: 33%;"><strong>Assigned To</strong></td>
</tr>
%users_counts
</tbody>
</table>

</table>
<table style="width: 100%; border-color: #FFFFFF;" border="0" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td>&nbsp;</td>
</tr>
</tbody>
</table>

<table style="width: 100%; border-color: #eeffee;" border="1" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td colspan="3">No Steps Testcases By Assigned Users</td>
</tr>
<tr>
<td style="width: 33%;"><strong>Name</strong></td>
<td style="width: 33%;"><strong>Count</strong></td>
<td style="width: 33%;"><strong>Testcases</strong></td>
</tr>
%users_no_steps
</tbody>
</table>

</table>
<table style="width: 100%; border-color: #FFFFFF;" border="0" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td>&nbsp;</td>
</tr>
</tbody>
</table>

<table style="width: 100%; border-color: #eeffee;" border="1" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td colspan="3">Duplicates By Assigned Users</td>
</tr>
%users_dups
</tbody>
</table>

</table>
<table style="width: 100%; border-color: #FFFFFF;" border="0" cellspacing="0" cellpadding="10">
<tbody>
<tr>
<td>&nbsp;</td>
</tr>
</tbody>
</table>
"""
}
