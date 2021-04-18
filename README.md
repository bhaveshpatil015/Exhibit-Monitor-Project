# Exhibit-Monitor-Project

Program shall do following
1.	Must monitor incoming new files in a given folder every 30 seconds
2.	Every new file must be checked for its valid name, its in time arrival(should be received before given time), and its duplicity.
3.	If any of the checks fail, file must be deleted. 
4.	If all the 3 checks above are passed, then file should be processed using following rules. 
5.	
a.	Every line in file must have comma separated fields(valid field names and their format should be specified in a separate format.xml file).
b.	If any line is not matching with format.xml file then that line should be written in error.txt  with file name and line number and actual data with 
	error.
c.	After validating all lines in every file, all valid records must be appended to corresponding out.txt file.

Sample Format.xml file
<file name=”A.txt”, timeToArrive=”12:30”, outfileName=”a.out.txt”>
     <format >
         <field name=”customername” maxlength=”80”>
         <field name=”address” maxlength=”120”>
         <field name=”ordervalue” maxlength=”10”>
     </format>
</file>
