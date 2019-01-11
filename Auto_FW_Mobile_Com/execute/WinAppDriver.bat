@ECHO OFF
cd "C:\Program Files (x86)\Windows Application Driver\"
ECHO Starting WinAppDriver ...
START WinAppDriver.exe
ECHO Started WinAppDriver
ECHO ...
ECHO Starting Appium ...
appium --address %1 --port %2 --no-reset --local-timezone
ping 127.0.0.1 -n 6 > nul
ECHO Started Appium!
GOTO :EOF
