# Android-wear-os-sensor-bluetoothSocket

안드로이드 OS 기반의 wearable에서 필요한 센서 데이터를 다른 기기에 전달하는 방법 - bluetooth socket 사용

#### Android 공식에서 권장하는 방법은 아님.
#### MessageClient나 DataClient Api를 사용을 권장.

#### 앱 실행 필수 사항
- 센서 데이터를 받을 수 있는 서버가 필요함(ex - android 휴대폰)
- 앱 실행 전 서버는 블루투스 소켓을 먼저 열어줘야 한다.
