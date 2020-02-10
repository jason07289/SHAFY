#!/usr/bin/env bash
curl -k -L -v -b cookie.txt -d "userId=$1&userPwd=abcdefgh&idSave=on" -H "Accept: application/json, text/javascript, */*" -H "Content-Type: application/x-www-form-urlencoded" -H "Origin: https://edu.ssafy.com" -H "X-CSRF-TOKEN: $2" --cacert cacert.pem --user-agent "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36" https://edu.ssafy.com/comm/login/SecurityLoginCheck.do

