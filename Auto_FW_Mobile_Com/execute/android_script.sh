export PATH='/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Users/nhan.nguyen/Library/Android/SDK/tools:/Users/nhan.nguyen/Library/Android/SDK/platform-tools:/Users/nhan.nguyen/Documents/maven-3.5.2/bin:/Users/nhan.nguyen/.rvm/bin'
open -a /Applications/Genymotion.app/Contents/MacOS/player.app --args --vm-name 87624a16-95e0-46d5-a624-612f06c3ca3e
#!/bin/bash
function check()
{
	echo "Loading ..."
	echo "Sleep for $1 seconds"
	sleep $1
	exit 0
}
check $1 &
bPid=$!
wait $bPid && echo "Start Appium now" || echo "Can't start Appium"
/usr/local/bin/node /usr/local/bin/appium --address $2 --port $3