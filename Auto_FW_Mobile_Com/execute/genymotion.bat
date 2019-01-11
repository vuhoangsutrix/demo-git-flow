rem cd C:\Program Files\Genymobile\Genymotion
rem player --vm-name %1

@ECHO OFF
cd C:\Program Files\Genymobile\Genymotion\
ECHO Starting Genymotion ...
START player --vm-name %1
ping 127.0.0.1 -n 30 > nul
ECHO Started Genymotion
ECHO ...
ECHO Starting Appium ...
appium --address %2 --port %3 --no-reset --local-timezone
ECHO Started Appium!
GOTO :EOF
