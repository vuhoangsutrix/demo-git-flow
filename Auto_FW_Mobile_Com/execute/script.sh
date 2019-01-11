export PATH='/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/bin'
# /usr/local/bin/node /usr/local/bin/appium --address 0.0.0.0 --port 4723 --full-reset
/usr/local/bin/node /usr/local/bin/appium --address $1 --port $2 --full-reset
